package com.bull.springboot.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class ApiRestController {

	
	@RequestMapping(value = "transactions/types/{type}", method = RequestMethod.GET)
	public ResponseEntity<?> fetchCandidates(@RequestParam(value = "type", required = false) String skill) {
		log.info("Response received. Params: type {}", skill);
		return ResponseEntity.ok(null);

	}

}
