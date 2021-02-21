package edu.kennesaw.matchmakerservice.to;

public class MatchMakerRequest {
    private String crudType;
    private PlayerInfo playerInfo;

    public MatchMakerRequest(){}

    public MatchMakerRequest(String crudType, PlayerInfo playerInfo) {
        this.crudType = crudType;
        this.playerInfo = playerInfo;
    }

    public String getCrudType() {
        return crudType;
    }

    public void setCrudType(String crudType) {
        this.crudType = crudType;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    @Override
    public String toString() {
        return "MatchMakerRequest{" +
                "crudType='" + crudType + '\'' +
                ", playerInfo=" + playerInfo +
                '}';
    }
}
