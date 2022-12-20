package ua.bizbiz.springbootapp.api.mapper;

import org.springframework.stereotype.Component;
import ua.bizbiz.springbootapp.api.model.PersonData;
import ua.bizbiz.springbootapp.api.model.PersonResponse;
import ua.bizbiz.springbootapp.persistance.entity.Person;

import java.time.LocalDate;
import java.time.Period;

@Component
public class PeopleMapper {

    public PersonResponse toPersonResponse(Person person) {
        LocalDate today = LocalDate.now();
        LocalDate birthdate = LocalDate.parse(person.getBirthdate());
        Integer age = Period.between(birthdate, today).getYears();

        return PersonResponse.builder()
                .id(person.getId())
                .name(person.getName())
                .surname(person.getSurname())
                .age(age)
                .build();
    }

    public Person toPerson(PersonData personData) {
        return Person.builder()
                .name(personData.getName())
                .surname(personData.getSurname())
                .birthdate(personData.getBirthdate().toString())
                .build();
    }
}
