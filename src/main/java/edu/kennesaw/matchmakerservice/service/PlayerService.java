package edu.kennesaw.matchmakerservice.service;

import java.sql.SQLException;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import edu.kennesaw.matchmakerservice.repo.Repository;
import edu.kennesaw.matchmakerservice.to.PlayerInfo;

@Service
public class PlayerService {

    @Autowired
    private Repository repo;

    public PlayerInfo getPlayerInformation(String gamerId) throws SQLException {
            return repo.getPlayerInformation(gamerId);
    }
    


}

