package com.music.server.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PlaylistDTO {

    private Long id;
    private String name;
    private String description;
    private Integer songCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
