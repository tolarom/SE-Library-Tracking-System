package project.library.demo;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LibraryService {
        private final LibraryRepository libraryRepository;
        private final BorrowRepository borrowRepository;

        public LibraryService(LibraryRepository libraryRepository , BorrowRepository borrowRepository){
            this.libraryRepository = libraryRepository;
            this.borrowRepository = borrowRepository;
        }


        public List<Book> getAllBooks(){
            return libraryRepository.findAll();
        }

        public List<BorrowRecord> getAllBorrowRecords(){
            // Implementation to retrieve all borrow records
            return borrowRepository.findAll();
        }

    // You can also add:
    // public Book saveBook(Book book) { ... }
    // public void deleteBook(Long id) { ... }
    // public Optional<Book> getBookById(Long id) { ... }
}
