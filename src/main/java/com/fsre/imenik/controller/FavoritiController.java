package com.fsre.imenik.controller;

import com.fsre.imenik.model.Favoriti;
import com.fsre.imenik.repository.FavoritiRepository;
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
@RequestMapping("/favoriti")
public class FavoritiController {
    @Autowired
    private FavoritiRepository favoritiRepository;

    @Autowired
    private ImenikRepository imenikRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @GetMapping("/getAll")
    public ResponseMessage getAll() {
        List<Favoriti> items = favoritiRepository.findAll();
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, "Successfully fetched all items.", items);

        return responseMessage;
    }

    @GetMapping("/get/{id}")
    public ResponseMessage getById(@PathVariable String id) {
        Optional<Favoriti> item = favoritiRepository.findById(id);
        ArrayList<Favoriti> returnList = new ArrayList<>();

        if (item.isEmpty()) {
            return new ResponseMessage(HttpStatus.NOT_FOUND, 404, "Item not found");
        } else {
            returnList.add(item.get());
            return new ResponseMessage(HttpStatus.OK, 200, "Item fetched successfully", returnList);
        }
    }

    @PostMapping("/add")
    public ResponseMessage add(@RequestBody @Valid Favoriti favoritiToAdd) {
        if (imenikRepository.findById(favoritiToAdd.getIdImenika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idImenika not found");
        }

        if (korisnikRepository.findById(favoritiToAdd.getIdKorisnika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idKorisnika not found");
        }

        favoritiRepository.save(favoritiToAdd);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED, 201, "Item successfully created");

        return responseMessage;
    }

    @PatchMapping("/edit/{id}")
    public ResponseMessage updateById(@PathVariable String id, @RequestBody @Valid Favoriti favoriti) {
        Optional<Favoriti> item = favoritiRepository.findById(id);
        ResponseMessage responseMessage = new ResponseMessage();

        if (imenikRepository.findById(favoriti.getIdImenika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idImenika not found");
        }

        if (korisnikRepository.findById(favoriti.getIdKorisnika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idKorisnika not found");
        }

        if (item.isPresent()) {
            Favoriti favoritiToEdit = item.get();
            favoritiToEdit.setIme(favoriti.getIme());
            favoritiToEdit.setPrezime(favoriti.getPrezime());
            favoritiToEdit.setBrojTelefona(favoriti.getBrojTelefona());
            favoritiToEdit.setIdImenika(favoriti.getIdImenika());
            favoritiToEdit.setIdKorisnika(favoriti.getIdKorisnika());

            favoritiRepository.save(favoritiToEdit);

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
        favoritiRepository.deleteById(id);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, String.format("Item with the id %s has been deleted.", id));
        return responseMessage;
    }

}
