package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import com.northcoders.recordshopapi.repo.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService{

    @Autowired
    AlbumRepository albumRepository;


    @Override
    public List<Album> getAllAlbums(boolean includeNonStock) {

        if(includeNonStock) {
            return (List<Album>) albumRepository.findAll();
        } else {
            List<Album> returnList = new ArrayList<>();
            List<Album> albums = (List<Album>) albumRepository.findAll();
            albums.stream().filter(a-> a.getStockQuantity() > 0).forEach(returnList::add);
            return returnList;
        }

    }

    @Override
    public Album getAlbumById(long id) {
        if(albumRepository.findById(id).isPresent()) {
            return albumRepository.findById(id).get();
        }
     return null;
    }

    @Override
    public List<Album> getAllAlbumsByArtist(String name) {
        return albumRepository.findByArtistName(name);
    }

    @Override
    public List<Album> getAllAlbumsByGenre(Genre genre) {
        return albumRepository.findAllByGenre(genre);
    }

    @Override
    public Album getAlbumByName(String albumName) {
        if(albumRepository.findByAlbumTitle(albumName).isPresent())  {
            return albumRepository.findByAlbumTitle(albumName).get();

        }
        return null;
    }

    @Override
    public Album postAlbum(Album album) {
        return albumRepository.save(album);
    }
}
