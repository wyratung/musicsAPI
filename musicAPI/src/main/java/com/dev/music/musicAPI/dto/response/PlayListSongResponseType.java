package com.dev.music.musicAPI.dto.response;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class PlayListSongResponseType {
    private SongResponseType songs;
    private PlayListResponseType playLists;
}
