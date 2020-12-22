<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Removed Product</title>
</head>
<body>
<%@ page import="java.io.*,java.util.*,cart.PurchaseProduct,cart.Product"%>
	<%
	HashMap<Integer,Product> productDetails;
	Product pb;
	String productName;
	int productId;
	
	if(request.getAttribute("removedProductId")!=null){
		out.print("You have Removed <br/>");
		
		String temp=(String)request.getAttribute("removedProductId");
		productId=Integer.parseInt(temp);
		
		productDetails=(HashMap<Integer,Product>)application.getAttribute("products");
		
		pb=productDetails.get(productId);
		
		productName=pb.getName();
		
		out.print("<b>"+productName+"</b><br/>");
		
	}
	else{
		
		out.print("Sorry requested product not found in cart");
	}
	

%>
	


<br/><form action='index.jsp' method='POST'><input type='submit'value='Back to shopping'  /></form>
</body>
</html>