package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlbumService {

    List<Album> getAllAlbums(boolean includeNonStock);
    Album getAlbumById(long id);
    List<Album> getAllAlbumsByArtist(String name);
}
