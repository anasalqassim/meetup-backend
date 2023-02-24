package com.anas.meetup.meetup;

import com.anas.meetup.user.User;
import com.anas.meetup.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetupService {

    @Autowired
    private MeetUpRepo meetUpRepo;
    @Autowired
    private UserRepo userRepo;

    public List<MeetUp> getAllMeetups() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

       System.out.println( auth.getPrincipal());
        User user = ((User) auth.getPrincipal());

        Optional<List<MeetUp>> meetUps = meetUpRepo.getByUserUserId(user.getUserId());

        return meetUps.orElseGet(List::of);
    }

    public MeetupResponse addMeetUp(MeetupRequest meetupRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = ((User) auth.getPrincipal());

        System.out.println(user);
        MeetUp meetUp = MeetUp.builder()
                .title(meetupRequest.getTitle())
                .address(meetupRequest.getAddress())
                .description(meetupRequest.getDescription())
                .meetDate(meetupRequest.getMeetupDate())
                .imgUrl(meetupRequest.getImgUrl())
                .user(user)
                .build();

        meetUpRepo.save(meetUp);
        return MeetupResponse.builder()
                .state("meetup has been added")
                .build();
    }

    public MeetupResponse deleteMeetUp(Long meetupId) {
        meetUpRepo.deleteById(meetupId);



        return MeetupResponse.builder()
                .state("Meetup has been deleted")
                .build();
    }

    @Transactional
    public MeetupResponse updateMeetup(Long meetupId, MeetupRequest meetupRequest) {
       Optional<MeetUp> meetUpOptional = meetUpRepo.findById(meetupId);
        if (meetUpOptional.isPresent()){
            MeetUp meetup = meetUpOptional.get();

            if (meetupRequest.getAddress() != null){
                meetup.setAddress(meetupRequest.getAddress());
            }
            if (meetupRequest.getTitle() != null){
                meetup.setTitle(meetupRequest.getTitle());
            }
            if (meetupRequest.getDescription() != null){
                meetup.setDescription(meetupRequest.getDescription());
            }
            if (meetupRequest.getImgUrl() != null){
                meetup.setImgUrl(meetupRequest.getImgUrl());
            }
            if (meetupRequest.getMeetupDate() != null){
                meetup.setMeetDate(meetupRequest.getMeetupDate());
            }

            meetUpRepo.save(meetup);

            return MeetupResponse.builder()
                    .state("Meetup has been updated")
                    .build();
        }


        return MeetupResponse.builder()
                .state("No Meetup with this id")
                .build();
    }
}
