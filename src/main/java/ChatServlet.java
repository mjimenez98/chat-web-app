import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

import chat.Message;

@WebServlet(name = "ChatServlet")
public class ChatServlet extends HttpServlet {
    //private ChatManager chatManager;
    private GrpcClient client;

    public void init(ServletConfig config) {
        client = new GrpcClient();
        if (client != null) {
            System.out.println("Client created");
        } else {
            System.out.println("Could not create a client");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getHeader("referer") == null) {
            request.setAttribute("nonValidReferrerError", "true");
        } else {
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
            // Update attributes
            session.setAttribute("userId", user);
            session.setAttribute("apiResponse", apiResponse);
            session.setAttribute("noMessageError", noMessageError);
            request.setAttribute("nonValidReferrerError", "false");
            LinkedList<Message> chat = client.listMessages("", "");
            session.setAttribute("chat", chat);
        }
        response.sendRedirect("/chat_web_app_war/chat");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateStart = request.getParameter("from");
        String dateEnd = request.getParameter("to");
        dateStart = dateStart == null ? "" : dateStart;
        dateEnd = dateEnd == null ? "" : dateEnd;
        if (request.getHeader("referer") == null) {
            request.setAttribute("nonValidReferrerError", "true");
        } else {
            if (request.getParameter("delete") != null) {
                String apiResponse = client.clearChat(dateStart, dateEnd);
                request.setAttribute("apiResponse", apiResponse);
            }
            request.setAttribute("nonValidReferrerError", "false");
        }

        LinkedList<Message> chat = client.listMessages(dateStart, dateEnd);
        request.setAttribute("chat", chat);

        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }
}
