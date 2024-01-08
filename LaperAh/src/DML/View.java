package DML;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class View {
	public View() {
		// TODO Auto-generated constructor stub
	}
	
	public static void ViewMenu(Connection conn, String branchID) {
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM menus WHERE branchID = '"+branchID+"'");
			
			System.out.println("=================================================================================================");
			System.out.println("| MenuID | MenuName                  | Tipe    | Story                             | Harga      |");
			System.out.println("=========+===========================+=========+===================================+=============");
			
			while(rs.next()){
				System.out.printf("| %-6s | %-25s | %-7s | %-33s | %-10s |\n", rs.getString("MenuID"), 
						rs.getString("MenuName"), rs.getString("Tipe"), rs.getString("Story"), rs.getString("Harga"));
			}
			System.out.println("=================================================================================================");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void viewReservations(Connection conn, String branchID) {
		
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM transactionHeader th JOIN staffs s ON th.staffID = s.staffID WHERE branchID = '"+branchID+"'");

			System.out.println("==============================================================");
			System.out.println("| TransactionID | StaffID     | CustomerName    | Status     |");
			System.out.println("================+=============+=================+=============");
			
			while(rs.next()){
				System.out.printf("| %-13s | %-11s | %-15s | %-10s |\n", rs.getString("TransactionID"), rs.getString("StaffID"), rs.getString("CustomerName"), rs.getString("Status"));
			}
			
			System.out.println("==============================================================");
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
