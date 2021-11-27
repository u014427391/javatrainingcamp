package com.example.concurrent.collaboration;

import java.util.Random;
import java.util.concurrent.Phaser;

public class MultipleStartTogetherPhserDemo {

	Random rd = new Random();
	int bound = 5000;

	public void step1Task() throws InterruptedException {
		// 经过一段时间后，到达公司
		Thread.sleep(rd.nextInt(bound));
		System.out.println(
				"员工【" + Thread.currentThread().getName() + "】" + "到达公司！");
	}

	public void step2Task() throws InterruptedException {
		System.out.println(
				"员工【" + Thread.currentThread().getName() + "】" + "出发去公园玩。。。");
		// 玩了一段时间后，到公园门口集合
		Thread.sleep(rd.nextInt(bound));
		System.out.println(
				"员工【" + Thread.currentThread().getName() + "】" + "完成公园游玩！");
	}

	public void step3Task() throws InterruptedException {
		System.out.println(
				"员工【" + Thread.currentThread().getName() + "】" + "出发去餐厅。。。。。。");
		// 玩了一段时间后，到公园门口集合
		Thread.sleep(rd.nextInt(bound));
		System.out.println(
				"员工【" + Thread.currentThread().getName() + "】" + "到达餐厅！");
	}

	public void step4Task() throws InterruptedException {
		System.out.println(
				"员工【" + Thread.currentThread().getName() + "】" + "开始用餐。。。。。。");
		// 玩了一段时间后，到公园门口集合
		Thread.sleep(rd.nextInt(bound));
		System.out.println(
				"员工【" + Thread.currentThread().getName() + "】" + "回家了！");
	}

	public static void main(String[] args) {

		// 创建阶段协同器对象，重写了onAdvance方法，增加阶段到达处理逻辑
		final Phaser ph = new Phaser() {
			protected boolean onAdvance(int phase, int registeredParties) {
				int staffs = registeredParties - 1;
				switch (phase) {
				case 0:
					System.out.println("大家都到公司了，出发去公园！人数：" + staffs);
					break;
				case 1:
					System.out.println("大家都到公园大门，出发去餐厅！人数：" + staffs);
					break;
				case 2:
					System.out.println("大家都到餐厅了，开始用餐！人数：" + staffs);
					break;
				}
				// 判断是否只剩主线程一个参与者，是，则返回true,阶段协同器终止。
				return registeredParties == 1;
			}
		};

		// 增加一个任务数，用来让主线程全程参与
		ph.register();

		final MultipleStartTogetherPhserDemo job = new MultipleStartTogetherPhserDemo();

		// 让3个全程参与的线程加入
		for (int i = 0; i < 3; i++) {
			// 增加参与任务数
			ph.register();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						job.step1Task();
						ph.arriveAndAwaitAdvance();
						job.step2Task();
						System.out.println(
								"员工【" + Thread.currentThread().getName() + "】"
										+ "到达公园大门集合。");
						ph.arriveAndAwaitAdvance();
						job.step3Task();
						ph.arriveAndAwaitAdvance();
						job.step4Task();
						// 完成了，注销离开
						ph.arriveAndDeregister();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

		// 让两个不参加聚餐的员工加入
		for (int i = 0; i < 2; i++) {
			// 增加参与任务数
			ph.register();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						job.step1Task();
						ph.arriveAndAwaitAdvance();
						job.step2Task();
						System.out.println(
								"员工【" + Thread.currentThread().getName() + "】"
										+ "回家了！");
						// 完成了，注销离开
						ph.arriveAndDeregister();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

		while (!ph.isTerminated()) {
			int phaser = ph.arriveAndAwaitAdvance();

			if (phaser == 2) { // 到了去餐厅的阶段，让只参加晚上聚餐的人加入
				for (int i = 0; i < 4; i++) {
					// 增加参与任务数
					ph.register();
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								job.step3Task();
								ph.arriveAndAwaitAdvance();
								job.step4Task();
								// 完成了，注销离开
								ph.arriveAndDeregister();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			}

		}

	}
}
