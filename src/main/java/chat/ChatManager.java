package chat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class ChatManager implements Serializable {
    private LinkedList<Message> chat;
    private LinkedList<Message> filteredChat;


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


    public LinkedList<Message> ListMessage(LocalDateTime startDate, LocalDateTime endDate) {
        filteredChat = new LinkedList<>();
        if (startDate == null && endDate == null) {
            filteredChat = chat;
            return filteredChat;
        } else if (startDate == null) {
            startDate = LocalDateTime.of(1000, 1, 1, 1, 1);}
        else if (endDate == null) {
            endDate = LocalDateTime.of(3000, 1, 1, 1, 1); }

        for (int i = 0; i < chat.size(); i++) {
            if (getChat().get(i).getTimestamp().isAfter(startDate) && getChat().get(i).getTimestamp().isBefore(endDate)) {
                filteredChat.add(chat.get(i)); } }
        return filteredChat;
    }

    public LinkedList<Message> getChat() {
        return chat;
    }

    public LinkedList<Message> getFilteredChat() {
        return filteredChat; }

}
