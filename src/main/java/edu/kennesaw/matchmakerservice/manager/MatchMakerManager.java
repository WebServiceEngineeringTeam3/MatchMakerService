package edu.kennesaw.matchmakerservice.manager;

import edu.kennesaw.matchmakerservice.datamodel.PlayerInfo;
import edu.kennesaw.matchmakerservice.repo.PlayerInfoRepository;
import edu.kennesaw.matchmakerservice.to.ErrorResponse;
import edu.kennesaw.matchmakerservice.to.MatchMakerResponse;
import edu.kennesaw.matchmakerservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.logging.Logger;

@Component
public class MatchMakerManager {

    private static final Logger LOGGER = Logger.getLogger(MatchMakerManager.class.getName());

    @Autowired
    PlayerInfoRepository repo;

    public MatchMakerResponse processPlayer(PlayerInfo player) {
        MatchMakerResponse response = new MatchMakerResponse();
        PlayerInfo dbPlayer = new PlayerInfo();
        LOGGER.info("Begin to process player");

        //Check to see if given gamer Id exists in database
        try{
            dbPlayer = repo.findPlayerInfoByGamerId(player.getGamerId());
        }
        catch(Exception e){
            LOGGER.info("Exception occurred in processPlayer method during player search: " + e.getMessage());
            response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));
        }

        //If repo does not have given gamer Id, then add player
        //Else update player information with given ID
        if(StringUtils.isEmpty(dbPlayer.getGamerId())) {
            LOGGER.info("Adding new player: " + player.getFirstName() + " " + player.getLastName());
            LOGGER.info("With Gamer ID: " + player.getGamerId());
            try {
                response.setGamerId(player.getGamerId());
                response.setPlayerInfo(player);
                repo.save(player);
            }
            catch(Exception e) {
                LOGGER.info("Exception occurred in processPlayer method during insertion: " + e.getMessage());
                response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));
            }
        }
        else {
            String gamerId = player.getGamerId();
            LOGGER.info("Updating player: " + gamerId);
            try {
                response.setGamerId(player.getGamerId());
                response.setPlayerInfo(player);
                //TODO: Need to see how repo can update a player
               // boolean result = repo.editPlayer(player);
                boolean result = true;
                if(!result) {response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));}
            }
            catch(Exception e) {
                LOGGER.info("Exception occurred in processPlayer method during update: " + e.getMessage());
                response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));
            }
        }

        LOGGER.info("End player processing");
        return response;
    }

    public ErrorResponse getErrorResponse(int code, String message) {
        return new ErrorResponse(code, message);
    }
}
