package com.anas.meetup.repo;


import com.anas.meetup.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite,Long> {

    Optional<List<Favorite>> getByUserUserId(Integer userId);

}
