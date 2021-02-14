package edu.kennesaw.matchmakerservice.to;

import edu.kennesaw.matchmakerservice.datamodel.PlayerInfo;

public class MatchMakerResponse {

	private String gamerId;
	private PlayerInfo playerInfo;
	private ErrorResponse errorResponse;

	public MatchMakerResponse() {}

	public MatchMakerResponse(String gamerId, PlayerInfo playerInfo, ErrorResponse errorResponse) {
		this.gamerId = gamerId;
		this.playerInfo = playerInfo;
		this.errorResponse = errorResponse;
	}

	public String getGamerId() {
		return gamerId;
	}

	public void setGamerId(String gamerId) {
		this.gamerId = gamerId;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	@Override
	public String toString() {
		return "MatchMakerResponse{" +
				"gamerId='" + gamerId + '\'' +
				", playerInfo=" + playerInfo +
				", errorResponse=" + errorResponse +
				'}';
	}
}
