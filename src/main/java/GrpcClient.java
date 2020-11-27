import chat.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class GrpcClient {

    private ManagedChannel channel;
    private ChatManagerGrpc.ChatManagerBlockingStub blockingStub;
    private ChatManagerGrpc.ChatManagerStub asyncStub;

    /**
     * Construct client for accessing ChatManager server using the existing channel.
     */
    public GrpcClient() {
        initializeStub();
        if (blockingStub != null) {
            System.out.println("Stub initialized");
        }
        //blockingStub = ChatManagerGrpc.newBlockingStub(channel);
    }

    private void initializeStub() {
        channel = ManagedChannelBuilder.forAddress("localhost", 8980).usePlaintext().build();
        blockingStub = ChatManagerGrpc.newBlockingStub(channel);
        asyncStub = ChatManagerGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String postMessage(String user, String message) {
        Msg newMessage = Msg.newBuilder().setMessage(message).setUser(user).build();
        Response rsp = blockingStub.postMessage(newMessage);
        return rsp.getResponse();
    }

    public LinkedList<Message> listMessages(String startDate, String endDate) {
        Request request = Request.newBuilder().setStartDate(startDate).setEndDate(endDate).build();
        Iterator<Msg> messages = blockingStub.listMessages(request);
        LinkedList<Message> chat = new LinkedList<>();
        for (int i = 0; messages.hasNext(); i++) {
            Msg message = messages.next();
            String user = message.getUser();
            String postedMessage = message.getMessage();
            String date = message.getDate();
            chat.add(new Message(LocalDateTime.parse(date), user, postedMessage));
        }
        return chat;
    }

    public String clearChat(String startDate, String endDate) {
        Request request = Request.newBuilder().setStartDate(startDate).setEndDate(endDate).build();
        Response rsp = blockingStub.clearChat(request);
        return rsp.getResponse();
    }
}
