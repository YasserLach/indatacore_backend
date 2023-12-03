package com.indatacore.backend.controller;

import com.indatacore.backend.dto.ContactDto;
import com.indatacore.backend.model.Contact;
import com.indatacore.backend.service.ContactService;
import com.indatacore.backend.utils.MapHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static com.indatacore.backend.common.CoreConstant.Pagination.DEFAULT_PAGE_NUMBER;
import static com.indatacore.backend.common.CoreConstant.Pagination.DEFAULT_PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @Mock
    private MapHelper mapHelper;

    @InjectMocks
    private ContactController contactController;

    @Test
    void getAll_ShouldReturnContactDtoList() {
        // Arrange
        int page = DEFAULT_PAGE_NUMBER;
        int size = DEFAULT_PAGE_SIZE;
        List<Contact> mockContacts = Collections.singletonList(new Contact());
        List<ContactDto> mockContactDtos = Collections.singletonList(new ContactDto());

        when(contactService.findAll(page, size)).thenReturn(mockContacts);
        when(mapHelper.convertListToDto(mockContacts)).thenReturn(mockContactDtos);

        // Act
        ResponseEntity<List<ContactDto>> responseEntity = contactController.getAll(page, size);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockContactDtos, responseEntity.getBody());
        verify(contactService, times(1)).findAll(page, size);
        verify(mapHelper, times(1)).convertListToDto(mockContacts);
    }

    @Test
    void save_ShouldReturnCreatedContactDto() {
        // Arrange
        // Using Lombok's @Builder
        ContactDto inputContactDto = ContactDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .build();

        Contact inputEntity = Contact.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .build();

        Contact savedEntity = Contact.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .build();

        ContactDto savedDto = ContactDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .build();


        when(mapHelper.convertToEntity(inputContactDto)).thenReturn(inputEntity);
        when(contactService.save(inputEntity)).thenReturn(savedEntity);
        when(mapHelper.convertToDto(savedEntity)).thenReturn(savedDto);

        // Act
        ResponseEntity<ContactDto> responseEntity = contactController.save(inputContactDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(savedDto, responseEntity.getBody());
        verify(mapHelper, times(1)).convertToEntity(inputContactDto);
        verify(contactService, times(1)).save(inputEntity);
        verify(mapHelper, times(1)).convertToDto(savedEntity);
    }
}
