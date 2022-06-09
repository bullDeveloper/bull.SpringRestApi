package com.bull.springboot.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.bull.springboot.application.model.Transaction;
import com.bull.springboot.application.model.TransactionSwaggerModel;
import com.bull.springboot.repository.exeption.CustomException;

@Service
public class MemoryDatabaseImpl implements MemoryDatabase {

	HashMap<Long, TransactionSwaggerModel> transactionHash = new HashMap<Long, TransactionSwaggerModel>();
	List<Transaction> transactionList = new ArrayList<Transaction>();

	@PostConstruct
	private void initializeDatabaseMemory() {

		transactionHash.put((long) 10, new TransactionSwaggerModel(50000, "cars", 20));
		transactionHash.put((long) 20, new TransactionSwaggerModel(5000, "shopping", 10));
		transactionList.add(new Transaction(10, 50000, "cars", 20));
		transactionList.add(new Transaction(20, 5000, "shopping", 10));

	}

	@Override
	public List<Long> findAll() {
		return transactionList.stream()
				.map(Transaction::getTransaction_id)
				.collect(Collectors.toList());
	}

	@Override
	public List<Transaction> findAllDetails() {
		return transactionList;
	}
	
	@Override
	public List<Long> findByType(String type) {
		return transactionList.stream()
				.filter(transaction -> transaction.getType().equals(type))
				.map(Transaction::getTransaction_id)
				.collect(Collectors.toList());
	}

	@Override
	public Boolean insertTransaction(Transaction transaction) throws CustomException {
		if(transactionHash.containsKey(transaction.getTransaction_id())) {
			throw new CustomException("Ya existe una transaccion con el transaction_id: " + transaction.getTransaction_id());
		}else {
			transactionHash.put(transaction.getTransaction_id(), new TransactionSwaggerModel(transaction.getAmount(), transaction.getType(), transaction.getParent_id()));
			transactionList.add(transaction);
			return true;
		}
	}

	@Override
	public Boolean updateOrInsertTransaction(Transaction transaction) {
		if(transactionHash.containsKey(transaction.getTransaction_id())) {
			transactionHash.put(transaction.getTransaction_id(), new TransactionSwaggerModel(transaction.getAmount(), transaction.getType(), transaction.getParent_id()));
			
			Transaction transctionToUpdate = transactionList.stream().filter(t -> t.getTransaction_id()==transaction.getTransaction_id()).collect(Collectors.toList()).get(0);
			transctionToUpdate.setAmount(transaction.getAmount());
			transctionToUpdate.setType(transaction.getType());
			transctionToUpdate.setParent_id(transaction.getParent_id());
			return true;
			
		}else {
			transactionHash.put(transaction.getTransaction_id(), new TransactionSwaggerModel(transaction.getAmount(), transaction.getType(), transaction.getParent_id()));
			transactionList.add(transaction);
			return false;
		}
	}

	@Override
	public Double groupByParentId(Long parent_id) {
		try{
			return transactionList.stream().filter(t -> t.getParent_id()==parent_id).mapToDouble(Transaction::getAmount).sum();
		}catch(Exception e) {
			return Double.valueOf(0);
		}
	}


}
