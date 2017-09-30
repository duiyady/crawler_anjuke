package com.duiya.model;

public class House {
	private String name;
	private String address;
	private String state;
	private String describe;
	private String price;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAdddress() {
		return address;
	}
	/**
	 * @param adddress the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the describe
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * @param describe the describe to set
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	public House(String name, String address, String state, String describe, String price) {
		super();
		this.name = name;
		this.address = address;
		this.state = state;
		this.describe = describe;
		this.price = price;
	}
	public House() {
		super();
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "House [name=" + name + ", address=" + address + ", state=" + state + ", describe=" + describe
				+ ", price=" + price + "]";
	}
	
}
