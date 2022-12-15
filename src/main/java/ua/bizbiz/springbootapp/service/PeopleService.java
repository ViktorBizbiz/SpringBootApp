package ua.bizbiz.springbootapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.bizbiz.springbootapp.api.response.PersonResponse;
import ua.bizbiz.springbootapp.persistance.entity.Person;
import ua.bizbiz.springbootapp.persistance.repository.PeopleRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public PersonResponse personForResponse(int id) {
        return makePersonResponse(findOne(id));
    }

    public List<PersonResponse> listOfPeopleForResponse() {
        List<PersonResponse> responseList = new ArrayList<>();
        for (Person person : findAll())
            responseList.add(makePersonResponse(person));
        return responseList;
    }

    public PersonResponse makePersonResponse(Person person) {
//        Integer yearOfTheBirth = LocalDate.parse(person.getBirthdate()).getYear();
//        Integer currentYear = LocalDate.now().getYear();
//        Integer age = currentYear - yearOfTheBirth;
        LocalDate today = LocalDate.now();
        LocalDate birthdate = LocalDate.parse(person.getBirthdate());
        Period difference = Period.between(birthdate, today);
        return new PersonResponse(person.getId(), person.getName(), person.getSurname(), difference.getYears());
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
