package edu.kennesaw.matchmakerservice.repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.kennesaw.matchmakerservice.config.DatabaseConfig;
import edu.kennesaw.matchmakerservice.to.GamerGroup;
import edu.kennesaw.matchmakerservice.to.GroupInfo;
import edu.kennesaw.matchmakerservice.to.PlayerInfo;
import org.springframework.stereotype.Component;

@Component
public class Repository {

    private static final Logger LOGGER = Logger.getLogger(Repository.class.getName());

    DatabaseConfig dbConfig = new DatabaseConfig();

    public List<PlayerInfo> getCompleteInfoForFriendsList(List<String> gamerIds) throws SQLException {
        List<PlayerInfo> completeFriendInfoList = new ArrayList<>();
        PlayerInfo playerInfo = new PlayerInfo();
        String friendIds = getArrayString(gamerIds);

        Connection con = getDatabaseConnection();
        PreparedStatement stmt = getDatabaseConnection().prepareStatement("select * from gamers where gamer_id in ("+friendIds+")");
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            playerInfo = new PlayerInfo(rs.getString("gamer_id"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getInt("age"),
                    rs.getString("skill_level"),
                    rs.getString("region"), rs.getString("language"),
                    rs.getString("personality_type"), rs.getInt("minimum_wait_time"),
                    rs.getString("preferred_game"), rs.getString("preferred_game_mode"));

            completeFriendInfoList.add(playerInfo);

        }
        rs.close();
        closeDatabaseConnection(con, stmt);
        return completeFriendInfoList;

    }

    public String getArrayString(List<String> gamerIds){
        StringBuilder stringBuilder = new StringBuilder();
        for(String gamerId : gamerIds){
            stringBuilder.append("'").append(gamerId).append("'").append(",");
        }
        //remove last comma from string
        String finalString = stringBuilder.substring(0, stringBuilder.length() - 1);
        LOGGER.info("getArrayString: " + finalString);
        return finalString;
    }

    public PlayerInfo getPlayerInformation(String gamerId) throws SQLException {
        PlayerInfo playerInfo = new PlayerInfo();

        Connection con = getDatabaseConnection();
        PreparedStatement stmt = getDatabaseConnection().prepareStatement("select * from gamers where gamer_id = ?");
        stmt.setString(1,gamerId.toUpperCase());

        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            playerInfo = new PlayerInfo(gamerId, rs.getString("first_name"), rs.getString("last_name"),
                    rs.getInt("age"),
                    rs.getString("skill_level"),
                    rs.getString("region"), rs.getString("language"),
                    rs.getString("personality_type"), rs.getInt("minimum_wait_time"),
                    rs.getString("preferred_game"), rs.getString("preferred_game_mode"));

        }
        rs.close();
        // get  players friends  next
        stmt = getDatabaseConnection().prepareStatement("SELECT * from gamer_friends where gamer_id=?");
         stmt.setString(1,gamerId.toUpperCase());


         rs = stmt.executeQuery();
        playerInfo.setFriendsList(extractFriendList(rs));

