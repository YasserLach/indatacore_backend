package com.indatacore.backend.service;

import com.indatacore.backend.model.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> findAll(final int page, final int size);

    Contact save(final Contact contact);
}
