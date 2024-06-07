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

        when(mockAlbumRepository.findAll()).thenReturn(albumsList);

        var albums = albumsServiceImpl.getAllAlbums();

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

}
