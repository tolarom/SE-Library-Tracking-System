package project.library.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;


@RestController
@RequestMapping("/books")
public class MyController {

    private final LibraryService libraryService;

    public MyController(LibraryService libraryService){
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<Book> getAll(){
        return libraryService.getAllBooks();
    }
    

    
}
