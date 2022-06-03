package com.fsre.imenik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fsre.imenik.model.PropusteniPoziv;

@Component
@Repository
public interface PropusteniPozivRepository extends MongoRepository<PropusteniPoziv, String>{
    
}
