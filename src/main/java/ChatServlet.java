import chat.ChatManager;
import chat.Message;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
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
        if (request.getHeader("referer") == null) {
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
            LinkedList<Message> chat = chatManager.ListMessages(null, null);
            request.setAttribute("chat", chat);
        }

        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateStart = request.getParameter("start");
        String dateEnd = request.getParameter("end");
        String download = request.getParameter("download");
        String XML = request.getParameter("xml");
        String txt = request.getParameter("txt");
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

        if (download !=null) {
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment;filename=\"chat-log.txt\"");
            try {
                PrintWriter writer = new PrintWriter(response.getOutputStream());
                for (Message message : chat) {
                    writer.println(message.getUser() + " - " + message.getMessage() + " - " + message.getTimestamp());
                }
                writer.flush();
    
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }
}
