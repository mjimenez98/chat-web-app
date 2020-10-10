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
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/custom.css">

        <script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js" type="jquery"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="bootstrap"></script>

        <title>JSP Chat Web App</title>
    </head>
    <body>
        <div class="container mw-100">
            <%-- Title --%>
            <div class="row py-5 bg-info">
                <div class="col-12">
                    <h1 class="text-center text-white">JSP Chat Web App</h1>
                </div>
            </div>

            <%-- Chat window --%>
            <div class="row justify-content-center my-2">
                <div class="col-8 chat-window overflow-auto">
                    <table class="table table-striped">
                        <tbody>
                            <%
                                ChatManager chatManager = (ChatManager) request.getAttribute("chatManager");
                                if (chatManager != null) {
                                    for (Message message : chatManager.getChat()) {
                            %>
                            <tr>
                                <td>
                                    <div class="container">
                                        <div class="row justify-content-between">
                                            <div class="col-3">
                                                <p class="font-weight-light"><%= message.getUser() %></p>
                                            </div>
                                            <div class="col-5">
                                                <p class="font-italic text-right"><%= message.getTimestamp() %>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-12">
                                                <p class="font-weight-normal text-wrap"><%= message.getMessage() %></p>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>

            <%-- Form --%>
            <div class="row justify-content-center my-2">
                <div class="col-8">
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
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
                    integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
                    crossorigin="anonymous"
                    type="popper.js">
            </script>
        </div>
    </body>
</html>
