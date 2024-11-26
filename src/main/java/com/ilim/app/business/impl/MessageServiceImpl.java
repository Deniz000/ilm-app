package com.ilim.app.business.impl;

import com.ilim.app.business.services.MessageService;
import com.ilim.app.business.validationhelper.MessageValidationHelper;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.MessageRepository;
import com.ilim.app.dto.message.MessageResponse;
import com.ilim.app.dto.message.SendMessageRequest;
import com.ilim.app.entities.Message;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageValidationHelper validate;
    private final ModelMapperService modelMapperService;

    @Override
    @Transactional
    public MessageResponse sendMessage(SendMessageRequest request) {
        log.info("Sending message from user ID: {} to user ID: {}", request.getSenderId(), request.getReceiverId());

        validate.validateSendMessageRequest(request);

        Message message = modelMapperService.forRequest().map(request, Message.class);
        messageRepository.save(message);
        log.info("Message sent with ID: {}", message.getId());

        return modelMapperService.forResponse().map(message, MessageResponse.class);
    }

    @Override
    @Transactional()
    public MessageResponse getMessageById(Long id) {
        log.info("Fetching message by ID: {} ", id);
        Message message = validate.getMessageIfExists(id);
        return modelMapperService.forResponse().map(message, MessageResponse.class);
    }

    @Override
    @Transactional()
    public List<MessageResponse> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        log.info("Fetching messages between sender ID: {} and receiver ID: {}", senderId, receiverId);
        List<Message> messages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        return messages.stream()
                .map(m -> modelMapperService.forResponse()
                        .map(m, MessageResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteMessage(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        Message message = validate.getMessageIfExists(id);
        messageRepository.delete(message);
        log.info("Message with ID: {} deleted.", id);
    }
}
