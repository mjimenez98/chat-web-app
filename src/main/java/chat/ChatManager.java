package chat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (chat == null)
            chat = new LinkedList<>();
            Stream<Message> messagesToKeepStream = chat.stream();
            if (startDate != null && endDate != null) {
                messagesToKeepStream = chat.stream().filter(postedMessage ->
                        postedMessage.getTimestamp().isAfter(startDate) || postedMessage.getTimestamp().isBefore(endDate));
            } else if (startDate != null) {
                messagesToKeepStream = chat.stream().filter(postedMessage ->
                        postedMessage.getTimestamp().isAfter(startDate));
            } else if (endDate != null) {
                messagesToKeepStream = chat.stream().filter(postedMessage ->
                        postedMessage.getTimestamp().isBefore(endDate));
            }
        return messagesToKeepStream.collect(Collectors.toCollection(LinkedList::new));
    }

    public LinkedList<Message> getChat() {
        return chat;
    }

}
