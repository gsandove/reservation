package com.nativa.reservation.service.impl;

import com.nativa.reservation.service.NotificationService;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationServiceImpl implements NotificationService {
    private final SqsTemplate sqsTemplate;
    private final SnsTemplate snsTemplate;

    public NotificationServiceImpl(SqsTemplate sqsTemplate, SnsTemplate snsTemplate) {
        this.sqsTemplate = sqsTemplate;
        this.snsTemplate = snsTemplate;
    }

    @Override
    public void send(String subject, String message) {
        this.snsTemplate.sendNotification(message, subject);
        this.sqsTemplate.send(message);
    }

    @SqsListener("${queue.aws.reservation}")
    @Override
    public void receive(String message) {
        log.info("message: {}", message);
    }
}
