package com.music.server.repository;

import com.music.server.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findBySongId(Long songId);

    boolean existsBySongId(Long songId);

    void deleteBySongId(Long songId);

    List<Favorite> findAllByOrderByCreatedAtDesc();
}
