package com.project.backend.services;

import com.project.backend.controllers.dto.ConversationRequest;
import com.project.backend.models.Conversation;
import com.project.backend.models.MessageStatus;
import com.project.backend.models.User;
import com.project.backend.repositories.ConversationRepository;
import com.project.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository repo;
    private final UserRepository userRepo;

    public ConversationService(ConversationRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public Conversation saveMessage(ConversationRequest dto) {
        User sender = userRepo.findById(dto.getSenderId()).orElseThrow();
        User receiver = userRepo.findById(dto.getReceiverId()).orElseThrow();

        Conversation conv = new Conversation();
        conv.setSender(sender);
        conv.setReceiver(receiver);
        conv.setMessage(dto.getMessage());
        conv.setStatus(MessageStatus.SENT);
        conv.setTimestamp(System.currentTimeMillis());

        return repo.save(conv);
    }

    public List<Conversation> getConversationHistory(Long user1, Long user2) {
        return repo.findConversationHistory(user1, user2);
    }
}
