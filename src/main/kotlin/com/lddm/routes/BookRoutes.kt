package com.lddm.routes

import com.lddm.models.BookRequest
import com.lddm.services.BookService
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bookRoutes(service: BookService) {
    route("/api/books") {

        // GET /api/books — List all books
        get({
            tags = listOf("Books")
            summary = "List all books"
            description = "Returns a list of all registered books."
            response {
                HttpStatusCode.OK to {
                    description = "List of books"
                }
            }
        }) {
            call.respond(HttpStatusCode.OK, service.getAllBooks())
        }

        // POST /api/books — Create book
        post({
            tags = listOf("Books")
            summary = "Create a new book"
            description = "Creates a new book with the provided data."
            request {
                body<BookRequest> {
                    description = "Book data to create"
                }
            }
            response {
                HttpStatusCode.Created to {
                    description = "Book created successfully"
                }
                HttpStatusCode.BadRequest to {
                    description = "Invalid request data"
                }
            }
        }) {
            try {
                val request = call.receive<BookRequest>()
                val book = service.createBook(request)
                call.respond(HttpStatusCode.Created, book)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // PUT /api/books/{id} — Update book
        put("{id}", {
            tags = listOf("Books")
            summary = "Update a book"
            description = "Updates the book with the specified ID."
            request {
                pathParameter<Int>("id") {
                    description = "Book ID"
                }
                body<BookRequest> {
                    description = "Updated book data"
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Book updated successfully"
                }
                HttpStatusCode.NotFound to {
                    description = "Book not found"
                }
                HttpStatusCode.BadRequest to {
                    description = "Invalid request data"
                }
            }
        }) {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID"))
            try {
                val request = call.receive<BookRequest>()
                val updated = service.updateBook(id, request)
                if (updated != null) {
                    call.respond(HttpStatusCode.OK, updated)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Book not found"))
                }
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // DELETE /api/books/{id} — Delete book
        delete("{id}", {
            tags = listOf("Books")
            summary = "Delete a book"
            description = "Deletes the book with the specified ID."
            request {
                pathParameter<Int>("id") {
                    description = "Book ID"
                }
            }
            response {
                HttpStatusCode.NoContent to {
                    description = "Book deleted successfully"
                }
                HttpStatusCode.NotFound to {
                    description = "Book not found"
                }
            }
        }) {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID"))
            if (service.deleteBook(id)) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Book not found"))
            }
        }
    }
}
