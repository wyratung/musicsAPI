package com.dev.music.musicAPI.service.impl;

import com.dev.music.musicAPI.dto.converter.ArtistConverter;
import com.dev.music.musicAPI.dto.response.ArtistSongResponseType;
import com.dev.music.musicAPI.repository.ArtistRepository;
import com.dev.music.musicAPI.service.ArtistSongService;
import org.springframework.stereotype.Service;

@Service
public class ArtistSongServiceImpl implements ArtistSongService {
    private ArtistRepository artistRepository;
    private ArtistConverter artistConverter;

    @Override
    public ArtistSongResponseType save(ArtistSongResponseType artistSongResponseType) {
//        Optional
        return null;
    }

    @Override
    public ArtistSongResponseType update(ArtistSongResponseType artistSongResponseType) {
        return null;
    }

    @Override
    public ArtistSongResponseType findById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
