package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.repo.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
