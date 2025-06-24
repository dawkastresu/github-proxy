package com.dawkastresu.git_proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "patientClient", configuration = FeignConfig.class)
public interface PatientClient {

    @GetMapping("/patients")
    List<PatientDto> fetchPatients();

    @GetMapping("/patients/{email}")
    public PatientDto fetchPatientByMail(@PathVariable("email") String email);
}
