package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface AlbumService {

    List<Album> getAllAlbums(boolean includeNonStock);
    Album getAlbumById(long id);
    List<Album> getAllAlbumsByArtist(String name);
    List<Album> getAllAlbumsByGenre(Genre genre);

    Album getAlbumByName(String albumName);
}
