package com.music.server.service;

import com.music.server.model.Favorite;
import com.music.server.model.MusicFile;
import com.music.server.repository.FavoriteRepository;
import com.music.server.repository.MusicFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MusicFileRepository musicFileRepository;

    public List<MusicFile> getAllFavorites() {
        List<Favorite> favorites = favoriteRepository.findAllByOrderByCreatedAtDesc();
        return favorites.stream()
                .map(fav -> musicFileRepository.findById(fav.getSongId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addFavorite(Long songId) {
        if (!musicFileRepository.existsById(songId)) {
            throw new IllegalArgumentException("歌曲不存在");
        }

        if (favoriteRepository.existsBySongId(songId)) {
            return;
        }

        Favorite favorite = new Favorite();
        favorite.setSongId(songId);
        favorite.setCreatedAt(LocalDateTime.now());
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(Long songId) {
        favoriteRepository.deleteBySongId(songId);
    }

    public boolean isFavorite(Long songId) {
        return favoriteRepository.existsBySongId(songId);
    }
}
