package com.dawkastresu.git_proxy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientClient client;
    private final PatientService service;

    @GetMapping
    public List<PatientDto> getPatients() {
        return service.getPatients();
    }

    @GetMapping("/{email}")
    public PatientDto getPatientByMail(@PathVariable("email") String email) {
        return service.getPatientByMaik(email);
    }

}
