package com.fsre.imenik.controller;

import com.fsre.imenik.model.BrojDrzave;
import com.fsre.imenik.repository.BrojDrzaveRepository;
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
@RequestMapping("/brojDrzave")
public class BrojDrzaveController {
    @Autowired
    private BrojDrzaveRepository brojDrzaveRepository;

    @GetMapping("/getAll")
    public ResponseMessage getAll() {
        List<BrojDrzave> items = brojDrzaveRepository.findAll();
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, "Successfully fetched all items.", items);

        return responseMessage;
    }

    @GetMapping
    public ResponseMessage getById(@PathVariable String id) {
        Optional<BrojDrzave> item = brojDrzaveRepository.findById(id);
        ArrayList<BrojDrzave> returnList = new ArrayList<>();

        if (item.isEmpty()) {
            return new ResponseMessage(HttpStatus.NOT_FOUND, 404, "Item not found");
        } else {
            returnList.add(item.get());
            return new ResponseMessage(HttpStatus.OK, 200, "Item fetched successfully", returnList);
        }
    }

    @PostMapping("/add")
    public ResponseMessage add(@RequestBody @Valid BrojDrzave brojDrzaveToAdd) {
        brojDrzaveRepository.save(brojDrzaveToAdd);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED, 201, "Item successfully created");

        return responseMessage;
    }

    @PatchMapping("/edit/{id}")
    public ResponseMessage updateById(@PathVariable String id, @RequestBody @Valid BrojDrzave brojDrzave) {
        Optional<BrojDrzave> item = brojDrzaveRepository.findById(id);
        ResponseMessage responseMessage = new ResponseMessage();

        if (item.isPresent()) {
            BrojDrzave brojDrzaveToEdit = item.get();
            brojDrzaveToEdit.setDrzava(brojDrzave.getDrzava());
            brojDrzaveToEdit.setPozivniBrojDrzave(brojDrzave.getPozivniBrojDrzave());

            brojDrzaveRepository.save(brojDrzaveToEdit);

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
        brojDrzaveRepository.deleteById(id);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, String.format("Item with the id %s has been deleted.", id));
        return responseMessage;
    }

}
