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

            // Create new Message and save it
            Message newMessage = chatManager.postMessage(user, message);

            // If message error
            // NOTE: Could be improved by having a NoMessageError subclass of Message for better error handling
            String noMessageError = (newMessage == null) ? "true" : "false";

            // Update attributes and forward the request to the view
            request.setAttribute("noMessageError", noMessageError);
            request.setAttribute("chatManager", chatManager);
        }

        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }

    // -------------------- HELPER FUNCTIONS --------------------

    private boolean referrerIsValid(String expected, String received) {
        return expected.equals(received);
    }

}
