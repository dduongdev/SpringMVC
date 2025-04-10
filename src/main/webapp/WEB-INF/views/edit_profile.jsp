<%@page import="com.abc.entities.ExtendedUser"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.abc.entities.Province" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>
</head>
<body>

	<%
		List<Province> provinces = (List<Province>) request.getAttribute("provinces");
		ExtendedUser extendedUser = (ExtendedUser) request.getAttribute("extendedUser");
	%>

	<div>
      <form method="post" action="${pageContext.request.contextPath}/profile/edit">
        <div>
      	<label for="username">Username:</label>
      	<input id="username" name="username" value="<%= extendedUser.getUsername() %>" required />
      </div>
      <div>
        <label for="email">Email:</label>
      	<input id="email" name="email" value="<%= extendedUser.getEmail() %>" required />
      </div>
      
      <select name="provinceId" required>
		    <option value="">Select province</option>
		    <% 
		            for (Province province : provinces) {
		                boolean isSelected = province.getId() == extendedUser.getProvinceId());
		    %>
		                <option value="<%=province.getId()%>" <%=isSelected ? "selected" : ""%>>
		                    <%=province.getName()%>
		                </option>
		    <%
		            }
		    %>
		</select>
      
      <div>
        <label>Chọn hình ảnh: </label>
        <input type="file" accept=".jpg,.jpeg,.png" name="avatar" />
      </div>
      </form>
	</div>
</body>
</html>