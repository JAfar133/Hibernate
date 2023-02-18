package ru.golovach.library_test.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovach.library_test.model.Book;
import ru.golovach.library_test.model.Person;
import ru.golovach.library_test.repository.BookRepo;
import ru.golovach.library_test.repository.PersonRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepo bookRepo;
    private final PersonRepo personRepo;

    @Autowired
    public BookService(BookRepo bookRepo, PersonRepo personRepo) {
        this.bookRepo = bookRepo;
        this.personRepo = personRepo;
    }

    public List<Book> showBooks(){

        return bookRepo.findAll(Sort.by("name"));

    }
    public List<Book> showBooks(String method, String field){
        if (method.equals("sort"))
            return bookRepo.findAll(Sort.by(field));
        else return bookRepo.findByNameStartingWith(field);

    }
    public List<Book> showBooks(int page, int booksPerPage){
        return bookRepo.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Optional<Book> showBook(int id){
        return bookRepo.findById(id);
    }
    public Optional<Book> showBook(String name){
        return bookRepo.findByName(name);
    }

    @Transactional
    public void addBook(Book book){
        bookRepo.save(book);
    }

    @Transactional
    public void deleteBook(int id){
        bookRepo.delete(bookRepo.findById(id).get());
    }
    @Transactional
    public void updateBook(int id, Book book){
        book.setBook_id(id);
        book.setOwner(bookRepo.findById(id).get().getOwner());
        book.setGiveAt(bookRepo.findById(id).get().getGiveAt());
        bookRepo.save(book);
    }

    @Transactional
    public void giveBookToPerson(int book_id, int person_id){
        bookRepo.findById(book_id).ifPresent(
            book -> {
                book.setOwner(personRepo.findById(person_id).get());
                book.setGiveAt(new Date());
            });
    }
    public Person getUserOfBook(int book_id){
        return bookRepo.findById(book_id).orElse(null).getOwner();

    }
    @Transactional
    public void freeBook(int book_id){
        bookRepo.findById(book_id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setGiveAt(null);
                    book.setOverdue(false);
                });
    }
    public List<Book> showFreeBooks(){
        List<Book> books = bookRepo.findAll();
        List<Book> freeBooks = new ArrayList<>();
        for (Book book:books) {
            if(book.getOwner()==null) freeBooks.add(book);
        }
        return freeBooks.size()>0 ? freeBooks : null;
    }

}
