package com.fsre.imenik.controller;

import com.fsre.imenik.model.BlokLista;
import com.fsre.imenik.repository.BlokListaRepository;
import com.fsre.imenik.repository.ImenikRepository;
import com.fsre.imenik.repository.KorisnikRepository;
import com.fsre.imenik.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
@RestController
@Validated
@RequestMapping("/blokLista")
public class BlokListaController {
    @Autowired
    private BlokListaRepository blokListaRepository;

    @Autowired
    private ImenikRepository imenikRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;
    
    @GetMapping("/getAll")
    public ResponseMessage getAll() {
        List<BlokLista> items = blokListaRepository.findAll();
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, "Successfully fetched all items.", items);

        return responseMessage;
    }

    @GetMapping
    public ResponseMessage getById(@PathVariable String id) {
        Optional<BlokLista> item = blokListaRepository.findById(id);
        ArrayList<BlokLista> returnList = new ArrayList<>();

        if (item.isEmpty()) {
            return new ResponseMessage(HttpStatus.NOT_FOUND, 404, "Item not found");
        } else {
            returnList.add(item.get());
            return new ResponseMessage(HttpStatus.OK, 200, "Item fetched successfully", returnList);
        }
    }

    @PostMapping("/add")
    public ResponseMessage add(@RequestBody @Valid BlokLista blokListaToAdd) {
        if (imenikRepository.findById(blokListaToAdd.getIdImenika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idImenika not found");
        }

        if (korisnikRepository.findById(blokListaToAdd.getIdKorisnika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idKorisnika not found");
        }

        blokListaRepository.save(blokListaToAdd);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED, 201, "Item successfully created");

        return responseMessage;
    }

    @PatchMapping("/edit/{id}")
    public ResponseMessage updateById(@PathVariable String id, @RequestBody @Valid BlokLista blokLista) {
        Optional<BlokLista> item = blokListaRepository.findById(id);
        ResponseMessage responseMessage = new ResponseMessage();

        if (imenikRepository.findById(blokLista.getIdImenika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idImenika not found");
        }

        if (korisnikRepository.findById(blokLista.getIdKorisnika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idKorisnika not found");
        }

        if (item.isPresent()) {
            BlokLista blokListaToEdit = item.get();
            blokListaToEdit.setIme(blokLista.getIme());
            blokListaToEdit.setPrezime(blokLista.getPrezime());
            blokListaToEdit.setBrojTelefona(blokLista.getBrojTelefona());
            blokListaToEdit.setIdImenika(blokLista.getIdImenika());
            blokListaToEdit.setIdKorisnika(blokLista.getIdKorisnika());

            blokListaRepository.save(blokListaToEdit);

            responseMessage.setHttpStatus(HttpStatus.OK);
            responseMessage.setCode(200);
            responseMessage.setMessage("Item updated successfully.");
            responseMessage.setTimestamp(new Date());
        } else {
            responseMessage.setHttpStatus(HttpStatus.NOT_FOUND);
            responseMessage.setCode(404);
            responseMessage.setMessage(String.format("Item with the id %s not found.", id));
            responseMessage.setTimestamp(new Date());
        }

        return responseMessage;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteById(@PathVariable String id) {
        blokListaRepository.deleteById(id);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, String.format("Item with the id %s has been deleted.", id));
        return responseMessage;
    }

}
