package project.library.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    private Long id;

    private String title;
    private String author;

    //Constructors
    public Book() {}

    public Book(String title, String author) {
        this.title=title;
        this.author=author;
    }

    //Getter Setter
    public Long getId() { return id; }
    public void setId(long id) { this.id = id;}

    public String getTtile() {return title; }
    public void setTtile(String title) {this.title = title;}

    public String getAuthor(){return author;}
    public void setAuthor(String author) {this.author = author;}

    }

