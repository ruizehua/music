package com.music.server.repository;

import com.music.server.model.MusicFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicFileRepository extends JpaRepository<MusicFile, Long> {

    Optional<MusicFile> findByFilePath(String filePath);

    boolean existsByFilePath(String filePath);

    @Query("SELECT m FROM MusicFile m WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR m.title LIKE %:keyword% OR m.artist LIKE %:keyword% OR m.album LIKE %:keyword%) " +
           "AND (:artist IS NULL OR :artist = '' OR m.artist = :artist) " +
           "AND (:album IS NULL OR :album = '' OR m.album = :album)")
    Page<MusicFile> searchMusicFiles(@Param("keyword") String keyword,
                                      @Param("artist") String artist,
                                      @Param("album") String album,
                                      Pageable pageable);

    @Query("SELECT m FROM MusicFile m WHERE " +
           "m.title LIKE %:keyword% OR m.artist LIKE %:keyword% OR m.album LIKE %:keyword% OR m.fileName LIKE %:keyword%")
    List<MusicFile> searchByKeyword(@Param("keyword") String keyword);

    List<MusicFile> findByArtist(String artist);

    List<MusicFile> findByAlbum(String album);
}
