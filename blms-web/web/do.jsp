<%@ page import="blms.facade.*" %>
<%@ page import="blms.*" %>
<%@ page import="blms.struts.*" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="java.util.*" %>
<%
try {
    Vector<String> special = new Vector<String>();
    special.add("method");
    special.add("title");
    special.add("fields");

    String methodName = request.getParameter("method");

    BlmsFacade facade = new BlmsFacade();

    Class klass = BlmsFacade.class;
    Method[] methods = klass.getMethods();
    for (Method method : methods) {
        if (method.getName().equals(methodName)) {
            LinkedList<String> params = new LinkedList<String>();
            Enumeration<String> names = request.getParameterNames();

            //int i = 0;        
            for (int i = 1; i < 1000; i++) {
                String s = (String)request.getParameter("p" + i);
                if (s != null)
                    params.add(s);
                else
                    break;
            }

            if (!params.isEmpty()) {
                try {
                    facade.useDatabase(BlmsConfig.DBNAME);
                    
                    method.invoke(facade, params.toArray());
                } catch (InvocationTargetException e) {
                    request.setAttribute("exception", e.getCause());
                    pageContext.forward("exception.jsp");
                } catch (Throwable e) {                    
                    request.setAttribute("exception", e);
                    pageContext.forward("exception.jsp");
                } finally {
                    facade.closeDatabase();
                }
                request.setAttribute("operation", "bli");
                //
                pageContext.forward("ok.jsp");
            }
        }
    }
} catch (Throwable e) {
    out.println("oops!");
    out.println(e.getMessage());
}
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
String method = request.getParameter("method");
String title = request.getParameter("title");
String fieldsStr = request.getParameter("fields");

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= title %></title>
    </head>
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
        <%
        if (fieldsStr != null) {
            String[] fields = fieldsStr.split(",");
        %>
        <h2><%= title %></h2>
        <form action="do.jsp" method="GET">
        <input type="hidden" name="method" value="<%= method %>"/>
        
        <%
        int i = 0;
        for (String field : fields) {
            i++;
            String nm = "p" + i;
        %>
        <%= field %> <input type="text" name="<%= nm %>" value="" /><br/>
        <%
            }%>
        <input type="submit"/>
        <%
        } // if fieldsStr != null
        
        %>
        
        
        
        </form>            
            
        </td></tr>
        </table>


        
        
    </body>
</html>
