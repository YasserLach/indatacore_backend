package com.indatacore.backend.service.impl;

import com.indatacore.backend.model.Contact;
import com.indatacore.backend.repository.ContactRepository;
import com.indatacore.backend.service.CSVReaderService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.indatacore.backend.common.CoreConstant.CSV.*;


@Service
@Slf4j
public class CSVReaderServiceImpl implements CSVReaderService {
    @Value("${csv.file.name}")
    private String csvFilename;

    private final ContactRepository contactRepository;

    public CSVReaderServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostConstruct
    @Transactional
    public void readCsvOnStartup() {
        if (csvFilename != null) {
            log.info("save records on startup");
            readCsvAndSaveToDatabase();
        } else {
            log.warn("CSV file path is not configured. Skipping CSV read on startup.");
        }
    }

    @Transactional
    @Override
    public void readCsvAndSaveToDatabase() {
        try (CSVReader reader = new CSVReader(new FileReader(new ClassPathResource(csvFilename).getFile()))) {
            List<String[]> rows = reader.readAll();
            List<Contact> contacts = rows.stream().skip(1).map(row -> Contact.builder().firstName(row[FIRST_NAME_INDEX]).lastName(row[LAST_NAME_INDEX]).email(row[EMAIL_INDEX]).phoneNumber(row[PHONE_NUMBER_INDEX]).build()).collect(Collectors.toList());

            contactRepository.saveAll(contacts);

        } catch (IOException | CsvException e) {
            log.error("Error processing CSV: {}", e.getMessage(), e);
        } catch (DataAccessException e) {
            log.error("Error in DAO operation: {}", e.getMessage(), e);
        }
    }


}
