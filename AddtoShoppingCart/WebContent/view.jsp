<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<title>MyCart</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Tech Zone </h1>
<h2>MyCart</h2>

<%@ page import="java.io.*,java.util.*,cart.PurchaseProduct,cart.Product"%>



<%
	HashMap<Integer,Product> productDetails;
	HashMap<Integer,PurchaseProduct> addedProducts;
	Product pb;
	PurchaseProduct ppb;
	String productName;
	int productQuantity;
	double unitPrice;
	double totalPrice;
	
	if(session.getAttribute("selected_products")!=null){
		
		
		
		addedProducts=(HashMap<Integer,PurchaseProduct>)session.getAttribute("selected_products");
		
		if(addedProducts.size()>0)
		{
		productDetails=(HashMap<Integer,Product>)application.getAttribute("products");
		out.print("<center><h3>You have selected the following products</h3></center><br/>");
		out.print("<table");
		out.print("<tr><th>Product Name</th><th>Quantity</th><th>Unit Price</th><th>Total Price</th><th>Remove?</th></tr>");
		for(Map.Entry<Integer,PurchaseProduct> m : addedProducts.entrySet()){
			out.print("<tr>");
			pb=productDetails.get(m.getKey());
			productName=pb.getName();
			ppb=m.getValue();
			productQuantity=ppb.getQuantity();
			
			unitPrice=pb.getProductPrice();
			totalPrice= productQuantity * unitPrice;
			ppb.setTotalPrice(totalPrice);
			
			out.print("<td>"+productName+"</td>");
			out.print("<td>"+productQuantity+"</td>");
			out.print("<td>"+unitPrice+"</td>");
			out.print("<td>"+totalPrice+"</td>");
			out.print("<td><form action='ShoppingContollerServlet' method=POST><input type='hidden' name='items' value='"
						+m.getKey()+"'><input type=submit value='Remove' name='submitButton'></form></td>");
			out.print("</tr>");
		}
		
		out.print("</table>");
		out.print("<br/><br/><form action='ShoppingContollerServlet' method='POST'><input type='submit'value='Checkout' class ='button' /></form>");
		}//end of inner if
		else out.print("Sorry no products found in cart");
	}
	else{
		out.print("Sorry no products found in cart");	
	}

%>
<br/><form action='index.jsp' method='POST'><input type='submit'value='Back to shopping'   /></form>
</body>
<jsp:include page="footer.jsp" />
</html>