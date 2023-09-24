package com.dev.music.musicAPI.repository;


import com.dev.music.musicAPI.entities.ArtistAlbumId;
import com.dev.music.musicAPI.entities.ArtistAlbums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistAlbumRepository extends JpaRepository<ArtistAlbums, ArtistAlbumId> {

}
