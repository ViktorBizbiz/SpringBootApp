package ua.bizbiz.springbootapp.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ua.bizbiz.springbootapp.api.PeopleApi;
import ua.bizbiz.springbootapp.api.model.PersonData;
import ua.bizbiz.springbootapp.api.model.PersonResponse;
import ua.bizbiz.springbootapp.service.PeopleService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PeopleController implements PeopleApi {

    private final PeopleService peopleService;

    @Override
    public ResponseEntity<PersonResponse> create(PersonData personData) {
        PersonResponse savedPerson = peopleService.create(personData);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<PersonResponse>> getAll() {
        return ResponseEntity.ok(peopleService.getAll());
    }

    @Override
    public ResponseEntity<PersonResponse> getById(Integer id) {
        return ResponseEntity.ok(peopleService.getById(id));
    }

    @Override
    public ResponseEntity<PersonResponse> update(PersonData personData, Integer id) {
        PersonResponse updatedPerson = peopleService.update(id, personData);
        return ResponseEntity.ok(updatedPerson);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        peopleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
