package ru.golovach.library_test.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.golovach.library_test.model.Person;

import java.util.Optional;


public interface PersonRepo extends JpaRepository<Person, Integer> {

    Optional<Person> findByFio(String fio);
}
