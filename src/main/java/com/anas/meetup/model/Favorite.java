package com.anas.meetup.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Favorite {

    @Id
    @GeneratedValue
    private Long favoriteId;





    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;


    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(
            name = "meetup_id",
            referencedColumnName = "meetupId"
    )
    private MeetUp meetUp;





}
