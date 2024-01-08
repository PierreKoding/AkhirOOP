package functions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Validation {
	
	static Scanner sc = new Scanner(System.in);
	
	public static List<String> IsInBranch(Connection conn, String branchID, String MenuID) {
		String[] allowedMenu = new String[10];
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM menus WHERE branchID = '"+branchID+"'");
			int i = 0;
			while(rs.next()){
				allowedMenu[i] = rs.getString("MenuID");
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		List<String> MenuAllowed = Arrays.asList(allowedMenu);

		return MenuAllowed;
		
	}
	
	public static boolean MejaAvailibility(Connection conn, String tipeMeja, int MejaDipesan, String branchID) {
		
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("SELECT COUNT(*) AS totalMeja FROM mejas WHERE tipeMeja = '" + tipeMeja + "' AND isOccupied = 0 AND branchID = '" + branchID + "'");
			if (rs.next()) {
                int totalMeja = rs.getInt("totalMeja");
                System.out.println(totalMeja);
                // Cek apakah jumlah meja yang tersedia cukup
                if(totalMeja >= MejaDipesan) {
                	return true;
                }
                else {
                	return false;
                }
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return false;
	}
	
	public static void AssignMeja(Connection conn, String tipeMeja) {
		
	}
	
}
