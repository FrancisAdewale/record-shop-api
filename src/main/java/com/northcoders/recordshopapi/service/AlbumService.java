package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlbumService {

    public List<Album> getAllAlbums();
    public Album getAlbumById(long id);
}
