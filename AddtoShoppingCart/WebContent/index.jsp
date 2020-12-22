<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*,cart.Product" import="cart.ShoppingContollerServlet" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Store</title>



</head>
<body>
<jsp:include page="header.jsp"/>
<%

if(getServletContext().getAttribute("products")==null){
	RequestDispatcher rd;
	rd = request.getRequestDispatcher("/ShoppingContollerServlet");
	rd.forward(request,response);
	out.print("No products in Database");
}
else
{
%>

<h2> Tech Zone</h2>
		<br>
		<h3>Available products</h3>
		<br>
		<center>
		<form action="ShoppingContollerServlet" method=POST>
		<input align="right" type=submit value='My Cart' name=submitButton >
		</form>
		</center>
		<% 
			HashMap<Integer,Product> productsHm=(HashMap<Integer,Product>)getServletContext().getAttribute("products");
			out.print("<table>");
			for(Map.Entry<Integer,Product> m:productsHm.entrySet()){
				int key = m.getKey();
				Product pb = m.getValue();
				%>
				<form method=POST action="Productinfo.jsp">
				<%
				out.print("<tr><td><br><img src='"+pb.getImageUrl()+
							"'style='width:auto;max-height:128px;padding:5px;align=center;'></td><td><br><hr width='1' size='128'></td><td>"
							+pb.getName()+"<input type='hidden' name='productId' value='"
							+pb.getId()+"'><input type='hidden' name='productName' value='"
							+pb.getName()+"'><br><br><input type='submit' value='View' name='view' class ='button' </td></tr>");
				%>
				</form>
				<%
			}
			out.print("</table>");
	}%>

</body>
 <jsp:include page="footer.jsp" />
</html>