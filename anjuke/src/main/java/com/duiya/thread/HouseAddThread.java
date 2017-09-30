//package com.duiya.thread;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.apache.ibatis.session.SqlSession;
//
//import com.duiya.mapper.HouseMapper;
//import com.duiya.model.House;
//import com.duiya.utils.DataList;
//import com.duiya.utils.MyBatisUtil;
//
//public class HouseAddThread extends Thread {
//	@Override
//	public void run() {
//		ExecutorService pool = Executors.newFixedThreadPool(10);
//		boolean flag = true;
//		while (flag) {
//			House house = null;
//			synchronized (DataList.houseList) {
//				try {
//					house = DataList.houseList.getFirst();
//					DataList.houseList.removeFirst();
//				} catch (Exception e) {
//				}
//				if (house == null) {
//					if (DataList.houseFlag == false) {
//						flag = false;
//					} else {
//						try {
//							DataList.houseList.wait(1000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//			pool.execute(new AddUtil(house));
//		}
//		pool.shutdown();
//		while(true){  
//            if(pool.isTerminated()){
//        		System.out.println("房源添加完成");
//                break;
//            }
//            try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}    
//        } 
//	}
//
//	class AddUtil extends Thread {
//		private House house;
//
//		public AddUtil(House house) {
//			this.house = house;
//		}
//
//		@Override
//		public void run() {
//			SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
//			HouseMapper mapper = sqlSession.getMapper(HouseMapper.class);
//			if(house != null) {
//				System.out.println(Thread.currentThread().getName() + "添加" + house);
//				mapper.addHouse(house);
//			}
//			sqlSession.close();
//		}
//	}
//}
