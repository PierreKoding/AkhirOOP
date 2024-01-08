package DML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import functions.Find;
import functions.Validation;

public class Delete {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void DeleteMenu(Connection conn, String branchID) {
		int valid = 0;
		List<String> MenuAllowed;
		String MenuID;
		do {
			View.ViewMenu(conn, branchID);
			System.out.print("Input MenuID to delete : ");
			MenuID = sc.nextLine();
			MenuAllowed = Validation.IsInBranch(conn, branchID);
			if(MenuAllowed.contains(MenuID)) {
				valid=1;
			}
			else {
				System.out.println("Pick a Menu Available to Your Branch!");
			}
		}while(valid == 0);		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM menus WHERE MenuID = ?");
			ps.setString(1, MenuID);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
		        System.out.println("Successfully deleted!");
		    } else {
		        System.out.println("No records deleted. MenuID may not exist.");
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to delete!");
		}
		
	}
	
	public static void CancelReservation(Connection conn, String staffID, String branchID, String transactionID) {
		try {
			Statement state = conn.createStatement();
			String updateQuery = "UPDATE transactionHeader SET status = 'canceled' WHERE transactionID = " + transactionID;
			state.executeUpdate(updateQuery);			
		} catch (Exception e) {
			// TODO: handle exception
		}
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
