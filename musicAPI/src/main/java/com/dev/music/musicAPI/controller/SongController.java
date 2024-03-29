package com.dev.music.musicAPI.controller;

import com.dev.music.musicAPI.dto.request.PaginationRequest;
import com.dev.music.musicAPI.dto.response.SongResponseType;
import com.dev.music.musicAPI.service.SongService;
import com.dev.music.musicAPI.service.impl.FileStorageServiceImpl;
import com.dev.music.musicAPI.ultils.GenericsHelper;
import com.dev.music.musicAPI.ultils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/songs")
public class SongController {
    private final FileStorageServiceImpl fileStorageService;
    private final SongService songService;
    Logger log = (Logger) LoggerFactory.getLogger("Song");
    @Autowired
    public SongController(SongService songService, FileStorageServiceImpl fileStorageService) {
        this.songService = songService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> paginationSongs(@RequestBody PaginationRequest paginationRequest) throws JsonProcessingException {
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("SongController - paginationSongs - start - request: ["+reqStartTime+"]");
        log.info("paginationRequest: "+ GenericsHelper.ObjectToJsonValue(paginationRequest));
        ResponseEntity<Map<String, Object>> response = new ResponseEntity<>(songService.paginationSongs(paginationRequest), HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("SongController - paginationSongs - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "+GenericsHelper.ObjectToJsonValue(response));
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponseType> findByIdSong(@PathVariable("id") int id) throws JsonProcessingException {
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("SongController - findByIdSong - start - request: ["+reqStartTime+"]");
        log.info("Song ID Request: "+id);
        ResponseEntity<SongResponseType> pResponse = new ResponseEntity<>(songService.findById(id), HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("SongController - findByIdSong - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") int id) {
        boolean isDelete = songService.delete(id);
        if (isDelete) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/save")
    public ResponseEntity<SongResponseType> saveSong(@RequestParam("song") String songJson,
                                                     @RequestParam(value = "files") MultipartFile[] files
    ) throws IOException {
        ResponseEntity<SongResponseType> pResponse;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("SongController - saveSong - start - request: ["+reqStartTime+"]");
        log.info("songRequest: "+songJson);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SongResponseType songResponseType = objectMapper.readValue(songJson, SongResponseType.class);
        String mp3 = "";
        String media = "";
        if (null != files) {
            for (int i = 0; i < files.length; i++) {
                if (Utils.getFileExtension(files[i].getOriginalFilename()).equals("mp3")) {
                    mp3 = fileStorageService.storeFile(files[i]);
                } else {
                    media = fileStorageService.storeFile(files[i]);
                }
            }
        }
        songResponseType.setImage(Utils.getUrlFilePathImage(media));
        songResponseType.setMediaUrl(Utils.getUrlFilePathMp3Source(mp3));
        pResponse = new ResponseEntity<>(songService.saveSong(songResponseType), HttpStatus.CREATED);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("SongController - saveSong - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }

    @PostMapping("/update-song/{id}")
    public ResponseEntity<SongResponseType> updateSong(@RequestParam("song") String songJson,
                                                       @RequestParam(value = "files", required = false) MultipartFile[] files,
                                                       @PathVariable("id") Integer id
    ) throws IOException {
        ResponseEntity<SongResponseType> pResponse;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("SongController - updateSong - start - request: ["+reqStartTime+"]");
        log.info("songRequest: "+songJson);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SongResponseType songResponseType = objectMapper.readValue(songJson, SongResponseType.class);
        songResponseType.setId(id);
        String mp3 = "";
        String media = "";
        System.out.println(songJson);
        if (null != files) {
            for (int i = 0; i < files.length; i++) {
                if (Utils.getFileExtension(files[i].getOriginalFilename()).equals("mp3")) {
                    mp3 = fileStorageService.storeFile(files[i]);
                } else {
                    media = fileStorageService.storeFile(files[i]);
                }
            }
            songResponseType.setImage(Utils.getUrlFilePathImage(media));
            songResponseType.setMediaUrl(Utils.getUrlFilePathMp3Source(mp3));
        }
        pResponse = new ResponseEntity<>(songService.saveSong(songResponseType), HttpStatus.ACCEPTED);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("SongController - updateSong - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }

    @GetMapping("/top15")
    public ResponseEntity<List<SongResponseType>> getTop15BestSong() throws JsonProcessingException {
        ResponseEntity<List<SongResponseType>> pResponse;
        String reqStartTime = GenericsHelper.getDateTime();
        log.info("SongController - getTop15BestSong - start - request: ["+reqStartTime+"]");
        List<SongResponseType> list = songService.getTop15SongsPopular();
        pResponse = new ResponseEntity<>(list, HttpStatus.OK);
        long milSecSt = System.currentTimeMillis();
        long milSecEnd = System.currentTimeMillis();
        String reqEndTime = GenericsHelper.getDateTime();
        log.info("SongController - getTop15BestSong - end - ReqStartTime[" + reqStartTime + "] ReqEndTime[" + reqEndTime + "] TimeDiffinMillSec["+ GenericsHelper.getDiffMilliSec(milSecSt, milSecEnd) +"] \n- psResponse: "
                +GenericsHelper.ObjectToJsonValue(pResponse));
        return pResponse;
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deleteListSong(@RequestBody List<Integer> listSongId) {
        boolean isDelete = songService.deleteListSong(listSongId);
        if (isDelete) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
