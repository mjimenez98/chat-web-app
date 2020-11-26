import chat.ChatManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import chat.ChatManagerGrpc;
import chat.Message;
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

@WebServlet(name = "ChatServlet")
public class ChatServlet extends HttpServlet {
    //private ChatManager chatManager;
    private GrpcClient client;

    public void init(ServletConfig config) {
        client = new GrpcClient();
        if(client!= null){
            System.out.println("Client created");
        }
        else{
            System.out.println("Could not create a client");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getHeader("referer") == null) {
            request.setAttribute("nonValidReferrerError", "true");
        }
        else {
            // Get session
            HttpSession session = request.getSession(true);

            // Initialize Message properties
            String message = request.getParameter("message");
            String user = request.getParameter("user");

            // Create new Message and save it
            String apiResponse = client.postMessage(user, message);

            // If message parameter missing
            // NOTE: Could be improved by having a NoMessageError subclass of Message for better error handling
            String noMessageError = (apiResponse == "SUCCESS") ? "true" : "false";
            System.out.println(apiResponse);
            // Update attributes
            session.setAttribute("userId", user);
            session.setAttribute("apiResponse", apiResponse);
            session.setAttribute("noMessageError", noMessageError);
            request.setAttribute("nonValidReferrerError", "false");
            //LinkedList<Message> chat = chatManager.ListMessages(null, null);
            //session.setAttribute("chat", chat);
        }

        response.sendRedirect("/chat_web_app_war/chat");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        String dateStart = request.getParameter("from");
        String dateEnd = request.getParameter("to");
        LocalDateTime start = null;
        LocalDateTime end = null;
        */
        /*
        if (request.getHeader("referer") == null) {
            request.setAttribute("nonValidReferrerError", "true");
        } else {
            // Parse String into LocalDateTime
            if (dateStart != null && dateStart.length() > 0) {
                start = LocalDateTime.parse(dateStart);
            }
            if (dateEnd != null && dateEnd.length() > 0) {
                end = LocalDateTime.parse(dateEnd);
            }

            if (request.getParameter("format") != null && request.getParameter("format").equals("Download as TXT")) {
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition", "attachment; filename=\"chat.txt\"");
                response.setHeader("Expires", String.valueOf(LocalDateTime.now().plusDays(1)));

                try {
                    OutputStream outputStream = response.getOutputStream();

                    for (Message message : chatManager.getChat()) {
                        String mStr = message.getUser() + " - " + message.getMessage() + " - " + message.getTimestamp() + "\n";
                        outputStream.write(mStr.getBytes());
                    }

                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (request.getParameter("format") != null && request.getParameter("format").equals("Download as XML")) {
                response.setContentType("text/xml");
                response.setHeader("Content-Disposition", "attachment; filename=\"chat.xml\"");
                response.setHeader("Expires", String.valueOf(LocalDateTime.now().plusDays(1)));

                try {
                    OutputStream outputStream = response.getOutputStream();
                    String xmlHeading = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
                    outputStream.write(xmlHeading.getBytes());

                    for (Message message : chatManager.getChat()) {
                        String mStr = "<chat>\n" + "\t<user>"+ message.getUser() +
                                "</user>\n" + "\t<message>"+ message.getMessage() +
                                "</message>\n" + "\t<timestamp>"+ message.getTimestamp() +
                                "</timestamp>\n" + "</chat>\n";
                        outputStream.write(mStr.getBytes());
                    }

                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (request.getParameter("delete") != null) {
                chatManager.clearChat(start, end);
            }

            request.setAttribute("nonValidReferrerError", "false");
        }

        LinkedList<Message> chat = chatManager.ListMessages(start, end);
        request.setAttribute("chat", chat);
*/
        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }
}
