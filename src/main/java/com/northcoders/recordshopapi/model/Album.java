package com.northcoders.recordshopapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "album_id", updatable = false, nullable = false)
    Long albumId;

    @Column(name = "album_title", unique = true)
    String albumTitle;


    @Column(name = "artist_name")
    String artistName;

    @Column
    @Enumerated(EnumType.STRING)
    Genre genre;

    @Column(name = "release_date")
    LocalDate releaseDate;

    @Column(name = "price")
    int price;

    @Column(name = "stock_quantity")
    int stockQuantity;


}
