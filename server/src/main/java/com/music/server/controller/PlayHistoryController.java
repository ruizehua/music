package com.music.server.controller;

import com.music.server.dto.ApiResponse;
import com.music.server.dto.PlayHistoryDTO;
import com.music.server.model.MusicFile;
import com.music.server.model.PlayHistory;
import com.music.server.repository.MusicFileRepository;
import com.music.server.service.PlayHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/history")
public class PlayHistoryController {

    @Autowired
    private PlayHistoryService playHistoryService;

    @Autowired
    private MusicFileRepository musicFileRepository;

    @GetMapping
    public ApiResponse<List<PlayHistoryDTO>> getPlayHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        List<PlayHistory> histories = playHistoryService.getPlayHistory(page, size);
        
        List<PlayHistoryDTO> historyDTOs = histories.stream()
                .map(history -> {
                    PlayHistoryDTO dto = new PlayHistoryDTO();
                    dto.setId(history.getId());
                    dto.setSongId(history.getSongId());
                    dto.setPlayTime(history.getPlayTime());
                    dto.setCreatedAt(history.getCreatedAt());
                    
                    Optional<MusicFile> musicFileOpt = musicFileRepository.findById(history.getSongId());
                    if (musicFileOpt.isPresent()) {
                        MusicFile musicFile = musicFileOpt.get();
                        dto.setFileName(musicFile.getFileName());
                        dto.setArtist(musicFile.getArtist());
                        dto.setTitle(musicFile.getTitle());
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());
        
        return ApiResponse.success(historyDTOs);
    }

    @PostMapping
    public ApiResponse<Void> addPlayHistory(@RequestBody Map<String, Object> request) {
        Long songId = request.get("songId") != null ? Long.valueOf(request.get("songId").toString()) : null;
        Integer playTime = request.get("playTime") != null ? Integer.valueOf(request.get("playTime").toString()) : null;
        
        if (songId == null) {
            return ApiResponse.error(400, "歌曲ID不能为空");
        }
        
        try {
            playHistoryService.addPlayHistory(songId, playTime);
            return ApiResponse.success("添加播放历史成功", null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @DeleteMapping
    public ApiResponse<Void> clearPlayHistory() {
        playHistoryService.clearPlayHistory();
        return ApiResponse.success("清空播放历史成功", null);
    }
}
