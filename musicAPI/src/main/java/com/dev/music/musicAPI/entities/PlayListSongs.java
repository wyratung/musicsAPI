package com.dev.music.musicAPI.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "play_list_songs")
@Entity

public class PlayListSongs {
    @EmbeddedId
    private PlayListSongId playListSongId;
}
