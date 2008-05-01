<%-- 
    Document   : template
    Created on : 01/05/2008, 14:22:34
--%>
<%@ page import="blms.facade.*" %>
<%@ page import="blms.*" %>
<%@ page import="blms.struts.*" %>

<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='t' %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><t:get name="title"/></title>
    </head>
    <h1>Billiards League Management System (BLMS)</h1>
    <body>
        <table border="0" cols="2" rows="1">
        <tr><td><ul>
        <li><a href="do.jsp?method=createUser&title=Create User&fields=First name,Last name,Home Phone,Work Phone,Cell Phone,Email,Picture">Create User</a></li>
        <li><a href="do.jsp?method=deleteUser&title=Delete User&fields=User ID">Delete User</a></li>
        <!-- TODO: update user info -->
        <!-- TODO: list users -->
        
        <li><a href="do.jsp?method=createLeague&title=Create League&fields=League name,User operator ID">Create League</a></li>
        <li><a href="do.jsp?method=deleteLeague&title=Delete User&fields=League ID">Delete League</a></li>
        <!-- TODO: update league -->
        <!-- TODO: list leagues -->
        
        <li><a href="do.jsp?method=joinLeague&title=User Join League&fields=User ID,League ID,Initial Handicap">User Join League</a></li>
        <li><a href="do.jsp?method=joinLeague&title=User Leave League&fields=User ID,League ID,Initial Handicap">User Leave League</a></li>
        
        <li><a href="do.jsp?method=addMatchResult&title=Add Match Result&fields=League ID,Date,Winner user ID,Loser user ID,Length,Score,Longest run for winner,Longest run for loser">Add Match Result</a></li> 
        <li><a href="do.jsp?method=deleteMatch&title=Delete Match Result&fields=Match ID">Delete Match Result</a></li> 
        <li><a href="do.jsp?method=updateMatchResult&title=Update Match Result&fields=Match ID,Date,Winner user ID,Loser user ID,Length,Score,Longest run for winner,Longest run for loser">Add Match Result</a></li> 
        <!-- TODO: list matches -->

        </ul>
            
        <li><a href="do.jsp?method=findUserByLastName&title=Find Users&fields=Last name (regular expressions allowed)">Find Users</a></li>
                
        </td><td>
            <t:get name="body"/>
        </td></tr>
        </table>
    </body>
</html>
