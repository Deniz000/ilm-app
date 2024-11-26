package com.ilim.app.business.services;

import com.ilim.app.dto.message.MessageResponse;
import com.ilim.app.dto.message.SendMessageRequest;

import java.util.List;

public interface MessageService {
    MessageResponse sendMessage(SendMessageRequest message);
    MessageResponse getMessageById(Long id);
    List<MessageResponse> getMessagesBetweenUsers(Long senderId, Long receiverId);
    void deleteMessage(Long id);
}
