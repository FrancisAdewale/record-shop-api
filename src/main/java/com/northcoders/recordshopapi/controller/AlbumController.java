package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import com.northcoders.recordshopapi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAllAlbums(@RequestParam(name = "includeNonStock") boolean includeNonStock) {
        return new ResponseEntity<>(albumService.getAllAlbums(includeNonStock), HttpStatus.OK);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable long id){
        return new ResponseEntity<>(albumService.getAlbumById(id),HttpStatus.OK);
    }

    @GetMapping("albums/artist")
    public ResponseEntity<List<Album>> getAlLAlbumsByArtist(@RequestParam(name = "name") String name) {
    return new ResponseEntity<>(albumService.getAllAlbumsByArtist(name),HttpStatus.OK);
    }

    @GetMapping("/albums/genre")
    public ResponseEntity<List<Album>> getAllAlbumsByYear(@RequestParam(name = "name") Genre genre ) {
        return new ResponseEntity<>(albumService.getAllAlbumsByGenre(genre),HttpStatus.OK);

    }

    @GetMapping("/albums/title")
    public ResponseEntity<Album> getAllAlbumsByYear(@RequestParam(name = "name") String title ) {
        return new ResponseEntity<>(albumService.getAlbumByName(title),HttpStatus.OK);

    }

    @PostMapping("/albums")
    public ResponseEntity<Album> postNewAlbum(@RequestBody Album album) {
        Album addedAlbum = albumService.postAlbum(album);
        return new ResponseEntity<>(addedAlbum, HttpStatus.CREATED);

    }

    @PutMapping("albums/{id}")
    public ResponseEntity<Album> updateAlbum(@RequestBody Album album, @PathVariable long id) {
        Album dbAlbum = albumService.getAlbumById(id);
        dbAlbum.setAlbumTitle(album.getAlbumTitle());
        dbAlbum.setPrice(album.getPrice());
        dbAlbum.setGenre(album.getGenre());
        dbAlbum.setStockQuantity(album.getStockQuantity());
        dbAlbum.setArtistName(album.getArtistName());
        dbAlbum.setReleaseDate(album.getReleaseDate());
        return new ResponseEntity<>(albumService.postAlbum(dbAlbum),HttpStatus.ACCEPTED);
    }

}
