package edu.kennesaw.matchmakerservice.repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.kennesaw.matchmakerservice.config.DatabaseConfig;
import edu.kennesaw.matchmakerservice.to.PlayerInfo;
import org.springframework.stereotype.Component;

@Component
public class Repository {

    private static final Logger LOGGER = Logger.getLogger(Repository.class.getName());

    DatabaseConfig dbConfig = new DatabaseConfig();

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
        stmt = getDatabaseConnection().prepareStatement("SELECT * "+
                        "FROM gamers fr "+
                "inner join "+
                        "(SELECT * FROM MatchMaker.gamers where gamer_id = ?) g "+
        "on g.skill_level = fr.skill_level "+
        "and g.region = fr.region "+
        "and g.personality_type = fr.personality_type "+

        "and g.preferred_game = fr.preferred_game "+
        "WHERE fr.gamer_id != ? ");

         stmt.setString(1,gamerId.toUpperCase());
         stmt.setString(2,gamerId.toUpperCase());

         rs = stmt.executeQuery();
        playerInfo.setFriendsList(extractFriendList(rs));

        closeDatabaseConnection(con, stmt);
        return playerInfo;
    }

  List extractFriendList(ResultSet rs) {
        List<String> friends = new ArrayList<String>();

           try{
               while(rs.next()) {
                   friends.add(rs.getString(1));
               }
            } catch (SQLException throwables) {
              //  throwables.printStackTrace();


        }
        return friends;
  }
    public String buildSelectSqlStatementForPlayerInfo(String gamerId){
       // String sql =  "select gamer_id, first_name, last_name, age, skill_level, region, language, personality_type, minimum_wait_time, preferred_game, preferred_game_mode from gamers where gamer_id in ( '" + gamerId.toUpperCase() + "' )";

       // LOGGER.info("buildSelectSqlStatementForPlayerInfo: " + sql);
       // return sql;
        return null;
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

}
