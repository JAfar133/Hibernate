package ru.golovach.library_test.conrollers;


import jakarta.servlet.http.HttpServletRequest;
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
import ru.golovach.library_test.util.BookValidator;

@Controller
@RequestMapping("/book")
public class BookController {


    private final BookService bookService;
    private final PersonService personService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, PersonService personService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookValidator = bookValidator;
    }
    @GetMapping(params = {"page", "books_per_page"})
    public String showBooksPage(@RequestParam(value = "page") int page,
                                @RequestParam(value = "books_per_page") int bookPerPage, Model model){

        model.addAttribute("books",bookService.showBooks(page-1,bookPerPage));

        return "book/books";
    }
    @GetMapping(params = "sort_by_year")
    public String showBooksPage(@RequestParam(value = "sort_by_year") boolean sort, Model model){

        if (sort) model.addAttribute("books",bookService.showBooks("sort","year"));
        else model.addAttribute("books",bookService.showBooks());

        return "book/books";
    }
    @GetMapping(params = "name")
    public String showBooksPage(@RequestParam String name, Model model){

        model.addAttribute("books",bookService.showBooks("search",name));

        return "book/books";
    }
    @GetMapping
    public String showBooksPage(Model model){

        model.addAttribute("books",bookService.showBooks());


        return "book/books";
    }

    @GetMapping("/{id}")
    public String showBookPage(@PathVariable("id") int id,
                               @ModelAttribute("person") Person person, Model model){
        model.addAttribute("book",bookService.showBook(id).get());

        model.addAttribute("personWhoUsingBook",bookService.getUserOfBook(id));
        model.addAttribute("people",personService.showPeoples());

        return "book/book_page";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book){
        return "book/add_book";
    }

    @PostMapping
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model){
        bookValidator.validate(book,bindingResult);
        if(bindingResult.hasErrors()) return "/book/new";

        bookService.addBook(book);

        return "redirect:/book";
    }

    @PatchMapping("/{id}/give")
    public String giveBook( @ModelAttribute("person") Person person,
                            @PathVariable("id") int id, HttpServletRequest request){
        String give = request.getParameter("give");
        String free = request.getParameter("freeBook");
        if(give!=null)
            bookService.giveBookToPerson(id, person.getPerson_id());
        else if(free!=null)
            bookService.freeBook(id);

        return "redirect:/book/"+id;
    }
    @DeleteMapping("/{id}/delete")
    public String delBook(@PathVariable("id") int id){
        bookService.deleteBook(id);
        return "redirect:/book";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model){
        System.out.println(bookService.showBook(id).get());
        model.addAttribute("book",bookService.showBook(id).get());
        return "book/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                           @PathVariable("id") int id){
        if(bindingResult.hasErrors()) return "/book/edit";
        bookService.updateBook(id,book);
        return "redirect:/book";
    }
}
