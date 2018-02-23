package com.duopoints;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value(value = "classpath:firebase_key.json")
    private Resource firebaseKey;

    @Bean
    public FirebaseApp provideFirebaseOptions() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(firebaseKey.getInputStream()))
                .setDatabaseUrl("https://duopoints.firebaseio.com/")
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    @Qualifier("chatDB")
    public DatabaseReference provideDatabaseReference(FirebaseApp firebaseApp) {
        FirebaseDatabase.getInstance(firebaseApp).setPersistenceEnabled(false);

        return FirebaseDatabase.getInstance(firebaseApp).getReference();
    }
}