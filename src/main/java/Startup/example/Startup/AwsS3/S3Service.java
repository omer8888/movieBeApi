package Startup.example.Startup.AwsS3;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

@Service
public class S3Service {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.access.key}")
    private String accessKey;

    @Value("${aws.s3.secret.key}")
    private String secretKey;

    private S3Client getS3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public String uploadImageToS3Bucket(String accountId, String imageUrl) {
        try {
            byte[] imageBytes = IOUtils.toByteArray(new URL(imageUrl));

            // Create a temporary file
            File tempFile = File.createTempFile("AI-Tattoo-Generated-image_", ".png");
            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                outputStream.write(imageBytes);
            }

            // Upload the file to S3 and get the URL
            String s3Url = "https://ai-tattoo-s3-bucket.s3.amazonaws.com/" + this.uploadFile(accountId, tempFile);

            // Delete the temporary file
            tempFile.delete();

            // Return the S3 URL
            return s3Url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String uploadFile(String userId, File file) {
        S3Client s3 = getS3Client();
        String key = userId + "/" + file.getName();

        try {
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3.putObject(putOb, file.toPath());
            System.out.println("File uploaded successfully: " + key);

        } catch (S3Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading file to S3: " + e.getMessage());
        }
        return key;
    }
}
