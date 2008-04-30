<%@ page import="blms.facade.*" %>
<%@ page import="blms.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User list</title>
    </head>
    <body>
        <ul>
        <%         
        Registry r = new Registry();
        r.useDatabase("pageDB2");
        User[] users = r.findUserByLastName(".*");
        for (User u : users) {
        %>
        <li><%=u.getFirstName() + " " + u.getLastName() + ", " + u.getEmail() %></li>
        <%
        }
        r.closeDatabase();
        %>
        </ul>
        
    </body>
</html>
