package com.music.server.controller;

import com.music.server.dto.ApiResponse;
import com.music.server.repository.FavoriteRepository;
import com.music.server.repository.MusicFileRepository;
import com.music.server.repository.PlayHistoryRepository;
import com.music.server.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/stats")
public class AdminStatsController {

    @Autowired
    private MusicFileRepository musicFileRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PlayHistoryRepository playHistoryRepository;

    @Value("${music.storage.path:./uploads/music}")
    private String musicStoragePath;

    @GetMapping("/overview")
    public ApiResponse<Map<String, Object>> getOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        long totalMusic = musicFileRepository.count();
        overview.put("totalMusic", totalMusic);

        long totalPlaylist = playlistRepository.count();
        overview.put("totalPlaylist", totalPlaylist);

        overview.put("totalUser", 1L);

        long totalStorage = calculateTotalStorage();
        overview.put("totalStorage", totalStorage);
        overview.put("storageUsed", totalStorage);

        long todayPlayCount = calculateTodayPlayCount();
        overview.put("todayPlayCount", todayPlayCount);
        
        return ApiResponse.success(overview);
    }

    @GetMapping("/storage")
    public ApiResponse<Map<String, Object>> getStorageStats() {
        Map<String, Object> storageStats = new HashMap<>();
        
        long totalStorage = calculateTotalStorage();
        storageStats.put("totalStorage", totalStorage);
        storageStats.put("totalStorageFormatted", formatFileSize(totalStorage));
        
        long musicCount = musicFileRepository.count();
        storageStats.put("musicCount", musicCount);
        
        double averageSize = musicCount > 0 ? (double) totalStorage / musicCount : 0;
        storageStats.put("averageSize", (long) averageSize);
        storageStats.put("averageSizeFormatted", formatFileSize((long) averageSize));
        
        long availableSpace = calculateAvailableSpace();
        storageStats.put("availableSpace", availableSpace);
        storageStats.put("availableSpaceFormatted", formatFileSize(availableSpace));
        
        return ApiResponse.success(storageStats);
    }

    @GetMapping("/play-trend")
    public ApiResponse<List<Map<String, Object>>> getPlayTrend(@RequestParam(defaultValue = "7") int days) {
        List<Map<String, Object>> trend = new ArrayList<>();
        for (int i = days - 1; i >= 0; i--) {
            Map<String, Object> dayData = new HashMap<>();
            LocalDate date = LocalDate.now().minusDays(i);
            dayData.put("date", date.toString());
            dayData.put("count", 0);
            trend.add(dayData);
        }
        return ApiResponse.success(trend);
    }

    @GetMapping("/popular-music")
    public ApiResponse<List<Map<String, Object>>> getPopularMusic(@RequestParam(defaultValue = "10") int limit) {
        List<Map<String, Object>> popular = new ArrayList<>();
        return ApiResponse.success(popular);
    }

    private long calculateTotalStorage() {
        long totalSize = 0;
        
        Path storagePath = Paths.get(musicStoragePath);
        File storageDir = storagePath.toFile();
        
        if (storageDir.exists() && storageDir.isDirectory()) {
            totalSize = calculateDirectorySize(storageDir);
        }
        
        return totalSize;
    }

    private long calculateDirectorySize(File directory) {
        long size = 0;
        File[] files = directory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    size += file.length();
                } else if (file.isDirectory()) {
                    size += calculateDirectorySize(file);
                }
            }
        }
        
        return size;
    }

    private long calculateAvailableSpace() {
        Path storagePath = Paths.get(musicStoragePath);
        File storageDir = storagePath.toFile();
        
        if (storageDir.exists()) {
            return storageDir.getUsableSpace();
        }
        
        File root = storageDir.getAbsoluteFile();
        while (root.getParentFile() != null) {
            root = root.getParentFile();
        }
        
        return root.getUsableSpace();
    }

    private long calculateTodayPlayCount() {
        return playHistoryRepository.count();
    }

    private String formatFileSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
        }
    }
}
