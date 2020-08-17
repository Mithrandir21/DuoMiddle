package com.duopoints

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.io.IOException

@Configuration
class FirebaseConfig {
    @Value(value = "classpath:firebase_key.json")
    private val firebaseKey: Resource? = null

    @Bean
    @Throws(IOException::class)
    fun provideFirebaseOptions(): FirebaseApp {
        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(firebaseKey!!.inputStream))
                .setDatabaseUrl("https://duopoints.firebaseio.com/")
                .build()
        return FirebaseApp.initializeApp(options)
    }

    @Bean
    @Qualifier("chatDB")
    fun provideDatabaseReference(firebaseApp: FirebaseApp?): DatabaseReference {
        FirebaseDatabase.getInstance(firebaseApp).setPersistenceEnabled(false)
        return FirebaseDatabase.getInstance(firebaseApp).reference
    }
}