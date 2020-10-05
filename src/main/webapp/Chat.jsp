<%@ page import="chat.ChatManager" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <title>JSP Chat Web App</title>
    </head>
    <body>
        <%
            ChatManager chatManager = (ChatManager) request.getAttribute("chatManager");
            String user = "NO USER";
            if (chatManager != null)
                user = chatManager.getChat().getFirst().getUser();
        %>
        <%= "Welcome, " + user %>
        <form action="chat" method="POST">
            User: <input type="text" name="user">
            <br />
            chat.Message: <input type="text" name="message" />
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
