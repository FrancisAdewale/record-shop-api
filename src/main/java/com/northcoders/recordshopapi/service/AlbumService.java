package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.Genre;
import jakarta.persistence.Cacheable;

import java.util.List;
import java.util.Optional;

public interface AlbumService {

    List<Album> getAllAlbums(boolean includeNonStock);
    Album getAlbumById(long id);
    List<Album> getAllAlbumsByArtist(String name);
    List<Album> getAllAlbumsByGenre(Genre genre);
    Album getAlbumByName(String albumName);
    Album postAlbum(Album album);
    String deleteAlbumById(long id);
    List<Album> getAlbumsByYear(int year);
}
