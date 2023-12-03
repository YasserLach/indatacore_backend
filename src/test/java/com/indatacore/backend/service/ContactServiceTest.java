package com.indatacore.backend.service;

import com.indatacore.backend.exception.ElementAlreadyExistsException;
import com.indatacore.backend.model.Contact;
import com.indatacore.backend.repository.ContactRepository;
import com.indatacore.backend.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;


    @Test
    void findAll_ShouldReturnContactList() {
        // Arrange
        int page = 0;
        int size = 10;
        List<Contact> expectedContacts = Collections.singletonList(new Contact());
        Page<Contact> expectedPage = new PageImpl<>(expectedContacts);
        when(contactRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Act
        List<Contact> actualContacts = contactService.findAll(page, size);

        // Assert
        assertEquals(expectedContacts, actualContacts);
        verify(contactRepository, times(1)).findAll(PageRequest.of(page, size));
    }


    @Test
    void save_ShouldReturnSavedContact_WhenNoException() {
        // Arrange
        Contact contactToSave = new Contact();
        when(contactRepository.save(contactToSave)).thenReturn(contactToSave);

        // Act
        Contact savedContact = contactService.save(contactToSave);

        // Assert
        assertEquals(contactToSave, savedContact);
        verify(contactRepository, times(1)).save(contactToSave);
    }

    @Test
    void save_ShouldThrowElementAlreadyExistsException_WhenDataIntegrityViolationExceptionOccurs() {
        // Arrange
        Contact contactToSave = new Contact();
        when(contactRepository.save(contactToSave)).thenThrow(new DataIntegrityViolationException(""));

        // Act & Assert
        assertThrows(ElementAlreadyExistsException.class, () -> contactService.save(contactToSave));

        verify(contactRepository, times(1)).save(contactToSave);
    }

}
