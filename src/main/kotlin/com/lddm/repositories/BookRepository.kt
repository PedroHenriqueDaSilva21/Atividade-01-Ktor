package com.lddm.repositories

import com.lddm.models.Book
import com.lddm.models.BookRequest
import com.lddm.models.Books
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

interface BookRepository {
    fun findAll(): List<Book>
    fun findById(id: Int): Book?
    fun create(request: BookRequest): Book
    fun update(id: Int, request: BookRequest): Book?
    fun delete(id: Int): Boolean
}

class BookRepositoryImpl : BookRepository {

    private fun rowToBook(row: ResultRow) = Book(
        id = row[Books.id],
        title = row[Books.title],
        isbn = row[Books.isbn],
        year = row[Books.year],
        authorId = row[Books.authorId],
        createdAt = row[Books.createdAt].toString()
    )

    override fun findAll(): List<Book> = transaction {
        Books.selectAll().map(::rowToBook)
    }

    override fun findById(id: Int): Book? = transaction {
        Books.select { Books.id eq id }
            .singleOrNull()
            ?.let(::rowToBook)
    }

    override fun create(request: BookRequest): Book = transaction {
        val insertedId = Books.insertAndGetId {
            it[title] = request.title
            it[isbn] = request.isbn
            it[year] = request.year
            it[authorId] = request.authorId
            it[createdAt] = LocalDateTime.now()
        }
        Books.select { Books.id eq insertedId.value }
            .single()
            .let(::rowToBook)
    }

    override fun update(id: Int, request: BookRequest): Book? = transaction {
        val updated = Books.update({ Books.id eq id }) {
            it[title] = request.title
            it[isbn] = request.isbn
            it[year] = request.year
            it[authorId] = request.authorId
        }
        if (updated > 0) {
            Books.select { Books.id eq id }.single().let(::rowToBook)
        } else null
    }

    override fun delete(id: Int): Boolean = transaction {
        Books.deleteWhere { Books.id eq id } > 0
    }
}
