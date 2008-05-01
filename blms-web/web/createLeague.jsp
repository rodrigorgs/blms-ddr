<%-- 
    Document   : createLeague
    Created on : 01/05/2008, 13:11:17
--%>
<%@ page import="blms.facade.*" %>
<%@ page import="blms.*" %>
<%@ page import="blms.struts.*" %>

<%
if (request.getParameter("name") != null) {
    //out.println(request.getParameter("name"));
    //out.println("<br/>");
    //out.println(request.getParameter("operator"));
    
    
    BlmsFacade facade = new BlmsFacade();
    facade.useDatabase(BlmsConfig.DBNAME);
    try {
        String name = request.getParameter("name");
        String operator = request.getParameter("operator");
        out.println(name);
        out.println(operator);
        facade.createLeague(name, operator);
    }
    catch (Throwable e) {
        //out.println(e.getMessage());
        request.setAttribute("exception", e);
        pageContext.forward("exception.jsp");
    }
    finally {
        facade.closeDatabase();
    }
    request.setAttribute("operation", "create league");
    pageContext.forward("ok.jsp");
}

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create League</title>
    </head>
    <body>
        <h2>Create League</h2>
        <form action="createLeague.jsp" method="POST">
        Name: <input type="text" name="name" value="" /><br/>
        League Operator ID: <input type="text" name="operator" value="" />
        <input type="submit" value="Criar" />
        </form>
    </body>
</html>
