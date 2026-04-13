package com.lddm.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

// ─── Request / Response DTOs ──────────────────────────────────────────────────

@Serializable
data class Author(
    val id: Int? = null,
    val name: String,
    val nationality: String? = null,
    val createdAt: String? = null
)

@Serializable
data class AuthorRequest(
    val name: String,
    val nationality: String? = null
)

// ─── Exposed Table Definition ─────────────────────────────────────────────────

object Authors : Table("authors") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val nationality = varchar("nationality", 100).nullable()
    val createdAt = datetime("created_at")

    override val primaryKey = PrimaryKey(id)
}
