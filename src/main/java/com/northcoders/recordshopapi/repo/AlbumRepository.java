package com.northcoders.recordshopapi.repo;

import com.northcoders.recordshopapi.model.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album,Long> {
}
