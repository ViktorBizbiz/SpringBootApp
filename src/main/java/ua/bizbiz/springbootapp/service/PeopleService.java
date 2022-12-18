package ua.bizbiz.springbootapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.bizbiz.springbootapp.api.mapper.PeopleMapper;
import ua.bizbiz.springbootapp.api.model.PersonData;
import ua.bizbiz.springbootapp.api.model.PersonResponse;
import ua.bizbiz.springbootapp.exception.BadRequestException;
import ua.bizbiz.springbootapp.exception.NotFoundException;
import ua.bizbiz.springbootapp.persistance.entity.Person;
import ua.bizbiz.springbootapp.persistance.repository.PeopleRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final PeopleMapper peopleMapper;

    @Transactional
    public PersonResponse create(PersonData personData) {
        validateBirthdate(personData.getBirthdate());

        Person person = peopleMapper.toPerson(personData);
        peopleRepository.save(person);

        Person savedPerson = peopleRepository.findById(person.getId())
                .orElseThrow(() -> new NotFoundException("Person did not created"));

        return peopleMapper.toPersonResponse(savedPerson);
    }

    public List<PersonResponse> getAll() {
        return peopleRepository.findAll().stream()
                .map(peopleMapper::toPersonResponse)
                .toList();
    }

    public PersonResponse getById(Integer id) {
        Person person = getPersonById(id);
        return peopleMapper.toPersonResponse(person);
    }

    @Transactional
    public PersonResponse update(Integer id, PersonData personData) {
        validateBirthdate(personData.getBirthdate());

        Person personToUpdate = getPersonById(id);
        personToUpdate.setName(personData.getName());
        personToUpdate.setSurname(personData.getSurname());
        personToUpdate.setBirthdate(personData.getBirthdate().toString());
        peopleRepository.save(personToUpdate);

        Person updatedPerson = peopleRepository.findById(personToUpdate.getId())
                .orElseThrow(() -> new NotFoundException("Person did not updated"));

        return peopleMapper.toPersonResponse(updatedPerson);
    }

    @Transactional
    public void delete(Integer id) {
        Person personToDelete = getPersonById(id);
        peopleRepository.deleteById(personToDelete.getId());
    }

    private Person getPersonById(Integer id) {
        return peopleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person does not exist"));
    }

    private void validateBirthdate(LocalDate birthdate) {
        if (birthdate.isAfter(LocalDate.now()))
            throw new BadRequestException("Wrong birthdate");
    }
}
