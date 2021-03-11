package edu.kennesaw.matchmakerservice.to;

import java.util.Objects;

public class Player {
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
    private String matchPercentage;

    public Player(){}

    public Player(String gamerId, String firstName, String lastName, int age, String skillLevel, String region,
                  String language, String personalityType, int minimumWaitTime, String game, String gameMode,
                  String matchPercentage) {
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
        this.matchPercentage = matchPercentage;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public int getMinimumWaitTime() {
        return minimumWaitTime;
    }

    public void setMinimumWaitTime(int minimumWaitTime) {
        this.minimumWaitTime = minimumWaitTime;
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

    public String getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(String matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return age == player.age && minimumWaitTime == player.minimumWaitTime && Objects.equals(gamerId, player.gamerId) && Objects.equals(firstName, player.firstName) && Objects.equals(lastName, player.lastName) && Objects.equals(skillLevel, player.skillLevel) && Objects.equals(region, player.region) && Objects.equals(language, player.language) && Objects.equals(personalityType, player.personalityType) && Objects.equals(game, player.game) && Objects.equals(gameMode, player.gameMode) && Objects.equals(matchPercentage, player.matchPercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gamerId, firstName, lastName, age, skillLevel, region, language, personalityType, minimumWaitTime, game, gameMode, matchPercentage);
    }

    @Override
    public String toString() {
        return "Player{" +
                "gamerId='" + gamerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", skillLevel='" + skillLevel + '\'' +
                ", region='" + region + '\'' +
                ", language='" + language + '\'' +
                ", personalityType='" + personalityType + '\'' +
                ", minimumWaitTime=" + minimumWaitTime +
                ", game='" + game + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", matchPercentage='" + matchPercentage + '\'' +
                '}';
    }
}
