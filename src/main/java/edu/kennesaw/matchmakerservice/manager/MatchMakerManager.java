package edu.kennesaw.matchmakerservice.manager;

import edu.kennesaw.matchmakerservice.repo.Repository;
import edu.kennesaw.matchmakerservice.to.ErrorResponse;
import edu.kennesaw.matchmakerservice.to.MatchMakerRequest;
import edu.kennesaw.matchmakerservice.to.MatchMakerResponse;
import edu.kennesaw.matchmakerservice.to.PlayerInfo;
import edu.kennesaw.matchmakerservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.logging.Logger;

@Component
public class MatchMakerManager {

    private static final Logger LOGGER = Logger.getLogger(MatchMakerManager.class.getName());

    @Autowired
    Repository repo;

    public MatchMakerResponse processPlayer(MatchMakerRequest request) {
        MatchMakerResponse response = new MatchMakerResponse();
        LOGGER.info("Begin to process player");
        PlayerInfo player = request.getPlayerInfo();

        if(request.getCrudType().equalsIgnoreCase(Constants.CREATE_OPERATION)){
            LOGGER.info("Adding new player: " + player.getFirstName() + " " + player.getLastName());
            LOGGER.info("With Gamer ID: " + player.getGamerId());
            try {
                response.setGamerId(player.getGamerId());
                response.setPlayerInfo(player);
                repo.addNewPlayer(player);
            }
            catch(SQLException e) {
                LOGGER.info("Exception occurred in processPlayer method during insertion: " + e.getMessage());
                response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));
            }
        }
        else if (request.getCrudType().equalsIgnoreCase(Constants.UPDATE_OPERATION)){
            String gamerId = player.getGamerId();
            LOGGER.info("Updating player: " + gamerId);
            try {
                response.setGamerId(player.getGamerId());
                response.setPlayerInfo(player);
                boolean result = repo.editPlayer(player);
                if(!result) {response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));}
            }
            catch(SQLException e) {
                LOGGER.info("Exception occurred in processPlayer method during update: " + e.getMessage());
                response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));
            }
        }

        LOGGER.info("End player processing");
        return response;
    }

    public MatchMakerResponse getPlayer(String gamerId){
        MatchMakerResponse response = new MatchMakerResponse();
        response.setGamerId(gamerId);
        LOGGER.info("Begin to get player with gamerId: " + gamerId);
        PlayerInfo player = new PlayerInfo();

        try {
            player = repo.getPlayerInformation(gamerId);
        }
        catch(SQLException e) {
            LOGGER.info("Exception occurred in getPlayer method during read: " + e.getMessage());
            response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));
        }

        if(StringUtils.isEmpty(player.getGamerId())){
            response.setErrorResponse(getErrorResponse(Constants.CODE_RESOURCE_NOT_AVAILABLE, Constants.MESSAGE_RESOURCE_NOT_AVAILABLE));
        }
        else{
            response.setPlayerInfo(player);
        }
        return response;
    }

    public ErrorResponse getErrorResponse(int code, String message) {
        return new ErrorResponse(code, message);
    }
}