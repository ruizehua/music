package com.music.server.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PlayHistoryDTO {

    private Long id;
    private Long songId;
    private String fileName;
    private String artist;
    private String title;
    private Integer playTime;
    private LocalDateTime createdAt;
}
