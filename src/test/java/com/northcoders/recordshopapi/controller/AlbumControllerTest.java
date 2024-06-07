package com.northcoders.recordshopapi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AlbumControllerTest {

    @Autowired
    MockMvc mockMvcController;

    @Mock
    AlbumServiceImpl albumServiceImpl;

    @Test
    @DisplayName("GET /albums")
    public void testGetAllAlbums(){

        enum Genre {
            SOUL,
            ROCK,
            POP,
            HIPHOP,
            HEAVYMETAL
        }

        //ARRANGE
        List<Album> albumsList = new ArrayList<Album>();
        albumsList.add(
                new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5),
                new Album(2L,"The Wind2", "John Doe2", Genre.SOUL, LocalDate.of(2000,12,12),9000,5),
                new Album(3L,"The Wind3", "John Doe3", Genre.SOUL, LocalDate.of(2001,12,12),9000,5),
                new Album(4L,"The Wind4", "John Doe4", Genre.SOUL, LocalDate.of(2002,12,12),9000,5),
                new Album(5L,"The Wind5", "John Doe5", Genre.SOUL, LocalDate.of(2004,12,12),9000,0)
        );

        when(albumServiceImpl.getAllAlbums()).thenReturn(albumsList);
        //ACT
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value(Genre.SOUL));


        //ASSERT

    }


}
