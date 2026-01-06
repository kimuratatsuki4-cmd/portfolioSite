package com.kimura.portfolio.service;

import com.kimura.portfolio.entity.Contact;
import com.kimura.portfolio.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }
}
