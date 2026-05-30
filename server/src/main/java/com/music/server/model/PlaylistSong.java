package com.music.server.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "playlist_songs", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"playlist_id", "song_id"}))
public class PlaylistSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "playlist_id", nullable = false)
    private Long playlistId;

    @Column(name = "song_id", nullable = false)
    private Long songId;

    @Column(nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", insertable = false, updatable = false)
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", insertable = false, updatable = false)
    private MusicFile song;
}
