package cart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ConfirmOrder {

private static HashMap<Integer,PurchaseProduct> addedProducts;
	
	public static int execute(HttpServletRequest request,HttpServletResponse response){
		
		addedProducts=(HashMap<Integer, PurchaseProduct>) request.getSession().getAttribute("selected_products");
		
		
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    
		 sdf.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
		 Calendar cal= Calendar.getInstance();
		    	
		 String time=sdf.format(cal.getTime());//get current datetime
			 	
		 Order ob=new Order();
		 ob.setDate(time);
		 
		
		 double price=0.0;
		 double finalPrice=0.0;
		 PurchaseProduct ppb=null;
		 for(Map.Entry<Integer, PurchaseProduct> m:addedProducts.entrySet()){
			 
			 ppb=m.getValue();
			 price=ppb.getTotalPrice();
			 finalPrice+=price;
			 price=0.0;
		 }
		 
		 ob.setTotalPrice(finalPrice);
		 
		 ob.setProducts(addedProducts);
		 
		 
		 ShoppingDbInteraction sbdbi=new ShoppingDbInteraction();
			
		int orderCheck=sbdbi.generateOrder(ob);
		int purchasedProductCheck=0;
		 if(orderCheck==1){
			 if(sbdbi.getOrderId(ob))
			 {
			 purchasedProductCheck= sbdbi.insertPurchasedProducts(ob);
			 
			 }
		 }
		 
		 return purchasedProductCheck;
			
	}
	
	
}
