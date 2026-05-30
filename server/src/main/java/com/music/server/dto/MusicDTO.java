package com.music.server.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MusicDTO {

    private Long id;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private Integer duration;
    private String format;
    private String artist;
    private String album;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
