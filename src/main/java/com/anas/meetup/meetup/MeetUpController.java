package com.anas.meetup.meetup;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/meetup")
public class MeetUpController {

    @Autowired
    private MeetupService meetupService;



    @GetMapping

    public ResponseEntity<MeetupResponse> getAllMeetUps(){
        try {
            return ResponseEntity.ok(MeetupResponse.builder()
                    .meetUps(meetupService.getAllMeetups())
                            .state("meetups has been received")
                    .build()
            );
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(MeetupResponse
                            .builder()
                            .state(e.getLocalizedMessage()).build());
        }

    }


//    @GetMapping("/{meetupId}")
//    public ResponseEntity<String> getMeetupById(
//            @PathVariable(name = "meetupId") String meetupId
//    ){
//        return ResponseEntity.ok("hello from seq");
//    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<MeetupResponse> addMeetup(
            @RequestBody MeetupRequest addMeetupRequest
    ){
        System.out.println(addMeetupRequest);
        return ResponseEntity.ok(meetupService.addMeetUp(addMeetupRequest));
    }

    @CrossOrigin
    @DeleteMapping("/{meetupId}")
    public ResponseEntity<MeetupResponse> deleteMeetup(
            @PathVariable("meetupId") Long meetupId
    ){
        return ResponseEntity.ok(meetupService.deleteMeetUp(meetupId));
    }

    @CrossOrigin
    @PutMapping("/{meetupId}")
    public ResponseEntity<MeetupResponse> updateMeetup(
            @PathVariable Long meetupId,
            @RequestBody MeetupRequest meetupRequest
    ){
        return ResponseEntity.ok(meetupService.updateMeetup(meetupId,meetupRequest));
    }

}
