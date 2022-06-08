package com.bull.springboot.application.controller;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bull.springboot.application.util.CandidatesUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/transactions")
public class ApiRestController {

	@GetMapping(value = "/types")
	public ResponseEntity<?> fetchCandidates(@RequestParam(value = "types", required = false) String types) {
		log.info("Response received. Params: types {}", types);

		// Simple util to help us get some dummy data
		var candidateList = CandidatesUtil.getCandidates();
		if (!StringUtils.isEmpty(types)) {
			// Filter by skill
			return ResponseEntity.ok(candidateList.stream()
							.filter(candidate -> candidate.getSkillsSet().contains(types.toLowerCase()))
							.collect(Collectors.toList()));
		}

		return ResponseEntity.ok(candidateList);
	}

}
