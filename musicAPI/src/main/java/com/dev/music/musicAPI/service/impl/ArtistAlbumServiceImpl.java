package com.dev.music.musicAPI.service.impl;

import com.dev.music.musicAPI.dto.response.ArtistAlbumResponseType;
import com.dev.music.musicAPI.entities.ArtistAlbumId;
import com.dev.music.musicAPI.service.ArtistAlbumService;
import org.springframework.stereotype.Service;

@Service
public class ArtistAlbumServiceImpl implements ArtistAlbumService {
    @Override
    public ArtistAlbumResponseType save(ArtistAlbumResponseType artistAlbumResponseType) {
        return null;
    }

    @Override
    public ArtistAlbumResponseType update(ArtistAlbumResponseType artistAlbumResponseType) {
        return null;
    }

    @Override
    public ArtistAlbumResponseType findById(ArtistAlbumId id) {
        return null;
    }

    @Override
    public boolean delete(ArtistAlbumId id) {
        return false;
    }
}
