<%@ page import="chat.Message" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>JSP Chat Web App</title>
    </head>
    <body>
        <table>
            <%
                LinkedList<Message> chat = (LinkedList<Message>) request.getAttribute("chat");

                if (request.getAttribute("chat") != null) {
                    for(Message message : chat) {
            %>
                <tr>
                    <td>
                        <%= message.getUser() + " - " + message.getMessage() + " - " + message.getTimestamp() %>
                    </td>
                </tr>
            <%
                    }
                }
            %>
        </table>

        <form action="chat" method="post">
            <div>
                <label for="user">User:</label>
                <input type="text" name="user" id="user">
            </div>
            <div>
                <label for="message">Message:</label>
                <input type="text" name="message" id="message">
            </div>
            <div>
                <input type="submit" value="Submit">
            </div>
        </form>

        <form action="chat" method="get">
            <div>
                <label for="startDate">Start date:</label>
                <input type="datetime-local" name= "start" id="startDate" >
            </div>
            <div>
                <label for="endDate">End date:</label>
                <input type="datetime-local" name = "end" id="endDate" >
            </div>
            <div>
                <input type="submit" value="Filter">
            </div>
      <% //testing %>
    <input type="radio" id="txt" name="typeFile" value="txt">
    <label for="txt">Txt</label><br>
    <input type="radio" id="xml" name="typeFile" value="xml">
    <label for="xml">Xml</label><br>
    <input type="submit" value="download" name="download">
        </form>

        <%
            String noMessageError = (String) request.getAttribute("noMessageError");
            String nonValidReferrerError = (String) request.getAttribute("nonValidReferrerError");
            if (noMessageError != null && noMessageError.equals("true")) {
        %>
            <p>Sorry, we could not get that :(</p>
            <p>Do not forget to include a message before submitting!</p>
        <% } else if (nonValidReferrerError != null && nonValidReferrerError.equals("true")) {
        %>
            <p>Sorry, we could not get that :(</p>
            <p>Your request seems to be coming from a page other than /chat</p>
        <% } %>
    </body>
</html>
