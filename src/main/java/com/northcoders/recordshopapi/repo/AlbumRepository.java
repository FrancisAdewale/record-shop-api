package com.northcoders.recordshopapi.repo;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AlbumRepository extends CrudRepository<Album,Long> {
    List<Album> findByArtistName(String artistName);
    List<Album> findAllByGenre(Genre genre);

}
