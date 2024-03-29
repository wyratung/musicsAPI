package com.dev.music.musicAPI.service.impl;

import com.dev.music.musicAPI.dto.converter.AlbumConverter;
import com.dev.music.musicAPI.dto.converter.AlbumSongConverter;
import com.dev.music.musicAPI.dto.request.PaginationRequest;
import com.dev.music.musicAPI.dto.response.AlbumResponseType;
import com.dev.music.musicAPI.dto.response.AlbumSongResponseType;
import com.dev.music.musicAPI.entities.AlbumSongs;
import com.dev.music.musicAPI.entities.Albums;
import com.dev.music.musicAPI.exception.NotFoundEntityException;
import com.dev.music.musicAPI.repository.AlbumRepository;
import com.dev.music.musicAPI.repository.AlbumSongRepository;
import com.dev.music.musicAPI.service.AlbumService;
import com.dev.music.musicAPI.ultils.Constraints;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumConverter albumConverter;
    private AlbumSongRepository albumSongRepository;
    private AlbumSongConverter albumSongConverter;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumConverter albumConverter) {
        this.albumRepository = albumRepository;
        this.albumConverter = albumConverter;
    }

    @Override
    public AlbumResponseType save(AlbumResponseType albumResponseType) throws Exception {
        AlbumResponseType response = null;
        if (StringUtils.isEmpty(albumResponseType.getAlbumName())) {
            throw new IllegalArgumentException("Album name is require");
        }
        Albums albums = albumConverter.convertToEntity(albumResponseType);
        Albums albumSave = albumRepository.save(albums);
        if (null != albumSave) {
            response = albumConverter.convertToAllDependency(albumSave);
        }
        return response;
    }

    @Override
    public AlbumResponseType update(AlbumResponseType albumResponseType) {
        return null;
    }

    @Override
    public AlbumResponseType findById(Integer id) {
        Optional<Albums> album = albumRepository.findById(id);
        if (album.isPresent()) {
            return albumConverter.convertToAllDependency(album.get());
        }
        throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Albums> album = albumRepository.findById(id);
        if (album.isPresent()) {
            albumRepository.deleteById(id);
            return true;
        }
        throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
    }

    @Override
    public Map<String, Object> paginationAlbum(PaginationRequest request) {
        Pageable pageable = null;
        Map<String, Object> result = new HashMap<>();
        if (request.getPage() > 0) {
            pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        }
        if (request.getOrder().equals("asc")) {
            pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getField()).ascending());
        }
        if (request.getOrder().equals("desc")) {
            pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getField()).descending());
        }
        Page<Albums> albumsPage = albumRepository.paginationAlbum(pageable, request.getSearch());
        List<AlbumResponseType> albumResponseTypes = albumsPage.toList().stream().map(albums -> albumConverter.convertToDTO(albums)).collect(Collectors.toList());
        result.put("albums", albumResponseTypes);
        result.put("totalPages", albumsPage.getTotalPages());
        result.put("totalElements", albumsPage.getTotalElements());
        result.put("currentPage", request.getPage());
        return result;
    }

    @Override
    public List<AlbumSongResponseType> getListSongByAlbumId(int albumId) {
        List<AlbumSongs> albumSongs = albumSongRepository.getAllByAlbumId(albumId);
        List<AlbumSongResponseType> albumResponseTypes = albumSongs.stream().map(albumSongs1 -> albumSongConverter.convertToDTO(albumSongs1)).collect(Collectors.toList());
        return albumResponseTypes;
    }

    public AlbumResponseType updateAlbum(AlbumResponseType albumResponseType, Integer id) throws Exception {
        Albums albums = null;
        Optional<Albums> albumsOptional = Optional.ofNullable(albumRepository.findById(id).orElse(null));
        Albums albumsUpdate = albumConverter.convertToEntity(albumResponseType);
        if (albumsOptional.isPresent()) {
            albumRepository.deleteByAlbumSongs(albumsOptional.get().getId());
            albums = albumsOptional.get();
            albums.setArtistAlbums(albumsUpdate.getArtistAlbums());
            albums.setGenres(albumsUpdate.getGenres());
            albums.setAlbumName(albumsUpdate.getAlbumName());
            albums.setTotalListen(albumsUpdate.getTotalListen());
            albums.setImage(albumsUpdate.getImage());
            albums.setArtistAlbums(albumsUpdate.getArtistAlbums());
            albums.setDownloadPermission(albumsUpdate.isDownloadPermission());
            albums.setAlbumSongs(albumsUpdate.getAlbumSongs());
            Albums albumsSave = albumRepository.save(albums);
            return albumConverter.convertToAllDependency(albumsSave);
        }
        return null;
    }

    @Transactional
    @Override
    public Boolean deleteListAlbumId(List<Integer> albumIds) {
        AtomicBoolean isSuccess = new AtomicBoolean(true);
        albumIds.forEach(integer -> {
            Optional<Albums> albums = albumRepository.findById(integer);
            if (albums.isPresent()) {
                albumRepository.delete(albums.get());
                isSuccess.set(true);
            } else {
                isSuccess.set(false);
            }
        });
        return isSuccess.get();
    }
}