package com.anas.meetup.meetup;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetupRequest {

    private String title;
    private String imgUrl;
    private String address;
    private String description;
    private LocalDate meetupDate;


}
