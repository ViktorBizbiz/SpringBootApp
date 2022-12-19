package ua.bizbiz.springbootapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.bizbiz.springbootapp.api.PeopleDataReceiver;
import ua.bizbiz.springbootapp.api.mapper.PeopleMapper;
import ua.bizbiz.springbootapp.api.model.PersonData;
import ua.bizbiz.springbootapp.api.model.PersonResponse;
import ua.bizbiz.springbootapp.persistance.entity.Person;
import ua.bizbiz.springbootapp.persistance.repository.PeopleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PeopleServiceUnitTest {

    @Autowired
    private PeopleService peopleService;

    @MockBean
    private PeopleRepository peopleRepositoryMock;

    @MockBean
    private PeopleMapper peopleMapperMock;

    @Autowired
    private PeopleDataReceiver peopleDataReceiver;

    @Test
    void create_test() {
        Person person = peopleDataReceiver.getPerson();
        PersonData personData = peopleDataReceiver.getPersonData();
        PersonResponse expectedResponse = peopleDataReceiver.getPersonResponse();

        when(peopleMapperMock.toPerson(personData)).thenReturn(person);
        when(peopleMapperMock.toPersonResponse(person)).thenReturn(expectedResponse);

        when(peopleRepositoryMock.save(person)).thenReturn(person);
        when(peopleRepositoryMock.findById(person.getId())).thenReturn(Optional.of(person));

        PersonResponse response = peopleService.create(personData);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void getAll_test() {
        int personQuantity = 3;
        List<Person> personList = new ArrayList<>();
        for (int i = 1; i <= personQuantity; i++) {
            personList.add(peopleDataReceiver.getPerson(i));
        }
        List<PersonResponse> expectedResponse = new ArrayList<>();
        for (int i = 1; i <= personQuantity; i++) {
            expectedResponse.add(peopleDataReceiver.getPersonResponse(i));
        }

        when(peopleRepositoryMock.findAll()).thenReturn(personList);

        for (int i = 0; i < personQuantity; i++) {
            when(peopleMapperMock.toPersonResponse(personList.get(i))).thenReturn(expectedResponse.get(i));
        }

        List<PersonResponse> response = peopleService.getAll();

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void getById_test() {
        Person person = peopleDataReceiver.getPerson();
        PersonResponse expectedResult = peopleDataReceiver.getPersonResponse();

        when(peopleRepositoryMock.findById(person.getId())).thenReturn(Optional.of(person));
        when(peopleMapperMock.toPersonResponse(person)).thenReturn(expectedResult);

        PersonResponse result = peopleService.getById(person.getId());

        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    void update_test() {
        Person personToUpdate = peopleDataReceiver.getPersonOld();
        Person updatedPerson = peopleDataReceiver.getPerson();
        PersonData personData = peopleDataReceiver.getPersonData();
        PersonResponse expectedResponse = peopleDataReceiver.getPersonResponse();

        when(peopleRepositoryMock.findById(personToUpdate.getId())).thenReturn(Optional.of(personToUpdate));
        when(peopleRepositoryMock.save(updatedPerson)).thenReturn(updatedPerson);

        when(peopleMapperMock.toPersonResponse(personToUpdate)).thenReturn(expectedResponse);

        PersonResponse response = peopleService.update(personToUpdate.getId(), personData);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void delete_test() {
        Person person = peopleDataReceiver.getPerson();

        when(peopleRepositoryMock.findById(person.getId())).thenReturn(Optional.of(person));

        peopleService.delete(person.getId());

        verify(peopleRepositoryMock).deleteById(person.getId());
    }

}
