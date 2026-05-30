package com.music.server.repository;

import com.music.server.model.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {

    List<PlaylistSong> findByPlaylistIdOrderByPositionAsc(Long playlistId);

    Optional<PlaylistSong> findByPlaylistIdAndSongId(Long playlistId, Long songId);

    boolean existsByPlaylistIdAndSongId(Long playlistId, Long songId);

    @Query("SELECT COALESCE(MAX(ps.position), 0) FROM PlaylistSong ps WHERE ps.playlistId = :playlistId")
    Integer findMaxPositionByPlaylistId(@Param("playlistId") Long playlistId);

    @Modifying
    @Query("DELETE FROM PlaylistSong ps WHERE ps.playlistId = :playlistId AND ps.songId = :songId")
    void deleteByPlaylistIdAndSongId(@Param("playlistId") Long playlistId, @Param("songId") Long songId);

    void deleteByPlaylistId(Long playlistId);
}
