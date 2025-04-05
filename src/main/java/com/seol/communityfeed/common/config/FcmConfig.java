package com.seol.communityfeed.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class FcmConfig {

    @Value("${fcm.certification}")
    private String fcmApplicationCredentials;

    @PostConstruct
    public void initialize() {
        ClassPathResource resource = new ClassPathResource(fcmApplicationCredentials);

        try (InputStream is = resource.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialized!");
            } else {
                log.info("FirebaseApp already initialized");
            }
        } catch (IOException e) {
            log.error("Failed to initialize FirebaseApp", e);
        }
    }
}
