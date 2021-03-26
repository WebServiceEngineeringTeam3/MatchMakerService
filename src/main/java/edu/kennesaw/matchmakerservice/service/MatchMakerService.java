package edu.kennesaw.matchmakerservice.service;

import edu.kennesaw.matchmakerservice.manager.MatchMakerManager;
import edu.kennesaw.matchmakerservice.to.*;
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
import java.util.ArrayList;
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
            List<GroupInfo> newGroups  = request.getPlayerInfo().getGroupsList();
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

    @ApiOperation(value = "search", notes = "search",
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

    @RequestMapping(method = RequestMethod.GET, path = "/search")

    /*
    example query  localhost:8080/matchmaker/search?skill_level=mid-core&personality_type=casual&preferred_game=Warzone
     */
    public @ResponseBody
    ResponseEntity<?> search(@RequestParam String skill_level, @RequestParam String personality_type,@RequestParam String preferred_game) {
        List<PlayerInfo> players = null;
        if( 1 < 0){
            MatchMakerResponse response = new MatchMakerResponse();
            response.setErrorResponse(new ErrorResponse(Constants.CODE_BAD_REQUEST, Constants.MESSAGE_BAD_REQUEST));

            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        else {
        players =  manager.search(skill_level,personality_type,preferred_game);
        }
        return new ResponseEntity<>(players, new HttpHeaders(), HttpStatus.OK);

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MatchMakerResponse.class),
            @ApiResponse(code = 204, message = "Resource Unavailable"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Service Time Out")})
    @CrossOrigin

    @RequestMapping(method = RequestMethod.GET, path = "/groups")

    /*
    example query  localhost:8080/matchmaker/groups?gamerid="sdfsdf"
     */
    public @ResponseBody
    ResponseEntity<?> getGroups(@RequestParam String gamer_id) {
        List<GroupInfo> groups = null;
        groups =  manager.findGroups(gamer_id);

        if( groups == null){
            MatchMakerResponse response = new MatchMakerResponse();
            response.setErrorResponse(new ErrorResponse(Constants.CODE_BAD_REQUEST, Constants.MESSAGE_BAD_REQUEST));

            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(groups, new HttpHeaders(), HttpStatus.OK);

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MatchMakerResponse.class),
            @ApiResponse(code = 204, message = "Resource Unavailable"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Service Time Out")})
    @CrossOrigin

    @RequestMapping(method = RequestMethod.POST, path = "/groups")

    /*
    example query  localhost:8080/matchmaker/groups?gamerid="sdfsdf"&gamer_group_id='casual'&gamer_friend_id='gfgd'
     */
    public @ResponseBody
    ResponseEntity<?> getGroups(@RequestParam String gamer_id,@RequestParam String gamer_group_id, @RequestParam String gamer_friend_id) {
      boolean  inserted = false;
        inserted =  manager.createGroup(gamer_id,gamer_friend_id,gamer_group_id);

        if( !inserted){
            MatchMakerResponse response = new MatchMakerResponse();
            response.setErrorResponse(new ErrorResponse(Constants.CODE_BAD_REQUEST, Constants.MESSAGE_BAD_REQUEST));

            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }


    else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        }

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MatchMakerResponse.class),
            @ApiResponse(code = 204, message = "Resource Unavailable"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Service Time Out")})
    @CrossOrigin

    @RequestMapping(method = RequestMethod.GET, path = "/gamer_groups")

    /*
    example   GET  localhost:8080/matchmaker/group_groups?gamerid="sdfsdf"
     */
    public @ResponseBody
    ResponseEntity<?> getGamerGroups(@RequestParam String gamer_id) {

        List<GamerGroup> groups =  manager.getGamerGroups(gamer_id);

        if(groups == null){
            MatchMakerResponse response = new MatchMakerResponse();
            response.setErrorResponse(new ErrorResponse(Constants.CODE_BAD_REQUEST, Constants.MESSAGE_BAD_REQUEST));

            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }


        else {
            return new ResponseEntity<>(groups, new HttpHeaders(), HttpStatus.OK);
        }

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MatchMakerResponse.class),
            @ApiResponse(code = 204, message = "Resource Unavailable"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Service Time Out")})
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/gamer_friends")
    /*
    example   GET  http://localhost:8080/matchmaker/gamer_friends?gamerIds=cisco94,ungo1985,ungo3000,ababbidge5x,noconcannon5b
     */
    public @ResponseBody
    ResponseEntity<?> getGamerFriends(@RequestParam List<String> gamerIds) {
        MatchMakerResponse response =  manager.getCompleteInfoForFriendsList(gamerIds);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
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
