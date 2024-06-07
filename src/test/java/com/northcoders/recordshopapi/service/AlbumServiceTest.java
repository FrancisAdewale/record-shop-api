package com.northcoders.recordshopapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

@DataJpaTest
public class AlbumServiceTest {

    @Mock
    private AlbumRepository mockAlbumItemRepository;

    @InjectMocks
    private AlbumServiceImpl albumsServiceImpl;

    @Test
    public void getAllAlbumsTest() {

        List<Album> albumsList = new ArrayList<Album>();
        albumsList.add(
                new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5),
                new Album(2L,"The Wind2", "John Doe2", Genre.SOUL, LocalDate.of(2000,12,12),9000,5),
                new Album(3L,"The Wind3", "John Doe3", Genre.SOUL, LocalDate.of(2001,12,12),9000,5),
                new Album(4L,"The Wind4", "John Doe4", Genre.SOUL, LocalDate.of(2002,12,12),9000,5),
                new Album(5L,"The Wind5", "John Doe5", Genre.SOUL, LocalDate.of(2004,12,12),9000,0)
        );

        when(mockAlbumItemRepository.getAllAlbums()).thenReturn(albumsList);

        var albums = albumsServiceImpl.getAllAlbums();

        assertThat(albums).hasSize(5);


    }

}
