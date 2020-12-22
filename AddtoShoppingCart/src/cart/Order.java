package cart;

import java.io.Serializable;
import java.util.*;

public class Order implements Serializable {
	private int orderId;
	private String date;
	private double totalPrice;
	private HashMap<Integer, PurchaseProduct> products;
	
	public void setOrderId(int orderId){
		this.orderId=orderId;
	}
	
	public int getOrderId(){
		return orderId;
	}
	
	public void setDate(String date){
		
		this.date=date;
		
	}
	
	public String getDate(){
		
		return date;
	}
	
	public void setTotalPrice(double totalPrice){
		this.totalPrice=totalPrice;
	}
	
	public double getTotalPrice(){
		
		return totalPrice;
	}

	public void setProducts(HashMap<Integer, PurchaseProduct> products){
		this.products=products;
	}
	
	public HashMap<Integer, PurchaseProduct> getProducts(){
		return products;
	}
}
