package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.exception.AlbumNotFoundException;
import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.Genre;
import com.northcoders.recordshopapi.repo.AlbumRepository;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@CacheConfig(cacheNames = "album")
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

//    @Caching(evict = {@CacheEvict(value = "albumcache", key = "#album.album_id")
//    })
@CachePut(value="album")
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
            Optional<Album> album = albumRepository.findByAlbumTitle(albumName);
            return album.orElseThrow(() -> new AlbumNotFoundException("album title does not exist:" + albumName));
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

    @Override
    public List<Album> getAlbumsByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        List<Album> albums = albumRepository.findByReleaseDateBetween(startDate, endDate).get();
        if (albums.isEmpty()) {
            throw new AlbumNotFoundException("No albums exist with that year: " + year);
        }
        return albums;
    }
}
