package edu.kennesaw.matchmakerservice.to;

public class GroupInfo {

    private String gamer_id;
    private String gamer_group_id;
    private String  gamer_friend_id;

    public GroupInfo(){}


    public GroupInfo (String gamer_id,String  gamer_group_id, String gamer_friend_id){
        this.gamer_id=gamer_id;
        this.gamer_group_id = gamer_group_id;
        this.gamer_friend_id=gamer_friend_id;
    }
    public String getGamer_id() {
        return gamer_id;
    }

    public void setGamer_id(String gamer_id) {
        this.gamer_id = gamer_id;
    }

    public String getGamer_group_id() {
        return gamer_group_id;
    }

    public void setGamer_group_id(String gamer_group_id) {
        this.gamer_group_id = gamer_group_id;
    }

    public String getGamer_friend_id() {
        return gamer_friend_id;
    }

    public void setGamer_friend_id(String gamer_friend_id) {
        this.gamer_friend_id = gamer_friend_id;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "gamer_id='" + gamer_id + '\'' +
                ", gamer_group_id='" + gamer_group_id + '\'' +
                ", gamer_friend_id='" + gamer_friend_id + '\'' +
                '}';
    }
}
