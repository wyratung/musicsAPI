package com.dev.music.musicAPI.service;

import com.dev.music.musicAPI.dto.request.PaginationRequest;
import com.dev.music.musicAPI.dto.response.ArtistResponseType;
import com.dev.music.musicAPI.dto.response.ArtistSongResponseType;

import java.util.List;
import java.util.Map;

public interface ArtistService extends BaseService<ArtistResponseType, Integer> {
    Map<String, Object> paginationArtist(PaginationRequest request);

    List<ArtistSongResponseType> getListArtistSongsByArtistId(int artistId);
}
