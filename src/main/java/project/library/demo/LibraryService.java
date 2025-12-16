package project.library.demo;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LibraryService {
        private final LibraryRepository libraryRepository;

        public LibraryService(LibraryRepository libraryRepository){
            this.libraryRepository = libraryRepository;
        }

        public List<Book> getAllBooks(){
            return libraryRepository.findAll();
        }

    // You can also add:
    // public Book saveBook(Book book) { ... }
    // public void deleteBook(Long id) { ... }
    // public Optional<Book> getBookById(Long id) { ... }
}
