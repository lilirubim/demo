package com.certidevs.controller;

import com.certidevs.model.Author;
import com.certidevs.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController { // Controller o Resource

    // declarar Arraylist de Books
    List<Book> books;

    // constructor: agregar 3 libros al arraylist

    public BookController() {
        books = new ArrayList<>();
        books.add(
                new Book(
                        1l,
                        "Torto Arado",
                        19.99,
                        true,
                        new Author(1L, "Itamar Vieira Junior", "Salvador")
                )
        );
        books.add(
                new Book(
                        2l,
                        "Tieta",
                        39.99,
                        false,
                        new Author(2L, "Jorge Amado", "Salvador")
                )
        );
        books.add(
                new Book(
                        3l,
                        "Dona Flor e seus dois maridos",
                        29.99,
                        false,
                        new Author(2L, "Jorge Amado", "Buenos Aires")
                )
        );


    }


    // Métodos GetMapping con ResponseEntity
    @GetMapping("books")
    public ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(this.books);

    }

    //Métodos GetMapping con ResponseEntity que devuelva un book por id filtrando
    @GetMapping("books/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {

        for (Book book: this.books){
            if (book.id().equals(id)) {
                // Si encuentra el book lo devuelve
                return ResponseEntity.ok(book);
            }
        }
        // Si no se ha encontrado entonces devuelve 404
        return ResponseEntity.notFound().build();
    }
    // Métodos GetMapping con ResponseEntity que devuelva una lista de books por autor
    @GetMapping("books/by-author-id/{authorId}")
    public ResponseEntity<List<Book>> findAllByAuthorId(@PathVariable Long authorId) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : this.books) {
            if(book.author().id().equals(authorId)) {
                filteredBooks.add(book); // Guardo el libro cuyo el autor coincide
            }

        }

        // if filteredbooks está vacio devolver un not found
        if (filteredBooks.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(filteredBooks);
        // si no, entonces devolver el arraylist con status ok
    }

    @PostMapping("books")
    public ResponseEntity<Book> create(@RequestBody Book book) {
        if (book.id() != null)
            return ResponseEntity.badRequest().build();

        // TODO generar un nuevo id
        // TODO guardar en base de datos
        // TODO try catch gestionar errores

        this.books.add(book);

        return ResponseEntity.status(201).body(book);
    }


}
