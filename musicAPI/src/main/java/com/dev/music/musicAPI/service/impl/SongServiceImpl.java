package com.dev.music.musicAPI.service.impl;

import com.dev.music.musicAPI.dto.converter.SongConverter;
import com.dev.music.musicAPI.dto.request.PaginationRequest;
import com.dev.music.musicAPI.dto.response.SongResponseType;
import com.dev.music.musicAPI.entities.Songs;
import com.dev.music.musicAPI.exception.NotFoundEntityException;
import com.dev.music.musicAPI.repository.ArtistSongRepository;
import com.dev.music.musicAPI.repository.SongRepository;
import com.dev.music.musicAPI.service.SongService;
import com.dev.music.musicAPI.ultils.Constraints;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final SongConverter songConverter;
    private final ArtistSongRepository artistSongRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, SongConverter songConverter, ArtistSongRepository artistSongRepository) {
        this.songRepository = songRepository;
        this.songConverter = songConverter;
        this.artistSongRepository = artistSongRepository;
    }

    @Override
    public SongResponseType save(SongResponseType songResponseType) {
        return null;
    }

    @Override
    public SongResponseType update(SongResponseType songResponseType) {
        return null;
    }

    @Override
    public SongResponseType findById(Integer id) {
        Optional<Songs> song = songRepository.findById(id);
        if (song.isPresent()) {
            return songConverter.convertToDTO(song.get());
        }
        throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Songs> songs = songRepository.findById(id);
        if (songs.isPresent()) {
            songRepository.deleteById(id);
            return true;
        } else if (!songs.isPresent()) {
            throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
        }
        return false;
    }

    @Override
    public Map<String, Object> paginationSongs(PaginationRequest paginationRequest) {
        Pageable pageable = null;
        Map<String, Object> result = new HashMap<>();
        if (paginationRequest.getPage() > 0) {
            pageable = PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize());
        }
        if (paginationRequest.getOrder().equals("asc")) {
            pageable = PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize(), Sort.by(paginationRequest.getField()).ascending());
        }
        if (paginationRequest.getOrder().equals("desc")) {
            pageable = PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize(), Sort.by(paginationRequest.getField()).descending());
        }
        Page<Songs> songsPage = songRepository.paginationSongs(pageable, paginationRequest.getSearch());
        List<SongResponseType> songResponseTypes = songsPage.toList().stream().map(songs -> songConverter.convertToDTO(songs)).collect(Collectors.toList());
        result.put("songs", songResponseTypes);
        result.put("totalPages", songsPage.getTotalPages());
        result.put("totalElements", songsPage.getTotalElements());
        result.put("currentPage", paginationRequest.getPage());
        return result;
    }

    @Override
    public SongResponseType saveSong(SongResponseType songResponseType) {
        Optional<Songs> songOptional = Optional.ofNullable(songRepository.findById(songResponseType.getId() == null ? 0 : songResponseType.getId()).orElse(null));
        Songs song = songConverter.convertToEntity(songResponseType);
        Songs songSave;
        SongResponseType songResponsetype;
        if (!songOptional.isPresent()) {
            songSave = songRepository.save(song);
            songResponsetype = songConverter.convertToDTO(songSave);
        } else {
            songSave = songOptional.get();
            if (null != song.getArtistSongs()) {
                if (song.getArtistSongs().size() != songSave.getArtistSongs().size()) {
                    if (songSave.getArtistSongs().size() > 0) {
                        artistSongRepository.deleteArtistSongs(songSave.getId());
                    }
                    songSave.setArtistSongs(song.getArtistSongs());
                }
            }
            songSave.setGenres(song.getGenres());
            songSave.setTitle(song.getTitle());
            if (null != song.getTimePlay()) {
                if (songSave.getTimePlay() != song.getTimePlay()) {
                    songSave.setTimePlay(song.getTimePlay());
                }
            }
            songSave.setLyrics(song.getLyrics());
            songSave.setDescription(song.getDescription());
            songSave.setCountListen(song.getCountListen());

            if (!StringUtils.isEmpty(song.getImage())) {
                songSave.setImage(song.getImage());
            }
            if (!StringUtils.isEmpty(song.getMediaUrl())) {
                songSave.setMediaUrl(song.getMediaUrl());
            }
            if (null != songResponseType.getAlbumSongs()) {
                if (songSave.getAlbumSongs().size() != song.getAlbumSongs().size()) {
                    songSave.setAlbumSongs(song.getAlbumSongs());
                }
            }
            if (songSave.getGenres().getId() != song.getGenres().getId()) {
                songSave.setGenres(song.getGenres());
            }
            songSave.setDownloadPermission(song.getDownloadPermission());
            songSave.setArtistSongs(song.getArtistSongs());
            songResponsetype = songConverter.convertToDTO(songRepository.save(songSave));
        }
        return songResponsetype;
    }

    @Override
    public List<SongResponseType> getTop15SongsPopular() {
        List<Songs> list15BestSongs = songRepository.getTop15SongPopular();
        List<SongResponseType> songResponseTypes = list15BestSongs.stream().map(songs -> songConverter.convertToDTO(songs)).collect(Collectors.toList());
        return songResponseTypes;
    }

    @Transactional
    @Override
    public boolean deleteListSong(List<Integer> listSongIds) {
        AtomicBoolean isSuccess = new AtomicBoolean(true);
        if (null != listSongIds) {
            listSongIds.forEach(id -> {
                Optional<Songs> songs = songRepository.findById(id);
                if (songs.isPresent()) {
                    songRepository.deleteById(id);
                    isSuccess.set(true);
                } else {
                    isSuccess.set(false);
                }
            });
        }
        return false;
    }
}
