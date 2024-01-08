package DML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import functions.Find;
import functions.Validation;

public class Alter {
	
	static Scanner sc = new Scanner(System.in);
	
	public Alter() {
		// TODO Auto-generated constructor stub
	}
	
	public static void UpdateMenu(Connection conn, String branchID) {
		
		int valid = 0;
		List<String> MenuAllowed;
		String MenuID;
		do {
			View.ViewMenu(conn, branchID);
			System.out.print("Input MenuID to update : ");
			MenuID = sc.nextLine();
			MenuAllowed = Validation.IsInBranch(conn, branchID);
			if(MenuAllowed.contains(MenuID)) {
				valid=1;
			}
			else {
				System.out.println("Pick a Menu Available to Your Branch!");
			}
		}while(valid == 0);
		
		String tipe = null;
		try {
			Statement state1 = conn.createStatement();
			ResultSet rs1 = state1.executeQuery("SELECT * FROM menus WHERE MenuID = '"+MenuID+"'");
			while(rs1.next()) {
				tipe = rs1.getString("Tipe");
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(tipe.equals("Special")) {
			UpdateSpecialMenu(conn, MenuID);
		}
		else {
			UpdateNormalMenu(conn, MenuID);
		}
		

	}
	
	public static void UpdateSpecialMenu(Connection conn, String MenuID) {
		int updateOpt= 0;
		try {
			do {
				System.out.print("1. Menu Name\n2. Price\n3. Story\n 4. Exit\n>> ");
				updateOpt = sc.nextInt();
				sc.nextLine();
				if(updateOpt == 1) {
					String newName;
					System.out.print("New Name : ");
					newName = sc.nextLine();
					PreparedStatement ps = conn.prepareStatement("UPDATE menus SET MenuName = ? WHERE MenuID = ?");
					ps.setString(1, newName);
					ps.setString(2, MenuID);
					int rowsAffected = ps.executeUpdate();
					if(rowsAffected != 0) {
						System.out.println("Name Succesfully Updated!");						
					}
					else {
						System.out.println("Failed to update");
					}
					break;
				}
				
				else if(updateOpt == 2) {
					int newPrice;
					System.out.print("New Price : ");
					newPrice = sc.nextInt();
					PreparedStatement ps = conn.prepareStatement("UPDATE menus SET MenuName = ? WHERE MenuID = ?");
					ps.setInt(1, newPrice);
					ps.setString(2, MenuID);
					int rowsAffected = ps.executeUpdate();
					if(rowsAffected != 0) {
						System.out.println("Price Succesfully Updated!");						
					}
					else {
						System.out.println("Failed to update");
					}
					break;
				}
				
				else if(updateOpt == 3) {
					String newStory;
					System.out.print("New Story : ");
					newStory = sc.nextLine();
					PreparedStatement ps = conn.prepareStatement("UPDATE menus SET MenuName = ? WHERE MenuID = ?");
					ps.setString(1, newStory);
					ps.setString(2, MenuID);
					int rowsAffected = ps.executeUpdate();
					if(rowsAffected != 0) {
						System.out.println("Story Succesfully Updated!");						
					}
					else {
						System.out.println("Failed to update");
					}
					break;
				}
			} while (updateOpt != 4);
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error");
		}
	}
	
	public static void UpdateNormalMenu(Connection conn, String MenuID) {
		int updateOpt= 0;
		try {
			do {
				System.out.print("1. Menu Name\n2. Price\n3. Exit\n>> ");
				updateOpt = sc.nextInt();
				sc.nextLine();
				if(updateOpt == 1) {
					String newName;
					System.out.print("New Name : ");
					newName = sc.nextLine();
					PreparedStatement ps = conn.prepareStatement("UPDATE menus SET MenuName = ? WHERE MenuID = ?");
					ps.setString(1, newName);
					ps.setString(2, MenuID);
					System.out.println(MenuID);
					int rowsAffected = ps.executeUpdate();
					if(rowsAffected != 0) {
						System.out.println("Name Succesfully Updated!");						
					}
					else {
						System.out.println("Failed to update");
					}
					break;
				}
				
				else if(updateOpt == 2) {
					int newPrice;
					System.out.print("New Price : ");
					newPrice = sc.nextInt();
					PreparedStatement ps = conn.prepareStatement("UPDATE menus SET Harga = ? WHERE MenuID = ?");
					ps.setInt(1, newPrice);
					ps.setString(2, MenuID);
					System.out.println(MenuID);
					int rowsAffected = ps.executeUpdate();
					if(rowsAffected != 0) {
						System.out.println("Price Succesfully Updated!");						
					}
					else {
						System.out.println("Failed to update");
					}
					break;
				}
			} while (updateOpt != 3);
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error");
		}
	}
	
	public static void PesanMenu(Connection conn, String staffID, String branchID, String transactionID, String mejaID) {
		List<String> AllowedMenu = Validation.IsInBranch(conn, branchID);
		String pesanMenuID = null;
		int menu;
		List<String> MenuAkanDipesan = null;
		do {			
			View.ViewMenu(conn, branchID);
			System.out.print("1. Add Menu\n2. Remove Menu\n3. Done");
			menu = sc.nextInt();
			if(menu==1) {
				MenuAkanDipesan.add(pesanMenuID);
			}
			else if(menu == 2) {
				String tempRemove;
				System.out.println(MenuAkanDipesan);
				System.out.println("Input ID menu yang ingin dihapus: ");
				tempRemove = sc.nextLine();
				MenuAkanDipesan.remove(tempRemove);
				System.out.println("Successfully remove pesanan!");
			}
		}while(menu !=3 );
		for (String menuID : MenuAkanDipesan) {
	        String insertQuery = "INSERT INTO TransactionDetail (TransactionID, MenuID) VALUES (?, ?)";
	        try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
	            preparedStatement.setString(1, transactionID);
	            preparedStatement.setString(2, menuID);
	            preparedStatement.setString(3, mejaID);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }
	    }
		
		try {
			Statement state = conn.createStatement();
	        String query = "UPDATE transactionHeader SET status = 'In Order' WHERE transactionID = " + transactionID;
	        state.executeUpdate(query);

		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void UpdateReservation(Connection conn, String staffID, String branchID, String transactionID) {
		int menu;
		do {
			System.out.print("1. Pesan Menu\n2. Cancel Reservation\n3. Exit\n>> ");
			menu = sc.nextInt();
			if(menu == 1) {
				
				String mejaID = Find.findMejaID(conn, transactionID);
				
				PesanMenu(conn, staffID, branchID, transactionID, mejaID);
			}
			else if(menu == 2) {
				Delete.CancelReservation(conn, staffID, branchID, transactionID);
			}
		}while(menu != 3);
		
	}
	
}


