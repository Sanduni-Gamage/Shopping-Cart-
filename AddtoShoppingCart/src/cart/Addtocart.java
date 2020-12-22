package cart;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Addtocart {

public static int execute(HttpServletRequest request,HttpServletResponse response){
		
		int acknowledgement=0;
		 HashMap<Integer,PurchaseProduct> addProduct;
		 
		 int selectedProductId=Integer.parseInt(request.getParameter("items"));//get selected product id
		 int quantity=0;
		 try{
			  quantity=Integer.parseInt(request.getParameter("quantity"));//get quantity 
		 }
		 catch(Exception e){}
		 finally{
			  acknowledgement=0;
		 }
		 if(quantity>0)
		 {
			 if(request.getSession().getAttribute("selected_products")!=null)//check product already exist
			 {
				 addProduct=(HashMap<Integer, PurchaseProduct>) request.getSession().getAttribute("selected_products");
			 }
			 else
			 {
				 addProduct=new HashMap<>();
			 }
			
			
			if(addProduct.size()>0 && addProduct.containsKey(selectedProductId) ){
				PurchaseProduct ppb=addProduct.get(selectedProductId);
				ppb.setQuantity(quantity);
				addProduct.put(selectedProductId,ppb);
			}
			else{
				
				PurchaseProduct ppb=new PurchaseProduct();
				ppb.setProductId(selectedProductId);
				ppb.setQuantity(quantity);
				addProduct.put(selectedProductId,ppb);
			}
			
			
			request.getSession().setAttribute("selected_products", addProduct);
			//return addProduct;
			acknowledgement++;
		 }
		 
		 return acknowledgement;
	}
	
	
	
}
