package com.northcoders.recordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import com.northcoders.recordshopapi.service.AlbumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AlbumControllerTest {

    @Mock
    AlbumServiceImpl albumServiceImpl;

    @InjectMocks
    AlbumController albumController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(albumController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }


    @Test
    @DisplayName("GET /albums?includeNonStock=false")
    public void testGetAllAlbums() throws Exception {

        //ARRANGE

        List<Album> albumsList = new ArrayList<>();
        albumsList.add(new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(2L,"The Wind2", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(3L,"The Wind3", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(4L,"The Wind4", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(5L,"The Wind5", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,0));
        when(albumServiceImpl.getAllAlbums(false)).thenReturn(albumsList);
        //ACT
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums").param("includeNonStock","false"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value(Genre.SOUL.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].id").doesNotExist());

        //ASSERT

    }

    @Test
    @DisplayName("GET /albums?includeNonStock=true")
    public void testGetAllAlbumsIncludeNonStock() throws Exception {
        // ARRANGE
        List<Album> albumsList = new ArrayList<>();
        albumsList.add(new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(2L,"The Wind2", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(3L,"The Wind3", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(4L,"The Wind4", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(5L,"The Wind5", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,0));
        when(albumServiceImpl.getAllAlbums(true)).thenReturn(albumsList);

        // ACT & ASSERT
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums")
                        .param("includeNonStock", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].albumId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].stockQuantity").value(0));
    }

    @Test
    public void testEmptyResponse() throws Exception {

        when(albumServiceImpl.getAllAlbums(false)).thenReturn(Collections.emptyList());

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums").param("includeNonStock","false"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }


    @Test
    @DisplayName("GET /albums/{id}")
    public void testGetAlbumById() throws Exception {

        //ARRANGE
       Album album =  new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5);

        when(albumServiceImpl.getAlbumById(1)).thenReturn(album);
        //ACT
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(Genre.SOUL.name()));

        //ASSERT

    }

    @Test
    @DisplayName("GET /albums/{id}")
    public void testGetAlbumByWrongId() throws Exception {

        //ARRANGE
        Album album =  new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5);

        when(albumServiceImpl.getAlbumById(7)).thenReturn(null);
        //ACT
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));


        //ASSERT

    }

    @Test
    @DisplayName("GET /albums")
    public void testGetAllAlbumsWithoutParam() throws Exception {

        //ARRANGE

        List<Album> albumsList = new ArrayList<>();
        albumsList.add(new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(2L,"The Wind2", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(3L,"The Wind3", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(4L,"The Wind4", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(5L,"The Wind5", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,0));
        when(albumServiceImpl.getAllAlbums(false)).thenReturn(albumsList);
        //ACT
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //ASSERT

    }

    @Test
    @DisplayName("GET /artist?name={}")
    public void testGetAllAlbumsByArtist() throws Exception {

        //ARRANGE
        List<Album> mikeAlbums = new ArrayList<>();
        mikeAlbums.add(new Album(3L, "The Wind3", "Mike", Genre.SOUL, LocalDate.of(1999, 12, 12), 9000, 5));

        when(albumServiceImpl.getAllAlbumsByArtist("Mike")).thenReturn(mikeAlbums);
        //ACT
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/artist").param("name","Mike"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artistName").value("Mike"));

        //ASSERT

    }


    @Test
    @DisplayName("GET /genre?name={}")
    public void testGetAllAlbumsByGenre() throws Exception {

        List<Album> albumsList = new ArrayList<>();
        albumsList.add(new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(2L,"The Wind2", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(3L,"The Wind3", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(4L,"The Wind4", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(5L,"The Wind5", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,0));
        when(albumServiceImpl.getAllAlbumsByGenre(Genre.SOUL)).thenReturn(albumsList);
        //ACT

        //ACT
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/genre").param("name","SOUL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].genre").value("SOUL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].albumId").value(5));

        //ASSERT

    }

}
