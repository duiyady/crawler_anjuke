package com.duiya.start;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tttest {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		pool.shutdown();
		for(int i = 0;i<10;i++) {
			pool.execute(new Thread(new Runnable() {
				
				public void run() {
					System.out.println(Thread.currentThread().getName());
					
				}
			}));
		}
		while(true) {
			if(pool.isTerminated()) {
				break;
			}
			System.out.println("没玩");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

