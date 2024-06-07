package com.northcoders.recordshopapi.repo;

import com.northcoders.recordshopapi.model.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album,Long> {
    List<Album> findByArtistName(String artistName);

}
