<%-- 
    Document   : ok
    Author     : Diego / Dalton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Handicap history</title>
    </head>
    <body>
        <h1>Handicap history</h1>
        <p>
            <%
                String result = (String)(session.getAttribute("result"));
                result = result.replaceAll(";", " <br/>\n");
                out.println(result);
            %>
        </p>
        <a href="javascript:history.go(-1);">back</a>
    
    </body>
</html>