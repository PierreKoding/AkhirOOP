package DML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import functions.Validation;

public class Add {
	
	static Scanner sc = new Scanner(System.in);
	
	public Add() {
		// TODO Auto-generated constructor stub
	}
	
	public static void AddNewMenu(Connection conn, String branchID) {
		String NewMenuName;
		int NewPrice;
		String Tipe;
		String newID = null;
		String newStory;
		String newLokasiAsal;
		int newTerjual = 0;
		
		System.out.print("Input Menu Name: ");
		NewMenuName = sc.next();
		
		System.out.print("Input Menu Price: ");
		NewPrice = sc.nextInt();
		
		System.out.print("Input Menu Type[Special | Local Special | Normal]: ");
		Tipe = sc.next();
		
		String lastID = null;
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("SELECT MenuID FROM menus");
			while(rs.next()) {
				lastID = rs.getString("MenuID");
				String numericPart = lastID.substring(3);
                int numericValue = Integer.parseInt(numericPart);
                numericValue = numericValue + 2;
                newID = "MN" + String.format("%03d", numericValue);
                
			}
			// jika menu kosong
			if(newID == null){
				newID = "MN001";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(Tipe.equals("Special")) {
			System.out.print("Add a story to the Special Menu : ");
			newStory = sc.next();
			newLokasiAsal = "-";
		}
		else if(Tipe.equals("Local Special")) {
			System.out.print("Add the menu's origin : ");
			newStory = "-";
			newLokasiAsal = sc.next();
		}
		else {
			newStory = "-";
			newLokasiAsal = "-";
		}
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO menus (BranchID, Harga, LokasiAsal, MenuID, MenuName, Story, terjual, Tipe) VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, branchID);
			ps.setInt(2, NewPrice);
			ps.setString(3, newLokasiAsal);
			ps.setString(4, newID);
			ps.setString(5, NewMenuName);
			ps.setString(6, newStory);
			ps.setInt(7, newTerjual);
			ps.setString(8, Tipe);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				System.out.println("Menu successfully added!");
			} else {
				System.out.println("Failed to add menu");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void AddNewReservation(Connection conn, String staffID, String branchID) {
		String NamaPemesan;
		int jumlahMeja;
		String tipeMeja;
		int jumlahOrangMeja;
		int totalOrang;
		int maksOrang = 0;
		
		do {
			System.out.print("Masukkan Nama Pemesan : ");
			NamaPemesan = sc.nextLine();
			
			System.out.print("Masukkan jumlah meja : ");
			jumlahMeja = sc.nextInt();
			sc.nextLine();
			
			System.out.print("Masukkan tipe meja yang diinginkan : ");
			tipeMeja = sc.nextLine();
			
			System.out.print("Masukkan jumlah orang per meja : ");
			jumlahOrangMeja = sc.nextInt();

			totalOrang = jumlahOrangMeja * jumlahMeja;
			if(tipeMeja.equals("Romantic")) {
				maksOrang = 2*jumlahMeja;
			}
			else if(tipeMeja.equals("General")) {
				maksOrang = 4*jumlahMeja;
			}
			else if(tipeMeja.equals("Family")) {
				maksOrang = 10*jumlahMeja;
			}
			
			if(totalOrang > maksOrang) {
				System.out.println("Total pelanggan melebihi kapasitas meja yang dipesan!");
			}
			if(!Validation.MejaAvailibility(conn, tipeMeja, jumlahMeja, staffID)) {
				System.out.println("Meja tidak tersedia!");
			}
		}while(totalOrang < maksOrang);
		
		String newID = null;;
		String lastID;
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("SELECT transactionID FROM transactionHeader");
			while(rs.next()) {
				lastID = rs.getString("TransactionID");
				String numericPart = lastID.substring(2);
                int numericValue = Integer.parseInt(numericPart);
                numericValue = numericValue + 1;
                newID = "TR" + String.format("%03d", numericValue);
                
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// ASSIGN RESERVASI
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO transactionHeader (TransactionID, StaffID, CustomerName, Status) VALUES (?,?,?,?)");
			ps.setString(1, newID);
			ps.setString(2, staffID);
			ps.setString(3, NamaPemesan);
			ps.setString(4, "In Reserve");
			ps.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// ASSIGN MEJA
		try {
				Statement state = conn.createStatement();
				ResultSet rs = state.executeQuery("SELECT * FROM mejas WHERE tipeMeja = '" + tipeMeja + "' AND isOccupied = false AND branchID = '" + branchID + "' ORDER BY MejaID");

			    if (rs.next()) {
			        String mejaID = rs.getString("MejaID");
			        try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE mejas SET isOccupied = true WHERE MejaID = ?")) {
			            updateStatement.setString(1, mejaID);
			            int rowsAffected = updateStatement.executeUpdate();

			            if (rowsAffected != 0) {
			            	// tabel sukses di mark occupy
			            } else {
			                // System.out.println("Gagal mark occupy");
			                return;
			            }
			        } catch (Exception e) {
			            e.printStackTrace();
			            return;
			        }

			        try (PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO transactionDetail (TransactionID, Menu, MejaID) VALUES (?, ?, ?)")) {
			            insertStatement.setString(1, newID);
			            insertStatement.setString(2, "-");
			            insertStatement.setString(3, mejaID);

			            int rowsInserted = insertStatement.executeUpdate();
			            if (rowsInserted != 0) {
			                System.out.println("TransactionDetail record inserted successfully!");
			            } else {
			                System.out.println("Failed to insert TransactionDetail record");
			                return;
			            }
			        } catch (Exception e) {
			            e.printStackTrace();
			            return;
			        }
			    } else {
			        System.out.println("No available tables of type " + tipeMeja + " found.");
			        return;
			    }
			} catch (Exception e) {
			    e.printStackTrace();
			    return;
			}
		System.out.println("Reservation successfully made...");
	}
	
}
