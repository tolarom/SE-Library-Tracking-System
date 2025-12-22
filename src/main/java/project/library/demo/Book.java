package project.library.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book {

    @Id
    private String id;
    private String isbn;
    
    private String publisher;
    private int total_copy;
    private int available_copy;
    private String title;
    private String author;

    //Constructors
    public Book() {}

    public Book(String title, String author, String isbn, String publisher, int total_copy, int available_copy) {
        this.title=title;
        this.author=author;
        this.isbn=isbn;
        this.publisher=publisher;
        this.total_copy=total_copy;
        this.available_copy=available_copy;
    }

    //Getter Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() {return title; }
    public void setTitle(String title) {this.title = title;}
    
    public String getAuthor(){return author;}
    public void setAuthor(String author) {this.author = author;}

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    public String getPublisher() {return publisher;}
    public void setPublisher(String publisher) {this.publisher = publisher;}

    public int getTotal_copy() {return total_copy;}
    public void setTotal_copy(int total_copy) {this.total_copy = total_copy;}

    public int getAvailable_copy() {return available_copy;}
    public void setAvailable_copy(int available_copy) {this.available_copy = available_copy;}


    }

