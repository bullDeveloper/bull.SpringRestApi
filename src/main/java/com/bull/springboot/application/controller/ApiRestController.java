package com.bull.springboot.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bull.springboot.application.model.Transaction;
import com.bull.springboot.repository.MemoryDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/transactions")
public class ApiRestController {

	@Autowired
	MemoryDatabase memoryDatabase;
	
	@GetMapping(value = {"/types/", "/types", "/types/{id}"})
	public ResponseEntity<?> findTransactions(@PathVariable (required = false) String id) {
		log.info("Response received. Params: types {}", id);
		return StringUtils.isEmpty(id)?ResponseEntity.ok(memoryDatabase.findAll()):ResponseEntity.ok(memoryDatabase.findByType(id));
	}
	
	@PutMapping(value = "/{transaction_id}", 
		    consumes=MediaType.APPLICATION_JSON_VALUE, 
		    produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> updateUser(@PathVariable (required = true) String transaction_id) {//, @RequestBody UserDetailsRequestModel requestUserDetails)    {
		Transaction transaction= new Transaction(10, 5000, "cars", 0);
		return ResponseEntity.ok(transaction);
	}
}
