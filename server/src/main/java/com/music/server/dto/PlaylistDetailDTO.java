package com.music.server.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlaylistDetailDTO {

    private Long id;
    private String name;
    private String description;
    private Integer songCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MusicDTO> songs;
}
