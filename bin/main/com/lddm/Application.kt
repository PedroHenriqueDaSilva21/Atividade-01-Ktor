package com.lddm

import com.lddm.plugins.configureDatabase
import com.lddm.plugins.configureRouting
import com.lddm.plugins.configureSerialization
import com.lddm.plugins.configureSwagger
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureDatabase()
    configureSwagger()
    configureRouting()
}
