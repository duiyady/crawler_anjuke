package com.duiya.start;

import com.duiya.thread.CitySearchThread;
import com.duiya.thread.DistrictSearchThread;
import com.duiya.thread.HouseSearchThread;

public class Start {
	public static void main(String[] args) {
		new CitySearchThread().start();
		new DistrictSearchThread().start();
		new HouseSearchThread().start();
	}
}
