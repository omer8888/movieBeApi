package Startup.example.Startup.AwsS3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;

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

    private S3Presigner getS3Presigner() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
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

    public String getPresignedUrl(String key) {
        S3Presigner presigner = getS3Presigner();
        GetObjectPresignRequest getObjectPresignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(b -> b.bucket(bucketName).key(key))
                        .build();

        URL url = presigner.presignGetObject(getObjectPresignRequest).url();
        return url.toString();
    }
}
