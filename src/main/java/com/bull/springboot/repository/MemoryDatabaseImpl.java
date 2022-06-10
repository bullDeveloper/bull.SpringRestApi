package com.bull.springboot.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.bull.springboot.application.model.Transaction;
import com.bull.springboot.application.model.TransactionSwaggerModel;
import com.bull.springboot.repository.exeption.CustomException;

@Service
public class MemoryDatabaseImpl implements MemoryDatabase {

	//Hash por transaction_id
	HashMap<Long, TransactionSwaggerModel> transactionHash = new HashMap<Long, TransactionSwaggerModel>();
	//Hash por parent_id
	HashMap<Long, List<Transaction>> transactionHashByParentId = new HashMap<Long, List<Transaction>>();
	//Lista de entrada de datos
	List<Transaction> transactionList = new ArrayList<Transaction>();

	@PostConstruct
	private void initializeDatabaseMemory() {
		
		transactionHash.put((long) 10, new TransactionSwaggerModel(5000, "cars", 0));
		transactionHash.put((long) 11, new TransactionSwaggerModel(10000, "shopping", 10));
		transactionHash.put((long) 12, new TransactionSwaggerModel(5000, "shopping", 11));
		
		//Los que tiene de parent_id = 10
		List<Transaction> transactionParent1 = new ArrayList<Transaction>();
		transactionParent1.add(new Transaction(11, 10000, "shopping", 10));
		transactionHashByParentId.put((long) 10,transactionParent1);
		
		//Los que tienen de parent_id = 11
		List<Transaction> transactionParent2 = new ArrayList<Transaction>();
		transactionParent2.add(new Transaction(12, 5000, "shopping", 11));
		transactionHashByParentId.put((long) 11,transactionParent2);
		
		transactionList.add(new Transaction(10, 5000, "cars", 0));
		transactionList.add(new Transaction(11, 10000, "shopping", 10));
		transactionList.add(new Transaction(12, 5000, "shopping", 11));

	}

	@Override
	public List<Long> findAll() {
		return transactionList.stream().map(Transaction::getTransaction_id).collect(Collectors.toList());
	}

	@Override
	public List<Transaction> findAllDetails() {
		return transactionList;
	}

	@Override
	public List<Long> findByType(String type) {
		return transactionList.stream().filter(transaction -> transaction.getType().equals(type))
				.map(Transaction::getTransaction_id).collect(Collectors.toList());
	}

	@Override
	public Boolean insertTransaction(Transaction transaction) throws CustomException {
		if (transactionHash.containsKey(transaction.getTransaction_id())) {
			throw new CustomException(
					"Ya existe una transaccion con el transaction_id: " + transaction.getTransaction_id());
		} else {
			transactionHash.put(transaction.getTransaction_id(), new TransactionSwaggerModel(transaction.getAmount(),
					transaction.getType(), transaction.getParent_id()));
			transactionList.add(transaction);
			insertHashParent(transaction);
			return true;
		}
	}

	@Override
	public Boolean updateOrInsertTransaction(Transaction transaction) {
		if(transactionHash.containsKey(transaction.getTransaction_id())) {
			updateHashParent(transaction);
			transactionHash.get(transaction.getTransaction_id())
				.update(transaction.getAmount(), transaction.getType(), transaction.getParent_id());
			
			transactionList.stream()
				.filter(t -> t.getTransaction_id()==transaction.getTransaction_id())
				.collect(Collectors.toList()).get(0)
				.update(transaction.getAmount(), transaction.getType(), transaction.getParent_id());;

			return true;
		}else {
			transactionHash.put(transaction.getTransaction_id(), new TransactionSwaggerModel(transaction.getAmount(), transaction.getType(), transaction.getParent_id()));
			transactionList.add(transaction);
			insertHashParent(transaction);
			return false;
		}
	}
	
	
	private Double transitiveRelationsTree(List<Transaction> listChild) {
		for (Transaction transaction : listChild) {
			long tid = transaction.getTransaction_id();
			if(tid!=0) {
				Long key = Long.valueOf(tid);
				if(transactionHashByParentId.containsKey(key)) {
					List<Transaction> listChilds = transactionHashByParentId.get(key);
					Double sumChilds = listChild.stream()
							.map(x -> x.getAmount())
							.collect(Collectors.summingDouble(Double::doubleValue));
					return sumChilds + transitiveRelationsTree(listChilds);
				} else {
					Double sumChilds = listChild.stream()
							.map(x -> x.getAmount())
							.collect(Collectors.summingDouble(Double::doubleValue));
					return sumChilds;
				}
			} else 
				return Double.valueOf(0);
		}
		return null;		
		
	}
	

	
	@Override
	public Double transitiveRelations(Long transaction_id) {
		try {
			if(transactionHash.containsKey(transaction_id))
				return transactionHash.get(transaction_id).getAmount() + transitiveRelationsTree(transactionHashByParentId.get(transaction_id));
			else
				return Double.valueOf(0);
					
		} catch (Exception e) {
			return Double.valueOf(0);
		}
	}

	private void insertHashParent(Transaction transaction) {
		if(transaction.getParent_id()!=0) {
			if (transactionHashByParentId.containsKey(transaction.getParent_id()))
				transactionHashByParentId.get(transaction.getParent_id()).add(transaction);
			else {
				ArrayList<Transaction> list = new ArrayList<Transaction>();
				list.add(transaction);
				transactionHashByParentId.put(transaction.getParent_id(), list);
			}
		}
	}
	
	private void updateHashParent(Transaction transaction) {
		long parent_id = transactionHash.get(transaction.getTransaction_id()).getParent_id();
		List<Transaction> listChilds = transactionHashByParentId.get(Long.valueOf(parent_id));
	    Predicate<Transaction> condition = employee -> employee.getTransaction_id()==transaction.getTransaction_id();
	    if(listChilds!=null)
	    	listChilds.removeIf(condition);
	    insertHashParent(transaction);
	}

}
