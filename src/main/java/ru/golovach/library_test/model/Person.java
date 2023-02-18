package ru.golovach.library_test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;
    @NotEmpty(message = "fio shouldn't be empty")
    @Pattern(regexp = "[A-Z]\\w+ *[A-Z]\\w+ *[A-Z]\\w+",message = "not valid fio")
    @Column(name = "fio")
    private String fio;

    @Min(value = 1900, message = "please enter valid year of birth")
    @Max(value = 2023, message = "please enter valid year of birth")
    @Column(name = "year")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;
    public Person(int person_id, String fio, int year) {
        this.person_id = person_id;
        this.fio = fio;
        this.year = year;
    }

    public Person() {
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", fio='" + fio + '\'' +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return person_id == person.person_id && year == person.year && Objects.equals(fio, person.fio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person_id, fio, year);
    }
}
