package cart;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cart.Addtocart;
import cart.Product;
import cart.PurchaseProduct;
import cart.RemoveFromCart;
import cart.ShoppingDbInteraction;

/**
 * Servlet implementation class ShoppingContollerServlet
 */
@WebServlet("/ShoppingContollerServlet")
public class ShoppingContollerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	HashMap<Integer,Product> productsHm = new HashMap<>();
	
    public ShoppingContollerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void init(){
    	try {
			productsHm = new ShoppingDbInteraction().getAllProductDetails();
			
			getServletContext().setAttribute("products", productsHm);
		} catch (SQLException | IOException |PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd;
		rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			RequestDispatcher rd=null;
		
		String checkAction=request.getParameter("submitButton");
		
		if(checkAction!=null)
		{
			if(checkAction.startsWith("Add")){
				
				 int count=Addtocart.execute(request,response);//To add product in session
				 request.setAttribute("quantity", count);
				 rd=request.getRequestDispatcher("view.jsp");
				 rd.forward(request, response);
				
			}
			else if(checkAction.startsWith("Remove") ){
				
				 String removedProductId=RemoveFromCart.execute(request, response);
				 request.setAttribute("removedProductId", removedProductId);
				 rd=request.getRequestDispatcher("view.jsp");
				 rd.forward(request, response);
			}
			else if(checkAction.startsWith("My Cart")){
				 
				  rd=request.getRequestDispatcher("view.jsp");
				  rd.forward(request, response);
			
			}
		
		}//end of if 
		else{
			
			  int checkCheckout=cart.ConfirmOrder.execute(request, response);
			  request.setAttribute("checkoutStatus", checkCheckout);
			  rd=request.getRequestDispatcher("checkout.jsp");
			  rd.forward(request, response);
			
		}
	}

}
