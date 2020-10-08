<%@ page import="chat.ChatManager" %>
<%@ page import="chat.Message" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta charset="utf-8" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/bootstrap.min.css">

        <title>JSP Chat Web App</title>
    </head>
    <body>
        <div class="container">
            <%-- Title --%>
            <div class="row my-2">
                <div class="col-md-12">
                    <h2 class="text-center">JSP Chat Web App</h2>
                </div>
            </div>

            <%-- Chat window --%>
            <div class="row justify-content-center my-2">
                <div class="col-md-6 border border-success bg-light overflow-auto" style="height: 10em;">
                    <table>
                        <%
                            ChatManager chatManager = (ChatManager) request.getAttribute("chatManager");
                            if (chatManager != null) {
                                for (Message message : chatManager.getChat()) {
                        %>
                        <tr>
                            <td>
                                <%= message.getUser() + " - " + message.getMessage() + " - " +
                                        message.getTimestamp()
                                %>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </table>
                </div>
            </div>

            <%-- Form --%>
            <div class="row justify-content-center my-2">
                <div class="col-md-6">
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
                </div>
            </div>
        </div>
        <div>
            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
                    integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
                    crossorigin="anonymous" type="jquery">
            </script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
                    integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
                    crossorigin="anonymous"
                    type="popper.js">
            </script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
                    integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
                    crossorigin="anonymous"
                    type="bootstrap">
            </script>
        </div>
    </body>
</html>
