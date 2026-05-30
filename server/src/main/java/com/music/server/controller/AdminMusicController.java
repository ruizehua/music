package com.music.server.controller;

import com.music.server.dto.ApiResponse;
import com.music.server.dto.MusicDTO;
import com.music.server.model.MusicFile;
import com.music.server.service.MusicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/music")
public class AdminMusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping
    public ApiResponse<Map<String, Object>> getMusicList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String album) {
        
        Page<MusicFile> musicPage = musicService.getMusicList(page, size, keyword, artist, album);
        
        List<MusicDTO> musicDTOList = musicPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        Map<String, Object> result = new HashMap<>();
        result.put("content", musicDTOList);
        result.put("totalElements", musicPage.getTotalElements());
        result.put("totalPages", musicPage.getTotalPages());
        result.put("currentPage", musicPage.getNumber());
        result.put("size", musicPage.getSize());
        
        return ApiResponse.success(result);
    }

    @GetMapping("/{id}")
    public ApiResponse<MusicDTO> getMusicById(@PathVariable Long id) {
        return musicService.getMusicById(id)
                .map(musicFile -> ApiResponse.success(convertToDTO(musicFile)))
                .orElse(ApiResponse.error(404, "音乐不存在"));
    }

    @PostMapping
    public ApiResponse<MusicDTO> uploadMusic(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ApiResponse.error(400, "上传文件不能为空");
            }
            
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                return ApiResponse.error(400, "文件名不能为空");
            }
            
            String extension = getFileExtension(originalFilename);
            if (extension == null || !isSupportedFormat(extension)) {
                return ApiResponse.error(400, "不支持的文件格式");
            }
            
            String uploadPath = System.getProperty("user.dir") + "/uploads/music";
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path filePath = uploadDir.resolve(fileName);
            file.transferTo(filePath.toFile());
            
            MusicFile musicFile = new MusicFile();
            musicFile.setFileName(originalFilename);
            musicFile.setFilePath(filePath.toString());
            musicFile.setFileSize(file.getSize());
            musicFile.setFormat(extension.toLowerCase());
            musicFile.setTitle(getFileNameWithoutExtension(originalFilename));
            musicFile.setArtist("未知艺术家");
            musicFile.setAlbum("未知专辑");
            musicFile.setCreatedAt(LocalDateTime.now());
            musicFile.setUpdatedAt(LocalDateTime.now());
            
            MusicFile savedMusic = musicService.saveMusicFile(musicFile);
            
            return ApiResponse.success(convertToDTO(savedMusic));
        } catch (IOException e) {
            return ApiResponse.error(500, "文件上传失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<MusicDTO> updateMusic(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        
        return musicService.getMusicById(id)
                .map(musicFile -> {
                    if (request.containsKey("title")) {
                        musicFile.setTitle(request.get("title"));
                    }
                    if (request.containsKey("artist")) {
                        musicFile.setArtist(request.get("artist"));
                    }
                    if (request.containsKey("album")) {
                        musicFile.setAlbum(request.get("album"));
                    }
                    musicFile.setUpdatedAt(LocalDateTime.now());
                    
                    MusicFile updatedMusic = musicService.saveMusicFile(musicFile);
                    return ApiResponse.success(convertToDTO(updatedMusic));
                })
                .orElse(ApiResponse.error(404, "音乐不存在"));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMusic(@PathVariable Long id) {
        if (musicService.getMusicById(id).isEmpty()) {
            return ApiResponse.error(404, "音乐不存在");
        }
        
        musicService.deleteMusic(id);
        return ApiResponse.success("删除成功", null);
    }

    @DeleteMapping("/batch")
    public ApiResponse<Void> batchDeleteMusic(@RequestBody Map<String, List<Long>> request) {
        List<Long> ids = request.get("ids");
        
        if (ids == null || ids.isEmpty()) {
            return ApiResponse.error(400, "请选择要删除的音乐");
        }
        
        for (Long id : ids) {
            if (musicService.getMusicById(id).isPresent()) {
                musicService.deleteMusic(id);
            }
        }
        
        return ApiResponse.success("批量删除成功", null);
    }

    @PostMapping("/scan")
    public ApiResponse<Void> scanMusicFiles() {
        musicService.scanMusicFiles();
        return ApiResponse.success("扫描完成", null);
    }

    private MusicDTO convertToDTO(MusicFile musicFile) {
        MusicDTO dto = new MusicDTO();
        BeanUtils.copyProperties(musicFile, dto);
        return dto;
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

    private boolean isSupportedFormat(String extension) {
        String lowerExt = extension.toLowerCase();
        return lowerExt.equals("mp3") || lowerExt.equals("wav") || 
               lowerExt.equals("flac") || lowerExt.equals("aac") || lowerExt.equals("ogg");
    }
}
