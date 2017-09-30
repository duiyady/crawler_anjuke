package com.duiya.start;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试静态房方法
 * @author duiya
 *
 */
public class Test2 {
	public static void main(String[] args) {
		new DaThread().start();
		new DbThread().start();
	}
}

class DaThread extends Thread{
	@Override
	public void run() {
		Dataa.add();
	}
}
class DbThread extends Thread{
	@Override
	public void run() {
		Dataa.dda();
	}
}

class Dataa{
	private static ReentrantLock look = new ReentrantLock();
	private static Condition condition = look.newCondition();
	private static ReentrantLock look2 = new ReentrantLock();
	private static Condition condition2 = look2.newCondition();
	public static void add() {
		try{
			look.lock();
		//	System.out.println("等待前");
		//	condition.await();
		//	System.out.println("醒来后");
			for(int i = 0;i<20;i++) {
				
				System.out.println("进来了add");
				System.out.println("add测试");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			look.unlock();
		}
	}
	public static void dda() {
		try{
			look2.lock();
			for(int i = 0;i<20;i++) {
				System.out.println("进来了dda==========");
				System.out.println("dda测试===========");
			}
			//condition.signalAll();
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			look2.unlock();
		}
	}
}
