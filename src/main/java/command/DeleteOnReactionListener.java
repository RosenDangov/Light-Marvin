package command;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.CustomEmoji;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.impl.ImplCustomEmoji;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.Reaction;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.message.ReactionAddListener;

public class DeleteOnReactionListener implements ReactionAddListener, MessageCreateListener{

    public void onReactionAdd(DiscordAPI discordAPI, Reaction reaction, User user) {
        if(reaction.getCustomEmoji().getName().equals("emojicastro") && reaction.getCount() > 1 && reaction.isUsedByYou())
            reaction.getMessage().delete();
    }

    public void onMessageCreate(DiscordAPI discordAPI, Message message) {
        if(message.getAuthor().equals(discordAPI.getYourself())){
            message.addCustomEmojiReaction(message.getChannelReceiver().getServer().getCustomEmojiByName("emojicastro"));
        }
    }
}
