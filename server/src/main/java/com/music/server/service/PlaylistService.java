package com.music.server.service;

import com.music.server.model.MusicFile;
import com.music.server.model.Playlist;
import com.music.server.model.PlaylistSong;
import com.music.server.repository.MusicFileRepository;
import com.music.server.repository.PlaylistRepository;
import com.music.server.repository.PlaylistSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistSongRepository playlistSongRepository;

    @Autowired
    private MusicFileRepository musicFileRepository;

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Playlist> getPlaylistById(Long id) {
        return playlistRepository.findById(id);
    }

    @Transactional
    public Playlist createPlaylist(String name, String description) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setCreatedAt(LocalDateTime.now());
        playlist.setUpdatedAt(LocalDateTime.now());
        return playlistRepository.save(playlist);
    }

    @Transactional
    public Playlist updatePlaylist(Long id, String name, String description) {
        Optional<Playlist> playlistOpt = playlistRepository.findById(id);
        if (playlistOpt.isEmpty()) {
            return null;
        }

        Playlist playlist = playlistOpt.get();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setUpdatedAt(LocalDateTime.now());
        return playlistRepository.save(playlist);
    }

    @Transactional
    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

    @Transactional
    public void addSongToPlaylist(Long playlistId, Long songId) {
        if (!playlistRepository.existsById(playlistId)) {
            throw new IllegalArgumentException("播放列表不存在");
        }

        if (!musicFileRepository.existsById(songId)) {
            throw new IllegalArgumentException("歌曲不存在");
        }

        if (playlistSongRepository.existsByPlaylistIdAndSongId(playlistId, songId)) {
            return;
        }

        Integer maxPosition = playlistSongRepository.findMaxPositionByPlaylistId(playlistId);
        int newPosition = (maxPosition != null ? maxPosition : 0) + 1;

        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setPlaylistId(playlistId);
        playlistSong.setSongId(songId);
        playlistSong.setPosition(newPosition);
        playlistSongRepository.save(playlistSong);

        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist != null) {
            playlist.setUpdatedAt(LocalDateTime.now());
            playlistRepository.save(playlist);
        }
    }

    @Transactional
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        playlistSongRepository.deleteByPlaylistIdAndSongId(playlistId, songId);

        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist != null) {
            playlist.setUpdatedAt(LocalDateTime.now());
            playlistRepository.save(playlist);
        }
    }

    public List<MusicFile> getPlaylistSongs(Long playlistId) {
        List<PlaylistSong> playlistSongs = playlistSongRepository.findByPlaylistIdOrderByPositionAsc(playlistId);
        return playlistSongs.stream()
                .map(ps -> musicFileRepository.findById(ps.getSongId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
