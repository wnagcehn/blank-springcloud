package com.comic.blankredis;

import com.comic.blankredis.util.RedisLockUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlankRedisApplicationTests {

	@Test
	public void test() {
		//要创建的线程的数量
		CountDownLatch looker = new CountDownLatch(1);
		CountDownLatch latch = new CountDownLatch(10);
		final String key = "lockKey";
		for(int i=0; i < latch.getCount(); i++){
			Jedis jedis = new Jedis();
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

}
