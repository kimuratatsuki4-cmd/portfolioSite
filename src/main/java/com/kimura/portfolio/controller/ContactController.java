package com.kimura.portfolio.controller;

import com.kimura.portfolio.entity.Contact;
import com.kimura.portfolio.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String showContactForm(Model model) {
        if (!model.containsAttribute("contact")) {
            model.addAttribute("contact", new Contact());
        }
        return "contact";
    }

    @PostMapping
    public String submitContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "contact";
        }
        contactService.saveContact(contact);
        return "redirect:/contact/success";
    }

    @GetMapping("/success")
    public String showSuccess() {
        return "contact_success";
    }
}
