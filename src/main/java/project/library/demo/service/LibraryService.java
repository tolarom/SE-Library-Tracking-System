package project.library.demo.service;
import java.util.List;

import org.springframework.stereotype.Service;

import project.library.demo.entity.Book;
import project.library.demo.entity.BorrowRecord;
import project.library.demo.entity.User;
import project.library.demo.repo.BorrowRepository;
import project.library.demo.repo.LibraryRepository;
import project.library.demo.repo.UserRepository;

@Service
public class LibraryService {
        private final LibraryRepository libraryRepository;
        private final BorrowRepository borrowRepository;
        private final UserRepository userRepository;

        public LibraryService(LibraryRepository libraryRepository , BorrowRepository borrowRepository, UserRepository userRepository){
            this.libraryRepository = libraryRepository;
            this.borrowRepository = borrowRepository;
            this.userRepository = userRepository;
        }


        public List<Book> getAllBooks(){
            return libraryRepository.findAll();
        }

        public List<BorrowRecord> getAllBorrowRecords(){
            // Implementation to retrieve all borrow records
            return borrowRepository.findAll();
        }

        public List<User> getAllUsers() {
            // Implementation to retrieve all users
            // Assuming you have a UserRepository similar to LibraryRepository
            return userRepository.findAll();
        }

    // You can also add:
    // public Book saveBook(Book book) { ... }
    // public void deleteBook(Long id) { ... }
    // public Optional<Book> getBookById(Long id) { ... }
}
