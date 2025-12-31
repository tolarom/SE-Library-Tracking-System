package project.library.demo.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import project.library.demo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    
}
