package ru.golovach.library_test.conrollers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.golovach.library_test.model.Book;
import ru.golovach.library_test.model.Person;
import ru.golovach.library_test.services.BookService;
import ru.golovach.library_test.services.PersonService;
import ru.golovach.library_test.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;
    private final BookService bookService;
    private final PersonValidator personValidator;
    @Autowired
    public PersonController(PersonService personService, BookService bookService, PersonValidator personValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }


    @GetMapping
    public String showPersonPage(Model model){
        model.addAttribute("people",personService.showPeoples());
        return "person/peoples";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable("id") int id, Model model,@ModelAttribute Book book){
        model.addAttribute("person",personService.showPerson(id).get());
        model.addAttribute("books",personService.getPersonBooks(id));
        model.addAttribute("free_books",bookService.showFreeBooks());
        return "person/person_page";
    }

    @DeleteMapping("/{id}/delete")
    public String delPerson(@PathVariable("id") int id){
        personService.deletePerson(id);
        return "redirect:/people";
    }
    @PatchMapping("/{id}/add")
    public String addFreeBook(@PathVariable("id") int id, @ModelAttribute("book") Book book ){
        bookService.giveBookToPerson(book.getBook_id(), id);

        return "redirect:/people/"+id;
    }

    @GetMapping("/new")
    public String addPerson(@ModelAttribute("person") Person person){
        return "person/add_person";
    }

    @PostMapping
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult,Model model){
        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors()) return "person/add_person";
        personService.addPerson(person);

        return "redirect:/people";
    }

    @GetMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id,Model model){
        model.addAttribute("person",personService.showPerson(id).get());
        return "person/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                             @PathVariable("id") int id){
        if(bindingResult.hasErrors()) return "person/edit";
        personService.updatePerson(id,person);
        return "redirect:/people";
    }








}
