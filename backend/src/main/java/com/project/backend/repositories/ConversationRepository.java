package com.project.backend.repositories;




import com.project.backend.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c WHERE " +
           "(c.sender.id = :user1 AND c.receiver.id = :user2) OR " +
           "(c.sender.id = :user2 AND c.receiver.id = :user1) " +
           "ORDER BY c.timestamp ASC")
    List<Conversation> findConversationHistory(@Param("user1") Long user1,
                                               @Param("user2") Long user2);
}
