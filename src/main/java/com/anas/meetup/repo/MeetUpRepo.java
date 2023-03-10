package com.anas.meetup.repo;

import com.anas.meetup.model.MeetUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetUpRepo extends JpaRepository<MeetUp,Long> {


    Optional<List<MeetUp>> getByUserUserId(Integer userId);

}
