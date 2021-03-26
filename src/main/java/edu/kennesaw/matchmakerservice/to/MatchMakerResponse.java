package edu.kennesaw.matchmakerservice.to;

import java.util.List;

public class MatchMakerResponse {

	private String gamerId;
	private PlayerInfo playerInfo;
	private List<Player> friendsList;
	private List<PlayerInfo> completeInfoFriendsList;
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

	public List<Player> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<Player> friendsList) {
		this.friendsList = friendsList;
	}

	public List<PlayerInfo> getCompleteInfoFriendsList() {
		return completeInfoFriendsList;
	}

	public void setCompleteInfoFriendsList(List<PlayerInfo> completeInfoFriendsList) {
		this.completeInfoFriendsList = completeInfoFriendsList;
	}

	@Override
	public String toString() {
		return "MatchMakerResponse{" +
				"gamerId='" + gamerId + '\'' +
				", playerInfo=" + playerInfo +
				", friendsList=" + friendsList +
				", completeInfoFriendsList=" + completeInfoFriendsList +
				", errorResponse=" + errorResponse +
				'}';
	}
}
