package cecs323project;

//Menu option 4 attribute (status) will only accept single character Strings according to mysql error message !!!!! Kevin
//Main Menu option 2 keeps displaying invaid choice message for submenu but still executes commands properly Kevin

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class Scrumnew {
	// Connection string
	// private static String dbURL1 = "jdbc:mysql://localhost:3306/" + DBname +
	// "?user=" + DBusername + "&password=" + DBpassword;

	// private static String dbURL1 =
	// "jdbc:mysql://192.168.189.134:3306/empDB?user=cecs323b&password=cecs323";

	// These connect to school server
	private static String dbURL1 = "jdbc:mysql://cecs-db01.coe.csulb.edu:3306/cecs323h26?user=cecs323h26&password=ahse5o";
	// private static String dbURL1 =
	// "jdbc:mysql://cecs-db01.coe.csulb.edu:3306/cecs323h15?user=cecs323h15&password=ii3beu";

	// private static String dbURL1 =
	// "jdbc:mysql://localhost:3306/cecs323h26?user=root&password=CECS323cecs";
	private static Connection conn1 = null;
	private static Statement stmnt = null;

	public static void main(String[] args) throws SQLException {
		CreateConnection();
		SelectOption();
		shutdown();
	}

	// Create connection
	private static void CreateConnection() {
		try {
			conn1 = DriverManager.getConnection(dbURL1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void SelectOption() throws SQLException {
		Scanner input = new Scanner(System.in);

		int num1 = 0;
		String username;
		String us;
		String options = "";

		while (!(options.equals("9"))) {
			// menu
			System.out.println("\n\nManage Records Menu");
			System.out.println(
					"______________________________________________________________________________________________________");
			System.out.println("1. CRUD Stakeholder"); // Constraints Complete
			System.out.println("2. CRUD Scrum Team Members"); // Constraints
																// Complete
			System.out.println("3. CRUD UserStory");
			System.out.println("4. Create Sprint"); // Constraints Complete
			System.out.println("5. Add user stories to sprint backlog"); // Constraints
																			// Complete
			System.out.println("6. Features to operate user stories of backlog"); // Constraints
																					// Complete
			System.out.println("7. List developer and Sprints"); // Constraints
																	// Complete
			System.out.println("8. List developer that is part of a Sprint"); // Constraints
																				// Complete
			System.out.println("9. Quit"); // Constraints Complete
			System.out.println("Enter Your Choice: ");
			options = input.next();
			System.out.print("\n\n");

			switch (options) {
			case "1":
				while (true) {

					try {
						num1 = 0;
						stmnt = conn1.createStatement();
						ResultSet results = stmnt.executeQuery("select idSprintTeam from SprintTeam");
						while (results.next()) {
							num1 = 1;
						}

						results.close();
						stmnt.close();

					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					if (num1 == 0) {
						System.out.println("No teams exist\n");
						break;
					}

					// CRUD stakeholders
					System.out.println("1. Create stakeholder");
					System.out.println("2. Read stakeholder");
					System.out.println("3. Update stakeholder");
					System.out.println("4. Delete stakeholder");
					System.out.println("0. Return to Main Menu");
					System.out.println("Enter Your Choice: ");
					String opt1 = input.next();
					if (opt1.equals("1")) {
						try {
							input.nextLine();
							System.out.println("Creating a new stakeholder... ");
							System.out.println("Enter stakeholder role: ");
							String Stakeholderrole = input.nextLine();
							System.out.println("Enter stakeholder name: ");
							String Stakeholdername = input.nextLine();
							System.out.print("\n");

							String Sprintteamid;
							num1 = 1;
							int w = 0;
							int re;
							String recx[] = new String[100];
							for (int i = 0; i < 100; i++) {
								recx[i] = "";
							}
							try {
								num1 = 1;
								stmnt = conn1.createStatement();
								ResultSet results = stmnt.executeQuery("select * from SprintTeamMembers");
								ResultSetMetaData rsmd = results.getMetaData();
								int numberCols = rsmd.getColumnCount();
								System.out.print("Index#\t\t");
								for (int i = 1; i <= numberCols; i++) {
									System.out.print(rsmd.getColumnLabel(i) + "\t\t");
								}
								System.out.println(
										"\n----------------------------------------------------------------------------------------------");
								while (results.next()) {
									num1 = 0;
									int recipeName = results.getInt(1);
									String recipeNam = results.getString(2);
									recx[w] = results.getString(3);
									// String recipeNa = results.getString(3);
									// String recipeN = results.getString(4);
									System.out.printf("%-24s%-30s%-26s%-20s", w, recipeName, recipeNam, recx[w]);
									System.out.println();
									w++;
								}
								results.close();
								stmnt.close();

							} catch (SQLException ex) {
								ex.printStackTrace();
							}

							if (num1 == 1) {
								System.out.println("None exist");
								break;
							}

							// asks for user input on software team index
							while (true) {
								System.out.println("Enter Index Number: ");
								us = input.next();
								System.out.print("\n");
								try {
									re = Integer.parseInt(us);
								} catch (NumberFormatException e) {
									System.out.println("Entry not number, try again.");
									continue;
								}

								if (re >= w || re < 0) {
									System.out.println("Index Number does not exist, try again.");
									continue;
								}
								Sprintteamid = recx[re];
								break;
							}

							int Stakeholderid;
							do {

								while (true) {
									System.out.println("Enter StakeholderID: ");
									username = input.next();
									System.out.print("\n");
									try {
										re = Integer.parseInt(username);
									} catch (NumberFormatException e) {
										System.out.println("Entry not number, try again.");
										continue;
									}

									Stakeholderid = re;
									break;
								}

								num1 = 1;
								try {
									stmnt = conn1.createStatement();
									ResultSet results = stmnt.executeQuery("select StakeholderID from Stakeholders");
									while (results.next()) {
										int recipeName = results.getInt(1);
										// System.out.println(Stakeholderid);
										if (Stakeholderid == recipeName) {
											System.out.println("ID Taken. Please enter a different ID");
											num1 = 0;
											break;
										}

									}
									results.close();
									stmnt.close();

								} catch (SQLException ex) {
									ex.printStackTrace();
								}
							} while (num1 == 0);

							String insert = "INSERT INTO Stakeholders(StakeholderRole, SprintTeamMembers_TeamMemberName, SprintTeam_idSprintTeam, StakeholderID)"
									+ " VALUES (?, ?, ?, ?)";
							PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
							prep.setString(1, Stakeholderrole);
							prep.setString(2, Stakeholdername);
							prep.setString(3, Sprintteamid);
							prep.setInt(4, Stakeholderid);
							// executes prepare statement
							prep.execute();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						System.out.println("Stakeholder added.\n");

						// break;
					} else if (opt1.equals("2")) {
						try {
							num1 = 0;
							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from Stakeholders");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							for (int i = 1; i <= numberCols; i++) {
								// printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n-----------------------------------------------------------------------------------------------------------------------------");
							// prints the table along with the recipe
							while (results.next()) {
								num1 = 1;
								String StakeholderName = results.getString(1);
								String StakeholderRole = results.getString(2);
								String idSprintTeam = results.getString(3);
								int StakeholderID = results.getInt(4);
								System.out.printf("%-33s%-42s%-35s%-25s", StakeholderName, StakeholderRole,
										idSprintTeam, StakeholderID);
								System.out.println();
							}
							if (num1 == 0) {
								System.out.println("nothing to display");
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
						// break;
					} else if (opt1.equals("3")) {
						// System.out.println("Enter stakeholder id to update:
						// ");
						int StakeID;
						input.nextLine();

						int recipeN[] = new int[100];
						int w = 0, re;
						try {

							num1 = 1;
							stmnt = conn1.createStatement();
							ResultSet results = stmnt.executeQuery("select * from Stakeholders");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("Index#\t\t");
							for (int i = 1; i <= numberCols; i++) {
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n--------------------------------------------------------------------------------------------------------------------------------------");
							while (results.next()) {
								num1 = 0;

								String recipeName = results.getString(1);
								String recipeNam = results.getString(2);
								String recipeNa = results.getString(3);
								recipeN[w] = results.getInt(4);
								// String recipeNa = results.getString(3);
								// String recipeN = results.getString(4);
								System.out.printf("%-20s%-30s%-43s%-32s%-10s", w, recipeName, recipeNam, recipeNa,
										recipeN[w]);
								System.out.println();
								w++;
							}
							results.close();
							stmnt.close();

						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						if (num1 == 1) {
							System.out.println("No data to modify");
							break;
						}

						// asks for user input on software team index
						while (true) {
							System.out.println("Enter Index Number: ");
							us = input.nextLine();
							System.out.print("\n");
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException e) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							StakeID = recipeN[re];
							break;
						}

						System.out.println("Enter new info, Stakeholdername: ");
						String StakeholderName = input.nextLine();

						if (!(StakeholderName.equals(""))) {
							String insert = "UPDATE Stakeholders SET SprintTeamMembers_TeamMemberName = ? WHERE StakeholderID = ?";
							PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
							prep.setString(1, StakeholderName);
							prep.setInt(2, StakeID);
							prep.executeUpdate();
						}

						System.out.println("Enter stakeholder role: ");
						String StakeholderRole = input.nextLine();

						if (!(StakeholderRole.equals(""))) {
							String insert = "UPDATE Stakeholders SET StakeholderRole = ? WHERE StakeholderID = ?";
							PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
							prep.setString(1, StakeholderRole);
							prep.setInt(2, StakeID);
							prep.executeUpdate();
						}

						// System.out.println("Enter sprint team id: ");
						String idSprintteam;// = input.next();

						num1 = 1;
						w = 0;
						String recx[] = new String[100];
						for (int i = 0; i < 100; i++) {
							recx[i] = "";
						}
						try {
							num1 = 1;
							stmnt = conn1.createStatement();
							ResultSet results = stmnt.executeQuery("select * from SprintTeamMembers");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("Index#\t\t");
							for (int i = 1; i <= numberCols; i++) {
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n------------------------------------------------------------------------------------------");
							while (results.next()) {
								num1 = 0;
								int recipeName = results.getInt(1);
								String recipeNam = results.getString(2);
								recx[w] = results.getString(3);
								// String recipeNa = results.getString(3);
								// String recipeN = results.getString(4);
								System.out.printf("%-23s%-29s%-28s%-20s", w, recipeName, recipeNam, recx[w]);
								System.out.println();
								w++;
							}
							results.close();
							stmnt.close();

						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						if (num1 == 1) {
							System.out.println("None exist");
							break;
						}

						// asks for user input on software team index
						while (true) {
							System.out.println("Enter index of sprint team to assign stakeholder to: ");
							us = input.nextLine();
							System.out.print("\n");

							if (us.equals("")) {
								idSprintteam = "";
								break;
								// System.out.println("it worked!!!!!");
								// break;
							}

							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException e) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							idSprintteam = recx[re];

							if (!(idSprintteam.equals(""))) {
								String insert = "UPDATE Stakeholders SET SprintTeam_idSprintTeam = ? WHERE StakeholderID = ?";
								PreparedStatement prep = (PreparedStatement) ((Connection) conn1)
										.prepareStatement(insert);
								prep.setString(1, idSprintteam);
								prep.setInt(2, StakeID);
								prep.executeUpdate();
							}

							break;
						}

						/*
						 * System.out.
						 * println("\nAre you sure you want to continue (type: yes or no)?: "
						 * ); username = input.next(); // returns to menu if
						 * user says no if (!(username.equals("yes"))) {
						 * System.out.println("OK. No changes have been made.");
						 * break; }
						 * 
						 * String insert =
						 * "UPDATE Stakeholders SET StakeholderRole = ?, SprintTeamMembers_TeamMemberName = ?, SprintTeam_idSprintTeam = ? WHERE StakeholderID = ?"
						 * ; PreparedStatement prep = (PreparedStatement)
						 * ((Connection) conn1).prepareStatement(insert); // if
						 * (!(StakeholderName.equals(""))) prep.setString(1,
						 * StakeholderName); if (!(StakeholderRole.equals("")))
						 * prep.setString(2, StakeholderRole); if
						 * (!(idSprintteam.equals(""))) prep.setString(3,
						 * idSprintteam); prep.setInt(4, StakeID);
						 * prep.executeUpdate();
						 */

						// break;
					} else if (opt1.equals("4")) {
						System.out.println("Delete a stakeholder(ID): ");
						int IDdlt;

						int recipeN[] = new int[100];
						int w = 0, re;
						try {

							num1 = 1;
							stmnt = conn1.createStatement();
							ResultSet results = stmnt.executeQuery("select * from Stakeholders");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("Index#\t\t");
							for (int i = 1; i <= numberCols; i++) {
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n--------------------------------------------------------------------------------------------------------------------------------------------");
							while (results.next()) {
								num1 = 0;

								String recipeName = results.getString(1);
								String recipeNam = results.getString(2);
								String recipeNa = results.getString(3);
								recipeN[w] = results.getInt(4);
								// String recipeNa = results.getString(3);
								// String recipeN = results.getString(4);
								System.out.printf("%-17s%-31s%-40s%-35s%-23s", w, recipeName, recipeNam, recipeNa,
										recipeN[w]);
								System.out.println();
								w++;
							}
							results.close();
							stmnt.close();

						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						if (num1 == 1) {
							System.out.println("Nothing to delete");
							break;
						}

						// asks for user input on software team index
						while (true) {
							System.out.println("Enter Index Number: ");
							us = input.next();
							System.out.print("\n");
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException e) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							IDdlt = recipeN[re];
							break;
						}

						System.out.println("\nAre you sure you want to continue (type: yes or no)?: ");
						username = input.next();
						// returns to menu if user says no
						if (!(username.equals("yes"))) {
							System.out.println("OK. No changes have been made.");
							break;
						}

						// sql statement for delete from table
						PreparedStatement st = conn1
								.prepareStatement("DELETE from Stakeholders WHERE StakeholderID =" + IDdlt);
						st.executeUpdate();

						System.out.println("Delete complete\n");

						// break;
					}

					else if (opt1.equals("0")) {
						break;
					}

					else {
						System.out.println("Valid choices are 1-4, Try again");
					}
				}
				break;
			case "2":

				num1 = 1;
				try {

					stmnt = conn1.createStatement();
					// establishes results of the table
					ResultSet results = stmnt.executeQuery("select idSprintTeam from SprintTeam");
					// ResultSetMetaData rsmd = results.getMetaData();
					// int numberCols = rsmd.getColumnCount();
					/*
					 * for (int i=1; i<=numberCols; i++) { //printColumnNames
					 * System.out.print(rsmd.getColumnLabel(i)+"\t\t"); }
					 * System.out.println(
					 * "\n----------------------------------------------------------------------------"
					 * );
					 */
					// prints the table along with the recipe
					while (results.next()) {
						// int inw = results.getInt(1);
						num1 = 0;
						// SprintTeamID[e1] = results.getString(1);
						// System.out.println(SprintTeamID[e]);
						// e1++;
					}
					results.close();
					stmnt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				if (num1 == 1) {
					System.out.println("No Teams exist");
					break;
				}

				while (true) {
					// CRUD Sprint team members
					System.out.println("1. Create sprint team member");
					System.out.println("2. Read sprint team member");
					System.out.println("3. Update sprint team member");
					System.out.println("4. Delete sprint team member");
					System.out.println("0. Return to MAIN MENU");
					System.out.println("Enter Your Choice: ");
					String opt2 = input.next();

					if (opt2.equals("1")) {

						int SprintTeamMemberID;// = input.nextInt();

						input.nextLine();
						System.out.println("Creating a sprint team member... ");
						// System.out.println("Enter sprint team member id: ");

						while (true) {
							int re;
							// String us;
							// System.out.println("Enter Index Number: ");
							// us = input.next();
							// System.out.println("Enter Userstory backlog id to
							// Update: ");
							System.out.println("Enter sprint team member id: ");
							us = input.next();

							System.out.print("\n");
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException p) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							SprintTeamMemberID = re;

							num1 = 1;
							try {

								stmnt = conn1.createStatement();
								// establishes results of the table
								ResultSet results = stmnt
										.executeQuery("select SprintTeamMemberID from SprintTeamMembers");
								while (results.next()) {
									int inw = results.getInt(1);
									if (SprintTeamMemberID == inw) {
										num1 = 0;
										// break;
									}
								}
								results.close();
								stmnt.close();
							} catch (SQLException ex) {
								ex.printStackTrace();
							}

							if (num1 == 0) {
								System.out.println("Name taken, Try again.");
								continue;
							}
							break;
						}

						input.nextLine();
						System.out.println("Enter team member name: ");
						String teamMemberName = input.nextLine();

						// System.out.println("Enter id sprint team: ");
						String idSprintTeam;// = input.nextLine();

						int w2 = 0;
						String IDne[] = new String[100];
						try {

							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from SprintTeam");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("index# \t\t");
							for (int i = 1; i <= numberCols; i++) { // printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println("\n-----------------------------------------");

							// prints the table along with the recipe
							while (results.next()) {
								// int recipeName = results.getInt(1);
								// String recipeNam = results.getString(2);
								IDne[w2] = results.getString(1);
								System.out.printf("%-14s%-10s", w2, IDne[w2]);
								System.out.println();
								w2++;
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						while (true) {
							int re;
							System.out.println("Enter Index Number: ");
							us = input.next();
							System.out.print("\n");
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException p) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w2 || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							idSprintTeam = IDne[re];
							break;
						}

						try {
							String insert = "INSERT INTO SprintTeamMembers(SprintTeamMemberID, TeamMemberName, SprintTeam_idSprintTeam)"
									+ "\nVALUES (?, ?, ?)";
							PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
							prep.setInt(1, SprintTeamMemberID);
							prep.setString(2, teamMemberName);
							prep.setString(3, idSprintTeam);
							// executes prepare statement
							prep.execute();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
						// break;
					} else if (opt2.equals("2")) {
						try {
							num1 = 0;
							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from SprintTeamMembers");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							for (int i = 1; i <= numberCols; i++) {
								// printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n--------------------------------------------------------------------------------");
							// prints the table along with the recipe
							while (results.next()) {
								num1 = 1;
								int id = results.getInt(1);
								String SprintTeamMemberName = results.getString(2);
								String SprintTeamID = results.getString(3);
								System.out.printf("%-36s%-26s%-20s", id, SprintTeamMemberName, SprintTeamID);
								System.out.println();
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						if (num1 == 0) {
							System.out.println("Nothing to list");
							continue;
						}

						// break;
					} else if (opt2.equals("3")) {
						// input.nextLine();
						// System.out.println("Enter sprint team member id to
						// update: ");
						int sprintteammemberid;// = input.nextInt();

						int w2 = 0;
						int Ie[] = new int[100];
						try {
							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from SprintTeamMembers");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("index# \t\t");
							for (int i = 1; i <= numberCols; i++) { // printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n-------------------------------------------------------------------------------------------------");

							// prints the table along with the recipe
							while (results.next()) {
								Ie[w2] = results.getInt(1);
								String recipeName = results.getString(2);
								String recipeNam = results.getString(3);
								System.out.printf("%-25s%-28s%-23s%-20s", w2, Ie[w2], recipeName, recipeNam);
								System.out.println();
								w2++;
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						while (true) {
							int re;
							System.out.println("Enter Index Number: ");
							us = input.next();
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException p) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w2 || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							sprintteammemberid = Ie[re];
							// System.out.println("value: " + IDdlt);
							break;
						}

						input.nextLine();
						System.out.println("Enter new info, Sprint team member name: ");
						String sprintteammembername = input.nextLine();

						if (!(sprintteammembername.equals(""))) {
							String insert = "UPDATE SprintTeamMembers SET TeamMemberName = ? WHERE SprintTeamMemberID = ?";
							PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
							prep.setString(1, sprintteammembername);
							// prep.setString(2, idSprintteam);
							prep.setInt(2, sprintteammemberid);
							prep.executeUpdate();
						}

						// System.out.println("Enter sprint team id: ");
						String idSprintteam;// = input.nextLine();

						int w21 = 0;
						String Ie1[] = new String[100];
						try {
							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from SprintTeam");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("index# \t\t");
							for (int i = 1; i <= numberCols; i++) { // printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println("\n----------------------------------");

							// prints the table along with the recipe
							while (results.next()) {
								Ie1[w21] = results.getString(1);
								// String recipeName = results.getString(2);
								// String recipeNam = results.getString(3);
								System.out.println(w21 + "\t\t" + Ie1[w21]);
								w21++;
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						while (true) {
							int re;
							System.out.println("Enter Index Number: ");
							us = input.nextLine();

							if ((us.equals(""))) {
								idSprintteam = "";
								break;
							}

							System.out.print("\n");
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException p) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w21 || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							idSprintteam = Ie1[re];
							// System.out.println("value: " + IDdlt);
							break;
						}

						if (!(idSprintteam.equals(""))) {
							String insertw = "UPDATE SprintTeamMembers SET SprintTeam_idSprintTeam= ? WHERE SprintTeamMemberID = ?";
							PreparedStatement prepw = (PreparedStatement) ((Connection) conn1)
									.prepareStatement(insertw);
							// prep.setString(1, sprintteammembername);
							prepw.setString(1, idSprintteam);
							prepw.setInt(2, sprintteammemberid);
							prepw.executeUpdate();
						}

						// break;
					} else if (opt2.equals("4")) {
						// System.out.println("Delete a team member(ID): ");
						int IDdlt;// = input.nextInt();
						// sql statement for delete from table

						int w2 = 0;
						int Ie[] = new int[100];
						try {
							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from SprintTeamMembers");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("index# \t\t");
							for (int i = 1; i <= numberCols; i++) { // printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n-------------------------------------------------------------------------------------------------");

							// prints the table along with the recipe
							while (results.next()) {
								Ie[w2] = results.getInt(1);
								String recipeName = results.getString(2);
								String recipeNam = results.getString(3);
								System.out.printf("%-25s%-28s%-24s%-20s", w2, Ie[w2], recipeName, recipeNam);
								System.out.println();
								w2++;
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						while (true) {
							int re;
							System.out.println("Enter Index Number: ");
							us = input.next();
							System.out.print("\n");
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException p) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w2 || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							IDdlt = Ie[re];
							// System.out.println("value: " + IDdlt);
							break;
						}

						PreparedStatement st = conn1
								.prepareStatement("DELETE from SprintTeamMembers WHERE SprintTeamMemberID=" + IDdlt);
						st.executeUpdate();
						// break;
					}

					else if (opt2.equals("0")) {
						break;
					} else {
						System.out.println("Valid choices are 1-4, Try again");
					}
					// break;
				}
				break;
			case "3":

				num1 = 1;
				try {

					stmnt = conn1.createStatement();
					// establishes results of the table
					ResultSet results = stmnt.executeQuery("select * from Sprint");
					// ResultSetMetaData rsmd = results.getMetaData();
					// int numberCols = rsmd.getColumnCount();
					/*
					 * for (int i=1; i<=numberCols; i++) { //printColumnNames
					 * System.out.print(rsmd.getColumnLabel(i)+"\t\t"); }
					 * System.out.println(
					 * "\n----------------------------------------------------------------------------"
					 * );
					 */
					// prints the table along with the recipe
					while (results.next()) {
						// int inw = results.getInt(1);
						num1 = 0;
						// SprintTeamID[e1] = results.getString(1);
						// System.out.println(SprintTeamID[e]);
						// e1++;
					}
					results.close();
					stmnt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				if (num1 == 1) {
					System.out.println("No Sprints Exist");
					break;
				}

				while (true) {
					// CRUD UserStory
					System.out.println("1. Create UserStory");
					System.out.println("2. Read UserStory");
					System.out.println("3. Update UserStory");
					System.out.println("4. Delete UserStory");
					System.out.println("0. Return to MAIN MENU");
					System.out.println("Enter Your Choice: ");
					String opt3 = input.next();

					if (opt3.equals("1")) {
						try {
							input.nextLine();
							System.out.println("Making UserStory... ");
							System.out.println("Enter new title as a 1. developer or 2. user: ");
							String title = input.nextLine();
							while (true) {
								if (title.equals("1")) {
									title = "developer";
									break;
								} else if (title.equals("2")) {
									title = "user";
									break;
								} else {
									System.out.println("Try again: ");
									title = input.nextLine();
								}
							}
							// System.out.println("Enter Project ID: ");
							int project;// = input.nextInt();

							while (true) {
								int re;
								System.out.println("Enter Project ID: ");
								us = input.next();
								System.out.print("\n");
								try {
									re = Integer.parseInt(us);
								} catch (NumberFormatException p) {
									System.out.println("Entry not number, try again.");
									continue;
								}
								project = re;

								// System.out.println("value: " + IDdlt);
								// break;
								// }
								int rec;
								int w = 0;
								// int w2 = 0;
								// int Ie[] = new int[100];
								try {
									stmnt = conn1.createStatement();
									// establishes results of the table
									ResultSet results = stmnt.executeQuery("select Project from UserStory");

									// prints the table along with the recipe
									while (results.next()) {
										rec = results.getInt(1);
										if (project == rec) {
											System.out.println("");
											w = 1;
											break;
										}
										// w2=1;
									}
									results.close();
									stmnt.close();
								} catch (SQLException ex) {
									ex.printStackTrace();
								}
								// project = rec;
								if (w == 1) {
									w = 0;
									continue;
								}
								break;
							}

							// System.out.println("Enter sprint id: ");
							int idSprint;// = input.nextInt();

							// int IDdlt;// = input.nextInt();
							// sql statement for delete from table

							int w2 = 0;
							int Ie[] = new int[100];
							try {
								stmnt = conn1.createStatement();
								// establishes results of the table
								ResultSet results = stmnt.executeQuery("select * from Sprint");
								ResultSetMetaData rsmd = results.getMetaData();
								int numberCols = rsmd.getColumnCount();
								System.out.print("index# \t\t");
								for (int i = 1; i <= numberCols; i++) { // printColumnNames
									System.out.print(rsmd.getColumnLabel(i) + "\t\t");
								}
								System.out.println(
										"\n----------------------------------------------------------------------------");

								// prints the table along with the recipe
								while (results.next()) {

									Ie[w2] = results.getInt(1);
									String recipeName = results.getString(2);
									String recipeNam = results.getString(3);
									String recipeNa = results.getString(4);
									String recipeN = results.getString(5);
									String recipe = results.getString(5);
									System.out.println(w2 + "\t\t" + recipeName + "\t\t" + Ie[w2] + "\t\t" + recipeNam
											+ "\t\t" + recipeNa + "\t\t" + recipeN + "\t\t" + recipe);
									w2++;
								}
								results.close();
								stmnt.close();
							} catch (SQLException ex) {
								ex.printStackTrace();
							}

							while (true) {
								int re;
								System.out.println("Enter Index Number: ");
								us = input.next();
								System.out.print("\n");
								try {
									re = Integer.parseInt(us);
								} catch (NumberFormatException p) {
									System.out.println("Entry not number, try again.");
									continue;
								}

								if (re >= w2 || re < 0) {
									System.out.println("Index Number does not exist, try again.");
									continue;
								}
								idSprint = Ie[re];
								// System.out.println("value: " + IDdlt);
								break;
							}

							input.nextLine();
							// System.out.println("Enter id of sprint team: ");
							String idSprintteam;// = input.nextLine();

							int w21 = 0;
							String Ie1[] = new String[100];
							try {
								stmnt = conn1.createStatement();
								// establishes results of the tableteam
								ResultSet results = stmnt.executeQuery("select * from SprintTeam");
								ResultSetMetaData rsmd = results.getMetaData();
								int numberCols = rsmd.getColumnCount();
								System.out.print("index# \t\t");
								for (int i = 1; i <= numberCols; i++) { // printColumnNames
									System.out.print(rsmd.getColumnLabel(i) + "\t\t");
								}
								System.out.println(
										"\n----------------------------------------------------------------------------");

								// prints the table along with the recipe
								while (results.next()) {
									Ie1[w21] = results.getString(1);
									// String recipeName = results.getString(2);
									// String recipeNam = results.getString(3);
									System.out.println(w21 + "\t\t" + Ie1[w21]);
									w21++;
								}
								results.close();
								stmnt.close();
							} catch (SQLException ex) {
								ex.printStackTrace();
							}

							while (true) {
								int re;
								System.out.println("Enter Index Number: ");
								us = input.next();
								System.out.print("\n");
								try {
									re = Integer.parseInt(us);
								} catch (NumberFormatException p) {
									System.out.println("Entry not number, try again.");
									continue;
								}

								if (re >= w21 || re < 0) {
									System.out.println("Index Number does not exist, try again.");
									continue;
								}
								idSprintteam = Ie1[re];
								// System.out.println("value: " + IDdlt);
								break;
							}

							input.nextLine();
							System.out.println("Enter userstory description: ");
							String Description = input.nextLine();
							System.out.println("Enter user story status(1. started or 2. finished): ");
							String userstorystatus = input.next();
							// System.out.println("value: " + userstorystatus);
							while (true) {
								if (userstorystatus.equals("1")) {
									userstorystatus = "started";
									break;
								} else if (userstorystatus.equals("2")) {
									userstorystatus = "finished";
									break;
								} else {
									System.out.println("Try again: ");
									userstorystatus = input.next();
								}
							}

							// System.out.println(title + "\t\t" + project +
							// "\t\t" + idSprint + "\t\t" + idSprintteam +
							// "\t\t" + Description + "\t\t" + userstorystatus);

							String insert = "INSERT INTO UserStory(Title, Project, Sprint_idSprint, Sprint_SprintTeam_idSprintTeam, Description, UserStoryStatus)"
									+ "\nVALUES (?, ?, ?, ?, ?, ?)";
							PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
							prep.setString(1, title);
							prep.setInt(2, project);
							prep.setInt(3, idSprint);
							prep.setString(4, idSprintteam);
							prep.setString(5, Description);
							prep.setString(6, userstorystatus);
							// executes prepare statement
							prep.execute();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
						// break;
					} else if (opt3.equals("2")) {
						try {
							num1 = 0;
							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from UserStory");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							for (int i = 1; i <= numberCols; i++) {
								// printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n------------------------------------------------------------------------------------------------------------------------------------------");
							while (results.next()) {
								num1 = 1;
								String title = results.getString(1);
								int project = results.getInt(2);
								int idsprint = results.getInt(3);
								String idSprintteam = results.getString(4);
								String description = results.getString(5);
								String userstorystat = results.getString(6);

								System.out.println(title + "   \t\t" + project + "  \t\t" + idsprint + "\t\t\t"
										+ idSprintteam + "\t\t\t" + description + "  \t\t" + userstorystat);
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						if (num1 == 0) {
							System.out.println("Nothing to print");
						}

						// break;
					} else if (opt3.equals("3")) {
						// input.nextLine();
						// System.out.println("Enter Userstory project id to
						// update: ");
						int project;// = input.nextInt();
						int re;
						while (true) {

							System.out.println("Enter UserStory project id to update: ");
							us = input.next();
							// if(us.equals("")) {
							// project = re;
							// break;
							// }
							System.out.print("\n");
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException p) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							// System.out.println("value: " + IDdlt);
							break;
						}
						project = re;
						input.nextLine();
						System.out.println("Enter new title as a 1. developer or 2. user: ");
						String title = input.nextLine();
						while (true) {
							if (title.equals("")) {
								break;
							}
							if (title.equals("1")) {
								title = "developer";
								break;
							} else if (title.equals("2")) {
								title = "user";
								break;
							} else {
								System.out.println("Try again: ");
								// title = input.next();
							}
						}

						if (!(title.equals(""))) {
							String insert = "UPDATE SprintTeamMembers SET Title = ? WHERE Project = ?";
							PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
							prep.setString(1, title);
							// prep.setInt(2, Sprintid);
							// prep.setString(3, sprintteamid);
							// prep.setString(4, description);
							// prep.setString(5, userstorystatus);
							prep.setInt(2, project);
						}

						// System.out.println("Enter sprint id: ");
						int Sprintid;// = input.nextInt();
						// input.nextLine();
						int w2 = 0;
						int Ie[] = new int[100];
						try {
							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from Sprint");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("index# \t\t");
							for (int i = 1; i <= numberCols; i++) { // printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n----------------------------------------------------------------------------");

							// prints the table along with the recipe
							while (results.next()) {

								Ie[w2] = results.getInt(1);
								String recipeName = results.getString(2);
								String recipeNam = results.getString(3);
								String recipeNa = results.getString(4);
								String recipeN = results.getString(5);
								String recipe = results.getString(5);
								System.out.println(w2 + "\t\t" + recipeName + "\t\t" + Ie[w2] + "\t\t" + recipeNam
										+ "\t\t" + recipeNa + "\t\t" + recipeN + "\t\t" + recipe);
								w2++;
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						while (true) {
							// int re;
							System.out.println("Enter Index Number: ");
							us = input.nextLine();
							System.out.print("\n");

							if (us.equals("")) {
								Sprintid = -1;
								break;
							}

							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException p) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w2 || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							Sprintid = Ie[re];
							// System.out.println("value: " + IDdlt);
							break;
						}
						if (Sprintid == -1) {
							String insert = "UPDATE SprintTeamMembers SET Sprint_idSprint = ? WHERE Project = ?";
							PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
							// prep.setString(1, title);
							prep.setInt(1, Sprintid);
							// prep.setString(3, sprintteamid);
							// prep.setString(4, description);
							// prep.setString(5, userstorystatus);
							prep.setInt(2, project);
						}

						// System.out.println("Enter id of sprint team: ");
						String sprintteamid;// = input.nextLine();
						int w21 = 0;
						String Ie1[] = new String[100];
						try {
							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from SprintTeam");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("index# \t\t");
							for (int i = 1; i <= numberCols; i++) { // printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n----------------------------------------------------------------------------");

							// prints the table along with the recipe
							while (results.next()) {
								Ie1[w21] = results.getString(1);
								// String recipeName = results.getString(2);
								// String recipeNam = results.getString(3);
								System.out.println(w21 + "\t\t" + Ie1[w21]);
								w21++;
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						while (true) {
							// int re;
							System.out.println("Enter Index Number: ");
							us = input.nextLine();

							if ((us.equals(""))) {
								sprintteamid = "";
								break;
							}

							System.out.print("\n");
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException p) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w21 || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							sprintteamid = Ie1[re];
							// System.out.println("value: " + IDdlt);
							break;
						}

						if (!(sprintteamid.equals(""))) {
							String insert1 = "UPDATE SprintTeamMembers SET Sprint_SprintTeam_idSprintTeam = ? WHERE Project = ?";
							PreparedStatement prep1 = (PreparedStatement) ((Connection) conn1)
									.prepareStatement(insert1);
							// prep.setString(1, title);
							// prep.setInt(2, Sprintid);
							prep1.setString(1, sprintteamid);
							// prep.setString(4, description);
							// prep.setString(5, userstorystatus);
							prep1.setInt(2, project);
						}

						System.out.println("Enter description of userstory: ");
						String description = input.nextLine();

						if (!(description.equals(""))) {
							String insert1 = "UPDATE SprintTeamMembers SET Description = ? WHERE Project = ?";
							PreparedStatement prep1 = (PreparedStatement) ((Connection) conn1)
									.prepareStatement(insert1);
							// prep.setString(1, title);
							// prep.setInt(2, Sprintid);
							// prep.setString(3, sprintteamid);
							prep1.setString(1, description);
							// prep.setString(5, userstorystatus);
							prep1.setInt(2, project);
						}

						System.out.println("Enter user story status(1. started or 2. finished): ");
						String userstorystatus = input.nextLine();
						while (true) {
							if (userstorystatus.equals("")) {
								break;
							}
							if (userstorystatus.equals("1")) {
								userstorystatus = "started";
								break;
							} else if (userstorystatus.equals("2")) {
								userstorystatus = "finished";
								break;
							} else {
								System.out.println("Try again: ");
								userstorystatus = input.next();
							}
						}
						if (!(userstorystatus.equals(""))) {
							String insert1 = "UPDATE SprintTeamMembers SET UserStoryStatus= ? WHERE Project = ?";
							PreparedStatement prep1 = (PreparedStatement) ((Connection) conn1)
									.prepareStatement(insert1);
							// prep.setString(1, title);
							// prep.setInt(2, Sprintid);
							// prep.setString(3, sprintteamid);
							// prep.setString(4, description);
							prep1.setString(1, userstorystatus);
							prep1.setInt(2, project);
						}
						// break;
					} else if (opt3.equals("4")) {
						System.out.println("Delete a userstory project: ");
						int IDdlt;// = input.nextInt();

						int w2 = 0;
						int Ie[] = new int[100];
						try {
							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select * from UserStory");
							ResultSetMetaData rsmd = results.getMetaData();
							int numberCols = rsmd.getColumnCount();
							System.out.print("index# \t\t");
							for (int i = 1; i <= numberCols; i++) { // printColumnNames
								System.out.print(rsmd.getColumnLabel(i) + "\t\t");
							}
							System.out.println(
									"\n----------------------------------------------------------------------------");

							// prints the table along with the recipe
							while (results.next()) {
								String recipeName = results.getString(1);
								Ie[w2] = results.getInt(2);

								int recipeNam = results.getInt(3);
								String recipeNa = results.getString(4);
								String recipeN = results.getString(5);
								String recipe = results.getString(5);
								System.out.println(w2 + "\t\t" + recipeName + "\t\t" + Ie[w2] + "\t\t" + recipeNam
										+ "\t\t" + recipeNa + "\t\t" + recipeN + "\t\t" + recipe);
								w2++;
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						while (true) {
							int re;
							System.out.println("Enter Index Number: ");
							us = input.next();
							System.out.print("\n");
							try {
								re = Integer.parseInt(us);
							} catch (NumberFormatException p) {
								System.out.println("Entry not number, try again.");
								continue;
							}

							if (re >= w2 || re < 0) {
								System.out.println("Index Number does not exist, try again.");
								continue;
							}
							IDdlt = Ie[re];
							// System.out.println("value: " + IDdlt);
							break;
						}

						// sql statement for delete from table
						PreparedStatement st = conn1
								.prepareStatement("DELETE from SprintBackLog WHERE UserStory_Project=" + IDdlt);
						st.executeUpdate();
						PreparedStatement sts = conn1.prepareStatement("DELETE from UserStory WHERE Project=" + IDdlt);
						sts.executeUpdate();
						// break;
					}

					else if (opt3.equals("0")) {
						break;

					} else {
						System.out.println("Valid choices are 1-4, Try again");
					}
				}
				break;
			case "4":
				num1 = 0;
				try {

					stmnt = conn1.createStatement();
					// establishes results of the table
					ResultSet results = stmnt.executeQuery("select idSprintTeam from SprintTeam");
					while (results.next()) {
						num1 = 1;
					}
					results.close();
					stmnt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				if (num1 == 0) {
					System.out.println("No teams exist");
					break;
				}

				// Create Sprint
				try {
					// input.nextLine();
					System.out.println("Making new Sprint... ");

					int idSprint;// = input.nextInt();

					while (true) {
						int re;
						System.out.println("Enter sprint ID: ");
						us = input.next();
						try {
							re = Integer.parseInt(us);
						} catch (NumberFormatException p) {
							System.out.println("Entry not number, try again.");
							continue;
						}

						idSprint = re;

						num1 = 0;
						try {

							stmnt = conn1.createStatement();
							// establishes results of the table
							ResultSet results = stmnt.executeQuery("select idSprint from Sprint");
							while (results.next()) {
								int r = results.getInt(1);
								if (r == re) {
									num1 = 1;
									System.out.println("ID entered already exists, try again.");
								}
							}
							results.close();
							stmnt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						if (num1 == 1) {
							continue;
						}

						break;
					}
					String startDate;// = input.next();

					while (true) {
						System.out.println("Enter Start Date (yyyy-mm-dd): ");
						startDate = input.next();

						SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
						dateF.setLenient(false);

						try {
							dateF.parse(startDate);
						} catch (ParseException e) {
							System.out.println("Invalid date input. Try again.");
							continue;
						}
						break;
					}

					// System.out.println("Enter end date of sprint(yyyy-mm-dd):
					// ");
					String endDate;// = input.next();

					while (true) {
						System.out.println("Enter End Date (yyyy-mm-dd): ");
						endDate = input.next();

						SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
						dateF.setLenient(false);

						try {
							dateF.parse(endDate);
						} catch (ParseException e) {
							System.out.println("Invalid date input. Try again.");
							continue;
						}
						break;
					}

					System.out.println("Enter status of sprint: (1)finished (2)started (3)not started: ");
					String status = input.next();

					while (true) {
						if (status.equals("1")) {
							status = "started";
							break;
						} else if (status.equals("2")) {
							status = "finished";
							break;
						} else {
							System.out.println("Try again: ");
							status = input.next();
						}
					}

					// System.out.println("Enter ID of sprint team");
					String idSprintteam;// = input.next();

					int w1 = 0;
					String IDn[] = new String[100];
					try {

						stmnt = conn1.createStatement();
						// establishes results of the table
						ResultSet results = stmnt.executeQuery("select * from SprintTeamMembers");
						ResultSetMetaData rsmd = results.getMetaData();
						int numberCols = rsmd.getColumnCount();
						System.out.print("index# \t\t");
						for (int i = 1; i <= numberCols; i++) { // printColumnNames
							System.out.print(rsmd.getColumnLabel(i) + "\t\t");
						}
						System.out.println(
								"\n-------------------------------------------------------------------------------------------------");

						// prints the table along with the recipe
						while (results.next()) {
							int recipeName = results.getInt(1);
							String recipeNam = results.getString(2);
							IDn[w1] = results.getString(3);

							System.out.printf("%-24s%-28s%-25s%-20s", w1, recipeName, recipeNam, IDn[w1]);
							System.out.println();
							w1++;
						}
						results.close();
						stmnt.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					if (w1 == 0) {
						System.out.println("Nothing to display\n");
						break;
					}

					while (true) {
						int re;
						System.out.println("Enter Index Number: ");
						us = input.next();
						try {
							re = Integer.parseInt(us);
						} catch (NumberFormatException p) {
							System.out.println("Entry not number, try again.");
							continue;
						}

						if (re >= w1 || re < 0) {
							System.out.println("Index Number does not exist, try again.");
							continue;
						}
						idSprintteam = IDn[re];
						break;
					}

					String insert = "INSERT INTO Sprint(idSprint, startDate, endDate, status, SprintTeam_idSprintTeam)"
							+ "\nVALUES (?, ?, ?, ?, ?)";
					PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
					prep.setInt(1, idSprint);
					prep.setString(2, startDate);
					prep.setString(3, endDate);
					prep.setString(4, status);
					prep.setString(5, idSprintteam);
					// executes prepare statement
					prep.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				break;
			case "5":
				// Add user stories to the sprint backlog

				// Add user stories to the sprint backlog
				String a[] = new String[100];
				int b[] = new int[100];
				int c[] = new int[100];
				String d[] = new String[100];
				String e[] = new String[100];
				String f[] = new String[100];
				// String g[] = new String[100];1
				// String h[] = new String[100];
				for (int i = 0; i < 100; i++)
					a[i] = d[i] = e[i] = f[i] = "";
				int k = 0;
				try {
					// System.out.println("Enter Sprint Team ID: ");
					// String idSprintTeam = input.next();
					stmnt = conn1.createStatement();
					// establishes results of the table
					ResultSet results = stmnt.executeQuery("select * from UserStory WHERE UserStoryStatus ='finished'"); // and
																															// Sprint_idSprint
																															// !=
																															// select
																															// UserStory_Sprint_idSprint
																															// from
																															// SprintBackLog");
					/*
					 * ResultSetMetaData rsmd = results.getMetaData(); int
					 * numberCols = rsmd.getColumnCount();
					 * System.out.println("Members of Sprint team: ");
					 * 
					 * for (int i=1; i<=numberCols; i++) { //printColumnNames
					 * System.out.println(rsmd.getColumnLabel(i)+"\t\t"); }
					 */
					while (results.next()) {
						a[k] = results.getString(1);
						b[k] = results.getInt(2);
						c[k] = results.getInt(3);
						d[k] = results.getString(4);
						e[k] = results.getString(5);
						f[k] = results.getString(6);
						// g[k] = results.getString(7);
						// h[k] = results.getString(8);
						// System.out.println(name );
						k++;
					}
					results.close();
					stmnt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				k--;
				if (k < 0) {
					System.out.println("Nothing to update");
				}
				// System.out.println(a[0]);
				// int q=0;
				try {
					// System.out.println("Enter Sprint Team ID: ");
					// String idSprintTeam = input.next();
					stmnt = conn1.createStatement();
					// establishes results of the table
					ResultSet results = stmnt.executeQuery("select UserStory_Sprint_idSprint from SprintBackLog"); // and
																													// Sprint_idSprint
																													// !=
																													// select
																													// UserStory_Sprint_idSprint
																													// from
																													// SprintBackLog");
					/*
					 * ResultSetMetaData rsmd = results.getMetaData(); int
					 * numberCols = rsmd.getColumnCount();
					 * System.out.println("Members of Sprint team: ");
					 * 
					 * for (int i=1; i<=numberCols; i++) { //printColumnNames
					 * System.out.println(rsmd.getColumnLabel(i)+"\t\t"); }
					 */
					while (results.next()) {
						int rrr = results.getInt(1);
						for (int j = 0; j <= k; j++) {
							if (rrr == c[j]) {
								if (k < 1) {
									k--;
									k--;
									k--;
									k--;
									break;
								}
								for (int i = j; i < k; i++) {
									a[i] = a[i + 1];
									b[i] = b[i + 1];
									c[i] = c[i + 1];
									d[i] = d[i + 1];
									e[i] = e[i + 1];
									f[i] = f[i + 1];
								}
								k--;
							}
						}
						// g[k] = results.getString(7);
						// h[k] = results.getString(8);
						// System.out.println(name );
						if (k < 0) {
							break;
						}
						// q++;
					}
					results.close();
					stmnt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				if (k < 0) {
					System.out.println("Nothing to update!");
					break;
				}
				String idSprintTeam;
				// while(true) {
				int s = 0;
				input.nextLine();
				do {
					boolean valid = true;

					try {
						// System.out.println("Enter Sprint Team ID: ");
						// String idSprintTeam = input.next();
						stmnt = conn1.createStatement();
						// establishes results of the table
						ResultSet result = stmnt.executeQuery("select * from UserStory"); // and
																							// Sprint_idSprint
																							// !=
																							// select
																							// UserStory_Sprint_idSprint
																							// from
																							// SprintBackLog");

						ResultSetMetaData rsmd = result.getMetaData();
						int numberCols = rsmd.getColumnCount();
						// System.out.println("Members of Sprint team: ");

						for (int i = 1; i <= numberCols; i++) { // printColumnNames
							System.out.print(rsmd.getColumnLabel(i) + "\t\t");
						}
						System.out.print("\n");
						System.out.println(
								"___________________________________________________________________________________________");
						System.out.println(
								a[s] + "\t\t" + b[s] + "\t\t" + c[s] + "\t\t" + d[s] + "\t\t" + e[s] + "\t\t" + f[s]);
						System.out.print("\n");
						/*
						 * while (results.next()) { a[k] = results.getString(1);
						 * b[k] = results.getInt(2); c[k] = results.getInt(3);
						 * d[k] = results.getString(4); e[k] =
						 * results.getString(5); f[k] = results.getString(6); //
						 * g[k] = results.getString(7); // h[k] =
						 * results.getString(8); // System.out.println(name );
						 * k++; }
						 */
						result.close();
						stmnt.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					System.out.println("Enter New Sprint ID: ");
					idSprintTeam = input.nextLine();

					if (idSprintTeam.equals("")) {
						s++;
						continue;
					}

					try {

						stmnt = conn1.createStatement();
						// establishes results of the table
						ResultSet results = stmnt.executeQuery("select SprintBackLogID from SprintBackLog"); // and
																												// Sprint_idSprint
																												// !=
																												// select
																												// UserStory_Sprint_idSprint
																												// from
																												// SprintBackLog");
						/*
						 * ResultSetMetaData rsmd = results.getMetaData(); int
						 * numberCols = rsmd.getColumnCount();
						 * System.out.println("Members of Sprint team: ");
						 * 
						 * for (int i=1; i<=numberCols; i++) {
						 * //printColumnNames
						 * System.out.println(rsmd.getColumnLabel(i)+"\t\t"); }
						 */
						while (results.next()) {
							String rrr = results.getString(1);
							if (rrr.equals(idSprintTeam)) {
								System.out.println("ID already exists, try again");
								valid = false;
							}

						}
						if (valid == false) {
							valid = true;
							continue;
						}
						results.close();
						stmnt.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					// break;
					// }

					// int s=0;
					// do {
					try {
						// System.out.println("Enter Sprint Team ID: ");
						// String idSprintTeam = input.next();
						stmnt = conn1.createStatement();
						// establishes results of the table
						PreparedStatement results = conn1.prepareStatement(
								"insert into SprintBackLog values('" + idSprintTeam + "', '" + a[s] + "', " + b[s]
										+ ", " + c[s] + ", '" + d[s] + "', '" + e[s] + "', '" + f[s] + "')");
						/*
						 * ResultSetMetaData rsmd = results.getMetaData(); int
						 * numberCols = rsmd.getColumnCount();
						 * System.out.println("Members of Sprint team: "); for
						 * (int i=1; i<=numberCols; i++) { //printColumnNames
						 * System.out.println(rsmd.getColumnLabel(i)+"\t\t"); }
						 * while(results.next()) { String name =
						 * results.getString(1); System.out.println(name ); }
						 */
						results.executeUpdate();
						stmnt.close();
						stmnt.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					s++;
				} while (s <= k + 1);
				break;

			// break;
			case "6":
				// Features to operate user stories of backlog
				// input.nextLine();
				// System.out.println("Enter Userstory backlog id to Update: ");
				int userstorybacklogid;// = input.nextInt();

				while (true) {
					int re;
					// String us;
					// System.out.println("Enter Index Number: ");
					// us = input.next();
					System.out.println("Enter Userstory backlog id to Update: ");
					us = input.next();

					System.out.print("\n");
					try {
						re = Integer.parseInt(us);
					} catch (NumberFormatException p) {
						System.out.println("Entry not number, try again.");
						continue;
					}

					userstorybacklogid = re;
					break;
				}

				input.nextLine();
				System.out.println("Enter new title of project: ");
				String title = input.nextLine();
				int Sprintid;
				while (true) {
					System.out.println("Enter sprint id: ");
					// Sprintid = input.nextInt();

					int re;
					// String us;
					// System.out.println("Enter Index Number: ");
					// us = input.next();
					// System.out.println("Enter Userstory backlog id to Update:
					// ");
					us = input.next();

					System.out.print("\n");
					try {
						re = Integer.parseInt(us);
					} catch (NumberFormatException p) {
						System.out.println("Entry not number, try again.");
						continue;
					}

					Sprintid = re;
					break;
				}

				input.nextLine();
				System.out.println("Enter id of sprint team: ");
				String sprintteamid = input.nextLine();
				System.out.println("Enter description of userstory: ");
				String description = input.nextLine();
				System.out.println("Enter user story status: (1)finished (2)started: ");
				String userstorystatus = input.next();

				while (true) {
					if (userstorystatus.equals("1")) {
						userstorystatus = "started";
						break;
					} else if (userstorystatus.equals("2")) {
						userstorystatus = "finished";
						break;
					} else {
						System.out.println("Try again: ");
						userstorystatus = input.next();
					}
				}

				String insert = "Update SprintTeamMembers SET Title = ?, Sprint_idSprint = ?, Sprint_SprintTeam_idSprintTeam = ?, Description = ?, UserStoryStatus= ? WHERE Project = ?";
				PreparedStatement prep = (PreparedStatement) ((Connection) conn1).prepareStatement(insert);
				prep.setString(1, title);
				prep.setInt(2, Sprintid);
				prep.setString(3, sprintteamid);
				prep.setString(4, description);
				prep.setString(5, userstorystatus);
				prep.setInt(6, userstorybacklogid);
				break;
			case "7":
				// List developer and Sprints
				String SprintTeamID[] = new String[100];
				for (int i = 0; i < 100; i++)
					SprintTeamID[i] = "";
				int e1 = 0;
				try {
					stmnt = conn1.createStatement();
					// establishes results of the table
					ResultSet results = stmnt.executeQuery("select idSprintTeam from SprintTeam");
					// ResultSetMetaData rsmd = results.getMetaData();
					// int numberCols = rsmd.getColumnCount();
					/*
					 * for (int i=1; i<=numberCols; i++) { //printColumnNames
					 * System.out.print(rsmd.getColumnLabel(i)+"\t\t"); }
					 * System.out.println(
					 * "\n----------------------------------------------------------------------------"
					 * );
					 */
					// prints the table along with the recipe
					while (results.next()) {
						SprintTeamID[e1] = results.getString(1);
						// System.out.println(SprintTeamID[e]);
						e1++;
					}
					results.close();
					stmnt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				if (e1 == 0) {
					System.out.println("Nothing to display\n");
					break;
				}

				int f1 = 0;
				int g = 0;
				do {
					g = 0;
					try {
						stmnt = conn1.createStatement();
						// establishes results of the table
						ResultSet results = stmnt.executeQuery(
								"select idSprint, startDate, endDate, status from Sprint where SprintTeam_idSprintTeam='"
										+ SprintTeamID[f1] + "'");
						ResultSetMetaData rsmd = results.getMetaData();
						int numberCols = rsmd.getColumnCount();
						System.out.println("Team: " + SprintTeamID[f1]);
						for (int i = 1; i <= numberCols; i++) {
							// printColumnNames
							System.out.print(rsmd.getColumnLabel(i) + "\t\t\t");
						}
						System.out.println(
								"\n---------------------------------------------------------------------------------------------------------");
						// prints the table along with the recipe
						while (results.next()) {
							g = 1;
							int id = results.getInt(1);
							String startDate = results.getString(2);
							String endDate = results.getString(3);
							String Status = results.getString(4);
							// String Sprintjkl = results.getString(5);
							System.out.printf("%-32s%-32s%-24s%-20s", id, startDate, endDate, Status);
							System.out.println();
							e1++;
						}
						results.close();
						stmnt.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					f1++;
					if (g == 0)
						System.out.println("Nothing to display");
					System.out.println("\n");
				} while ((!SprintTeamID[f1].equals("")));

				break;
			case "8":
				num1 = 1;
				int w = 0;
				int re;
				String recx[] = new String[100];
				// String idSprintTeam;
				for (int i = 0; i < 100; i++) {
					recx[i] = "";
				}
				// List developer that is part of a Sprint
				try {
					// System.out.println("Enter Sprint Team ID: ");
					// String idSprintTeam = input.next();

					try {
						num1 = 1;
						stmnt = conn1.createStatement();
						ResultSet results = stmnt.executeQuery("select * from Sprint");
						ResultSetMetaData rsmd = results.getMetaData();
						int numberCols = rsmd.getColumnCount();
						System.out.print("Index#\t\t");
						for (int i = 1; i <= numberCols; i++) {
							System.out.print(rsmd.getColumnLabel(i) + "\t\t");
						}
						System.out.println(
								"\n-----------------------------------------------------------------------------------------------------------------------");
						while (results.next()) {
							num1 = 0;
							int recipeName = results.getInt(1);
							String recipeNam = results.getString(2);
							String recipeNa = results.getString(3);
							String recipeN = results.getString(4);
							recx[w] = results.getString(5);
							System.out.printf("%-18s%-22s%-24s%-16s%-21s%-20s", w, recipeName, recipeNam, recipeNa,
									recipeN, recx[w]);
							System.out.println();
							w++;
						}
						results.close();
						stmnt.close();

					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					if (num1 == 1) {
						System.out.println("None exist");
						break;
					}

					// asks for user input on software team index
					while (true) {
						System.out.println("Enter Index Number: ");
						us = input.next();
						System.out.print("\n");
						try {
							re = Integer.parseInt(us);
						} catch (NumberFormatException p) {
							System.out.println("Entry not number, try again.");
							continue;
						}

						if (re >= w || re < 0) {
							System.out.println("Index Number does not exist, try again.");
							continue;
						}
						idSprintTeam = recx[re];
						break;
					}

					stmnt = conn1.createStatement();
					// establishes results of the table
					ResultSet results = stmnt.executeQuery(
							"select TeamMemberName from SprintTeamMembers WHERE SprintTeam_idSprintTeam ='"
									+ idSprintTeam + "'");
					System.out.println("Members of Sprint team: ");
					while (results.next()) {
						String name = results.getString(1);
						System.out.println(name);
					}
					if (!results.next()) {
						System.out.println("------------");
						System.out.println("End of list");
					}
					results.close();
					stmnt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "9":
				shutdown();
				break;
			default:
				System.out.println("Options are 1-9");
			}
			// System.out.println("Enter an option: ");
			// options = input.next();
		}
		input.close();
	}

	// Terminate connection at end
	private static void shutdown() {
		try {
			if (stmnt != null) {
				stmnt.close();
			}
			if (conn1 != null) {
				conn1 = DriverManager.getConnection(dbURL1 + "shutdown=true");
				conn1.close();
			}
		} catch (SQLException slqEx) {

		}
	}
}
