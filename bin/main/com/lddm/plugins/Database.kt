package com.lddm.plugins

import com.lddm.models.Authors
import com.lddm.models.Books
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val dotenv = dotenv {
        ignoreIfMissing = true
    }

    val host = dotenv["DB_HOST"]
    val port = dotenv["DB_PORT"]
    val name = dotenv["DB_NAME"]
    val user = dotenv["DB_USER"]
    val password = dotenv["DB_PASSWORD"]

    val jdbcUrl = "jdbc:postgresql://$host:$port/$name"

    // Run Flyway migrations
    val flyway = Flyway.configure()
        .dataSource(jdbcUrl, user, password)
        .locations("classpath:db/migration")
        .load()
    flyway.migrate()

    // Setup HikariCP connection pool
    val config = HikariConfig().apply {
        this.jdbcUrl = jdbcUrl
        this.username = user
        this.password = password
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 10
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    log.info("Database connected successfully to $jdbcUrl")
}
