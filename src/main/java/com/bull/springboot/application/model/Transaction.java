package com.bull.springboot.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {

	private long transaction_id;
	private double amount;
	private String type;
	private long parent_id;

	public Transaction(long transaction_id, double amount, String type, long parent_id) {
		super();
		this.transaction_id = transaction_id;
		this.amount = amount;
		this.type = type;
		this.parent_id = parent_id;
	}
	
}
