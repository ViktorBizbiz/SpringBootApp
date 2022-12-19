package ua.bizbiz.springbootapp.api;

import org.springframework.stereotype.Component;
import ua.bizbiz.springbootapp.api.model.PersonData;
import ua.bizbiz.springbootapp.api.model.PersonResponse;
import ua.bizbiz.springbootapp.persistance.entity.Person;

import java.time.LocalDate;

@Component
public class PeopleDataReceiver {

    private final int idIndex = 2;

    public Person getPerson() {
        return Person.builder()
                .id(idIndex)
                .name("name")
                .surname("surname")
                .birthdate("1999-11-11")
                .build();
    }

    public PersonData getPersonData() {
        return PersonData.builder()
                .name("name")
                .surname("surname")
                .birthdate(LocalDate.parse("1999-11-11"))
                .build();
    }

    public PersonResponse getPersonResponse() {
        return PersonResponse.builder()
                .id(idIndex)
                .name("name")
                .surname("surname")
                .age(23)
                .build();
    }

    public Person getPerson(int i) {
        return Person.builder()
                .id(i)
                .name("name" + i)
                .surname("surname" + i)
                .birthdate(LocalDate.parse("1999-11-11").minusYears(i).toString())
                .build();
    }

    public PersonResponse getPersonResponse(int i) {
        return PersonResponse.builder()
                .id(i)
                .name("name" + i)
                .surname("surname" + i)
                .age(23 + i)
                .build();
    }

    public Person getPersonOld() {
        return Person.builder()
                .id(idIndex)
                .name("old_name")
                .surname("old_surname")
                .birthdate("1980-11-11")
                .build();
    }

    public PersonData getPersonDataForSuccess() {
        return PersonData.builder()
                .name("name")
                .surname("surname")
                .birthdate(LocalDate.parse("1999-11-11"))
                .build();
    }

    public PersonData getPersonDataForBadRequest() {
        return PersonData.builder()
                .name("name")
                .surname("surname")
                .birthdate(LocalDate.parse("4000-11-11"))
                .build();
    }
}
