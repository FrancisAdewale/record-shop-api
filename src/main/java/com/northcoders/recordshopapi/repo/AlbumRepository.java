package com.northcoders.recordshopapi.repo;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends CrudRepository<Album,Long> {
    Optional<List<Album>> findByArtistName(String artistName);
    List<Album> findAllByGenre(Genre genre);
    Optional<Album> findByAlbumTitle(String title);

}
