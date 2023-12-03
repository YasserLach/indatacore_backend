package com.indatacore.backend.service.impl;

import com.indatacore.backend.exception.ElementAlreadyExistsException;
import com.indatacore.backend.model.Contact;
import com.indatacore.backend.repository.ContactRepository;
import com.indatacore.backend.service.ContactService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.indatacore.backend.common.CoreConstant.Messages.ELEMENT_ALREADY_EXISTS;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    public List<Contact> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contactRepository.findAll(pageable).toList();
    }

    @Override
    public Contact save(Contact contact) {
        try {
            return contactRepository.save(contact);
        } catch (DataIntegrityViolationException exception) {
            throw new ElementAlreadyExistsException(ELEMENT_ALREADY_EXISTS);
        }
    }
}
