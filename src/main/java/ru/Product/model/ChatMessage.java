package ru.Product.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;
@Entity
@Table(name="chat_message")
@Data
@Builder
@Getter
public class ChatMessage {
    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "message_type")
    @Setter
    private String messageType;

    @Column(name = "message_content")
    @Setter
    private String messageContent;

    @Column(name = "date_time")
    @Setter
    private Timestamp dateTime;
}
