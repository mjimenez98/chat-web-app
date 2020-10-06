<%@ page import="chat.ChatManager" %>
<%@ page import="chat.Message" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <title>JSP Chat Web App</title>
    </head>
    <body>
        <%
            ChatManager chatManager = (ChatManager) request.getAttribute("chatManager");
            if (chatManager != null) {
                for (Message message : chatManager.getChat()) {
        %>
            <table>
                <tr>
                    <td>
                        <%= message.getUser() + " - " + message.getMessage() + " - " +
                                message.getTimestamp()
                        %>
                    </td>
                </tr>
            </table>
        <%
                }
            }
        %>
        <form action="chat" method="POST">
            User: <input type="text" name="user">
            <br />
            Message: <input type="text" name="message" />
            <input type="submit" value="Submit" />
        </form>
        <%
            String error = (String) request.getAttribute("noMessageError");
            if (error != null && error.equals("true")) {
        %>
            <p>Sorry, we could not get that :(</p>
            <p>Do not forget to include a message before submitting!</p>
        <% } %>
    </body>
</html>
