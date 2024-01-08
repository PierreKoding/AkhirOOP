package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckOut {
	public CheckOut(Connection conn, String TransactionID) {
		// TODO Auto-generated constructor stub
		try {
            printOrderDetails(conn, TransactionID);

            updateMejasIsOccupied(conn, TransactionID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private static void printOrderDetails(Connection conn, String transactionID) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM transactionDetail WHERE TransactionID = ?")) {
            ps.setString(1, transactionID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String mejaID = rs.getString("MejaID");
                    System.out.println("MejaID: " + mejaID);
                }
            }
        }
    }

    private static void updateMejasIsOccupied(Connection conn, String transactionID) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE mejas SET isOccupied = false WHERE MejaID IN (SELECT MejaID FROM transactionDetail WHERE TransactionID = ?)")) {
            ps.setString(1, transactionID);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected <= 0) {
                System.out.println("Alter mejas failed");
            }
        }
    }
	
}
