package com.dev.music.musicAPI.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistSongResponseType {
    private ArtistResponseType artists;
    private SongResponseType songs;
}
