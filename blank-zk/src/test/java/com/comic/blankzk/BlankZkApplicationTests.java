package com.comic.blankzk;

import com.comic.blankzk.util.ZkLockUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlankZkApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void test(){
		ExecutorService threadpool = Executors.newCachedThreadPool();
		System.out.println("开始购买...");
		for (int i = 0; i <2 ; i++) {
			threadpool.execute(new Runnable() {
				public void run() {
					System.out.println("我是线程:"+Thread.currentThread().getName()+"我开始抢购了...");
					ZkLockUtil.getLock();
					System.out.println(Thread.currentThread().getName()+":我正在疯狂的剁手购买中...");
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+":我买完了,有请下一位...");
					try {
						ZkLockUtil.addWatcher2Path("/zk-lock");
						System.out.println("添加完毕...");
						ZkLockUtil.release("/zk-lock/distribute-lock");
						System.out.println("释放完毕...");
						Thread.sleep(10);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	@Test
	public void test2(){
		CountDownLatch latch = new CountDownLatch(2);

		new Thread(){
			public void run() {
				try {
					System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
					Thread.sleep(1000);
					System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		new Thread(){
			public void run() {
				try {
					System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
					Thread.sleep(1000);
					System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		try {
			System.out.println("等待2个子线程执行完毕...");
			latch.await();
			System.out.println("2个子线程已经执行完毕");
			System.out.println("继续执行主线程");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
