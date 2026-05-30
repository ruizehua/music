package com.music.server.repository;

import com.music.server.model.PlayHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayHistoryRepository extends JpaRepository<PlayHistory, Long> {

    Page<PlayHistory> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<PlayHistory> findTop100ByOrderByCreatedAtDesc();

    @Modifying
    @Query("DELETE FROM PlayHistory")
    void deleteAll();
}
