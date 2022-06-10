package com.bull.springboot.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
@JsonPropertyOrder({"transaction_id", "amount", "type", "parent_id"})
public class Transaction extends TransactionSwaggerModel {

	@JsonProperty("transaction_id")
	private long transaction_id;


	public Transaction(long transaction_id, double amount, String type, long parent_id) {
		super();
		this.transaction_id = transaction_id;
		this.setAmount(amount);
		this.setType(type);
		this.setParent_id(parent_id);
	}
	
	public Transaction() {
		super();
	}
	
	public long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

}
