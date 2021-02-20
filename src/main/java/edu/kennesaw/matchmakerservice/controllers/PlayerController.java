package edu.kennesaw.matchmakerservice.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import edu.kennesaw.matchmakerservice.service.PlayerService;
import edu.kennesaw.matchmakerservice.to.MatchMakerResponse;


@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @CrossOrigin
    @GetMapping("/{gamerId}")
    public @ResponseBody
        ResponseEntity<MatchMakerResponse> getPlayerInformation(@PathVariable String gamerId) {
            ResponseEntity<MatchMakerResponse> res = null;
            MatchMakerResponse data = new MatchMakerResponse();
            try {
                data.setPlayerInfo(playerService.getPlayerInformation(gamerId));
                res = ResponseEntity.ok(data);
            } catch (SQLException e) {
                res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
			return res;
        }

}
