import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;

import java.util.ArrayList;

public class Party {
    private User leader;
    private ArrayList<User> members;
    private String name;
    private String game;
    private boolean recruiting;
    public Party(User leader, String name, String game){
        this.leader = leader;
        this.name = name;
        this.game = game;
        members = new ArrayList<User>();
        recruiting = true;
    }


    public User getLeader(){return leader;}
    public void setRecruiting(){
        recruiting = !recruiting;
    }
    public boolean isRecruiting() { return recruiting; }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setGame(String game){
        this.game = game;
    }
    public String getGame(){
        return game;
    }

    public ArrayList<User> getMembers(){
        return members;
    }
    public void removeMember(User user){
        members.remove(user);
    }
    public void addMember(User user){
        members.add(user);
    }
    public int partySize(){
        return members.size()+1;
    }
}
