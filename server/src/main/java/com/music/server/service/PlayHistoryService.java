package com.music.server.service;

import com.music.server.model.MusicFile;
import com.music.server.model.PlayHistory;
import com.music.server.repository.MusicFileRepository;
import com.music.server.repository.PlayHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlayHistoryService {

    @Autowired
    private PlayHistoryRepository playHistoryRepository;

    @Autowired
    private MusicFileRepository musicFileRepository;

    public List<PlayHistory> getPlayHistory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlayHistory> historyPage = playHistoryRepository.findAllByOrderByCreatedAtDesc(pageable);
        return historyPage.getContent();
    }

    @Transactional
    public void addPlayHistory(Long songId, Integer playTime) {
        if (!musicFileRepository.existsById(songId)) {
            throw new IllegalArgumentException("歌曲不存在");
        }

        PlayHistory history = new PlayHistory();
        history.setSongId(songId);
        history.setPlayTime(playTime);
        history.setCreatedAt(LocalDateTime.now());
        playHistoryRepository.save(history);
    }

    @Transactional
    public void clearPlayHistory() {
        playHistoryRepository.deleteAll();
    }
}
