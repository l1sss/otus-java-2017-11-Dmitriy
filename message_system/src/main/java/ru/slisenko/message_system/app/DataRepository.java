package ru.slisenko.message_system.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slisenko.message_system.front.model.ChatMessage;

@Repository("dataRepository")
public interface DataRepository extends JpaRepository<ChatMessage, Long> {
}
