package com.anas.meetup.controller;


import com.anas.meetup.payload.MeetupRequest;
import com.anas.meetup.payload.MeetupResponse;
import com.anas.meetup.service.MeetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/meetup")
public class MeetUpController {

    @Autowired
    private MeetupService meetupService;



    @ExceptionHandler
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
                            .state("sorry "+e.getLocalizedMessage()).build());
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
    @ExceptionHandler
    public ResponseEntity<MeetupResponse> addMeetup(
            @RequestBody MeetupRequest addMeetupRequest
    ){
        System.out.println(addMeetupRequest);
        return ResponseEntity.ok(meetupService.addMeetUp(addMeetupRequest));
    }

    @CrossOrigin
    @DeleteMapping("/{meetupId}")
    @ExceptionHandler
    public ResponseEntity<MeetupResponse> deleteMeetup(
            @PathVariable("meetupId") Long meetupId
    ){
        return ResponseEntity.ok(meetupService.deleteMeetUp(meetupId));
    }

    @CrossOrigin
    @PutMapping("/{meetupId}")
    @ExceptionHandler
    public ResponseEntity<MeetupResponse> updateMeetup(
            @PathVariable Long meetupId,
            @RequestBody MeetupRequest meetupRequest
    ){
        return ResponseEntity.ok(meetupService.updateMeetup(meetupId,meetupRequest));
    }

}
