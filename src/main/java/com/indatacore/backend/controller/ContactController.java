package com.indatacore.backend.controller;

import com.github.javafaker.Faker;
import com.indatacore.backend.dto.ContactDto;
import com.indatacore.backend.model.Contact;
import com.indatacore.backend.service.ContactService;
import com.indatacore.backend.utils.MapHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.indatacore.backend.common.CoreConstant.Pagination.DEFAULT_PAGE_NUMBER;
import static com.indatacore.backend.common.CoreConstant.Pagination.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {
    private final ContactService contactService;
    private final MapHelper mapHelper;

    public ContactController(ContactService contactService, MapHelper mapHelper) {
        this.contactService = contactService;
        this.mapHelper = mapHelper;
    }


    @GetMapping
    public ResponseEntity<List<ContactDto>> getAll(@RequestParam(value = "page", defaultValue = "" + DEFAULT_PAGE_NUMBER) Integer page, @RequestParam(value = "size", defaultValue = "" + DEFAULT_PAGE_SIZE) Integer size) {
        List<Contact> contacts = contactService.findAll(page, size);
        return ResponseEntity.ok(mapHelper.convertListToDto(contacts));
    }

    @PostMapping
    public ResponseEntity<ContactDto> save(@RequestBody @Valid ContactDto contact) {
        final Contact entity = mapHelper.convertToEntity(contact);
        final Contact saved = contactService.save(entity);
        return new ResponseEntity<>(mapHelper.convertToDto(saved), HttpStatus.CREATED);
    }

    @PostMapping("/random")
    public ResponseEntity<ContactDto> randomSave() {
        ContactDto randomContactDto = generateRandomContactDto();

        Contact entity = mapHelper.convertToEntity(randomContactDto);

        Contact saved = contactService.save(entity);

        return new ResponseEntity<>(mapHelper.convertToDto(saved), HttpStatus.CREATED);

    }

    private ContactDto generateRandomContactDto() {
        Faker faker = new Faker();
        return ContactDto.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .build();
    }
}
