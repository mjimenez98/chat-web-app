import chat.ChatManager;
import chat.Message;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
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
        if (request.getHeader("referer").length() < 1) {
            String nonValidReferrerError = "true";
            request.setAttribute("nonValidReferrerError", nonValidReferrerError);
        } else {
            // Get session
            HttpSession session = request.getSession(false);

            // Initialize Message properties
            String message = request.getParameter("message");
            String user = request.getParameter("user");

            // Create new Message and save it
            Message newMessage = chatManager.postMessage(user, message);

            // If message parameter missing
            // NOTE: Could be improved by having a NoMessageError subclass of Message for better error handling
            String noMessageError = (newMessage == null) ? "true" : "false";

            // Update attributes
            session.setAttribute("userId", user);
            session.setAttribute("noMessageError", noMessageError);
            LinkedList<Message> chat = chatManager.ListMessages(null, null);
            session.setAttribute("chat", chat);
        }

        response.sendRedirect("/chat_web_app_war/chat");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateStart = request.getParameter("start");
        String dateEnd = request.getParameter("end");
        LocalDateTime start = null;
        LocalDateTime end = null;

        // Parse String into LocalDateTime
        if (dateStart != null && dateStart.length() > 0) {
            start = LocalDateTime.parse(dateStart);
        }
        if (dateStart != null && dateEnd.length() > 0) {
            end = LocalDateTime.parse(dateEnd);
        }

        LinkedList<Message> chat = chatManager.ListMessages(start, end);
        request.setAttribute("chat", chat);

        if (request.getParameterMap().containsKey("delete")) {
            String dateStart = request.getParameter("start");
            String dateEnd = request.getParameter("end");
            LocalDateTime start = dateStart.isEmpty() ? null : LocalDateTime.parse(dateStart);
            LocalDateTime end = dateEnd.isEmpty() ? null : LocalDateTime.parse(dateEnd);
            if (request.getParameterMap().containsKey("delete")) {
                chatManager.clearChat(start, end);
                request.setAttribute("chatManager", chatManager);
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }
}
