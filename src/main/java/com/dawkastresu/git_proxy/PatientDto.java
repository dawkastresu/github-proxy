package com.dawkastresu.git_proxy;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = ANY)
public class PatientDto {

    Long id;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate birthday;
    String fullName;
    String idCardNo;
    UserDto user;

}
