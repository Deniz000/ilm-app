package com.ilim.app.business.services;

import com.ilim.app.entities.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Message message);
    Message getMessageById(Long id);
    List<Message> getMessagesBetweenUsers(Long senderId, Long receiverId);
    void deleteMessage(Long id);
}
