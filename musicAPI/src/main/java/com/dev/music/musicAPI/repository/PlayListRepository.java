package com.dev.music.musicAPI.repository;

import com.dev.music.musicAPI.entities.PlayLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListRepository extends JpaRepository<PlayLists, Integer> {
}