        closeDatabaseConnection(con, stmt);
        return playerInfo;
    }

  List extractFriendList(ResultSet rs) {
        List<String> friends = new ArrayList<String>();

           try{
               while(rs.next()) {
                   friends.add(rs.getString(2));
               }
            } catch (SQLException throwables) {
              //  throwables.printStackTrace();


        }
        return friends;
  }

  public void addFriends(String player_id,String[] friend_ids) throws  SQLException{
      String sql = " insert into gamer_friends (gamer_id,gamer_friend_id)  values ( ?, ? )";

      PreparedStatement stmt = getDatabaseConnection().prepareStatement(sql);
      Connection con = getDatabaseConnection();
      con.setAutoCommit(true);
      for( int i =0 ;i< friend_ids.length; i++){
          stmt.setString(1,player_id);
          stmt.setString(2,friend_ids[i]);
         int x =  stmt.executeUpdate();
         System.out.println(x);
      }

      closeDatabaseConnection(con, stmt);

  }
    public List<PlayerInfo>search(String skill_level,String personality_type,String preferred_game)throws  SQLException{
        List<PlayerInfo> players = new ArrayList();
        String sql = "select * from gamers where skill_level=? AND personality_type=? and preferred_game=?";
        PreparedStatement stmt = getDatabaseConnection().prepareStatement(sql);
        Connection con = getDatabaseConnection();
        stmt.setString(1,skill_level);
        stmt.setString(2,personality_type);
        stmt.setString(3,preferred_game);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            PlayerInfo player  = new PlayerInfo( rs.getString("gamer_Id"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getInt("age"),
                    rs.getString("skill_level"),
                    rs.getString("region"), rs.getString("language"),
                    rs.getString("personality_type"), rs.getInt("minimum_wait_time"),
                    rs.getString("preferred_game"), rs.getString("preferred_game_mode"));
            players.add(player);

        }
       return players;
    }

    public List<GroupInfo> findGroups(String gamerID) throws  SQLException{
        List<GroupInfo> groups = new ArrayList();
        String sql = "select * from gamer_groups where gamer_id=?";
        PreparedStatement stmt = getDatabaseConnection().prepareStatement(sql);
        Connection con = getDatabaseConnection();
        stmt.setString(1,gamerID);

        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            GroupInfo group  = new GroupInfo( rs.getString("gamer_Id"), rs.getString("gamer_group_id"), rs.getString("gamer_friend_id"));
            groups.add(group);

        }
        return groups;
    }

    public boolean createGroup(String gamer_id,List<String> gamer_friend_ids,String gamer_group_id) throws SQLException{
        boolean result = false;
        for(String gamer_friend_id : gamer_friend_ids){
            String sql = "insert into gamer_groups (gamer_id,gamer_friend_id,gamer_group_id) values (?,?,?)";
            PreparedStatement stmt = getDatabaseConnection().prepareStatement(sql);
            Connection con = getDatabaseConnection();
            stmt.setString(1,gamer_id);
            stmt.setString(2,gamer_friend_id);
            stmt.setString(3,gamer_group_id);

            int x =  stmt.executeUpdate();
            if(x > 0){
                result = true;
            }
            else{
                LOGGER.info("createGroup method failed to insert record for gamer_id " + gamer_id
                + " gamer_friend_id " + gamer_friend_id + " gamer_group_id " + gamer_group_id);
                result = false;
            }
        }
        return result;
    }

    public String buildInsertSqlStatementForPlayerInfo(PlayerInfo player) {
        String sql = "INSERT INTO gamers VALUES( '"
                + player.getGamerId().toUpperCase() + "', '"
                + player.getSkillLevel().toUpperCase() + "', '"
                + player.getRegion().toUpperCase() + "', '"
                + player.getLanguage().toUpperCase() + "', '"
                + player.getPersonalityType().toUpperCase() + "', '"
                + player.getMinimumWaitTime() + "', '"
                + player.getGame().toUpperCase() + "', '"
                + player.getGameMode().toUpperCase() + "', '"
                + player.getFirstName().toUpperCase() + "', '"
                + player.getLastName().toUpperCase() + "', '"
                + player.getAge() + "' )";
        LOGGER.info("buildInsertSqlStatementForPlayerInfo: " + sql);
        return sql;
    }

    public String buildUpdateSqlStatementForPlayerInfo(PlayerInfo player) {
        String sql = "UPDATE gamers "
                + "SET first_name = '"+player.getFirstName().toUpperCase()+"', "
                + "last_name = '"+player.getLastName().toUpperCase()+"', "
                + "age = '"+player.getAge()+"', "
                + "skill_level = '"+player.getSkillLevel().toUpperCase()+"', "
                + "region = '"+player.getRegion().toUpperCase()+"', "
                + "language = '"+player.getLanguage().toUpperCase()+"', "
                + "personality_type = '"+player.getPersonalityType().toUpperCase()+"', "
                + "minimum_wait_time = '"+player.getMinimumWaitTime()+"', "
                + "preferred_game = '" + player.getGame().toUpperCase()+"', "
                + "preferred_game_mode = '" + player.getGameMode().toUpperCase()+"' "
                + " WHERE gamer_id = '"+player.getGamerId()+"' ";
        LOGGER.info("buildUpdateSqlStatementForPlayerInfo: " + sql);
        return sql;
    }

    private Connection getDatabaseConnection() {
        Connection con = null;
        con = dbConfig.getConnection();
        return con;
    }

    private Statement getStatement(Connection con) throws SQLException {
        Statement stmt = null;
        stmt = con.createStatement();
        return stmt;
    }

    private void closeDatabaseConnection(Connection con, Statement stmt) throws SQLException {
        stmt.close();
        con.close();
    }

    public boolean addNewPlayer(PlayerInfo player) throws SQLException{
        Connection con = getDatabaseConnection();
        Statement stmt = getStatement(con);
        int numberOfRows = stmt.executeUpdate(buildInsertSqlStatementForPlayerInfo(player));
        closeDatabaseConnection(con, stmt);
        if(numberOfRows == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean editPlayer(PlayerInfo player) throws SQLException {
        Connection con = getDatabaseConnection();
        Statement stmt = getStatement(con);
        int numberOfRows = stmt.executeUpdate(buildUpdateSqlStatementForPlayerInfo(player));
        closeDatabaseConnection(con, stmt);
        if(numberOfRows == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public List<GamerGroup> getGamerGroups(String playerId) throws SQLException{
        String sql =
                "SELECT distinct gg.gamer_id, gg.gamer_group_id ,gg.gamer_friend_id, g.skill_level, g.region, g.language, g.personality_type,"+
               " g.minimum_wait_time, g.preferred_game, g.preferred_game_mode, g.first_name, g.last_name, g.age "+
        " FROM MatchMaker.gamer_groups gg "+
        " INNER JOIN MatchMaker.gamer_friends gf " +
        " ON gg.gamer_friend_id = gf.gamer_friend_id " +
        " INNER JOIN MatchMaker.gamers g " +
        " ON g.gamer_id = gf.gamer_friend_id" +
        " WHERE gg.gamer_id = ?  ORDER BY gg.gamer_group_id;" ;
        PreparedStatement stmt = getDatabaseConnection().prepareStatement(sql);
        Connection con = getDatabaseConnection();
        stmt.setString(1,playerId);
        ResultSet rs = stmt.executeQuery();
        List<GamerGroup> groups = new ArrayList<GamerGroup>();
        while(rs.next()){
            GamerGroup group  = new GamerGroup( );
            group.setGamer_id(rs.getString("gamer_Id"));
            group.setGamer_group_id(rs.getString("gamer_group_id"));
            group.setGamer_friend_id(rs.getString("gamer_friend_id"));

            group.setSkill_level(rs.getString("skill_level"));
            group.setRegion(rs.getString("region"));
            group.setLanguage(rs.getString("language"));
            group.setPersonality_type(rs.getString("personality_type"));

            group.setMinimum_wait_time(rs.getString("minimum_wait_time"));
            group.setPreferred_game(rs.getString("preferred_game"));
            group.setPreferred_game_mode(rs.getString("preferred_game_mode"));
            group.setFirst_name( rs.getString("first_name"));
            group.setLast_name(rs.getString("last_name"));
            group.setAge(rs.getString("age"));

            groups.add(group);
        }
 return groups;


    }
}
