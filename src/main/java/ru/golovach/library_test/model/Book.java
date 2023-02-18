package ru.golovach.library_test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "author shouldn't be empty")
    @Column(name = "author")
    private String author;
    @Max(value = 2023, message = "please enter correct release year")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "give_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date giveAt;

    @Transient
    private boolean isOverdue;

    public Book(int book_id, String name, String author, int year) {
        this.book_id = book_id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getGiveAt() {
        return giveAt;
    }

    public void setGiveAt(Date giveAt) {
        this.giveAt = giveAt;
    }

    public boolean isOverdue() {
        return isOverdue;
    }


    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return book_id == book.book_id && year == book.year && Objects.equals(name, book.name) && Objects.equals(author, book.author);
    }



    @Override
    public int hashCode() {
        return Objects.hash(book_id, name, author, year);
    }
}
