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
        List<GroupInfo> groups = new ArrayList<>();

        try {
            player = repo.getPlayerInformation(gamerId);
            //Find groups and populate groupsList attribute for PlayerInfo
            groups = repo.findGroups(gamerId);
            player.setGroupsList(groups);
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
    // add friends
    //@ params :person id and  comma delimted list of friend ID
    public void addFriends(String player,String friends){

        MatchMakerResponse response = new MatchMakerResponse();

  try {
      String [] friend_ids = friends.split(",");
      repo.addFriends(player, friend_ids);
     }
  catch(SQLException e) {
      LOGGER.info("SQLException occurred in addFriends: " + e.getMessage());

        }


    }


    public List<PlayerInfo> search(String skill_level, String personality_type, String preferred_game){
        try{
          List<PlayerInfo> players =  repo.search(skill_level,personality_type,preferred_game);
          return players;
        }
        catch(SQLException e) {
            LOGGER.info("SQLException occurred in addFriends: " + e.getMessage());

        }
        return null;

    }
    public List<GroupInfo> findGroups(String gamerID){
        try{
            List<GroupInfo> groups =  repo.findGroups(gamerID);
            return groups;
        }
        catch(SQLException e) {
            LOGGER.info("SQLException occurred in addFriends: " + e.getMessage());

        }
        return null;
    }
    public boolean createGroup(String gamer_id,List<String> gamer_friend_ids,String gamer_group_id) {
        try {
            return repo.createGroup(gamer_id, gamer_friend_ids, gamer_group_id);
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred in addFriends: " + e.getMessage());

        }
        return  false;
    }
public List<GamerGroup>getGamerGroups(String player_id){
    try{
        List<GamerGroup> groups =  repo.getGamerGroups(player_id);
        return groups;
    }
    catch(SQLException e) {
        LOGGER.info("SQLException occurred in getGamerGroups: " + e.getMessage());

    }
    return null;

}

    public MatchMakerResponse getCompleteInfoForFriendsList(List<String> gamerIds){
        MatchMakerResponse response = new MatchMakerResponse();
        try{
            List<PlayerInfo> friendsInfo = repo.getCompleteInfoForFriendsList(gamerIds);
            response.setCompleteInfoFriendsList(friendsInfo);
        }
        catch(SQLException e){
            LOGGER.info("SQLException occurred in getCompleteInfoForFriendsList: " + e.getMessage());
            response.setErrorResponse(getErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR));
        }
        return response;
    }

    public ErrorResponse getErrorResponse(int code, String message) {
        return new ErrorResponse(code, message);
    }
}