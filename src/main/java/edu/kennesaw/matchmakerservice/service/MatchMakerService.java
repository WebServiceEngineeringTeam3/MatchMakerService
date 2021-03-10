package edu.kennesaw.matchmakerservice.service;

import edu.kennesaw.matchmakerservice.manager.MatchMakerManager;
import edu.kennesaw.matchmakerservice.to.ErrorResponse;
import edu.kennesaw.matchmakerservice.to.MatchMakerRequest;
import edu.kennesaw.matchmakerservice.to.MatchMakerResponse;
import edu.kennesaw.matchmakerservice.to.PlayerInfo;
import edu.kennesaw.matchmakerservice.util.Constants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/matchmaker")
public class MatchMakerService {

    @Autowired
    MatchMakerManager manager;

    @ApiOperation(value = "postPlayer", notes = "postPlayer",
            httpMethod = "POST", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MatchMakerResponse.class),
            @ApiResponse(code = 204, message = "Resource Unavailable"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Service Time Out")})
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/postPlayer")
    public @ResponseBody
    ResponseEntity<?> postPlayer(@RequestBody MatchMakerRequest request) {
        boolean valid = isValidRequest(request);
        if(!valid){
            MatchMakerResponse response = new MatchMakerResponse();
            response.setErrorResponse(new ErrorResponse(Constants.CODE_BAD_REQUEST, Constants.MESSAGE_BAD_REQUEST));
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(manager.processPlayer(request), new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "getPlayer", notes = "getPlayer",
            httpMethod = "GET", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MatchMakerResponse.class),
            @ApiResponse(code = 204, message = "Resource Unavailable"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Service Time Out")})
    @CrossOrigin
    @GetMapping("/getPlayer")
    public @ResponseBody
    ResponseEntity<MatchMakerResponse> getPlayerInformation(@RequestParam(name="gamerId", required=true) String gamerId) {
        if (gamerId != null) {
            return new ResponseEntity<>(manager.getPlayer(gamerId), new HttpHeaders(), HttpStatus.OK);
        }
        return null;
    }

    @ApiOperation(value = "putPlayer", notes = "putPlayer",
            httpMethod = "PUT", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MatchMakerResponse.class),
            @ApiResponse(code = 204, message = "Resource Unavailable"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Service Time Out")})
    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT, path = "/putPlayer")
    public @ResponseBody
    ResponseEntity<?> putPlayer(@RequestBody MatchMakerRequest request) {
        boolean valid = isValidRequest(request);
        if(valid){
            List<String> newfriends = request.getPlayerInfo().getFriendsList();
            if(newfriends != null) {
                for (String friend : newfriends) {
                    // insert into the join table
                }
            }
            List<String> newGroups  = request.getPlayerInfo().getGroupsList();
            if(newGroups != null){
              // for(Group)
            }
        }
        if(!valid){
            MatchMakerResponse response = new MatchMakerResponse();
            response.setErrorResponse(new ErrorResponse(Constants.CODE_BAD_REQUEST, Constants.MESSAGE_BAD_REQUEST));
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(manager.processPlayer(request), new HttpHeaders(), HttpStatus.OK);
    }


    @ApiOperation(value = "putPlayerAddFriends", notes = "putPlayerAddFriends",
            httpMethod = "PUT", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MatchMakerResponse.class),
            @ApiResponse(code = 204, message = "Resource Unavailable"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Service Time Out")})
    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT, path = "/addFriends")
    public @ResponseBody
    ResponseEntity<?> postPlayerAddFriends(@RequestParam String player_id,@RequestParam String friend_ids) {

        if(player_id == null || player_id.isEmpty()  || friend_ids == null  ){
            MatchMakerResponse response = new MatchMakerResponse();
            response.setErrorResponse(new ErrorResponse(Constants.CODE_BAD_REQUEST, Constants.MESSAGE_BAD_REQUEST));

            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        else {
            manager.addFriends(player_id,friend_ids);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "searchPlayers", notes = "searchPlayers",
            httpMethod = "POST", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MatchMakerResponse.class),
            @ApiResponse(code = 204, message = "Resource Unavailable"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Service Time Out")})
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/searchPlayers")
    public @ResponseBody
    ResponseEntity<?> searchPlayers(@RequestBody MatchMakerRequest request) {
        boolean valid = isValidRequest(request);
        if(!valid){
            MatchMakerResponse response = new MatchMakerResponse();
            response.setErrorResponse(new ErrorResponse(Constants.CODE_BAD_REQUEST, Constants.MESSAGE_BAD_REQUEST));
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(manager.searchPlayers(request), new HttpHeaders(), HttpStatus.OK);
    }

    public boolean isValidRequest(MatchMakerRequest request){

        if(null != request && null != request.getPlayerInfo() &&
                !StringUtils.isEmpty(request.getCrudType())
                && !StringUtils.isEmpty(request.getPlayerInfo().getGamerId())
        ){
            return true;
        }

        return false;
    }

}
