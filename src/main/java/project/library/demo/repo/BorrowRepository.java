package project.library.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.library.demo.entity.BorrowRecord;


@Repository
public interface BorrowRepository extends JpaRepository<BorrowRecord, String>{
    
}
