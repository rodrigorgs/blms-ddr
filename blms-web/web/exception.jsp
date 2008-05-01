<%-- 
    Document   : exception
    Created on : 01/05/2008, 12:54:31
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <% Throwable e = ((Throwable)request.getAttribute("exception")); %>
        <h2>Error: <%= e.getMessage() %></h2>
        <hr/>
        <h4>Debugging info</h4>
        <p>Type:<%= e.getClass() %></p>
        <p>Stack trace:</p>
        <p><ul><%        
            StackTraceElement[] ste = e.getStackTrace();
            for (StackTraceElement elem : ste) {
                out.println("<li>" + elem.toString() + "</li>");
            }                    
    %></ul></p>
    <p>
        
    </p>
    </body>
</html>
