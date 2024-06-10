package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.exception.AlbumNotFoundException;
import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.Genre;
import com.northcoders.recordshopapi.repo.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            return albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException("Invalid Id: " + id));
    }

    @Override
    public List<Album> getAllAlbumsByArtist(String name) {
        return albumRepository.findByArtistName(name)
                .filter(albums -> !albums.isEmpty())
                .orElseThrow(() -> new AlbumNotFoundException("Invalid artist name: " + name));
    }

    @Override
    public List<Album> getAllAlbumsByGenre(Genre genre) {
        return albumRepository.findAllByGenre(genre).get();
    }

    @Override
    public Album getAlbumByName(String albumName) {
            return albumRepository.findByAlbumTitle(albumName)
                    .orElseThrow(() -> new AlbumNotFoundException("Can't find album name: " + albumName));

    }

    @Override
    public Album postAlbum(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public String deleteAlbumById(long id) {
        Optional<Album> album = albumRepository.findById(id);
        if(album.isPresent()) {
            albumRepository.deleteById(id);
            return "Album deleted";
        } else {
            throw new AlbumNotFoundException("Can't find album Id: " + id);
        }

    }
}
