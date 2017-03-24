package com.wisdom.takeout.module.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_user")
public class User {
	@DatabaseField(id = true) //此行是特殊的，是id;如果是id = true（手动id);
	// generatedId = true(自动id), 类似auto icrement效果
	private int id;
	@DatabaseField(columnName = "name")
	private String name;
	@DatabaseField(columnName = "balance")
	private float balance;
	@DatabaseField(columnName = "discount")
	private int discount;
	@DatabaseField(columnName = "integral")
	private int integral;
	@DatabaseField(columnName = "phone")
	private String phone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
