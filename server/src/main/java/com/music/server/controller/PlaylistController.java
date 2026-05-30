package com.music.server.controller;

import com.music.server.dto.ApiResponse;
import com.music.server.dto.MusicDTO;
import com.music.server.dto.PlaylistDTO;
import com.music.server.dto.PlaylistDetailDTO;
import com.music.server.model.MusicFile;
import com.music.server.model.Playlist;
import com.music.server.service.PlaylistService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public ApiResponse<List<PlaylistDTO>> getAllPlaylists() {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        List<PlaylistDTO> playlistDTOs = playlists.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ApiResponse.success(playlistDTOs);
    }

    @GetMapping("/{id}")
    public ApiResponse<PlaylistDetailDTO> getPlaylistById(@PathVariable Long id) {
        return playlistService.getPlaylistById(id)
                .map(playlist -> {
                    PlaylistDetailDTO detailDTO = convertToDetailDTO(playlist);
                    List<MusicFile> songs = playlistService.getPlaylistSongs(id);
                    List<MusicDTO> songDTOs = songs.stream()
                            .map(this::convertMusicToDTO)
                            .collect(Collectors.toList());
                    detailDTO.setSongs(songDTOs);
                    return ApiResponse.success(detailDTO);
                })
                .orElse(ApiResponse.error(404, "播放列表不存在"));
    }

    @PostMapping
    public ApiResponse<PlaylistDTO> createPlaylist(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String description = request.get("description");
        
        if (name == null || name.trim().isEmpty()) {
            return ApiResponse.error(400, "播放列表名称不能为空");
        }
        
        Playlist playlist = playlistService.createPlaylist(name.trim(), description);
        return ApiResponse.success(convertToDTO(playlist));
    }

    @PutMapping("/{id}")
    public ApiResponse<PlaylistDTO> updatePlaylist(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        
        String name = request.get("name");
        String description = request.get("description");
        
        if (name == null || name.trim().isEmpty()) {
            return ApiResponse.error(400, "播放列表名称不能为空");
        }
        
        Playlist playlist = playlistService.updatePlaylist(id, name.trim(), description);
        
        if (playlist == null) {
            return ApiResponse.error(404, "播放列表不存在");
        }
        
        return ApiResponse.success(convertToDTO(playlist));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePlaylist(@PathVariable Long id) {
        if (playlistService.getPlaylistById(id).isEmpty()) {
            return ApiResponse.error(404, "播放列表不存在");
        }
        
        playlistService.deletePlaylist(id);
        return ApiResponse.success("删除成功", null);
    }

    @PostMapping("/{id}/songs")
    public ApiResponse<Void> addSongToPlaylist(
            @PathVariable Long id,
            @RequestBody Map<String, Long> request) {
        
        Long songId = request.get("songId");
        
        if (songId == null) {
            return ApiResponse.error(400, "歌曲ID不能为空");
        }
        
        try {
            playlistService.addSongToPlaylist(id, songId);
            return ApiResponse.success("添加成功", null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{id}/songs/{songId}")
    public ApiResponse<Void> removeSongFromPlaylist(
            @PathVariable Long id,
            @PathVariable Long songId) {
        
        if (playlistService.getPlaylistById(id).isEmpty()) {
            return ApiResponse.error(404, "播放列表不存在");
        }
        
        playlistService.removeSongFromPlaylist(id, songId);
        return ApiResponse.success("移除成功", null);
    }

    private PlaylistDTO convertToDTO(Playlist playlist) {
        PlaylistDTO dto = new PlaylistDTO();
        BeanUtils.copyProperties(playlist, dto);
        dto.setSongCount(playlist.getSongs() != null ? playlist.getSongs().size() : 0);
        return dto;
    }

    private PlaylistDetailDTO convertToDetailDTO(Playlist playlist) {
        PlaylistDetailDTO dto = new PlaylistDetailDTO();
        BeanUtils.copyProperties(playlist, dto);
        dto.setSongCount(playlist.getSongs() != null ? playlist.getSongs().size() : 0);
        return dto;
    }

    private MusicDTO convertMusicToDTO(MusicFile musicFile) {
        MusicDTO dto = new MusicDTO();
        BeanUtils.copyProperties(musicFile, dto);
        return dto;
    }
}
