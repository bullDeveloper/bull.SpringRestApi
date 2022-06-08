package com.bull.springboot.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.bull.springboot.application.model.Transaction;
import com.bull.springboot.application.model.TransactionSwaggerModel;
import com.bull.springboot.repository.exeption.NotUniqueException;

@Service
public class MemoryDatabaseImpl implements MemoryDatabase {

	HashMap<Long, TransactionSwaggerModel> transactionHash = new HashMap<Long, TransactionSwaggerModel>();
	List<Transaction> transactionList = new ArrayList<Transaction>();

	@PostConstruct
	private void initializeDatabaseMemory() {

		transactionHash.put((long) 10, new TransactionSwaggerModel(50000, "cars", 0));
		transactionHash.put((long) 20, new TransactionSwaggerModel(5000, "shopping", 0));
		transactionList.add(new Transaction(10, 50000, "cars", 0));
		transactionList.add(new Transaction(20, 5000, "shopping", 0));

	}

	@Override
	public List<Transaction> findAll() {
		return transactionList;
	}

	@Override
	public List<Transaction> findByType(String type) {
		return transactionList.stream().filter(transaction -> transaction.getType().equals(type))
				.collect(Collectors.toList());
	}

	@Override
	public Boolean insertTransaction(Transaction transaction) throws NotUniqueException {
		if(transactionHash.containsKey(transaction.getTransaction_id())) {
			throw new NotUniqueException("Ya existe una transaccion con el transaction_id: " + transaction.getTransaction_id());
		}else {
			transactionHash.put(transaction.getTransaction_id(), new TransactionSwaggerModel(transaction.getAmount(), transaction.getType(), transaction.getParent_id()));
			transactionList.add(transaction);
			return true;
		}
	}
}
