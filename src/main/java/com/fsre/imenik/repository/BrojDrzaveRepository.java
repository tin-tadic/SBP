package com.fsre.imenik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fsre.imenik.model.BrojDrzave;

@Repository
@Component
public interface BrojDrzaveRepository extends MongoRepository<BrojDrzave, String> {
    
}
