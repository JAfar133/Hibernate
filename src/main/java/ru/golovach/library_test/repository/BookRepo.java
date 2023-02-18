package ru.golovach.library_test.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.golovach.library_test.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Integer> {
    Optional<Book> findByName(String name);

    List<Book> findByNameStartingWith(String name);
}
