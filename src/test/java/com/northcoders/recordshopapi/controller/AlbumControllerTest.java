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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.verify;
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

       Album album =  new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5);

        when(albumServiceImpl.getAlbumById(1)).thenReturn(album);
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(Genre.SOUL.name()));


    }

    @Test
    @DisplayName("GET /albums/{id}")
    public void testGetAlbumByWrongId() throws Exception {

        Album album =  new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5);

        when(albumServiceImpl.getAlbumById(7)).thenReturn(null);
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));



    }

    @Test
    @DisplayName("GET /albums")
    public void testGetAllAlbumsWithoutParam() throws Exception {


        List<Album> albumsList = new ArrayList<>();
        albumsList.add(new Album(1L,"The Wind", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(2L,"The Wind2", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(3L,"The Wind3", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(4L,"The Wind4", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,5));
        albumsList.add(new Album(5L,"The Wind5", "John Doe", Genre.SOUL, LocalDate.of(1999,12,12),9000,0));
        when(albumServiceImpl.getAllAlbums(false)).thenReturn(albumsList);
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }

    @Test
    @DisplayName("GET /artist?name={}")
    public void testGetAllAlbumsByArtist() throws Exception {

        List<Album> mikeAlbums = new ArrayList<>();
        mikeAlbums.add(new Album(3L, "The Wind3", "Mike", Genre.SOUL, LocalDate.of(1999, 12, 12), 9000, 5));

        when(albumServiceImpl.getAllAlbumsByArtist("Mike")).thenReturn(mikeAlbums);
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/artist").param("name","Mike"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artistName").value("Mike"));


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

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/genre").param("name","SOUL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].genre").value("SOUL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].albumId").value(5));


    }

    @Test
    @DisplayName("GET /albums/title?name={albumName}")
    public void testGetAlbumByName() throws Exception {

        String albumName = "The Wind";
        Album expectedAlbum = new Album(1L, albumName, "John Doe", Genre.SOUL, LocalDate.of(1999, 12, 12), 9000, 5);

        when(albumServiceImpl.getAlbumByName(albumName)).thenReturn(expectedAlbum);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/title")
                        .param("name", albumName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumId").value(expectedAlbum.getAlbumId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumTitle").value(expectedAlbum.getAlbumTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artistName").value(expectedAlbum.getArtistName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(expectedAlbum.getGenre().toString()));

        verify(albumServiceImpl).getAlbumByName(albumName);
    }

    @Test
    @DisplayName("POST /albums")
    public void testPostAlbum() throws Exception {


        Album postAlbum = new Album(1L, "The WInd", "John Doe", Genre.SOUL, LocalDate.of(1999, 12, 12), 9000, 5);

        when(albumServiceImpl.postAlbum(postAlbum)).thenReturn(postAlbum);

        this.mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/albums").content(mapper.writeValueAsBytes(postAlbum))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumId").value(postAlbum.getAlbumId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumTitle").value(postAlbum.getAlbumTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artistName").value(postAlbum.getArtistName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(postAlbum.getGenre().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(postAlbum.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockQuantity").value(postAlbum.getStockQuantity()));

    }


    @Test
    @DisplayName("POST /albums")
    public void testPostAlbumInvalidInput() throws Exception {

String json = "  {\n" +
        "        \"albd\": 3,\n" +
        "        \"albumTitle\": \"Album 2\",\n" +
        "        \"arstName\": \"Artist 2\",\n" +
        "        \"genre\": \"POP\",\n" +
        "        \"releasate\": \"2021-02-01\",\n" +
        "        \"price\": 2000,\n" +
        "        \"stocQuantity\": 0\n" +
        "    },";

        this.mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/albums")
                        .content(mapper.writeValueAsBytes(json))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /albums/{id}")
    public void testPutAlbum() throws Exception {

        Album album = new Album(1L, "The WInd", "John Doe", Genre.SOUL, LocalDate.of(1999, 12, 12), 9000, 5);


        Album putAlbum = new Album(1L, "The WIndUpdate", "John Doeupdate", Genre.SOUL, LocalDate.of(1999, 12, 12), 9000, 5);

        when(albumServiceImpl.getAlbumById(1)).thenReturn(album);
        when(albumServiceImpl.postAlbum(putAlbum)).thenReturn(putAlbum);

        this.mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/albums/1").content(mapper.writeValueAsBytes(putAlbum))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumId").value(putAlbum.getAlbumId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumTitle").value(putAlbum.getAlbumTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artistName").value(putAlbum.getArtistName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(putAlbum.getGenre().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(putAlbum.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockQuantity").value(putAlbum.getStockQuantity()));

    }

}
