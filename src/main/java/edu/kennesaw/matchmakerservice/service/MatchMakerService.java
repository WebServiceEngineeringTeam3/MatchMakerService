package edu.kennesaw.matchmakerservice.service;

import edu.kennesaw.matchmakerservice.datamodel.PlayerInfo;
import edu.kennesaw.matchmakerservice.manager.MatchMakerManager;
import edu.kennesaw.matchmakerservice.to.MatchMakerResponse;
import edu.kennesaw.matchmakerservice.to.PlayerInfoTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<?> postPlayer(@RequestBody PlayerInfo playerInfo) {
        return new ResponseEntity<>(manager.processPlayer(playerInfo), new HttpHeaders(), HttpStatus.OK);
    }

}
