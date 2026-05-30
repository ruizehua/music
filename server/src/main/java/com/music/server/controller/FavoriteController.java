package com.music.server.controller;

import com.music.server.dto.ApiResponse;
import com.music.server.dto.FavoriteDTO;
import com.music.server.dto.MusicDTO;
import com.music.server.model.Favorite;
import com.music.server.model.MusicFile;
import com.music.server.repository.FavoriteRepository;
import com.music.server.service.FavoriteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping
    public ApiResponse<List<FavoriteDTO>> getAllFavorites() {
        List<MusicFile> favorites = favoriteService.getAllFavorites();
        
        List<FavoriteDTO> favoriteDTOs = favorites.stream()
                .map(musicFile -> {
                    FavoriteDTO dto = new FavoriteDTO();
                    dto.setSongId(musicFile.getId());
                    dto.setFileName(musicFile.getFileName());
                    dto.setArtist(musicFile.getArtist());
                    dto.setTitle(musicFile.getTitle());
                    favoriteRepository.findBySongId(musicFile.getId())
                            .ifPresent(fav -> {
                                dto.setId(fav.getId());
                                dto.setCreatedAt(fav.getCreatedAt());
                            });
                    return dto;
                })
                .collect(Collectors.toList());
        
        return ApiResponse.success(favoriteDTOs);
    }

    @PostMapping("/{songId}")
    public ApiResponse<Void> addFavorite(@PathVariable Long songId) {
        try {
            favoriteService.addFavorite(songId);
            return ApiResponse.success("添加收藏成功", null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{songId}")
    public ApiResponse<Void> removeFavorite(@PathVariable Long songId) {
        favoriteService.removeFavorite(songId);
        return ApiResponse.success("取消收藏成功", null);
    }

    @GetMapping("/check/{songId}")
    public ApiResponse<Map<String, Boolean>> checkFavorite(@PathVariable Long songId) {
        boolean isFavorite = favoriteService.isFavorite(songId);
        Map<String, Boolean> result = new HashMap<>();
        result.put("isFavorite", isFavorite);
        return ApiResponse.success(result);
    }
}
