package ua.bizbiz.springbootapp.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.bizbiz.springbootapp.persistance.entity.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
