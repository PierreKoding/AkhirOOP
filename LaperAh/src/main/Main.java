package main;

import java.util.Scanner;

import DML.Add;
import DML.Alter;
import DML.Delete;
import DML.View;
import connection.SQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	static Connection conn;
	private static String staffName = null;
	private static String staffID = null;
	private static String branchID = null;
	private static String branchName = null;
	
	// BELUM VALIDASI
	public static void login() {
		System.out.print("Input your Employee ID >> ");
		staffID = sc.next();
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM staffs s JOIN branches b ON s.branchID = b.branchID");
			
			while(rs.next()){
				if(staffID.equals(rs.getString("StaffID"))) {
					branchID = rs.getString("branchID");
					staffName = rs.getString("staffName");
					staffID = rs.getString("staffID");
					branchName = rs.getString("Lokasi");
				}
				else {
				}
			}
		} catch (Exception e) {

		}
	}
	
	public static void Menu() {
		int menuOpt;
		do {
			System.out.print("1. View Menu\n2. Add Menu\n3. Update Menu\n4. Delete Menu\n5. Back\n>> ");
			menuOpt = sc.nextInt();
			sc.nextLine();
			
			if(menuOpt == 1) {
				View.ViewMenu(conn, branchID);
			}
			else if(menuOpt == 2) {
				Add.AddNewMenu(conn, branchID);
			}
			else if(menuOpt == 3) {
				Alter.UpdateMenu(conn, branchID);
			}
			else if(menuOpt == 4) {
				Delete.DeleteMenu(conn, branchID);
			}
			
		}while(menuOpt != 5);
	}

	public static void ReservationMenu() {
		int ResMenuOpt;
		do {
			System.out.print("1. View Reservations\n2. Add Reservations\n3. Exit\n>> ");
			ResMenuOpt = sc.nextInt();
			if(ResMenuOpt == 1) {
				View.viewReservations(conn, branchID);
			}
			else if(ResMenuOpt == 2) {
				Add.AddNewReservation(conn, staffID, branchID);
			}
		}while(ResMenuOpt != 3);
	}
	
	public static void MainMenu() {
		int menu;
		login();
		do {
			System.out.println("Welcome "+ staffName + " from Branch " + branchName);
			System.out.print("1. Menu\n2. Reservation Menu\n>> ");
			menu = sc.nextInt();
			sc.nextLine();
			if(menu == 1) {
				Menu();
			}
			else if(menu == 2) {
				ReservationMenu();
			}
			
		}while(menu != 3);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		conn = SQLConnection.createConnection();
		MainMenu();
	}

}
