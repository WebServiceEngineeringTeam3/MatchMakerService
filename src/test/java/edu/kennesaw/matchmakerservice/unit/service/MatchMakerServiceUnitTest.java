package edu.kennesaw.matchmakerservice.unit.service;

import edu.kennesaw.matchmakerservice.manager.MatchMakerManager;
import edu.kennesaw.matchmakerservice.service.MatchMakerService;
import edu.kennesaw.matchmakerservice.to.ErrorResponse;
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
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MatchMakerServiceUnitTest {

    private static final Logger LOGGER = LogManager.getLogger(MatchMakerServiceUnitTest.class);

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
    MatchMakerManager manager;

    @InjectMocks
    MatchMakerService service;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPostPlayer_Success(){
        String gamerId = "EXA6777";
        MatchMakerRequest request = getMatchMakerRequest(Constants.CREATE_OPERATION, gamerId);
        Mockito.when(manager.processPlayer(Mockito.any())).thenReturn(getMatchMakerResponse(gamerId, null));
        ResponseEntity entity = service.postPlayer(request);
        LOGGER.info("testPostPlayer_Success: " + entity.getBody().toString());
        MatchMakerResponse response = (MatchMakerResponse)entity.getBody();
        assertNotNull(response);
        assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
        assertNotNull(response.getPlayerInfo());
        assertNull(response.getErrorResponse());
    }

    @Test
    public void testPostPlayer_Failure(){
        String gamerId = "EXA6777";
        MatchMakerRequest request = getMatchMakerRequest(Constants.CREATE_OPERATION, gamerId);
        Mockito.when(manager.processPlayer(Mockito.any())).thenReturn(getMatchMakerResponse(gamerId, new ErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR)));
        ResponseEntity entity = service.postPlayer(request);
        LOGGER.info("testPostPlayer_Failure: " + entity.getBody().toString());
        MatchMakerResponse response = (MatchMakerResponse)entity.getBody();
        assertNotNull(response);
        assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
        assertNotNull(response.getPlayerInfo());
        assertNotNull(response.getErrorResponse());
    }

    @Test
    public void testGetPlayerInfo_Success(){
        String gamerId = "EXA6777";
        Mockito.when(manager.getPlayer(Mockito.anyString())).thenReturn(getMatchMakerResponse(gamerId, null));
        ResponseEntity entity = service.getPlayerInformation(gamerId);
        LOGGER.info("testGetPlayerInfo_Success: " + entity.getBody().toString());
        MatchMakerResponse response = (MatchMakerResponse)entity.getBody();
        assertNotNull(response);
        assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
        assertNotNull(response.getPlayerInfo());
        assertNull(response.getErrorResponse());
    }

    @Test
    public void testGetPlayerInfo_Failure(){
        String gamerId = "EXA6777";
        Mockito.when(manager.getPlayer(Mockito.anyString())).thenReturn(getMatchMakerResponse(gamerId, new ErrorResponse(Constants.CODE_SERVICE_ERROR, Constants.MESSAGE_SERVICE_ERROR)));
        ResponseEntity entity = service.getPlayerInformation(gamerId);
        LOGGER.info("testGetPlayerInfo_Failure: " + entity.getBody().toString());
        MatchMakerResponse response = (MatchMakerResponse)entity.getBody();
        assertNotNull(response);
        assertTrue(response.getGamerId().equalsIgnoreCase(gamerId));
        assertNotNull(response.getPlayerInfo());
        assertNotNull(response.getErrorResponse());
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

    public MatchMakerResponse getMatchMakerResponse(String gamerId, ErrorResponse errorResponse){
        MatchMakerResponse response = new MatchMakerResponse();
        response.setGamerId(gamerId);
        response.setPlayerInfo(getPlayerInfo(gamerId));
        response.setErrorResponse(errorResponse);
        return response;
    }
}
