package edu.kennesaw.matchmakerservice.unit.manager;

import edu.kennesaw.matchmakerservice.Application;
import edu.kennesaw.matchmakerservice.manager.MatchMakerManager;
import edu.kennesaw.matchmakerservice.repo.Repository;
import edu.kennesaw.matchmakerservice.to.MatchMakerRequest;
import edu.kennesaw.matchmakerservice.to.MatchMakerResponse;
import edu.kennesaw.matchmakerservice.to.PlayerInfo;
import edu.kennesaw.matchmakerservice.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MatchMakerManagerUnitTest {

    private static final Logger LOGGER = LogManager.getLogger(MatchMakerManagerUnitTest.class);

    String firstName = "Eric";
    String lastName = "Acevedo";
    int age = 35;
    String skillLevel = "Advanced";
    String region = "Southeast US";
    String language = "English";
    String personalityType = "Casual";
    int minimumWaitTime = 2;
    String game = "Overwatch";
    String gameMode = "Competitive";

    @Mock
    Repository repo;

    @InjectMocks
    MatchMakerManager manager;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessPlayer_CREATE_Request_Success(){
        String gamerId = "EXA6777";
        MatchMakerRequest request = getMatchMakerRequest(Constants.CREATE_OPERATION, gamerId);
        try{
            Mockito.when(repo.addNewPlayer(Mockito.any())).thenReturn(true);
            MatchMakerResponse response = manager.processPlayer(request);
            LOGGER.info("testProcessPlayer_CREATE_Request_Success: " + response.toString());
            assertNotNull(response);
            assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
            assertNotNull(response.getPlayerInfo());
            assertTrue(response.getPlayerInfo().getFirstName().equalsIgnoreCase(firstName));
            assertTrue(response.getPlayerInfo().getLastName().equalsIgnoreCase(lastName));
            assertTrue(response.getPlayerInfo().getAge() == age);
            assertTrue(response.getPlayerInfo().getMinimumWaitTime() == minimumWaitTime);
            assertTrue(response.getPlayerInfo().getSkillLevel().equalsIgnoreCase(skillLevel));
            assertTrue(response.getPlayerInfo().getRegion().equalsIgnoreCase(region));
            assertTrue(response.getPlayerInfo().getLanguage().equalsIgnoreCase(language));
            assertTrue(response.getPlayerInfo().getPersonalityType().equalsIgnoreCase(personalityType));
            assertTrue(response.getPlayerInfo().getGame().equalsIgnoreCase(game));
            assertTrue(response.getPlayerInfo().getGameMode().equalsIgnoreCase(gameMode));
            assertNull(response.getErrorResponse());
        }
        catch(Exception e){
            assertTrue(false);
        }

    }

    @Test
    public void testProcessPlayer_CREATE_Request_Failure(){
        String gamerId = "EXA6777";
        MatchMakerRequest request = getMatchMakerRequest(Constants.CREATE_OPERATION, gamerId);
        try{
            Mockito.when(repo.addNewPlayer(Mockito.any())).thenReturn(false);
            MatchMakerResponse response = manager.processPlayer(request);
            LOGGER.info("testProcessPlayer_CREATE_Request_Failure: " + response.toString());
            assertNotNull(response);
            assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
            assertNotNull(response.getPlayerInfo());
            assertNotNull(response.getErrorResponse());
        }
        catch(Exception e){
            assertTrue(false);
        }

    }

    @Test
    public void testProcessPlayer_UPDATE_Request_Success(){
        String gamerId = "EXA6777";
        MatchMakerRequest request = getMatchMakerRequest(Constants.UPDATE_OPERATION, gamerId);
        try{
            Mockito.when(repo.editPlayer(Mockito.any())).thenReturn(true);
            MatchMakerResponse response = manager.processPlayer(request);
            LOGGER.info("testProcessPlayer_UPDATE_Request_Success: " + response.toString());
            assertNotNull(response);
            assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
            assertNotNull(response.getPlayerInfo());
            assertTrue(response.getPlayerInfo().getFirstName().equalsIgnoreCase(firstName));
            assertTrue(response.getPlayerInfo().getLastName().equalsIgnoreCase(lastName));
            assertTrue(response.getPlayerInfo().getAge() == age);
            assertTrue(response.getPlayerInfo().getMinimumWaitTime() == minimumWaitTime);
            assertTrue(response.getPlayerInfo().getSkillLevel().equalsIgnoreCase(skillLevel));
            assertTrue(response.getPlayerInfo().getRegion().equalsIgnoreCase(region));
            assertTrue(response.getPlayerInfo().getLanguage().equalsIgnoreCase(language));
            assertTrue(response.getPlayerInfo().getPersonalityType().equalsIgnoreCase(personalityType));
            assertTrue(response.getPlayerInfo().getGame().equalsIgnoreCase(game));
            assertTrue(response.getPlayerInfo().getGameMode().equalsIgnoreCase(gameMode));
            assertNull(response.getErrorResponse());
        }
        catch(Exception e){
            assertTrue(false);
        }

    }

    @Test
    public void testProcessPlayer_UPDATE_Request_Failure(){
        String gamerId = "EXA6777";
        MatchMakerRequest request = getMatchMakerRequest(Constants.UPDATE_OPERATION, gamerId);
        try{
            Mockito.when(repo.editPlayer(Mockito.any())).thenReturn(false);
            MatchMakerResponse response = manager.processPlayer(request);
            LOGGER.info("testProcessPlayer_UPDATE_Request_Failure: " + response.toString());
            assertNotNull(response);
            assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
            assertNotNull(response.getPlayerInfo());
            assertNotNull(response.getErrorResponse());
        }
        catch(Exception e){
            assertTrue(false);
        }

    }

    @Test
    public void testGetPlayer_Success(){
        String gamerId = "EXA6777";
        try{
            Mockito.when(repo.getPlayerInformation(Mockito.anyString())).thenReturn(getPlayerInfo(gamerId));
            MatchMakerResponse response = manager.getPlayer(gamerId);
            LOGGER.info("testGetPlayer_Success: " + response.toString());
            assertNotNull(response);
            assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
            assertNotNull(response.getPlayerInfo());
            assertNull(response.getErrorResponse());
        }
        catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testGetPlayer_Failure(){
        String gamerId = "EXA6777";
        try{
            Mockito.when(repo.getPlayerInformation(Mockito.anyString())).thenReturn(new PlayerInfo());
            MatchMakerResponse response = manager.getPlayer(gamerId);
            LOGGER.info("testGetPlayer_Failure: " + response.toString());
            assertNotNull(response);
            assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
            assertNull(response.getPlayerInfo());
            assertNotNull(response.getErrorResponse());
        }
        catch(Exception e){
            assertTrue(false);
        }
    }

    public MatchMakerRequest getMatchMakerRequest(String crudType, String gamerId){
        MatchMakerRequest request = new MatchMakerRequest();
        request.setCrudType(crudType);
        request.setPlayerInfo(new PlayerInfo(gamerId, firstName, lastName, age, skillLevel, region, language, personalityType, minimumWaitTime, game, gameMode));
        return request;
    }

    public PlayerInfo getPlayerInfo(String gamerId){
        return new PlayerInfo(gamerId, firstName, lastName, age, skillLevel, region, language, personalityType, minimumWaitTime, game, gameMode);
    }
}
