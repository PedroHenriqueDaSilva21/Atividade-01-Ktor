package com.lddm.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

// ─── Request / Response DTOs ──────────────────────────────────────────────────

@Serializable
data class Book(
    val id: Int? = null,
    val title: String,
    val isbn: String? = null,
    val year: Int? = null,
    val authorId: Int,
    val createdAt: String? = null
)

@Serializable
data class BookRequest(
    val title: String,
    val isbn: String? = null,
    val year: Int? = null,
    val authorId: Int
)

// ─── Exposed Table Definition ─────────────────────────────────────────────────

object Books : Table("books") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val isbn = varchar("isbn", 20).nullable()
    val year = integer("year").nullable()
    val authorId = integer("author_id").references(Authors.id)
    val createdAt = datetime("created_at")

    override val primaryKey = PrimaryKey(id)
}
