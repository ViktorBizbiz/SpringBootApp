package ua.bizbiz.springbootapp.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.bizbiz.springbootapp.api.model.PersonData;
import ua.bizbiz.springbootapp.api.model.PersonResponse;

import java.util.List;

@RequestMapping("/people")
public interface PeopleApi {

    @PostMapping()
    ResponseEntity<PersonResponse> create(@RequestBody PersonData personData);

    @GetMapping()
    ResponseEntity<List<PersonResponse>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<PersonResponse> getById(@PathVariable("id") Integer id);

    @PutMapping("/{id}")
    ResponseEntity<PersonResponse> update(@RequestBody PersonData personData, @PathVariable("id") Integer id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Integer id);

}
