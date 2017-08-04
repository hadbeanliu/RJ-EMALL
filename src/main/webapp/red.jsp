<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
// 用于多系统登陆支持
String url = request.getParameter("redUrl");
if (url != null && !url.trim().isEmpty()) {
	response.sendRedirect(url);
	return;
}

%><%
String cb = request.getParameter("cb");
if (cb != null && !cb.trim().isEmpty()) { %>
<html>
<body>
<script>
var cb = '<%=cb%>';
if (parent && parent[cb] && typeof parent[cb] === 'function') {
	parent[cb]();
}
</script>
</body>
</html>
<% } %>