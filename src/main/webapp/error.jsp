<%@ page import="java.io.PrintWriter" %><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<h2>出错啦！！！</h2>
<% Object ex = request.getAttribute("ex"); %>
<% if (ex != null) { %>
<p><%=request.getAttribute("errorMsg") %></p>
<% } else { %>
<% ex = request.getAttribute("javax.servlet.error.exception"); %>
<% } %>
<pre><%
if (ex instanceof Exception) {
	Exception e = (Exception) ex;
	e.printStackTrace(new PrintWriter(out));
}
%></pre>
</body>
</html>
