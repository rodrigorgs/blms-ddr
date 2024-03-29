<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<html:html locale="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="welcome.title"/></title>
        <html:base/>
    </head>
    <body style="background-color: white">
        <h1>User creation</h1><br/><br/>
        <html:form action="createUserAction">
            First name: <html:text property="firstName"/> <br/><br/>
            Last name: <html:text property="lastName"/> <br/><br/>
            Home phone number: <html:text property="homePhone"/> <br/><br/>
            Work phone number: <html:text property="workPhone"/> <br/><br/>
            Cell phone number: <html:text property="cellPhone"/> <br/><br/>
            Email: <html:text property="email"/> <br/><br/>
            
            <html:submit>OK</html:submit>
        </html:form>
    </body>
</html:html>
