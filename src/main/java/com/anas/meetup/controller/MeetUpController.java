package com.anas.meetup.controller;


import com.anas.meetup.payload.MeetupRequest;
import com.anas.meetup.payload.MeetupResponse;
import com.anas.meetup.service.MeetupService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

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
    @ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class, AccessDeniedException.class, AuthenticationException.class,Exception.class})

    public ResponseEntity<MeetupResponse> addMeetup(
            @RequestBody MeetupRequest addMeetupRequest
    ){
        System.out.println(addMeetupRequest);
        return ResponseEntity.ok(meetupService.addMeetUp(addMeetupRequest));
    }

    @CrossOrigin
    @DeleteMapping("/{meetupId}")
    @ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class, AccessDeniedException.class, AuthenticationException.class,Exception.class})

    public ResponseEntity<MeetupResponse> deleteMeetup(
            @PathVariable("meetupId") Long meetupId
    ){
        return ResponseEntity.ok(meetupService.deleteMeetUp(meetupId));
    }

    @CrossOrigin
    @GetMapping("/{meetupId}")
    public ResponseEntity<MeetupResponse> addFavorite(
            @PathVariable Long meetupId
    ){
        return ResponseEntity.ok(meetupService.addFavorite(meetupId));
    }

    @CrossOrigin
    @GetMapping("/favorites")
    @ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class, AccessDeniedException.class, AuthenticationException.class,Exception.class})
    public ResponseEntity<MeetupResponse> getFavorites(

    ){
        return ResponseEntity.ok(meetupService.getFavorites());
    }



}
