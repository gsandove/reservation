package com.nativa.reservation.service.impl;

import com.nativa.reservation.service.NotificationService;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;

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
        sqsTemplate.send("queue-test", message);
    }

    @SqsListener("${queue.aws.reservation}")
    @Override
    public void receive(String message) {
        log.info("message: {}", message);
    }
}
