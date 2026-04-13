package com.lddm.routes

import com.lddm.models.AuthorRequest
import com.lddm.services.AuthorService
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authorRoutes(service: AuthorService) {
    route("/api/authors") {

        // GET /api/authors — List all authors
        get({
            tags = listOf("Authors")
            summary = "List all authors"
            description = "Returns a list of all registered authors."
            response {
                HttpStatusCode.OK to {
                    description = "List of authors"
                }
            }
        }) {
            call.respond(HttpStatusCode.OK, service.getAllAuthors())
        }

        // POST /api/authors — Create author
        post({
            tags = listOf("Authors")
            summary = "Create a new author"
            description = "Creates a new author with the provided data."
            request {
                body<AuthorRequest> {
                    description = "Author data to create"
                }
            }
            response {
                HttpStatusCode.Created to {
                    description = "Author created successfully"
                }
                HttpStatusCode.BadRequest to {
                    description = "Invalid request data"
                }
            }
        }) {
            try {
                val request = call.receive<AuthorRequest>()
                val author = service.createAuthor(request)
                call.respond(HttpStatusCode.Created, author)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // PUT /api/authors/{id} — Update author
        put("{id}", {
            tags = listOf("Authors")
            summary = "Update an author"
            description = "Updates the author with the specified ID."
            request {
                pathParameter<Int>("id") {
                    description = "Author ID"
                }
                body<AuthorRequest> {
                    description = "Updated author data"
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Author updated successfully"
                }
                HttpStatusCode.NotFound to {
                    description = "Author not found"
                }
                HttpStatusCode.BadRequest to {
                    description = "Invalid request data"
                }
            }
        }) {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID"))
            try {
                val request = call.receive<AuthorRequest>()
                val updated = service.updateAuthor(id, request)
                if (updated != null) {
                    call.respond(HttpStatusCode.OK, updated)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Author not found"))
                }
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // DELETE /api/authors/{id} — Delete author
        delete("{id}", {
            tags = listOf("Authors")
            summary = "Delete an author"
            description = "Deletes the author with the specified ID."
            request {
                pathParameter<Int>("id") {
                    description = "Author ID"
                }
            }
            response {
                HttpStatusCode.NoContent to {
                    description = "Author deleted successfully"
                }
                HttpStatusCode.NotFound to {
                    description = "Author not found"
                }
            }
        }) {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID"))
            if (service.deleteAuthor(id)) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Author not found"))
            }
        }
    }
}
