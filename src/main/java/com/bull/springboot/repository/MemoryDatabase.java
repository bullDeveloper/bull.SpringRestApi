package com.bull.springboot.repository;

import java.util.List;

import com.bull.springboot.application.model.Transaction;
import com.bull.springboot.repository.exeption.CustomException;

public interface MemoryDatabase {

	public List<Long> findAll();
	public List<Transaction> findAllDetails();
	public List<Long> findByType(String type);
	public Boolean insertTransaction(Transaction transaction) throws CustomException;
	public Boolean updateOrInsertTransaction(Transaction transaction);
	public Double transitiveRelations(Long transaction_id);

}