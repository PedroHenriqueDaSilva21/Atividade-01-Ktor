package com.lddm.services

import com.lddm.models.Author
import com.lddm.models.AuthorRequest
import com.lddm.repositories.AuthorRepository

class AuthorService(private val repository: AuthorRepository) {

    fun getAllAuthors(): List<Author> = repository.findAll()

    fun getAuthorById(id: Int): Author? = repository.findById(id)

    fun createAuthor(request: AuthorRequest): Author {
        require(request.name.isNotBlank()) { "Author name cannot be blank" }
        return repository.create(request)
    }

    fun updateAuthor(id: Int, request: AuthorRequest): Author? {
        require(request.name.isNotBlank()) { "Author name cannot be blank" }
        return repository.update(id, request)
    }

    fun deleteAuthor(id: Int): Boolean = repository.delete(id)
}
