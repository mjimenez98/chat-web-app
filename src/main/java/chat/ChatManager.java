package chat;

import java.io.Serializable;
import java.util.LinkedList;

public class ChatManager implements Serializable {
    private LinkedList<Message> chat;

    public void postMessage(Message newMessage) {
        if (chat == null)
            chat = new LinkedList<>();

        chat.add(newMessage);
    }

    public LinkedList<Message> getChat() {
        return chat;
    }
}
