package ua.bizbiz.springbootapp.data;

import org.springframework.stereotype.Component;
import ua.bizbiz.springbootapp.api.model.PersonData;
import ua.bizbiz.springbootapp.api.model.PersonResponse;
import ua.bizbiz.springbootapp.persistance.entity.Person;

import java.time.LocalDate;

@Component
public class PeopleDataReceiver {

    private final int idIndex = 1;

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
}
