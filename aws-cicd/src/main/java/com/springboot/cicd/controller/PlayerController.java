package com.springboot.cicd.controller;

import com.springboot.cicd.model.Player;
import com.springboot.cicd.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public List<Player> getAllPlayersController() {
        return playerService.getAllPlayers();
    }

}
