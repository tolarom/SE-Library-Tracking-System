package project.library.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping()
public class MyController {

    private final LibraryService libraryService;

    public MyController(LibraryService libraryService){
        this.libraryService = libraryService;
    }

    @GetMapping("/books")
    public List<Book> getAll(){
        return libraryService.getAllBooks();
    }

    @GetMapping("/borrow-records")
    public List<BorrowRecord> getAllBorrowRecords(){
        return libraryService.getAllBorrowRecords();   
    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return libraryService.getAllUsers();
    }
    
    
}
