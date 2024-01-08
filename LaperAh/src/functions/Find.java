package functions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Find {
	public static String findMejaID(Connection conn, String transactionID) {
	    try {
	        Statement state = conn.createStatement();
	        ResultSet rs = state.executeQuery("SELECT mejaID FROM transactionDetail WHERE transactionID = " + transactionID);

	        if (rs.next()) {
	            return rs.getString("mejaID");
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Handle the exception appropriately in a real-world scenario
	    }

	    return "-"; // Return a default value if the transaction ID is not found or an error occurs
	}

}
