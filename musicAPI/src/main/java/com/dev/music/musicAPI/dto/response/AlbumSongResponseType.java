package com.dev.music.musicAPI.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumSongResponseType {
    private AlbumResponseType albums;
    private SongResponseType songs;
}
