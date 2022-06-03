package com.fsre.imenik.controller;

import com.fsre.imenik.model.Korisnik;
import com.fsre.imenik.repository.KorisnikRepository;
import com.fsre.imenik.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
@RestController
@Validated
@RequestMapping("/korisnik")
public class KorisnikController {
    @Autowired
    private KorisnikRepository korisnikRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16, new SecureRandom());


    @GetMapping("/getAll")
    public ResponseMessage getAll() {
        List<Korisnik> items = korisnikRepository.findAll();
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, "Successfully fetched all items.", items);

        return responseMessage;
    }

    @PostMapping("/add")
    public ResponseMessage add(@RequestBody @Valid Korisnik korisnikToAdd) {
        korisnikRepository.save(korisnikToAdd);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED, 201, "Item successfully created");

        return responseMessage;
    }

    @GetMapping("/pw")
    public ResponseMessage doStuff() {
        return new ResponseMessage(HttpStatus.OK, 200, bCryptPasswordEncoder.encode("123"));
    }

    @PatchMapping("/edit/{id}")
    public ResponseMessage updateById(@PathVariable String id, @RequestBody @Valid Korisnik blokLista) {
        Optional<Korisnik> item = korisnikRepository.findById(id);
        ResponseMessage responseMessage = new ResponseMessage();

        if (item.isPresent()) {
            Korisnik korisnikToEdit = item.get();

            korisnikToEdit.setKorisnickoIme(korisnikToEdit.getKorisnickoIme());
            korisnikToEdit.setLozinka(bCryptPasswordEncoder.encode(korisnikToEdit.getLozinka()));
            korisnikToEdit.setUloga(korisnikToEdit.getUloga());
            korisnikToEdit.setPrezime(korisnikToEdit.getPrezime());
            korisnikToEdit.setBrojTelefona(korisnikToEdit.getBrojTelefona());
            korisnikToEdit.setAdresa(korisnikToEdit.getAdresa());
            korisnikToEdit.setIme(korisnikToEdit.getIme());

            korisnikRepository.save(korisnikToEdit);

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
        korisnikRepository.deleteById(id);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, String.format("Item with the id %s has been deleted.", id));
        return responseMessage;
    }

}
