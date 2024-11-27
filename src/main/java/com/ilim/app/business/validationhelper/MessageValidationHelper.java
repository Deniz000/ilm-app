package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.MessageNotFoundException;
import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.dataAccess.MessageRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.dto.message.SendMessageRequest;
import com.ilim.app.entities.Message;
import org.springframework.stereotype.Component;

@Component
public record MessageValidationHelper(
        UserRepository userRepository,
        MessageRepository messageRepository
) {
    public void validateSendMessageRequest(SendMessageRequest request) {
        if (!userRepository.existsById(request.getSenderId())) {
            throw new UserNotFoundException("Sender with ID " + request.getSenderId() + " does not exist.");
        }
        if (!userRepository.existsById(request.getReceiverId())) {
            throw new UserNotFoundException("Receiver with ID " + request.getReceiverId() + " does not exist.");
        }
        if (request.getMessageContent().isBlank()) {
            throw new IllegalArgumentException("Message content cannot be empty.");
        }
    }

    public Message getMessageIfExists(Long id) {
        return messageRepository.findById(id).orElseThrow(() ->
                new MessageNotFoundException("Message with ID " + id + " does not exist."));
    }
}
