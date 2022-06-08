package com.bull.springboot.repository;

import java.util.List;

import com.bull.springboot.application.model.Transaction;
import com.bull.springboot.repository.exeption.NotUniqueException;

public interface MemoryDatabase {

	public List<Transaction> findAll();
	public List<Transaction> findByType(String type);
	public Boolean insertTransaction(Transaction transaction) throws NotUniqueException;

}