package com.dev.music.musicAPI.repository;

import com.dev.music.musicAPI.entities.HistoryListens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryListenRepository extends JpaRepository<HistoryListens,Integer> {
}
