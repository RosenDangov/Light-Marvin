package command;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

import java.util.ArrayList;

public class PartyListener implements MessageCreateListener {
    private ArrayList<Party> parties;

    private class Party {
        private User leader;
        private ArrayList<User> members;
        private String name;
        private String game;
        private boolean recruiting;

        public Party(User leader, String name, String game) {
            this.leader = leader;
            this.name = name;
            this.game = game;
            members = new ArrayList<User>();
            recruiting = true;
        }


        public User getLeader() {
            return leader;
        }

        public void setRecruiting() {
            recruiting = !recruiting;
        }

        public boolean isRecruiting() {
            return recruiting;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }

        public ArrayList<User> getMembers() {
            return members;
        }

        public void removeMember(User user) {
            members.remove(user);
        }

        public void addMember(User user) {
            members.add(user);
        }

        public int partySize() {
            return members.size() + 1;
        }
    }

    public PartyListener(){
        parties = new ArrayList<Party>();
    }
    public void onMessageCreate(DiscordAPI discordAPI, Message message) {

        if (message.getAuthor().getName().equals("LightBot")) return;
        if (message.getContent().equalsIgnoreCase("ping")) {
            // reply to the message
            message.reply("pong");
            return;
        }
        if (message.getContent().charAt(0) == '!') {
            String[] args = message.getContent().substring(1).split(" ");
            String command = args[0].toLowerCase();
//            if(command.equals("help")){
//                message.reply(printHelp());
//                return;
//            }
            if (command.equals("createparty")) {
                try {
                    String name = args[1];
                    String game = args[2];
                    User leader = message.getAuthor();
                    for(Party p : parties){
                        if(p.getLeader().equals(leader)) {
                            message.reply("You already have a party");
                            return;
                        }
                    }
                    Party party = new Party(leader, name, game);
                    parties.add(party);
                    message.reply("Party created");
                } catch (Exception e) {};
            }
            if (command.equals("deleteparty")){
                Party toRemove = null;
                for(Party p : parties){
                    if(p.getLeader().equals(message.getAuthor())){
                        toRemove = p;
                    }
                }
                if(toRemove == null)
                    message.reply("No party to delete");
                else{
                    parties.remove(toRemove);
                    message.reply("Party deleted");
                }
            }
            if(command.equals("setname")) {
                String name = args[1];
                for (Party p : parties) {
                    if (p.getLeader().equals(message.getAuthor())) {
                        p.setName(name);
                        message.reply("Party name changed");
                        return;
                    }
                }
                message.reply("You don't have a party");
            }
            if(command.equals("setgame")){
                String game= args[1];
                for(Party p : parties){
                    if(p.getLeader().equals(message.getAuthor())){
                        p.setGame(game);
                        message.reply("Party game changed");
                        return;
                    }
                }
                message.reply("You don't have a party");
            }

            if(command.equals("listparty")){
                String members = null;
                for(Party p : parties){
                    if(p.getLeader().equals(message.getAuthor())){
                        members = "";
                        members += "Leader: "+p.getLeader().getName();
                        if(p.partySize()>=2)
                            members+=", Members: ";
                        for(User u : p.getMembers()){
                            members+=u.getName()+", ";
                        }
                        if(p.partySize()>=2)
                            members=members.substring(0,members.length()-2);
                    }
                    break;
                }
                if(members == null)
                    message.reply("You don't own a party");
                else{
                    message.reply(members);
                }

            }
            if(command.equals("joinparty")){
                try{
                    String leader = args[1];
                    if(leader.equals(message.getAuthor().getName())){
                        message.reply("Can't join your own party :( ");
                        return;
                    }
                    for(Party p :parties){
                        if(p.getLeader().getName().equals(leader)) {
                            p.addMember(message.getAuthor());
                            message.reply("Joined party");
                        }
                    }
                }catch(Exception e){};
            }
            if(command.equals("leaveparty")){
                String leader = args[1];
                for(Party p : parties){
                    if(p.getLeader().getName().equals(leader)){
                        if(p.getMembers().remove(message.getAuthor())){
                            message.reply("Party left");
                        }else{
                            message.reply("You are not in this party");
                        }
                    }
                }
            }
            if(command.equals("parties")){
                try{
                    String toReturn = "";
                    if(parties.isEmpty()) {
                        message.reply("No parties. Maybe start one?");
                        return;
                    }
                    for(Party p : parties){
                        toReturn += "Leader: "+p.getLeader().getName()+", Party: "+p.getName()+", Game: "+p.getGame()+", Members: "+p.partySize()+"\n";
                    }
                    message.reply(toReturn);
                }catch(Exception e){};
            }
        }

    }

}
