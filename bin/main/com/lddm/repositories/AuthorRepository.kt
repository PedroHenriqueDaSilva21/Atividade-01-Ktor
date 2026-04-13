package com.lddm.repositories

import com.lddm.models.Author
import com.lddm.models.AuthorRequest
import com.lddm.models.Authors
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

interface AuthorRepository {
    fun findAll(): List<Author>
    fun findById(id: Int): Author?
    fun create(request: AuthorRequest): Author
    fun update(id: Int, request: AuthorRequest): Author?
    fun delete(id: Int): Boolean
}

class AuthorRepositoryImpl : AuthorRepository {

    private fun rowToAuthor(row: ResultRow) = Author(
        id = row[Authors.id],
        name = row[Authors.name],
        nationality = row[Authors.nationality],
        createdAt = row[Authors.createdAt].toString()
    )

    override fun findAll(): List<Author> = transaction {
        Authors.selectAll().map(::rowToAuthor)
    }

    override fun findById(id: Int): Author? = transaction {
        Authors.select { Authors.id eq id }
            .singleOrNull()
            ?.let(::rowToAuthor)
    }

    override fun create(request: AuthorRequest): Author = transaction {
        val insertedId = Authors.insertAndGetId {
            it[name] = request.name
            it[nationality] = request.nationality
            it[createdAt] = LocalDateTime.now()
        }
        Authors.select { Authors.id eq insertedId.value }
            .single()
            .let(::rowToAuthor)
    }

    override fun update(id: Int, request: AuthorRequest): Author? = transaction {
        val updated = Authors.update({ Authors.id eq id }) {
            it[name] = request.name
            it[nationality] = request.nationality
        }
        if (updated > 0) {
            Authors.select { Authors.id eq id }.single().let(::rowToAuthor)
        } else null
    }

    override fun delete(id: Int): Boolean = transaction {
        Authors.deleteWhere { Authors.id eq id } > 0
    }
}
