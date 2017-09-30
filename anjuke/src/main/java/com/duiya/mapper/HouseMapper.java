package com.duiya.mapper;

import org.apache.ibatis.annotations.Insert;

import com.duiya.model.House;

public interface HouseMapper {
	 @Insert("insert into house values(null,#{name}, #{address}, #{state}, #{describe}, #{price})")
	 public int addHouse(House house);
}
