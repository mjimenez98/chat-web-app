import chat.ChatManagerGrpc;
import chat.Msg;
import chat.Response;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcClient {

    private ManagedChannel channel;
    private ChatManagerGrpc.ChatManagerBlockingStub blockingStub;

    /** Construct client for accessing ChatManager server using the existing channel. */
    public GrpcClient() {
        initializeStub();
        if(blockingStub != null){
            System.out.println("Stub initialized");
        }
        //blockingStub = ChatManagerGrpc.newBlockingStub(channel);
    }

    private void initializeStub() {
        channel = ManagedChannelBuilder.forAddress("localhost", 8980).usePlaintext().build();
        blockingStub = ChatManagerGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String postMessage(String user, String message){
        Msg newMessage = Msg.newBuilder().setMessage(message).setUser(user).build();
        Response rsp = blockingStub.postMessage(newMessage);
        return rsp.getResponse();
    }
}
