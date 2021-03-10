package edu.kennesaw.matchmakerservice.manager;

import edu.kennesaw.matchmakerservice.repo.Repository;
import edu.kennesaw.matchmakerservice.to.*;
import edu.kennesaw.matchmakerservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                boolean inserted = repo.addNewPlayer(player);
                if(!inserted){
                    LOGGER.info("Record not inserted in processPlayer method for gamerId: " + player.getGamerId());
                    response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));
                }
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

    public MatchMakerResponse searchPlayers(MatchMakerRequest request){
        return getMockedMatchMakerResponse(request);
    }

    public MatchMakerResponse getMockedMatchMakerResponse(MatchMakerRequest request){
        MatchMakerResponse response = new MatchMakerResponse();
        response.setGamerId(request.getPlayerInfo().getGamerId());

        List<Player> friendsList = new ArrayList<>();

        Player player1 = new Player("aguillermanp4", "Carlie", "MacGibbon", 54, "casual", "Northwest US",
                "Hindi", "achiever", 15, "Overwatch", "DeathMatch",
                "54%");

        Player player2 = new Player("cisco94", "Francisco", "Sayago", 26, "hardcore", "Southeast US",
                "Spanish", "socializer", 5, "Overwatch", "Competitive",
                "98%");

        Player player3 = new Player("liss79", "Vilma", "Guerra", 40, "basic", "Northeast US",
                "English", "explorer", 10, "Overwatch", "Quick Play",
                "90%");

        Player player4 = new Player("carlos77", "Carlos", "Guerra", 42, "mid-core", "Latin America",
                "Spanish", "thinker", 2, "Overwatch", "Hybrid",
                "80%");

        Player player5 = new Player("mejia61", "Ruth", "Acevedo", 60, "casual", "Europe",
                "German", "achiever", 5, "Overwatch", "Capture the Flag",
                "86%");

        friendsList.add(player1);
        friendsList.add(player2);
        friendsList.add(player3);
        friendsList.add(player4);
        friendsList.add(player5);

        response.setFriendsList(friendsList);
        return response;
    }

    public ErrorResponse getErrorResponse(int code, String message) {
        return new ErrorResponse(code, message);
    }
}