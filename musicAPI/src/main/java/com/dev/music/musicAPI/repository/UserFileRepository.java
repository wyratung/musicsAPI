package com.dev.music.musicAPI.repository;

import com.dev.music.musicAPI.entities.UserFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileRepository extends JpaRepository<UserFiles, Integer> {
}
