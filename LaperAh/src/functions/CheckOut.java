package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckOut {
	public CheckOut(Connection conn, String TransactionID) {
		// TODO Auto-generated constructor stub
		try {
            printOrderDetails(conn, TransactionID);
            updateTransactions(conn, TransactionID);
            updateMejasIsOccupied(conn, TransactionID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private static void printOrderDetails(Connection conn, String transactionID) throws SQLException {
	    try (PreparedStatement ps = conn.prepareStatement(
	            "SELECT td.MejaID, SUM(m.Harga * td.Quantity) AS TotalPrice " +
	            "FROM transactionDetail td " +
	            "JOIN menus m ON td.MenuID = m.MenuID " +
	            "WHERE td.TransactionID = ? " +
	            "GROUP BY td.MejaID")) {
	        ps.setString(1, transactionID);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                String mejaID = rs.getString("MejaID");
	                double totalPrice = rs.getDouble("TotalPrice");
	                System.out.println("MejaID: " + mejaID + ", Total Price: " + totalPrice);
	            }
	        }
	    }
	}


	private static void updateTransactions(Connection conn, String TransactionID) {
		try {
			Statement state = conn.createStatement();
			String updateQuery = "UPDATE transactionHeader SET Status = Completed WHERE TransactionID = " + TransactionID;
			state.executeUpdate(updateQuery);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
    private static void updateMejasIsOccupied(Connection conn, String transactionID) {
    	String mejaID = Find.findMejaID(conn, transactionID);
		try {
			Statement state = conn.createStatement();
			String updateQuery = "UPDATE mejas SET isOccupied = 0 WHERE mejaID = " + mejaID;
			state.executeUpdate(updateQuery);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	
}
