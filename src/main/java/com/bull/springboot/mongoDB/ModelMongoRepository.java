package com.bull.springboot.mongoDB;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bull.springboot.application.model.Transcation;

public interface  ModelMongoRepository extends  PagingAndSortingRepository<Transcation, String> {
}     