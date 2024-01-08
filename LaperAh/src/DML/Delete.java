package DML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import functions.Validation;

public class Delete {
	
	static Scanner sc = new Scanner(System.in);
	
	public Delete() {
		// TODO Auto-generated constructor stub
	}
	
	public static void DeleteMenu(Connection conn, String branchID) {
		int valid = 0;
		List<String> MenuAllowed;
		String MenuID;
		do {
			View.ViewMenu(conn, branchID);
			System.out.print("Input MenuID to delete : ");
			MenuID = sc.nextLine();
			MenuAllowed = Validation.IsInBranch(conn, branchID, MenuID);
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
}
