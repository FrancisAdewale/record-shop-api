package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import com.northcoders.recordshopapi.repo.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AlbumServiceTest {

    @Mock
    private AlbumRepository mockAlbumRepository;

    @InjectMocks
    private AlbumServiceImpl albumsServiceImpl;


    @Test
    public void getAllAlbumsTest() {

        List<Album> albumsList = new ArrayList<>();
        albumsList.add(new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(2L,"The Wind2", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(3L,"The Wind3", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(4L,"The Wind4", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(4L,"The Wind4", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,0));

        when(mockAlbumRepository.findAll()).thenReturn(albumsList);

        var albums = albumsServiceImpl.getAllAlbums(false);

        assertThat(albums).isNotNull();
        assertThat(albums).hasSize(4);


    }

    @Test
    public void getAlbumByIdTest() {

       Album album = new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5);
        when(mockAlbumRepository.findById(1L)).thenReturn(Optional.of(album));

        var actualAlbum = albumsServiceImpl.getAlbumById(1);

        assertThat(actualAlbum).isNotNull();
        assertThat(actualAlbum.getAlbumId()).isEqualTo(1);
        assertThat(actualAlbum.getStockQuantity()).isEqualTo(5);

    }

    @Test
    public void getAlbumByInvalidIdTest() {

        when(mockAlbumRepository.findById(7L)).thenReturn(Optional.empty());

        var actualAlbum = albumsServiceImpl.getAlbumById(7);

        assertThat(actualAlbum).isNull();


    }

    @Test
    public void getAllAlbumsByArtistNameTest() {


        List<Album> mikeAlbums = new ArrayList<>();
        mikeAlbums.add(new Album(3L, "The Wind3", "Mike", Genre.SOUL, LocalDate.of(1999, 12, 12), 9000, 5));
        when(mockAlbumRepository.findByArtistName("Mike")).thenReturn(mikeAlbums);

        var actualResult = albumsServiceImpl.getAllAlbumsByArtist("Mike");

        assertThat(actualResult).hasSize(1);


    }

    @Test
    public void getAllAlbumsByGenreTest() {

        List<Album> albumsList = new ArrayList<>();
        albumsList.add(new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(2L,"The Wind2", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(3L,"The Wind3", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(4L,"The Wind4", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(5L,"The Wind4", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,0));
        albumsList.add(new Album(6L,"The Wind4", "John Doe", Genre.POP, LocalDate.of(1999,12,12),9000,0));

        when(albumsServiceImpl.getAllAlbumsByGenre(Genre.SOUL)).thenReturn(albumsList.subList(0,5));

        var actualResult = albumsServiceImpl.getAllAlbumsByGenre(Genre.SOUL);

        assertThat(actualResult).hasSize(5);


    }

    @Test
    public void getAlbumByNameTest() {

        // ARRANGE
        String albumName = "The Wind";
        Album expectedAlbum = new Album(1L, albumName, "John Doe", Genre.SOUL, LocalDate.of(1999, 12, 12), 9000, 5);

        when(mockAlbumRepository.findByAlbumTitle(albumName)).thenReturn(Optional.of(expectedAlbum));

        // ACT
        var actualAlbum = albumsServiceImpl.getAlbumByName(albumName);

        // ASSERT
        assertThat(actualAlbum).isNotNull();
        assertThat(actualAlbum.getAlbumId()).isEqualTo(1L);
        assertThat(actualAlbum.getAlbumTitle()).isEqualTo(albumName);
        assertThat(actualAlbum.getArtistName()).isEqualTo("John Doe");
        assertThat(actualAlbum.getGenre()).isEqualTo(Genre.SOUL);
        assertThat(actualAlbum.getReleaseDate()).isEqualTo(LocalDate.of(1999, 12, 12));
        assertThat(actualAlbum.getPrice()).isEqualTo(9000);
    }


    @Test
    public void postALbumTest() {

        Album postAlbum = new Album(1L, "The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999, 12, 12), 9000, 5);

        when(mockAlbumRepository.save(postAlbum)).thenReturn(postAlbum);

        // ACT
        var actualAlbum = albumsServiceImpl.postAlbum(postAlbum);

        // ASSERT
        assertThat(actualAlbum).isNotNull();
        assertThat(actualAlbum.getAlbumId()).isEqualTo(1L);
        assertThat(actualAlbum.getArtistName()).isEqualTo("John Doe");
        assertThat(actualAlbum.getGenre()).isEqualTo(Genre.SOUL);
        assertThat(actualAlbum.getReleaseDate()).isEqualTo(LocalDate.of(1999, 12, 12));
        assertThat(actualAlbum.getPrice()).isEqualTo(9000);
    }


}
