package com.duiya.start;

import java.util.LinkedList;
import java.util.List;

public class Test3 {
	public static List<String> alist = new LinkedList<String>();
	public static void main(String[] args) {
		new DcThread().start();
		new DdThread().start();
	}
}

class DcThread extends Thread{
	@Override
	public void run() {
		synchronized (Test3.alist) {
			new Thread(new Runnable() {
				
				public void run() {
					for(int a=0;a<20;a++) {
						System.out.println("hehe");
					}
					
				}
			}).start();
new Thread(new Runnable() {
				
				public void run() {
					for(int a=0;a<20;a++) {
						System.out.println("哈哈哈");
					}
					
				}
			}).start();
		}
	}
}
class DdThread extends Thread{
	@Override
	public void run() {
		try {
			Thread.currentThread().sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (Test3.alist) {
			System.out.println("得到了");
		}
	}
}

