package com.fsre.imenik.repository;

import com.fsre.imenik.model.BlokLista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface BlokListaRepository extends MongoRepository<BlokLista, String> {

}
