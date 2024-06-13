package com.northcoders.recordshopapi.repo;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.Genre;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends CrudRepository<Album,Long> {
    Optional<List<Album>> findByArtistName(String artistName);
    Optional<List<Album>> findAllByGenre(Genre genre);
    Optional<Album> findByAlbumTitle(String title);
    List<Album> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);
}
