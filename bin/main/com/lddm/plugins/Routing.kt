package com.lddm.plugins

import com.lddm.repositories.AuthorRepositoryImpl
import com.lddm.repositories.BookRepositoryImpl
import com.lddm.routes.authorRoutes
import com.lddm.routes.bookRoutes
import com.lddm.services.AuthorService
import com.lddm.services.BookService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val authorService = AuthorService(AuthorRepositoryImpl())
    val bookService = BookService(BookRepositoryImpl())

    routing {
        get("/") {
            call.respondText("LDDM Biblioteca API - Acesse /swagger para a documentação")
        }
        authorRoutes(authorService)
        bookRoutes(bookService)
    }
}
