package com.music.server.service;

import com.music.server.model.MusicFile;
import com.music.server.repository.MusicFileRepository;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MusicService {

    private static final List<String> SUPPORTED_FORMATS = Arrays.asList("mp3", "wav", "flac", "aac", "ogg");

    @Autowired
    private MusicFileRepository musicFileRepository;

    @Value("${music.storage.path}")
    private String musicStoragePath;

    public Page<MusicFile> getMusicList(int page, int size, String keyword, String artist, String album) {
        Pageable pageable = PageRequest.of(page, size);
        return musicFileRepository.searchMusicFiles(keyword, artist, album, pageable);
    }

    public Optional<MusicFile> getMusicById(Long id) {
        return musicFileRepository.findById(id);
    }

    @Transactional
    public MusicFile saveMusicFile(MusicFile musicFile) {
        LocalDateTime now = LocalDateTime.now();
        if (musicFile.getId() == null) {
            musicFile.setCreatedAt(now);
        }
        musicFile.setUpdatedAt(now);
        return musicFileRepository.save(musicFile);
    }

    @Transactional
    public void deleteMusic(Long id) {
        musicFileRepository.deleteById(id);
    }

    public List<MusicFile> searchMusic(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return musicFileRepository.searchByKeyword(keyword.trim());
    }

    @Transactional
    public void scanMusicFiles() {
        Path rootPath = Paths.get(musicStoragePath);
        if (!Files.exists(rootPath)) {
            return;
        }
        scanDirectory(rootPath.toFile());
    }

    private void scanDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file);
            } else if (file.isFile()) {
                processMusicFile(file);
            }
        }
    }

    private void processMusicFile(File file) {
        String fileName = file.getName();
        String extension = getFileExtension(fileName);
        
        if (extension == null || !SUPPORTED_FORMATS.contains(extension.toLowerCase())) {
            return;
        }

        String filePath = file.getAbsolutePath();
        
        if (musicFileRepository.existsByFilePath(filePath)) {
            return;
        }

        MusicFile musicFile = new MusicFile();
        musicFile.setFileName(fileName);
        musicFile.setFilePath(filePath);
        musicFile.setFileSize(file.length());
        musicFile.setFormat(extension.toLowerCase());

        extractMetadata(file, musicFile);

        musicFile.setCreatedAt(LocalDateTime.now());
        musicFile.setUpdatedAt(LocalDateTime.now());
        
        musicFileRepository.save(musicFile);
    }

    private void extractMetadata(File file, MusicFile musicFile) {
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            AudioHeader audioHeader = audioFile.getAudioHeader();
            Tag tag = audioFile.getTag();

            int duration = audioHeader.getTrackLength();
            musicFile.setDuration(duration);

            if (tag != null) {
                String title = tag.getFirst(FieldKey.TITLE);
                String artist = tag.getFirst(FieldKey.ARTIST);
                String album = tag.getFirst(FieldKey.ALBUM);

                musicFile.setTitle(title != null && !title.isEmpty() ? title : getFileNameWithoutExtension(file.getName()));
                musicFile.setArtist(artist != null && !artist.isEmpty() ? artist : "未知艺术家");
                musicFile.setAlbum(album != null && !album.isEmpty() ? album : "未知专辑");
            } else {
                musicFile.setTitle(getFileNameWithoutExtension(file.getName()));
                musicFile.setArtist("未知艺术家");
                musicFile.setAlbum("未知专辑");
            }
        } catch (Exception e) {
            musicFile.setTitle(getFileNameWithoutExtension(file.getName()));
            musicFile.setArtist("未知艺术家");
            musicFile.setAlbum("未知专辑");
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return null;
    }

    private String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    public Resource streamMusic(Long id) {
        Optional<MusicFile> musicFileOpt = musicFileRepository.findById(id);
        if (musicFileOpt.isEmpty()) {
            return null;
        }

        MusicFile musicFile = musicFileOpt.get();
        File file = new File(musicFile.getFilePath());
        
        if (!file.exists()) {
            return null;
        }

        return new FileSystemResource(file);
    }
}
