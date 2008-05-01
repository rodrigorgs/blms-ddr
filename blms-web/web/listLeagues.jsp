<%-- 
    Document   : listLeagues
    Created on : 01/05/2008, 13:06:11
--%>

<%@ page import="blms.facade.*" %>
<%@ page import="blms.*" %>
<%@ page import="blms.struts.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Leagues</title>
    </head>
    <body>
        <h2>Leagues</h2>
                <ul>
        <%         
        Registry r = new Registry();
        r.useDatabase(BlmsConfig.DBNAME);
        League[] leagues = r.findLeague(".*");
        for (League l : leagues) {
        %>
        <li><%= String.format("%s (operated by %s %s) ID = %s", 
                l.getName(),
                l.getOperator().getFirstName(),
                l.getOperator().getLastName(),
                r.getId(l)
                )
        %></li>
        <%
        }
        r.closeDatabase();
        %>
        </ul>
    </body>
</html>
