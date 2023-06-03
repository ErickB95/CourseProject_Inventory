import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class Inventory {

	static Logger logger = Logger.getLogger(Inventory.class.getName());

	/**
	 * Start up inventory system's menu.
	 */
	public void openInventory() {
		// Activate Logger
		logger = configLogger(logger);

		// Basic Structure
		System.out.println("Welcome to the Inventory System.");
		logger.log(Level.INFO, "Opening Inventory");
		System.out.println();

		// User's choice
		char input = ' ';

		do {
			// Display menu
			System.out.println("Please make a selection.");
			System.out.println("===============");
			System.out.println("Enter v to view all products.");
			System.out.println("Enter p to view product by name.");
			System.out.println("Enter a to add a product.");
			System.out.println("Enter q to quit the application.");
			System.out.println("===============");

			// get input from user
			Scanner sc = new Scanner(System.in);

			// Retrieve first character of user input
			input = sc.next().charAt(0);
			System.out.println();

			if (input == 'v') {
				// View All Products
				System.out.println("View All Products");
				System.out.println("===============");
				logger.log(Level.INFO, "Now viewing all products");
				viewAllProducts();
				System.out.println();
			} else if (input == 'p') {
				// View product by name
				System.out.println("View Product By Name");
				System.out.println("===============");
				System.out.println("What is the name of the product your looking for?");
				String pName = sc.next();
				System.out.println();

				logger.log(Level.INFO, "Now viewing product: " + pName);
				getProductByName(pName);

			} else if (input == 'a') {
				// Add a product
				System.out.println("Add a product");
				System.out.println("===============");

				System.out.println("What is the name of the product?");
				String productName = sc.next();

				System.out.println("What type of product is it? (Ex: Snack, Clothing, etc,)");
				String productType = sc.next();

				System.out.println("How many are there?");
				int quantity = sc.nextInt();

				System.out.println("What is the price of the product? (Include decimals.)");
				double price = sc.nextDouble();

				addProduct(new Product(productName, productType, quantity, price));
				System.out.println();
			} else if (input == 'q') {
				// Close application
				System.out.println("Thank you for using the system.");
				sc.close();
			} else {
				// Invalid selection
				System.out.println("Invalid selection. Please try again.");
				System.out.println();
			}

		} while (input != 'q');
	}

	/**
	 * Adds a product to the inventory list.
	 * 
	 * @param product - A String containing the product to be added
	 * 
	 */
	public void addProduct(Product product) {
		try {
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root",
					"TasunaG1234!@#$");
			PreparedStatement ps = connect.prepareStatement(
					"insert into products (productName, productType, quantity, productPrice) values (?, ?, ?, ?)");

			ps.setString(1, product.getpName());
			ps.setString(2, product.getpType());
			ps.setInt(3, product.getQuantity());
			ps.setDouble(4, product.getPrice());
			ps.executeUpdate();

			System.out.println("Product added: " + product.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Displays a list of existing products.
	 */
	public void viewAllProducts() {
		try {
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root",
					"TasunaG1234!@#$");
			PreparedStatement ps = connect.prepareStatement("Select * from products");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("productID") + ". " + rs.getString("productName") + "("
						+ rs.getString("productType") + ")" + " : " + rs.getInt("quantity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Get product by name
	 * 
	 * @param productName
	 */
	public void getProductByName(String productName) {
		try {
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root",
					"TasunaG1234!@#$");
			PreparedStatement ps = connect
					.prepareStatement("Select * from products Where productName = '" + productName + "'");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("productID") + ". " + rs.getString("productName") + "("
						+ rs.getString("productType") + ")" + " : " + rs.getInt("quantity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Logger configLogger(Logger logger) {
		try {
			LogManager.getLogManager()
					.readConfiguration(new FileInputStream("C://Users//erick//OneDrive//Documents/LoggingDemo.txt"));
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}

		logger.setLevel(Level.INFO);

		try {
			Handler fileHandler = new FileHandler("C://Users//erick//OneDrive//Documents/logging.txt", 2000, 3);
			fileHandler.setFormatter(new XMLFormatter());
			logger.addHandler(fileHandler);

		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

		return logger;

	}

}
