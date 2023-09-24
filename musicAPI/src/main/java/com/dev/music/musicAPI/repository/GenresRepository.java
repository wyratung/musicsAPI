package com.dev.music.musicAPI.repository;

import com.dev.music.musicAPI.entities.Albums;
import com.dev.music.musicAPI.entities.Genres;
import com.dev.music.musicAPI.entities.Songs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends JpaRepository<Genres, Integer> {
    @Query(value = "SELECT g FROM Genres g where g.genresName like %:search%")
    Page<Genres> paginationGenres(Pageable pageable, String search);
    @Query(value = "select a from Albums a where a.genres.id =:genresId and a.albumName like %:search%")
    Page<Albums> getAlbumByGenresId(Pageable pageable, String search, int genresId);
    @Query(value = "select s from Songs s where s.genres.id =:genresId and s.title like %:search%")
    Page<Songs> getSongByGenresId(Pageable pageable, String search, int genresId);
}
