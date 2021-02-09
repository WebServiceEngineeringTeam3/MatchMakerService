package edu.kennesaw.matchmakerservice.to;

public class PlayerInfo {
    private String gamerId;
    private String fullName;
    private String skillLevel;
    private String region;
    private String language;
    private String personalityType;
    private String minimumWaitTime;
    private String game;
    private String gameMode;

    public PlayerInfo(){}

    public PlayerInfo(String gamerId, String fullName, String skillLevel, String region, String language, String personalityType, String minimumWaitTime, String game, String gameMode) {
        this.gamerId = gamerId;
        this.fullName = fullName;
        this.skillLevel = skillLevel;
        this.region = region;
        this.language = language;
        this.personalityType = personalityType;
        this.minimumWaitTime = minimumWaitTime;
        this.game = game;
        this.gameMode = gameMode;
    }

    public String getGamerId() {
        return gamerId;
    }

    public void setGamerId(String gamerId) {
        this.gamerId = gamerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getMinimumWaitTime() {
        return minimumWaitTime;
    }

    public void setMinimumWaitTime(String minimumWaitTime) {
        this.minimumWaitTime = minimumWaitTime;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "gamerId='" + gamerId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", skillLevel='" + skillLevel + '\'' +
                ", region='" + region + '\'' +
                ", language='" + language + '\'' +
                ", personalityType='" + personalityType + '\'' +
                ", minimumWaitTime='" + minimumWaitTime + '\'' +
                ", game='" + game + '\'' +
                ", gameMode='" + gameMode + '\'' +
                '}';
    }
}
