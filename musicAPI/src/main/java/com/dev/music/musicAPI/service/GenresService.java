package com.dev.music.musicAPI.service;

import com.dev.music.musicAPI.dto.request.PaginationRequest;
import com.dev.music.musicAPI.dto.response.GenresResponseType;

import java.util.Map;

public interface GenresService extends BaseService<GenresResponseType, Integer> {
    Map<String, Object> getSongAndAlbumByGenresId(int genresId,PaginationRequest paginationRequestAlbum,PaginationRequest paginationRequestSong);
    Map<String, Object> paginationGenres(PaginationRequest paginationRequest);
}
