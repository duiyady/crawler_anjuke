package com.duiya.start;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 为什么在最后那个类里面的count++方法里面加入那个输入语句线程最后就停不了
 * @author duiya
 *
 */
public class Test {
	public final static CountDownLatch aa = new CountDownLatch(5);
	public final static CountDownLatch bb = new CountDownLatch(5); 
	public static void main(String[] args) {
		ExecutorService pool1 = Executors.newFixedThreadPool(5);
		ExecutorService pool2 = Executors.newFixedThreadPool(5);
		
//		ArrayList<Callable<Integer>> callers1 = new ArrayList<Callable<Integer>>(); 
//		ArrayList<Callable<Integer>> callers2 = new ArrayList<Callable<Integer>>(); 
		for(int i = 0;i<5;i++) {
			pool1.execute(new ThreadAA());
			pool2.execute(new ThreadBB());
		}
//		try {
//			pool1.invokeAll(callers1);
//			pool2.invokeAll(callers2);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		try {
			Test.aa.await();
			Test.bb.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("主线程完成");
		pool1.shutdown();
		pool2.shutdown();
	}
}
class ThreadAA extends Thread{
	@Override
	public void run() {
		for(int i = 0;i<10;i++) {
			synchronized (DataList.list) {
				DataList.list.add("=====信息：" + i + Thread.currentThread().getName());
				if(DataList.list.size() == 1) {
					//System.out.println(Thread.currentThread().getName() + "唤醒所有");
					DataList.list.notifyAll();
				}
			}
		}
		Test.aa.countDown();
		DataList.countAdd();
	}
}

class ThreadBB extends Thread{
	@Override
	public void run() {
		
		while(DataList.count < 5) {
			synchronized (DataList.list) {
				String str = null;
				try {
					str = DataList.list.getFirst();
				}catch (Exception e) {
					// TODO: handle exception
				}
				if(str != null) {
					System.out.println(Thread.currentThread().getName() + "获取" + str);
					DataList.list.removeFirst();
				}else {
					try {
						DataList.list.wait(1000);//这里设置等待时间是防止最后一直等待
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		Test.bb.countDown();
	}
}

class DataList{
	private static ReentrantLock look = new ReentrantLock();
	private static Condition condition = look.newCondition();
	public static LinkedList<String> list = new LinkedList<String>(); 
	public static Integer count = 0;
	public static void countAdd() {
		try {
			look.lock();
			DataList.count++;
		}catch(Exception e){
			
		}finally {
			look.unlock();
		}
	}
}