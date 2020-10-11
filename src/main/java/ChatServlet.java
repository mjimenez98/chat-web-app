import chat.ChatManager;
import chat.Message;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

@WebServlet(name = "ChatServlet")
public class ChatServlet extends HttpServlet {
    private ChatManager chatManager;

    public void init(ServletConfig config) {
        chatManager = new ChatManager();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Save referrer header
        String referrer = request.getHeader("referer");
        referrer = referrer.substring(referrer.lastIndexOf("/") + 1);

        if (!referrerIsValid("chat", referrer)) {
            String nonValidReferrerError = "true";
            request.setAttribute("nonValidReferrerError", nonValidReferrerError);
        } else {
            // Initialize Message properties
            String message = request.getParameter("message");
            String user = request.getParameter("user");
            
            //Initialize Filtered Message properties
            String dateStart = request.getParameter("start");
            String dateEnd = request.getParameter("end");
            LocalDateTime start =null;
            LocalDateTime end =null;

            // Parse String into LocalDateTime
            if (!dateStart.isEmpty()) {
                start=LocalDateTime.parse(dateStart);
            }
            if (!dateEnd.isEmpty()){
                end = LocalDateTime.parse(dateEnd);}
            
            // Create new Message and save it
            Message newMessage = chatManager.postMessage(user, message);

             // Create new Filtered chat and save it
             LinkedList<Message> filteredChat = chatManager.ListMessage(start,end);

            // If message error
            // NOTE: Could be improved by having a NoMessageError subclass of Message for better error handling
            String noMessageError = (newMessage == null) ? "true" : "false";

            // Update attributes and forward the request to the view
            request.setAttribute("noMessageError", noMessageError);
            request.setAttribute("chatManager", chatManager);
            request.setAttribute("filteredChat", filteredChat);
        }

        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateStart = request.getParameter("start");
        String dateEnd = request.getParameter("end");
        LocalDateTime start =null;
        LocalDateTime end =null;

        // Parse String into LocalDateTime
        if (dateStart !=null && dateStart.length() >0) {
            start=LocalDateTime.parse(dateStart);
        }
        if (dateStart !=null && dateEnd.length() >0){
            end = LocalDateTime.parse(dateEnd);}

    chatManager.ListMessage(start,end);
    request.setAttribute("chatManager", chatManager);
    
        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }

    // -------------------- HELPER FUNCTIONS --------------------

    private boolean referrerIsValid(String expected, String received) {
        return expected.equals(received);
    }

}
