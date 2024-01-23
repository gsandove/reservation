package com.nativa.reservation.service.impl;

import com.nativa.reservation.service.NotificationService;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Slf4j
@Component
public class NotificationServiceImpl implements NotificationService {
    private final SqsTemplate sqsTemplate;
    private final SnsTemplate snsTemplate;
    private final SnsClient snsClient;

    public NotificationServiceImpl(SqsTemplate sqsTemplate, SnsTemplate snsTemplate, SnsClient snsClient) {
        this.sqsTemplate = sqsTemplate;
        this.snsTemplate = snsTemplate;
        this.snsClient = snsClient;
    }

    @Override
    public void send(String subject, String message) {
        PublishRequest request = PublishRequest.builder()
                .targetArn("arn:aws:sqs:us-west-2:674342301842:queue-test")
                .message(message)
                .subject(subject)
                .build();
        this.snsClient.publish(request);
    }

    @SqsListener("${queue.aws.reservation}")
    @Override
    public void receive(String message) {
        log.info("message: {}", message);
    }
}
