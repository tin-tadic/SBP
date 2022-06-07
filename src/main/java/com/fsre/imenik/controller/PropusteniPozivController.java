package com.fsre.imenik.controller;

import com.fsre.imenik.model.PropusteniPoziv;
import com.fsre.imenik.repository.ImenikRepository;
import com.fsre.imenik.repository.PropusteniPozivRepository;
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
@RequestMapping("/propusteniPoziv")
public class PropusteniPozivController {
    @Autowired
    private PropusteniPozivRepository propusteniPozivRepository;

    @Autowired
    private ImenikRepository imenikRepository;


    @GetMapping("/getAll")
    public ResponseMessage getAll() {
        List<PropusteniPoziv> items = propusteniPozivRepository.findAll();
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, "Successfully fetched all items.", items);

        return responseMessage;
    }

    @GetMapping
    public ResponseMessage getById(@PathVariable String id) {
        Optional<PropusteniPoziv> item = propusteniPozivRepository.findById(id);
        ArrayList<PropusteniPoziv> returnList = new ArrayList<>();

        if (item.isEmpty()) {
            return new ResponseMessage(HttpStatus.NOT_FOUND, 404, "Item not found");
        } else {
            returnList.add(item.get());
            return new ResponseMessage(HttpStatus.OK, 200, "Item fetched successfully", returnList);
        }
    }

    @PostMapping("/add")
    public ResponseMessage add(@RequestBody @Valid PropusteniPoziv propusteniPozivToAdd) {
        if (imenikRepository.findById(propusteniPozivToAdd.getIdImenika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idImenika not found");
        }
        
        propusteniPozivRepository.save(propusteniPozivToAdd);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED, 201, "Item successfully created");

        return responseMessage;
    }

    @PatchMapping("/edit/{id}")
    public ResponseMessage updateById(@PathVariable String id, @RequestBody @Valid PropusteniPoziv propusteniPoziv) {
        Optional<PropusteniPoziv> item = propusteniPozivRepository.findById(id);
        ResponseMessage responseMessage = new ResponseMessage();

        if (imenikRepository.findById(propusteniPoziv.getIdImenika()).isEmpty()) {
            return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, 422, "idImenika not found");
        }

        if (item.isPresent()) {
            PropusteniPoziv propusteniPozivToEdit = item.get();

            propusteniPozivToEdit.setDatumVrijeme(propusteniPoziv.getDatumVrijeme());
            propusteniPozivToEdit.setIdImenika(propusteniPoziv.getIdImenika());

            propusteniPozivRepository.save(propusteniPozivToEdit);

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
        propusteniPozivRepository.deleteById(id);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, 200, String.format("Item with the id %s has been deleted.", id));
        return responseMessage;
    }

}
