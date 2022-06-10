package com.bull.springboot.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bull.springboot.application.model.Transaction;
import com.bull.springboot.application.model.TransactionSwaggerModel;
import com.bull.springboot.repository.MemoryDatabaseImpl;

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
	
	@GetMapping(value = "/details")
	@ApiOperation(value="Devuelve todas las transacciones con sus detalles",
			notes="Provee servicio de de obtenciones de todas las transacciones con todos sus detalles.",
			response=Long.class)
	public ResponseEntity<?> findAllTransactionDetails() {
		return ResponseEntity.status(HttpStatus.OK).body(memoryDatabase.findAllDetails());
	}
	
	@PutMapping(value = "/{transaction_id}", 
		    consumes=MediaType.APPLICATION_JSON_VALUE, 
		    produces = MediaType.APPLICATION_JSON_VALUE )
	@ApiOperation(value="Actualiza / Da de Alta, una transaccion",
	notes="Provee servicio de actualizacion y de alta de transaccion, el transaction_id se pone como parametro de tipo Path si el mismo existe dentro de la lista de transacciones pre existentes se actualiza de lo contrario se da de alta, en el Body se encuentra el resto de la informacion",
	response=Transaction.class)
	public ResponseEntity<?> updateUser(@PathVariable (required = true) long transaction_id, @RequestBody TransactionSwaggerModel transactionDetails)    {
		Transaction transaction= new Transaction(transaction_id, transactionDetails.getAmount(), transactionDetails.getType(), transactionDetails.getParent_id());
		try {
			Boolean update = memoryDatabase.updateOrInsertTransaction(transaction);
			if(update) 
				return ResponseEntity.status(HttpStatus.OK).body(transaction);	
			else
				return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e);
		}
		
	}
	
	@GetMapping(value = "/sum/{transaction_id }")
	@ApiOperation(value="Encuentra transacciones que estan transitivamente conectadas por su parent_id a transaction_id y realiza la suma de los amount",
			notes="Provee servicio de la suma de todas las transacciones que estan transitivamente conectadas por su parent_id a transaction_id, realizando la suma de los amount",
			response=Long.class)
	public ResponseEntity<?> sumTransactions(@PathVariable (name ="transaction_id ", required = true) Long transaction_id ) {
		log.info("Response received. Params: types {}", transaction_id);
		return ResponseEntity.status(HttpStatus.OK).body("sum:" + memoryDatabase.transitiveRelations(transaction_id));
	}
}
