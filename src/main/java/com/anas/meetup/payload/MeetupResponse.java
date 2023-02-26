package com.anas.meetup.payload;


import com.anas.meetup.model.MeetUp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetupResponse {

    String state;
    List<MeetUp> meetUps;

}
