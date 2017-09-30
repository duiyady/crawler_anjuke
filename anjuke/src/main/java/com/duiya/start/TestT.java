package com.duiya.start;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.jsoup.select.Elements;

import com.duiya.model.House;
import com.duiya.utils.ExcisionUtil;
import com.duiya.utils.HttpUtils;

public class TestT {
	public static void main(String[] args) {
		String url = new String("https://cq.fang.anjuke.com/loupan/yubei/");
		String result = HttpUtils.CreatHttpGet(url);
		String baseUrl = url;
		LinkedList<Elements> resultList = new LinkedList<Elements>();
		Map<String, Object> map = ExcisionUtil.roughExcisionAPage(result);// 获取当地的首页获取信息和页数
		Integer page = (Integer) map.get("page");
		resultList.add((Elements) map.get("list"));
		// 获取当地的剩下几页
		if (page != null) {
			for (; page > 1; page--) {
				String nextUrl = baseUrl + "p" + page + "/";
				// System.out.println(Thread.currentThread().getName() + "开始爬取" + nextUrl + "的楼盘信息");
				result = HttpUtils.CreatHttpGet(nextUrl);
				resultList.add(ExcisionUtil.roughExcisionNPage(result));
			}
		}
		// 添加信息
		Iterator<Elements> it = resultList.iterator();
		LinkedList<House> houseList = new LinkedList<House>();
		while (it.hasNext()) {
			Elements ele = it.next();
			if (ele != null) {
				houseList.addAll(ExcisionUtil.exactExcision(ele));
			}
		}
	}
}
