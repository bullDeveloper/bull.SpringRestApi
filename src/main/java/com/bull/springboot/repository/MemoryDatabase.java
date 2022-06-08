package com.bull.springboot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.bull.springboot.application.model.Transaction;

@Service
public class MemoryDatabase {

	List<Transaction> transactions = new ArrayList<Transaction>();

	@PostConstruct
	private void initializeDatabaseMemory() {

		transactions = List.of(
				new Transaction(10, 5000, "cars", 0), 
				new Transaction(10, 5000, "shopping", 0));

	}
	
	public List<Transaction> findAll() {
		return transactions;
	}
	
	public List<Transaction> findByType(String type) {
		return transactions.stream().filter(transaction -> transaction.getType().equals(type)).collect(Collectors.toList()); 
	}


}