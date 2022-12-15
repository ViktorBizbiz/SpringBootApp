package ua.bizbiz.springbootapp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.bizbiz.springbootapp.api.response.PersonResponse;
import ua.bizbiz.springbootapp.persistance.entity.Person;
import ua.bizbiz.springbootapp.service.PeopleService;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public ResponseEntity<List<PersonResponse>> index() {
        return ResponseEntity.ok(peopleService.listOfPeopleForResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> showPersonById(@PathVariable("id") int id) {
        return ResponseEntity.ok(peopleService.personForResponse(id));
    }

    @PostMapping("/new")
    public void create(@RequestBody Person person) {
        peopleService.save(person);
    }

    @PatchMapping("/edit/{id}")
    public void update(@RequestBody Person person, @PathVariable("id") int id) {
        peopleService.update(id, person);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        peopleService.delete(id);
    }
}
