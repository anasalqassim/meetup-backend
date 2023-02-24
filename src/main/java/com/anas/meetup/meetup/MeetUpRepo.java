package com.anas.meetup.meetup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetUpRepo extends JpaRepository<MeetUp,Long> {


    Optional<List<MeetUp>> getByUserUserId(Integer userId);

}
