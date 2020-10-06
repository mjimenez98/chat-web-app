package chat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class ChatManager implements Serializable {
    private LinkedList<Message> chat;

    public Message postMessage(String user, String message) {
        if (chat == null)
            chat = new LinkedList<>();

        // Message parameter is required. If message is empty, do not process it
        if (message.isEmpty())
            return null;

        LocalDateTime timestamp = LocalDateTime.now();
        Message newMessage = new Message(timestamp, user, message);

        chat.add(newMessage);

        return newMessage;
    }

    public LinkedList<Message> getChat() {
        return chat;
    }
}
