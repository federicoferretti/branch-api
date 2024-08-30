package com.store.service.infrastructure.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Indexes
import io.micronaut.context.annotation.Value
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton
import org.bson.Document

@Singleton
class MongoInitializer(
    @Value("\${mongodb.uri}") private val uri: String,
    @Value("\${mongodb.database}") private val database: String
) {

    @EventListener
    fun onStartup(event: StartupEvent) {
        val mongoClient: MongoClient = MongoClients.create(uri)
        val database = mongoClient.getDatabase(database)

        generateGeospatialIndex(database)
        generateFirstUser(database)

    }

    private fun generateGeospatialIndex(database: MongoDatabase) {
        if (!database.listCollectionNames().contains("branch")) {
            database.createCollection("branch")
        }

        val collection = database.getCollection("branch")
        val indexExists = collection.listIndexes()
            .map { it.getString("name") }
            .contains("location_2dsphere")

        if (!indexExists) {
            collection.createIndex(Indexes.geo2dsphere("location"))
        }
    }

    private fun generateFirstUser(database: MongoDatabase) {
        val userCollection = database.getCollection("user")

        val existingUser = userCollection.find(Document("username", "user")).firstOrNull()

        if (existingUser == null) {
            val userDocument = Document()
                .append("username", "user")
                .append("password", "test")
                .append("roles", listOf("ROLE_USER"))

            userCollection.insertOne(userDocument)
        }
    }
}
