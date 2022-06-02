package com.fsre.imenik.controller;

import com.fsre.imenik.model.BlokLista;
import com.fsre.imenik.repository.BlokListaRepository;
import com.fsre.imenik.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Component
@RestController
@Validated
@RequestMapping("/blokLista")
public class BlokListaController {
    @Autowired
    private BlokListaRepository blokListaRepository;

    @GetMapping("/getAll")
    public ResponseMessage getAll() {
        List<BlokLista> items = blokListaRepository.findAll();
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, "Successfully fetched all items.", items);

        return responseMessage;
    }

    @PostMapping("/add")
    public ResponseMessage add(@RequestBody @Valid BlokLista blokListaToAdd) {
        blokListaRepository.save(blokListaToAdd);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED, 201, "Item successfully created");

        return responseMessage;
    }

    @PatchMapping("/edit/{id}")
    public ResponseMessage updateById(@PathVariable String id, @RequestBody @Valid BlokLista blokLista) {
        Optional<BlokLista> item = blokListaRepository.findById(id);
        ResponseMessage responseMessage = new ResponseMessage();

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
        } else {
            responseMessage.setHttpStatus(HttpStatus.NOT_FOUND);
            responseMessage.setCode(404);
            responseMessage.setMessage(String.format("Item with the id %s not found.", id));
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
