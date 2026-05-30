package com.music.server.repository;

import com.music.server.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findAllByOrderByCreatedAtDesc();

    boolean existsByName(String name);
}
