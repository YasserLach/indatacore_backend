package com.indatacore.backend.utils;

import com.indatacore.backend.dto.ContactDto;
import com.indatacore.backend.model.Contact;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapHelper {

    private final ModelMapper modelMapper;

    public MapHelper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ContactDto convertToDto(Contact source) {
        return modelMapper.map(source, ContactDto.class);
    }

    public Contact convertToEntity(ContactDto dto) {
        return modelMapper.map(dto, Contact.class);
    }

    public List<ContactDto> convertListToDto(final List<Contact> entities) {
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


}
