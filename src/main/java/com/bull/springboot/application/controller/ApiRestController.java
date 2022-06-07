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

	
	@GetMapping(value = "/transactions")
	public ResponseEntity<?> fetchCandidates(@RequestParam(value = "types", required = false) String types) {
		log.info("Response received. Params: types {}", types);
		return ResponseEntity.ok(null);

	}

}
