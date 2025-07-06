package com.recipes.recipe.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {


    @Bean
    public S3Client s3Client() {
        String accessKey = "AKIAWVU5TR2PH3CC5N4H";
        String secretKey = "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";
        AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
