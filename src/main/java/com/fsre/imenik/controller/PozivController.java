package com.fsre.imenik.controller;

import com.fsre.imenik.model.Poziv;
import com.fsre.imenik.repository.PozivRepository;
import com.fsre.imenik.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
@RestController
@Validated
@RequestMapping("/poziv")
public class PozivController {
    @Autowired
    private PozivRepository pozivRepository;

    @GetMapping("/getAll")
    public ResponseMessage getAll() {
        List<Poziv> items = pozivRepository.findAll();
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, "Successfully fetched all items.", items);

        return responseMessage;
    }

    @PostMapping("/add")
    public ResponseMessage add(@RequestBody @Valid Poziv PozivToAdd) {
        pozivRepository.save(PozivToAdd);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED, 201, "Item successfully created");

        return responseMessage;
    }

    @PatchMapping("/edit/{id}")
    public ResponseMessage updateById(@PathVariable String id, @RequestBody @Valid Poziv poziv) {
        Optional<Poziv> item = pozivRepository.findById(id);
        ResponseMessage responseMessage = new ResponseMessage();

        if (item.isPresent()) {
            Poziv pozivToEdit = item.get();
            pozivToEdit.setTrajanje(poziv.getTrajanje());
            pozivToEdit.setDatum(poziv.getDatum());
            pozivToEdit.setIdImenika(poziv.getIdImenika());

            pozivRepository.save(pozivToEdit);

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
        pozivRepository.deleteById(id);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, String.format("Item with the id %s has been deleted.", id));
        return responseMessage;
    }

}
