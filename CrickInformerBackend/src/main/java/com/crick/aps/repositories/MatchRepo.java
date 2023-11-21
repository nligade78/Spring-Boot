package com.crick.aps.repositories;

import com.crick.aps.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepo extends JpaRepository<Match,Integer> {

    // I want fetch match
    // providing team name

    Optional<Match> findByTeamHeading(String teamHeading);
}
