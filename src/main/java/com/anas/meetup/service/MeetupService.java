package com.anas.meetup.service;

import com.anas.meetup.model.Favorite;
import com.anas.meetup.model.MeetUp;
import com.anas.meetup.model.User;
import com.anas.meetup.payload.MeetupRequest;
import com.anas.meetup.payload.MeetupResponse;
import com.anas.meetup.repo.FavoriteRepo;
import com.anas.meetup.repo.MeetUpRepo;
import com.anas.meetup.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetupService {

    @Autowired
    private MeetUpRepo meetUpRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FavoriteRepo favoriteRepo;

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

    @Transactional
    public MeetupResponse addFavorite(Long meetupId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = ((User) auth.getPrincipal());

        Optional<MeetUp> meetUpOptional = meetUpRepo.findById(meetupId);
        if (meetUpOptional.isPresent()){
            Favorite favorite = Favorite.builder()
                    .meetUp(meetUpOptional.get())
                    .user(user)
                    .build();
            favoriteRepo.save(favorite);

            return  MeetupResponse.builder().state("favorite has been added").build();
        }

        return  MeetupResponse.builder().state("something gone wrong").build();
    }

    public MeetupResponse getFavorites() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = ((User) auth.getPrincipal());

       Optional<List<Favorite>> optionalMeetUpList = favoriteRepo.getByUserUserId(user.getUserId());
        if (optionalMeetUpList.isPresent()){
           List<Favorite> favorites = optionalMeetUpList.get();
          List<MeetUp> meetUps = favorites.stream().map(Favorite::getMeetUp).toList();
          return MeetupResponse.builder().state("ok").meetUps(meetUps).build();
        }else {
            return MeetupResponse.builder().state("sorry something gone wrong").build();
        }
    }

    public MeetupResponse delFavorite(Long meetupId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = ((User) auth.getPrincipal());

        Optional<List<Favorite>> optionalMeetUpList = favoriteRepo.getByUserUserId(user.getUserId());
        if (optionalMeetUpList.isPresent()){
            List<Favorite> favorites = optionalMeetUpList.get();
            Optional<Favorite> favorite = favorites.stream().filter(favoritee ->
                    Objects.equals(favoritee.getMeetUp().getMeetupId(), meetupId)
            ).findFirst();


            favorite.ifPresent(favorite1 -> {
                favoriteRepo.deleteById(favorite1.getFavoriteId());
            });


            return MeetupResponse.builder().state("favorite has been deleted").build();
        }else {
            return MeetupResponse.builder().state("sorry something gone wrong").build();
        }


    }
}
