package com.music.server.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "play_histories")
public class PlayHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long songId;

    private Integer playTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
