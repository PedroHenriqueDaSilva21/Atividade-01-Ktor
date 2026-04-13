package com.lddm.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.OpenApiInfo
import io.ktor.server.application.*

fun Application.configureSwagger() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = false
        }
        info {
            title = "LDDM Biblioteca API"
            version = "1.0.0"
            description = "API REST para gerenciamento de Autores e Livros. " +
                    "Atividade LDDM - Laboratório de Desenvolvimento de Dispositivos Móveis."
        }
        server {
            url = "http://localhost:8080"
            description = "Local Development Server"
        }
    }
}
