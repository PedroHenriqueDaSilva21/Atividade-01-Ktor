package com.lddm.services

import com.lddm.models.Book
import com.lddm.models.BookRequest
import com.lddm.repositories.BookRepository

class BookService(private val repository: BookRepository) {

    fun getAllBooks(): List<Book> = repository.findAll()

    fun getBookById(id: Int): Book? = repository.findById(id)

    fun createBook(request: BookRequest): Book {
        require(request.title.isNotBlank()) { "Book title cannot be blank" }
        return repository.create(request)
    }

    fun updateBook(id: Int, request: BookRequest): Book? {
        require(request.title.isNotBlank()) { "Book title cannot be blank" }
        return repository.update(id, request)
    }

    fun deleteBook(id: Int): Boolean = repository.delete(id)
}
