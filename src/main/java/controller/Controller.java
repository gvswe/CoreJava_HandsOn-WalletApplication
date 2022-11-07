package controller;
import dto.Wallet;
import exception.WalletException;
import service.WalletService;
import service.WalletServiceImpl;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {

	public static void main(String[] args) {

		WalletService walletService = new WalletServiceImpl();

		System.out.println("\t\tWelcome to Wallet Application");
		Scanner scanner = new Scanner(System.in);
		Scanner scanstr = new Scanner(System.in);
		boolean quit = false;
		do {
			System.out.print("\n 1.Registration\n 2.Login\n 3.Deposit\n 4.Balance Enquiry\n 5.Transfer Fund\n 6.Unregister\n 7.Withdraw\n 8.Exit\n\n CHOOSE ONE OPTION:");
			int choice = scanner.nextInt();
			switch (choice) {

				case 1:
					//registration
					try {
						System.out.print("Enter ID:");
						Integer id=scanner.nextInt();
						System.out.print("Enter Name:");
						String name=scanstr.nextLine();
						System.out.print("Enter Password:");
						String password=scanstr.nextLine();
						System.out.print("Enter Opening Amount in Rs. x.xx format:");
						Double amount=scanner.nextDouble();
						Wallet wallet = walletService.registerWallet(new Wallet(id, name, amount, password));
						System.out.println(wallet);
					}

					catch (WalletException e) {
						System.out.println(e.getMessage());
					}
					catch (InputMismatchException e) {
						scanner.next();
						System.out.println("\nInvalid Input");
						System.out.println("Enter the Input in specified format\n ID:Integer\n Name:String\n Password:String\n Amount:Double");
					}
					break;


				case 2:
					//login
					try {
						System.out.print("Enter ID:");
						int id = scanner.nextInt();
						System.out.print("Enter Password:");
						String password=scanstr.nextLine();
						boolean success = walletService.login(id, password);
						if(success) {
							System.out.println("Logged in Successful.");
						}
					} catch (WalletException e) {
						System.out.println(e.getMessage());
					}
					catch (InputMismatchException e) {
						scanner.next();
						System.out.println("\nInvalid Input");
						System.out.println("Enter the Input in specified format\n ID:Integer\n Password:String\n");
					}
					catch (NullPointerException e){
						System.out.println(e.getMessage());
					}
					break;


				case 3:
					//add funds
					try {
						System.out.print("Enter ID:");
						int id = scanner.nextInt();
						System.out.print("Enter Amount in Rs. x.xx format:");
						Double addfund=scanner.nextDouble();
						Double amount=walletService.addFundsToWallet(id, addfund);
						System.out.println("Rs."+addfund+ " was added successfully to your Account ID "+id);
						System.out.println("Your Current Balance is Rs."+amount);
					} catch (WalletException e) {
						System.out.println(e.getMessage());
					}
					catch (InputMismatchException e) {
						scanner.next();
						System.out.println("\nInvalid Input");
						System.out.println("Enter the Input in specified format\n ID:Integer\n Amount:Double");
					}
					catch (NullPointerException e){
						System.out.println(e.getMessage());
					}
					break;


				case 4:
					//showBalance
					try {
						System.out.print("Enter ID:");
						int id = scanner.nextInt();
						Double balance=walletService.showWalletBalance(id);
						System.out.print("Your Account Balance is Rs."+balance);
					} catch (WalletException e) {
						System.out.println(e.getMessage());
					}
					catch (InputMismatchException e) {
						scanner.next();
						System.out.println("\nInvalid Input");
						System.out.println("Enter the Input in specified format\n ID:Integer\n");
					}
					catch (NullPointerException e){
						System.out.println(e.getMessage());
					}
					break;


				case 5:
					//fundTransfer
					try {
						System.out.print("Enter your ID:");
						int fromId = scanner.nextInt();
						System.out.print("Enter Recipient ID:");
						int toId = scanner.nextInt();
						System.out.print("Enter Amount in Rs. x.xx format to transfer:");
						Double sendfund=scanner.nextDouble();
						boolean success=walletService.fundTransfer(fromId, toId, sendfund);
						if(success) {
							System.out.println("Rs."+sendfund+ " was transferred successfully from Account ID "+fromId+ " to Account ID "+toId);
						}
					} catch (WalletException e) {
						System.out.println(e.getMessage());
					}
					catch (InputMismatchException e) {
						scanner.next();
						System.out.println("\nInvalid Input");
						System.out.println("Enter the Input in specified format\n ID:Integer\n Amount:Double");
					}
					catch (NullPointerException e){
						System.out.println(e.getMessage());
					}
					break;


				case 6:
					//unregister
					try {
						System.out.print("Enter ID to unregister:");
						int id = scanner.nextInt();
						System.out.print("Enter Password:");
						String password=scanstr.nextLine();
						Wallet deletedWallet=walletService.unRegisterWallet(id, password);
						System.out.println(deletedWallet+ " has been removed successfully.");
					} catch (WalletException e) {
						System.out.println(e.getMessage());
					}
					catch (InputMismatchException e) {
						scanner.next();
						System.out.println("\nInvalid Input");
						System.out.println("Enter the Input in specified format\n ID:Integer\n Password:String\n");
					}
					catch (NullPointerException e){
						System.out.println(e.getMessage());
					}
					break;

				case 7:
					//withdraw funds
					try {
						System.out.print("Enter ID:");
						int id = scanner.nextInt();
						System.out.print("Enter Amount in Rs. x.xx format: to withdraw");
						Double addfund=scanner.nextDouble();
						Double amount=walletService.withdrawFunds(id, addfund);
						System.out.println("Rs."+addfund+ " withdrawed from your balance.\n Your Current Balance is Rs."+amount);
					} catch (WalletException e) {
						System.out.println(e.getMessage());
					}
					catch (InputMismatchException e) {
						scanner.next();
						System.out.println("\nInvalid Input");
						System.out.println("Enter the Input in specified format\n ID:Integer\n Amount:Double");
					}
					catch (NullPointerException e){
						System.out.println(e.getMessage());
					}
					break;


				case 8:
					//exit
					quit = true;
					break;


				default:
					System.out.println("\nInvalid Option");
					break;
			}
		}while (!quit);
	}
}
