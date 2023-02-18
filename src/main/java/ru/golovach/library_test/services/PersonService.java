package ru.golovach.library_test.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovach.library_test.model.Book;
import ru.golovach.library_test.model.Person;
import ru.golovach.library_test.repository.PersonRepo;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepo personRepo;
    //private final EntityManager entityManager;

    @Autowired
    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public List<Person> showPeoples(){
        return personRepo.findAll();
    }
    public Optional<Person> showPerson(int id){
        return personRepo.findById(id);
    }
    public Optional<Person> showPerson(String name){
        return personRepo.findByFio(name);
    }

    @Transactional
    public void addPerson(Person person){
        personRepo.save(person);
    }

    @Transactional
    public void deletePerson(int id){
        personRepo.delete(personRepo.findById(id).get());
    }

    @Transactional
    public void updatePerson(int id, Person person){
        person.setPerson_id(id);
        personRepo.save(person);
    }
    public List<Book> getPersonBooks(int id){
        Optional<Person> person = personRepo.findById(id);
        if(person.isPresent()) {
            if (person.get().getBooks()!=null) {
                person.get().getBooks().forEach(book -> {
                    long different = (new Date()).getTime() - book.getGiveAt().getTime();
                    if (different / (24 * 60 * 60 * 1000) > 10)
                        book.setOverdue(true);
                });
            }
            return person.get().getBooks();
        }
        else return Collections.emptyList();
    }

}
