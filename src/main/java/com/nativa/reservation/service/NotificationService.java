package com.nativa.reservation.service;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface NotificationService {
    void send(String subject, String message);
    void receive(String message);
}
