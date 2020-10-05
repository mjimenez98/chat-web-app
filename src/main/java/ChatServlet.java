import chat.ChatManager;
import chat.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet(name = "ChatServlet")
public class ChatServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDateTime timestamp = LocalDateTime.now();
        String message = request.getParameter("message");
        String user = request.getParameter("user");

        Message newMessage = new Message(timestamp, message, user);
        ChatManager chatManager = new ChatManager();
        chatManager.postMessage(newMessage);

        request.setAttribute("chatManager", chatManager);
        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("Chat.jsp");
        rd.forward(request, response);
    }
}
