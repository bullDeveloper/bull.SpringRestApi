package com.bull.springboot.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class TransactionSwaggerModel {

	@JsonProperty("amount")
	private double amount;
	@JsonProperty("type")
	private String type;
	@JsonProperty("parent_id")
	private long parent_id;

	public TransactionSwaggerModel(double amount, String type, long parent_id) {
		super();
		this.amount = amount;
		this.type = type;
		this.parent_id = parent_id;
	}
	
	public TransactionSwaggerModel() {
		super();
	}
	

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
}
