package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String language;
    private String image;
    private String name;
    private String country;
    private String rating;
    private String summary;
    private LocalDate premierd;
    private String url;
    @ElementCollection
    private List<Integer> genreId;
    @ElementCollection
    private List<Integer> commentId;
}
