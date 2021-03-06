package com.fsre.imenik.controller;

import com.fsre.imenik.model.Imenik;
import com.fsre.imenik.repository.BrojDrzaveRepository;
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
@RequestMapping("/imenik")
public class ImenikController {
    @Autowired
    private ImenikRepository imenikRepository;

    @Autowired
    private BrojDrzaveRepository brojDrzaveRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @GetMapping("/getAll")
    public ResponseMessage getAll() {
        List<Imenik> items = imenikRepository.findAll();
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, "Successfully fetched all items.", items);

        return responseMessage;
    }

    @GetMapping("/get/{id}")
    public ResponseMessage getById(@PathVariable String id) {
        Optional<Imenik> item = imenikRepository.findById(id);
        ArrayList<Imenik> returnList = new ArrayList<>();

        if (item.isEmpty()) {
            return new ResponseMessage(HttpStatus.NOT_FOUND, 404, "Item not found");
        } else {
            returnList.add(item.get());
            return new ResponseMessage(HttpStatus.OK, 200, "Item fetched successfully", returnList);
        }
    }

    @PostMapping("/add")
    public ResponseMessage add(@RequestBody @Valid Imenik imenikListaToAdd) {
        if (brojDrzaveRepository.findById(imenikListaToAdd.getIdBrojaDrzave()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idBrojaDrzave not found");
        }

        if (korisnikRepository.findById(imenikListaToAdd.getIdKorisnika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idKorisnika not found");
        }

        imenikRepository.save(imenikListaToAdd);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED, 201, "Item successfully created");

        return responseMessage;
    }

    @PatchMapping("/edit/{id}")
    public ResponseMessage updateById(@PathVariable String id, @RequestBody @Valid Imenik imenik) {
        Optional<Imenik> item = imenikRepository.findById(id);
        ResponseMessage responseMessage = new ResponseMessage();

        if (brojDrzaveRepository.findById(imenik.getIdBrojaDrzave()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idBrojaDrzave not found");
        }

        if (korisnikRepository.findById(imenik.getIdKorisnika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idKorisnika not found");
        }

        if (item.isPresent()) {
            Imenik imenikToEdit = item.get();
            imenikToEdit.setIme(imenik.getIme());
            imenikToEdit.setPrezime(imenik.getPrezime());
            imenikToEdit.setBrojTelefona(imenik.getBrojTelefona());
            imenikToEdit.setIdBrojaDrzave(imenik.getIdBrojaDrzave());
            imenikToEdit.setIdKorisnika(imenik.getIdKorisnika());

            imenikRepository.save(imenikToEdit);

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
        imenikRepository.deleteById(id);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, String.format("Item with the id %s has been deleted.", id));
        return responseMessage;
    }

}
