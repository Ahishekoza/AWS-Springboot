package com.springboot.cicd.service;

import com.springboot.cicd.model.Player;
import com.springboot.cicd.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {

    List<Player> players = new ArrayList<>();
    AtomicInteger id = new AtomicInteger(0);

    public List<Player> getAllPlayers() {
        return players;
    }

    public Player getPlayerById(Integer id) {
        return players.stream()
                .filter(player -> player.Id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Player createNewPlayer(String name , Team team){
        Player newPlayer = new Player(id.incrementAndGet(), name, team);
        players.add(newPlayer);
        return newPlayer;
    }

    public Player updatePlayerTeam(Integer id, Team team) {
        Player player = getPlayerById(id);
        if (player != null) {
            Player updatedPlayer = new Player(player.Id(), player.name(), team);
            players.set(players.indexOf(player), updatedPlayer);
            return updatedPlayer;
        }
        return null;
    }

    public String deletePlayer(Integer id){
       Player player =  getPlayerById(id);
       if (player != null){
           players.remove(player);
           return "Player with id " + id + " deleted successfully.";
       }
       return "Player with id " + id + " not found.";
    }


    @PostConstruct
    private void init() {
        players.add(new Player(1, "Rohit Sharma", Team.MI));
        players.add(new Player(2, "Virat Kohli", Team.RCB));
        players.add(new Player(3, "Shikhar Dhawan", Team.DC));
        players.add(new Player(4, "MS Dhoni", Team.CSK));
    }

}
