<%@ page import="blms.facade.*" %>
<%@ page import="blms.*" %>
<%@ page import="blms.struts.*" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="java.util.*" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='t' %>

<%
String what = request.getParameter("what");
String title = request.getParameter("title");
if (title == null)
    title = "";
%>
<t:insert template="template.jsp">
    <t:put name="title" value="<%= title %>"/>
    <t:put name="body" type="page" direct="true">
        <h2><%= title %></h2>
        <p>
        <ul>
        <%
        if (what != null) {
        Registry r = new Registry();
        try {
            r.useDatabase(BlmsConfig.DBNAME);
               
            if (what.equals("User")) {
                Collection<User> array = r.store.users;
                for (User x : array) {
                    out.print("<li>");
                    out.println( String.format("[ID = %s] %s %s (%s). Home phone: %s. Work Phone: %s. Cell Phone: %s",
                        r.getId(x),
                        x.getFirstName(),
                        x.getLastName(),
                        x.getEmail(),
                        x.getHomePhone(),
                        x.getWorkPhone(),
                        x.getCellPhone())
                    + "</li>");
                }
            }
            else if (what.equals("Match")) {
                Collection<Match> array = r.store.matches;
                for (Match x : array) {
                    out.print("<li>");
                    out.println( 
                        String.format("[ID = %s] League: %s. Date: %s. Winner: %s. Loser: " +
                        "%s. Race Length: %d. Score: %d. Longest run for winner:" +
"                        %d. Longest run for loser: %d",
                            r.getId(x),
                            x.getLeague().getName(),
                            x.getDate(),
                            r.getId(x.getWinner()),
                            r.getId(x.getLoser()),
                            x.getLength(),
                            x.getScore(),
                            x.getLongestRunForWinner(),
                            x.getLongestRunForLoser())
                    + "</li>");
                }
            }
            else if (what.equals("League")) {
                Collection<League> array = r.store.leagues;
                for (League x : array) {
                    out.print("<li>");
                    out.println( 
                        String.format("[ID = %s] Name: %s. Operator: %s",
                            r.getId(x),
                            x.getName(),
                            r.getId(x.getOperator()))
                    + "</li>");
                }
            }
            else if (what.equals("Join")) {
                Collection<Join> array = r.store.joins;
                for (Join x : array) {
                    out.print("<li>");
                    out.println( 
                        String.format("[ID = %s] User: %s. League: %s. Join date: %s. Player handicap: %s",
                            r.getId(x),
                            r.getId(x.getUser()),
                            r.getId(x.getLeague()),
                            x.getJoinDate(),
                            x.getCurrentHandicap())
                    + "</li>");
                }
            }
        } finally {
            r.closeDatabase();
        }
        }
        %>
        </ul>
    </p>
    </t:put>
</t:insert>    

