package edu.kennesaw.matchmakerservice.repo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        Statement stmt = getStatement(con);
        ResultSet rs = stmt.executeQuery(buildSelectSqlStatementForPlayerInfo(gamerId));

        if(rs.next()){
            playerInfo = new PlayerInfo(gamerId, rs.getString("first_name"), rs.getString("last_name"),
                    rs.getInt("age"),
                    rs.getString("skill_level"),
                    rs.getString("region"), rs.getString("language"),
                    rs.getString("personality_type"), rs.getInt("minimum_wait_time"),
                    rs.getString("preferred_game"), rs.getString("preferred_game_mode"));
        }
        closeDatabaseConnection(con, stmt);
        return playerInfo;
    }

    public String buildSelectSqlStatementForPlayerInfo(String gamerId){
        String sql =  "select gamer_id, first_name, last_name, age, skill_level, region, language, personality_type, minimum_wait_time, preferred_game, preferred_game_mode from gamers where gamer_id in ( '" + gamerId.toUpperCase() + "' )";
        LOGGER.info("buildSelectSqlStatementForPlayerInfo: " + sql);
        return sql;
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
