<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='t' %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><t:getAsString name="title" ignore="true"/></title>
    </head>
    <h1>Billiards League Management System (BLMS)</h1>
    <body>
        <table border="1" cols="2" rows="1" width="100%" cellpadding="3">
        <tr><td valign="top" width="250"><ul>
        <li><a href="view.jsp?title=BLMS Home">Home</a></li>
        <li>Users</li>
        <ul>
        <li><a href="do.jsp?method=createUser&title=Create User&fields=First name,Last name,Home Phone,Work Phone,Cell Phone,Email,Picture">Create User</a></li>
        <li><a href="do.jsp?method=deleteUser&title=Delete User&fields=User ID">Delete User</a></li>
        <li><a href="do.jsp?method=updateUser&title=Update User&fields=User ID,First name,Last name,Home Phone,Work Phone,Cell Phone,Email,Picture">Update User</a></li>
        <li><a href="view.jsp?what=User&title=View Users">View Users</a></li>
        </ul>

        <li>Leagues</li>
        <ul>        
        <li><a href="do.jsp?method=createLeague&title=Create League&fields=League name,User operator ID">Create League</a></li>
        <li><a href="do.jsp?method=deleteLeague&title=Delete League&fields=League ID">Delete League</a></li>
        <li><a href="do.jsp?method=updateLeague&title=Update League&fields=League ID,Attribute Name='operator',New value">Update League</a></li>
        <li><a href="view.jsp?what=League&title=View Leagues">View Leagues</a></li>
        </ul>

        <li>User/League</li>
        <ul>        
        <li><a href="do.jsp?method=joinLeague&title=User Join League&fields=User ID,League ID,Initial Handicap">User Join League</a></li>
        <li><a href="do.jsp?method=joinLeague&title=User Leave League&fields=User ID,League ID,Initial Handicap">User Leave League</a></li>
        </ul>
        
        <li>Matches</li>
        <ul>
        <li><a href="do.jsp?method=addMatchResultToday&title=Add Match Result&fields=League ID,Winner user ID,Loser user ID">Add Match Result</a></li> 
        <li><a href="do.jsp?method=deleteMatch&title=Delete Match Result&fields=Match ID">Delete Match Result</a></li> 
        <li><a href="do.jsp?method=updateMatchResult&title=Update Match Result&fields=Match ID,Date,Winner user ID,Loser user ID,Length,Score,Longest run for winner,Longest run for loser">Update Match Result</a></li> 
        <li><a href="view.jsp?what=Match&title=View Matches">View Matches</a></li>
        </ul>
        
        <li>Handicaps</li>
        <ul>
            <li><a href="do.jsp?method=defineHandicapExpression&title=Update Handicap Expression (use variables 'win' and 'loss')&fields=League ID,Handicap expression">Update handicap expression</a></li>
            <li><a href="do.jsp?method=changeHandicap&title=Update User Handicap&fields=User ID, League ID,New handicap">Update user handicap</a></li>
            <li><a href="do.jsp?method=showHandicapHistory&title=Show Handicap History&fields=User ID, League ID">Show Handicap History</a></li>
        </ul>
        </ul>
        
        
            
        </td><td align="left" valign="top" width="*">            
            <t:getAsString name="body" ignore="true"/>
        </td></tr>
        </table>
    </body>
</html>
