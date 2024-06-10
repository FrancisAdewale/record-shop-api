package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;

import java.util.List;

public interface AlbumService {

    List<Album> getAllAlbums(boolean includeNonStock);
    Album getAlbumById(long id);
    List<Album> getAllAlbumsByArtist(String name);
    List<Album> getAllAlbumsByGenre(Genre genre);
    Album getAlbumByName(String albumName);
    Album postAlbum(Album album);
    String deleteAlbumById(long id);
}
