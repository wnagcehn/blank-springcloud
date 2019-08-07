package com.comic.blank;

import com.comic.blank.interceptor.CacheLockInterceptor;
import com.comic.blank.service.ISeckillService;
import com.comic.blank.service.impl.SecKillServiceImpl;
import com.comic.blank.util.RedisClient;
import com.comic.blank.util.RedisLockUtil;
import com.comic.blank.util.RedisTemp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisShardInfo;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlankRedisApplicationTests {

	private static Long commidityId1 = 10000001L;
	private static Long commidityId2 = 10000002L;
	private
	RedisClient client;
	public static String HOST = "127.0.0.1";
	private JedisPool jedisPool;
	@Before
	public synchronized void  beforeTest() throws IOException {
		jedisPool = new JedisPool("127.0.0.1");
	}

	@Test
	public void test() {
		//要创建的线程的数量
		CountDownLatch looker = new CountDownLatch(1);
		CountDownLatch latch = new CountDownLatch(10);
		final String key = "lockKey";
		//这里是连接的本地地址和端口
		JedisShardInfo shardInfo = new JedisShardInfo("redis://localhost:6379");
		//这里是密码
		shardInfo.setPassword("12345");
		for(int i=0; i < latch.getCount(); i++){
			Jedis jedis = new Jedis(shardInfo);
			UUID uuid = UUID.randomUUID();
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						looker.await();
						System.out.println(Thread.currentThread().getName()+"竞争资源，获取锁");
						boolean isLock = RedisLockUtil.tryGetDistributedLock(jedis, key, uuid.toString(), 5000);
						if(isLock){
							System.out.println(Thread.currentThread().getName()+"获取到了锁，处理业务，用时3秒");
							Thread.sleep(3000);
							boolean releaseResult = RedisLockUtil.releaseDistributedLock(jedis, key, uuid.toString());
							if(releaseResult){
								System.out.println(Thread.currentThread().getName()+"业务处理完毕，释放锁");
							}
						}else {
							System.out.println(Thread.currentThread().getName()+"竞争资源失败，未获取到锁");
						}
						latch.countDown();
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			});
			thread.start();
		}

		try {
			System.out.println("准备，5秒后开始");
			Thread.sleep(5000);
			looker.countDown(); //发令
			latch.await();
			System.out.println("结束");
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Test
	public void test2() {
		int threadCount = 1000;
		int splitPoint = 500;
		CountDownLatch endCount = new CountDownLatch(threadCount);
		CountDownLatch beginCount = new CountDownLatch(1);
		SecKillServiceImpl testClass = new SecKillServiceImpl();

		Thread[] threads = new Thread[threadCount];
		//起500个线程，秒杀第一个商品
		for(int i= 0;i < splitPoint;i++){
			threads[i] = new Thread(new  Runnable() {
				public void run() {
					try {
						//等待在一个信号量上，挂起
						beginCount.await();
						//用动态代理的方式调用secKill方法
						ISeckillService proxy = (ISeckillService) Proxy.newProxyInstance(ISeckillService.class.getClassLoader(),
								new Class[]{ISeckillService.class}, new CacheLockInterceptor(testClass));
						proxy.secKill("test", commidityId1);
						endCount.countDown();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			threads[i].start();

		}
		//再起500个线程，秒杀第二件商品
		for(int i= splitPoint;i < threadCount;i++){
			threads[i] = new Thread(new  Runnable() {
				public void run() {
					try {
						//等待在一个信号量上，挂起
						beginCount.await();
						//用动态代理的方式调用secKill方法
						ISeckillService proxy = (ISeckillService) Proxy.newProxyInstance(ISeckillService.class.getClassLoader(),
								new Class[]{ISeckillService.class}, new CacheLockInterceptor(testClass));
						proxy.secKill("test", commidityId2);
						//testClass.testFunc("test", 10000001L);
						endCount.countDown();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			threads[i].start();

		}


		long startTime = System.currentTimeMillis();
		//主线程释放开始信号量，并等待结束信号量，这样做保证1000个线程做到完全同时执行，保证测试的正确性
		beginCount.countDown();

		try {
			//主线程等待结束信号量
			endCount.await();
			//观察秒杀结果是否正确
			System.out.println(SecKillServiceImpl.inventory.get(commidityId1));
			System.out.println(SecKillServiceImpl.inventory.get(commidityId2));
			System.out.println("error count" + CacheLockInterceptor.ERROR_COUNT);
			System.out.println("total cost " + (System.currentTimeMillis() - startTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test3() {
		RedisTemp redisTemp = new RedisTemp();
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				try {
					redisTemp.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}

}
