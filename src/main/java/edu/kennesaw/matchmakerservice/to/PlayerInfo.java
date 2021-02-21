package edu.kennesaw.matchmakerservice.to;

import java.util.List;

public class PlayerInfo {
    private String gamerId;
    private String firstName;
    private String lastName;
    private int age;
    private String skillLevel;
    private String region;
    private String language;
    private String personalityType;
    private int minimumWaitTime;
    private String game;
    private String gameMode;
    private List<String> friendsList;
    private List<String> groupsList;

    public PlayerInfo(){}

    public PlayerInfo(String gamerId, String firstName, String lastName, int age, String skillLevel, String region, String language, String personalityType, int minimumWaitTime, String game, String gameMode) {
        this.gamerId = gamerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.skillLevel = skillLevel;
        this.region = region;
        this.language = language;
        this.personalityType = personalityType;
        this.minimumWaitTime = minimumWaitTime;
        this.game = game;
        this.gameMode = gameMode;
    }

    public PlayerInfo(String gamerId, String firstName, String lastName, int age, String skillLevel, String region, String language, String personalityType, int minimumWaitTime, String game, String gameMode, List<String> friendsList, List<String> groupsList) {
        this.gamerId = gamerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.skillLevel = skillLevel;
        this.region = region;
        this.language = language;
        this.personalityType = personalityType;
        this.minimumWaitTime = minimumWaitTime;
        this.game = game;
        this.gameMode = gameMode;
        this.friendsList = friendsList;
        this.groupsList = groupsList;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMinimumWaitTime(int minimumWaitTime) {
        this.minimumWaitTime = minimumWaitTime;
    }

    public int getMinimumWaitTime() {
        return minimumWaitTime;
    }

    public String getGamerId() {
        return gamerId;
    }

    public void setGamerId(String gamerId) {
        this.gamerId = gamerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<String> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<String> friendsList) {
        this.friendsList = friendsList;
    }

    public List<String> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(List<String> groupsList) {
        this.groupsList = groupsList;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "gamerId='" + gamerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", skillLevel='" + skillLevel + '\'' +
                ", region='" + region + '\'' +
                ", language='" + language + '\'' +
                ", personalityType='" + personalityType + '\'' +
                ", minimumWaitTime='" + minimumWaitTime + '\'' +
                ", game='" + game + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", friendsList=" + friendsList +
                ", groupsList=" + groupsList +
                '}';
    }
}
