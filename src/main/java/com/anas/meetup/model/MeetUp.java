package com.anas.meetup.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "_meetup"
)
public class MeetUp {

    @Id
    @GeneratedValue
    private Long meetupId;
    @Column(
            nullable = false
    )
    private String title;
    private String imgUrl;
    private String address;
    private String description;
    private LocalDate meetDate;

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;

    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    @ManyToMany(mappedBy = "favoriteMeetup")
    private Set<User> favorites;

}
