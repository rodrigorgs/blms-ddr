<%@ page import="blms.facade.*" %>
<%@ page import="blms.*" %>
<%@ page import="blms.struts.*" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="java.util.*" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='t' %>
<%
String method = request.getParameter("method");
String title = request.getParameter("title");
if (title == null)
    title = "";
String fieldsStr = request.getParameter("fields");

try {
    /*
    Vector<String> special = new Vector<String>();
    special.add("method");
    special.add("title");
    special.add("fields");
    */

    String methodName = request.getParameter("method");

    BlmsFacade facade = new BlmsFacade();

    Class klass = BlmsFacade.class;
    Method[] methods = klass.getMethods();
    for (Method m : methods) {
        if (m.getName().equals(methodName)) {
            LinkedList<String> params = new LinkedList<String>();
            Enumeration<String> names = request.getParameterNames();

            for (int i = 1; i < 1000; i++) {
                String s = (String)request.getParameter("p" + i);
                if (s != null)
                    params.add(s);
                else
                    break;
            }
            
            String result = "";

            if (!params.isEmpty()) {
                try {
                    facade.useDatabase(BlmsConfig.DBNAME);
                    if(methodName.equals("showHandicapHistory")) {
                        result = (String)(m.invoke(facade, params.toArray()));
                        session.setAttribute("result", result);
                    }
                    m.invoke(facade, params.toArray());
                } catch (InvocationTargetException e) {
                    request.setAttribute("exception", e.getCause());
                    pageContext.forward("exception.jsp");
                } catch (Throwable e) {                    
                    request.setAttribute("exception", e);
                    pageContext.forward("exception.jsp");
                } finally {
                    facade.closeDatabase();
                }
                if(methodName.equals("showHandicapHistory")) {
                    pageContext.forward("handicap.jsp");
                } else {
                    pageContext.forward("ok.jsp?operation=" + title);
                }
            }
        }
    }
} catch (Throwable e) {
    out.println("oops!");
    out.println(e.getMessage());
}
%>

<t:insert template="template.jsp">
    <t:put name="title"><%= title %></t:put>
    <t:put name="body">
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
    </t:put>
</t:insert>    

