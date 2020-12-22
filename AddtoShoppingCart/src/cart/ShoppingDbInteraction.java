package cart;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShoppingDbInteraction {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private PreparedStatement pst = null;
	
	public HashMap<Integer,Product> getAllProductDetails() throws SQLException, IOException, PropertyVetoException{
		
		HashMap<Integer,Product> productsHm = new HashMap<>();
		
		try{
		 
		connection =ShopDbConnection.getConnection();
		statement = connection.createStatement(); 
		
		resultSet = statement.executeQuery("select * from products");
		
			while (resultSet.next()){
				
				Product pb = new Product();
				pb.setId(resultSet.getInt("product_id"));
				pb.setName(resultSet.getString("name"));
				pb.setProductDescription(resultSet.getString("description"));
				pb.setProductPrice(resultSet.getDouble("price"));
				pb.setImageUrl(resultSet.getString("image_URL"));
				productsHm.put(pb.getId(), pb);
				
			}
		}//end of try
		
		finally{
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		return productsHm;

	}
	
	public int generateOrder(Order ob){
		
		int acknowledgment =0;
		
		try {
			//connection = ShoppingDBConnection.getInstance().getConnection();
			connection = ShopDbConnection.getConnection();
			String query="Insert into orders (date,total_order_price) values(?,?)";
			pst=connection.prepareStatement(query);
			
			pst.setString(1, ob.getDate());
			pst.setDouble(2, ob.getTotalPrice());
			
			acknowledgment=pst.executeUpdate();
			
		} catch (SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
            if (pst != null) try { pst.close(); } catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		 return acknowledgment;
	}
	
	public boolean getOrderId(Order ob){
		
		boolean check=false;
		try {
			// connection = ShoppingDBConnection.getInstance().getConnection();
			connection = ShopDbConnection.getConnection();
			String query="Select order_id from orders where date='"+ob.getDate()+"' AND total_order_price="+ob.getTotalPrice()+" ORDER BY order_id DESC LIMIT 1";
			pst=connection.prepareStatement(query);
			
			resultSet=pst.executeQuery(query); 
			
			 if(resultSet.first())
				 {
					check=true;
					int orderId=resultSet.getInt("order_id");
					System.out.println(orderId);
					ob.setOrderId(orderId);
				 }
			 
			//acknowledgment=pst.executeUpdate();
			
		} catch (SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {e.printStackTrace();}	
			if (pst != null) try { pst.close(); } catch (SQLException e) {e.printStackTrace();}
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		 return check;
		
	}
	
	public int insertPurchasedProducts(Order ob){
		
		int acknowledgment =0;
		
		try {
			//connection = ShoppingDBConnection.getInstance().getConnection();
			connection = ShopDbConnection.getConnection();
			PurchaseProduct ppb;
			
			String query="Insert into purchasedproduct (total_price,quantity,order_id,product_id) values(?,?,?,?)";
			pst=connection.prepareStatement(query);
			
			HashMap<Integer, PurchaseProduct> PurchaseProduct=ob.getProducts();
			
			int orderId=ob.getOrderId();
			int productId=0;
			double totalPrice=0.0;
			int quantity=0;
			
			for(Map.Entry<Integer, PurchaseProduct> product:PurchaseProduct.entrySet() ){
				
				ppb=product.getValue();
				productId=ppb.getProductId();
				totalPrice=ppb.getTotalPrice();
				quantity=ppb.getQuantity();
					
				pst.setDouble(1, totalPrice);
				pst.setInt(2, quantity);
				pst.setInt(3, orderId);
				pst.setInt(4, productId);
				acknowledgment=pst.executeUpdate();
							
			}
			
		} catch (SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if (pst != null) try { pst.close(); } catch (SQLException e) {e.printStackTrace();}
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		return acknowledgment;
	}
	
}
