package com.ilim.app.business.concretes;

import com.ilim.app.business.abstracts.MessageService;
import com.ilim.app.dataAccess.MessageRepository;
import com.ilim.app.entities.Message;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message sendMessage(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + id));
    }

    @Override
    public List<Message> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
