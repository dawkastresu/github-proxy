package com.dawkastresu.git_proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    public final PatientClient client;

    public List<PatientDto> getPatients() {
        return client.fetchPatients();
    }

    public PatientDto getPatientByMaik(String email) {
        return client.fetchPatientByMail(email);
    }

}
