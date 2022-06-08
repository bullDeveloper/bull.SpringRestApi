package com.bull.springboot.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bull.springboot.application.model.Transaction;
import com.bull.springboot.application.model.TransactionSwaggerModel;
import com.bull.springboot.repository.MemoryDatabaseImpl;
import com.bull.springboot.repository.exeption.CustomException;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/transactions")
public class ApiRestController {

	@Autowired
	MemoryDatabaseImpl memoryDatabase;
	
	@GetMapping(value = {"/types/", "/types"})
	@ApiOperation(value="Encuentra todas las transacciones",
			notes="Provee servicio de busqueda de todas las transacciones",
			response=Long.class)
	public ResponseEntity<?> findAllTransactions() {
		return ResponseEntity.status(HttpStatus.OK).body(memoryDatabase.findAll());
	}
	
	@GetMapping(value = "/types/{types}")
	@ApiOperation(value="Encuentra transacciones por tipo",
			notes="Provee servicio de busqueda de transacciones por tipo, si el tipo no se encuentra dentro de la lista de transacciones en memoria devolvera una lista vacia, el parametro por definicion no puede ser nulo y es requerido",
			response=Long.class)
	public ResponseEntity<?> findTransactions(@PathVariable (name ="types", required = false) String types) {
		log.info("Response received. Params: types {}", types);
		return StringUtils.isEmpty(types)?ResponseEntity.status(HttpStatus.OK).body(memoryDatabase.findAll()):ResponseEntity.status(HttpStatus.OK).body(memoryDatabase.findByType(types));
	}
	
	@PostMapping(value = "/{transaction_id}", 
		    consumes=MediaType.APPLICATION_JSON_VALUE, 
		    produces = MediaType.APPLICATION_JSON_VALUE )
	@ApiOperation(value="Da de alta una transaccion",
	notes="Provee servicio de alta de transaccion, el transaction_id se pone como parametro de tipo Path y el mismo no debe existir dentro de la lista de transaccion pre existentes, mientras que se envia un Body con el resto de la informacion",
	response=Transaction.class)
	public ResponseEntity<?> createUser(@PathVariable (required = true) long transaction_id, @RequestBody TransactionSwaggerModel transactionDetails)    {
		Transaction transaction= new Transaction(transaction_id, transactionDetails.getAmount(), transactionDetails.getType(), transactionDetails.getParent_id());
		try {
			memoryDatabase.insertTransaction(transaction);
			return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
		} catch(CustomException e) {
			return ResponseEntity.status(HttpStatus.FOUND).body(e.getMessage().toCharArray());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e);
		}
		
	}
	
	@PutMapping(value = "/{transaction_id}", 
		    consumes=MediaType.APPLICATION_JSON_VALUE, 
		    produces = MediaType.APPLICATION_JSON_VALUE )
	@ApiOperation(value="Actualiza una transaccion",
	notes="Provee servicio de actualizacion de transaccion, el transaction_id se pone como parametro de tipo Path y el mismo debe existir dentro de la lista de transaccion pre existentes, mientras que se envia un Body con el resto de la informacion",
	response=Transaction.class)
	public ResponseEntity<?> updateUser(@PathVariable (required = true) long transaction_id, @RequestBody TransactionSwaggerModel transactionDetails)    {
		Transaction transaction= new Transaction(transaction_id, transactionDetails.getAmount(), transactionDetails.getType(), transactionDetails.getParent_id());
		try {
			memoryDatabase.updateTransaction(transaction);
			return ResponseEntity.status(HttpStatus.OK).body(transaction);
		} catch(CustomException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage().toCharArray());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e);
		}
		
	}
	
	@GetMapping(value = "/sum/{parent_id }")
	@ApiOperation(value="Encuentra transacciones por parent_id y realiza la suma de los amount",
			notes="Provee servicio de agrupamiento de transacciones por parent_id, realizando la suma de los amount",
			response=Long.class)
	public ResponseEntity<?> sumTransactions(@PathVariable (name ="parent_id ", required = true) Long parent_id ) {
		log.info("Response received. Params: types {}", parent_id);
		return ResponseEntity.status(HttpStatus.OK).body("sum:" + memoryDatabase.groupByParentId(parent_id));
	}
}
