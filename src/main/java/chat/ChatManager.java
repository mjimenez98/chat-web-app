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
        String thisUser = (user.isEmpty()) ? "anonymous" : user;
        Message newMessage = new Message(timestamp, thisUser, message);

        chat.add(newMessage);

        return newMessage;
    }


   public LinkedList<Message> ListMessages(LocalDateTime startDate, LocalDateTime endDate) {
        // We don't need this. Would be replaced if using a stream
        LinkedList<Message> filteredChat = new LinkedList<>();

       // We don't need this specific if. The logic could be redesigned if using a stream
        if (startDate == null && endDate == null) {
            return chat;
        } else if (startDate == null) {
            startDate = LocalDateTime.of(1000, 1, 1, 1, 1);
        }
        else if (endDate == null) {
            endDate = LocalDateTime.of(3000, 1, 1, 1, 1);
        }

        // Here you can use a stream().filter() and it would take off the nodes you don't want
       // For that you can do chat.stream() and use that as an object
        for (int i = 0; i < chat.size(); i++) {
            if (getChat().get(i).getTimestamp().isAfter(startDate) && getChat().get(i).getTimestamp().isBefore(endDate)) {
                filteredChat.add(chat.get(i));
            }
        }

        // Return that stream as a linked list
        return filteredChat;
    }

    public LinkedList<Message> getChat() {
        return chat;
    }

}
