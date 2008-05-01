<%-- 
    Document   : ok
    Author     : Diego / Dalton /Rodrigo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successful operation</title>
    </head>
    <body>
        <h1>Successful operation</h1>
        <p>
        Operation <%= request.getParameter("operation") %> was 
        performed successfully
        </p>
        <a href="javascript:history.go(-1);">back</a>
    
    </body>
</html>
