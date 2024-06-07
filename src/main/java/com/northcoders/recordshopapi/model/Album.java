package com.northcoders.recordshopapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    Long albumId;

    @Column
    String albumTitle;


    @Column
    String artistName;

    @Column
    Genre genre;

    @Column
    LocalDate releaseDate;

    @Column
    int price;

    @Column
    int stockQuantity;


}
