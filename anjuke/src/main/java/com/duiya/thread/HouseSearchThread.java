package com.duiya.thread;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.select.Elements;

import com.duiya.mapper.HouseMapper;
import com.duiya.model.House;
import com.duiya.utils.DataList;
import com.duiya.utils.ExcisionUtil;
import com.duiya.utils.HttpUtils;
import com.duiya.utils.MyBatisUtil;

public class HouseSearchThread extends Thread {
	ExecutorService pool = Executors.newFixedThreadPool(10);
	ExecutorService pool1 = Executors.newFixedThreadPool(5);
	@Override
	public void run() {
		boolean flag = true;
		while (flag) {
			String url = null;
			synchronized (DataList.districtList) {
				try {
					url = DataList.districtList.getFirst();
					DataList.districtList.removeFirst();
				} catch (Exception e) {
				}
				if (url == null) {
					if (DataList.districtFlag == false) {
						flag = false;
					} else {
						try {
							DataList.districtList.wait(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			pool.execute(new SearchUtil(url));
		}
		pool.shutdown();
		while (true) {
			if (pool.isTerminated()) {
				System.out.println("楼盘搜索完成");
				break;
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pool1.shutdown();
		while (true) {
			if (pool1.isTerminated()) {
				System.out.println("楼盘添加完成");
				break;
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("完成");
	}
	class SearchUtil extends Thread {
		private String url;

		public SearchUtil(String url) {
			this.url = url;
		}
		@Override
		public void run() {
			String baseUrl = url;
			String result = null;

			if (url != null) {
				result = HttpUtils.CreatHttpGet(url);
				if (result != null) {
					LinkedList<Elements> resultList = new LinkedList<Elements>();
					Map<String, Object> map = ExcisionUtil.roughExcisionAPage(result);// 获取当地的首页获取信息和页数
					Integer page = (Integer) map.get("page");
					resultList.add((Elements) map.get("list"));
					// 获取当地的剩下几页
					if (page != null) {
						for (; page > 1; page--) {
							String nextUrl = baseUrl + "p" + page + "/";
							result = HttpUtils.CreatHttpGet(nextUrl);
							resultList.add(ExcisionUtil.roughExcisionNPage(result));
						}
					}
					// 添加信息
					Iterator<Elements> it = resultList.iterator();
					while (it.hasNext()) {
						Elements ele = it.next();
						if (ele != null) {
							LinkedList<House> houseList = ExcisionUtil.exactExcision(ele);
							pool1.execute(new AddUtil(houseList));
						}
					}
				}
			}
		}
	}
	
	class AddUtil extends Thread{
		private LinkedList<House> houseList;
		public AddUtil(LinkedList<House> houseList) {
			this.houseList = houseList;
		}
		@Override
		public void run() {
			SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
			HouseMapper mapper = sqlSession.getMapper(HouseMapper.class);
			Iterator<House> it = houseList.iterator();
			while(it.hasNext()) {
				House house = it.next();
				mapper.addHouse(house);
			}
			sqlSession.close();
		}
	}
}
