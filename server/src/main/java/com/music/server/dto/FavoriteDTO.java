package com.music.server.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FavoriteDTO {

    private Long id;
    private Long songId;
    private String fileName;
    private String artist;
    private String title;
    private LocalDateTime createdAt;
}
