package evoting;

import java.awt.*;
import java.awt.event.*;
import java.sql.Statement;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.print.*;

/*TODO: ADD STUFF TO ALL BUTTONS Ctrl+F JButtons
 * CHANGE DIMENSIONS EVENTUALLY Ctrl+F setBounds
 * ADD getTallys,printResults to adminMenu() and implement them outside.
 * 
 * Listing tuples in a database from here: http://stackoverflow.com/questions/21898053/display-records-from-mysql-database-using-jtable-in-java
 * Encryption algorithm taken from here: http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
*/


public class GUI {	
	
	static JFrame myFrame;	
	private static int N = 1024;
	static String databasePass = ""; //define your root account db password here

	
	public static void makeGUI(){
		
	
		Container myPanel = myFrame.getContentPane();
		myPanel.setLayout(null);
		
		myFrame.setSize(1280, 720);

		myPanel.add(new JLabel("Welcome to the electronic voting system"));
		
		JMenuBar myMenuBar = new JMenuBar();
		myFrame.setJMenuBar(myMenuBar);				

		
		JButton adminButton = new JButton("Admin menu");
		adminButton.setVerticalTextPosition(AbstractButton.CENTER);
		adminButton.setHorizontalTextPosition(AbstractButton.CENTER);
		adminButton.setBounds(640,0,640,720);
		adminButton.setToolTipText("This brings up the admin menu");
		adminButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkAdmin();
			}
		});
		
		JButton voterButton = new JButton("Voter menu");
		voterButton.setVerticalTextPosition(AbstractButton.CENTER);
		voterButton.setHorizontalTextPosition(AbstractButton.CENTER);
		voterButton.setBounds(0,0,640,720);
		voterButton.setToolTipText("This brings up the voter menu");
		voterButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				voterMenu();
			}
		});
		
		
		myPanel.add(adminButton);
		myPanel.add(voterButton);
		myFrame.getContentPane().setLayout(null);		
		myFrame.setVisible(true);	
		
	}
	
	protected static void checkAdmin() {
		
		JDialog dialog = new JDialog(myFrame, "Admin menu", true);
		dialog.setSize(1280,720);	
		dialog.getContentPane().setLayout(null);
		
		JLabel fName = new JLabel("First name: ");
		fName.setBounds(10, 10, 200, 100);
		
		JLabel lName = new JLabel("Last name: ");
		lName.setBounds(10, 70, 200, 100);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setBounds(10, 130, 200, 100);
				
		JTextField fText = new JTextField();
		fText.setBounds(250, 50, 200,30);
		
		JTextField lText = new JTextField();
		lText.setBounds(250, 110, 200,30);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(250, 170,200,30);
		
		JButton close = new JButton("Close");
		close.setBounds(400, 350, 300, 100);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.dispose();
			}
		});
		
		dialog.add(close);
		
		JButton login =new JButton("Login");
		login.setHorizontalTextPosition(AbstractButton.CENTER);
		login.setVerticalTextPosition(AbstractButton.CENTER);
		login.setBounds(50, 350, 300, 100);
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String first = fText.getText();
				String last = lText.getText();
				String password = new String(passwordField.getPassword());
				
				fText.setText("");
				lText.setText("");
				passwordField.setText("");
				//TODO: DO STUFF WITH ADMIN INFO WITH DATABASE
				
				String url = "jdbc:mysql://localhost:3306/voting?autoReconnect=true&useSSL=false";
		        String userid = "root";
		        String pw = databasePass;
		        String sql = "SELECT * FROM polladmin WHERE firstName = '" + first + "' AND lastName = '" + last + "'AND pass = '" + password + "'"; 
		        boolean isAdmin = false; 
		        try (Connection connection = DriverManager.getConnection( url, userid, databasePass );
			            Statement statement = connection.createStatement();			// TODO: CHANGE THIS
			            ResultSet rs = statement.executeQuery( sql ))
			        {
			           if(rs != null){
			        	   rs.next();
			        	   isAdmin = rs.getString("pass") != null; 
			           }
			        }
			        catch (SQLException z)
			        {
			            System.out.println( z.getMessage() );
			        }		

		      
				//pollWorker worker = new pollWorker();
				if(isAdmin != false){  			// CHECK IF WORKER IS IN DATABASE CHANGE THIS
					
					adminMenu();
				}
				else{
					JDialog dError = new JDialog(myFrame, "Error", true);
					dError.setSize(400, 500);
					
					JLabel msg = new JLabel("This user is not an admin.");
					msg.setBounds(50,50,200,30);			
					
					JButton close = new JButton("Close");
					close.setHorizontalTextPosition(AbstractButton.CENTER);
					close.setVerticalTextPosition(AbstractButton.CENTER);
					close.setBounds(50, 100, 300, 100);
					close.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							dError.dispose();
						}

					});
					
					dError.getContentPane().setLayout(null);
					dError.add(msg);
					dError.add(close);			
					dError.setVisible(true);
				}
				
			}
		});
		
		dialog.add(fName);
		dialog.add(lName);
		dialog.add(passwordLabel);
		dialog.add(fText);
		dialog.add(lText);
		dialog.add(passwordField);
		dialog.add(login);
		dialog.setVisible(true);
	}

	public static void adminMenu(){		
		
		JDialog dialog = new JDialog(myFrame, "Admin menu", true);
		dialog.setSize(1280,720);		
		dialog.setLocationRelativeTo(myFrame);
		
		JButton close = new JButton("Close");
		close.setBounds(70, 550, 300, 100);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.dispose();
			}
		});
		
		dialog.add(close);
		
		JButton regVoter = new JButton("Register Voter");
		regVoter.setVerticalTextPosition(AbstractButton.CENTER);
		regVoter.setHorizontalTextPosition(AbstractButton.CENTER);
		regVoter.setBounds(50,50,200,200);
		regVoter.setToolTipText("This brings up menu to register a new voter.");
		regVoter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				registerVoter();				
			}
		});
		
		JButton check = new JButton("Check registration");
		check.setVerticalTextPosition(AbstractButton.CENTER);
		check.setHorizontalTextPosition(AbstractButton.CENTER);
		check.setBounds(50,300,200,200);
		check.setToolTipText("This brings up menu to check registration of a voter.");
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList columnNames = new ArrayList();
		        ArrayList data = new ArrayList();
		        
				JDialog listDialog = new JDialog(myFrame, "List of Voters", true);
				listDialog.getContentPane().setLayout(null);
				listDialog.setSize(1280,720);				


				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        String url = "jdbc:mysql://localhost:3306/voting?autoReconnect=true&useSSL=false";
		        String userid = "root";
		        String pw = databasePass;
		        String sql = "SELECT firstName, lastName, voterID, registrationStatus FROM Voter";


		        try (Connection connection = DriverManager.getConnection( url, userid, databasePass );
		            Statement statement = connection.createStatement();			// TODO: CHANGE THIS
		            ResultSet rs = statement.executeQuery( sql ))
		        {
		            ResultSetMetaData md = rs.getMetaData();
		            int columns = md.getColumnCount();


		            for (int i = 1; i <= columns; i++)
		            {
		                columnNames.add( md.getColumnName(i) );
		            }


		            while (rs.next())
		            {
		                ArrayList row = new ArrayList(columns);

		                for (int i = 1; i <= columns; i++)
		                {
		                    row.add( rs.getObject(i) );
		                    
		                }

		                data.add( row );
		                System.out.println(row);
		            }
		        }
		        catch (SQLException z)
		        {
		            System.out.println( z.getMessage() );
		        }

		        Vector columnNamesVector = new Vector();
		        Vector dataVector = new Vector();

		        for (int i = 0; i < data.size(); i++)
		        {
		            ArrayList subArray = (ArrayList)data.get(i);
		            Vector subVector = new Vector();
		            for (int j = 0; j < subArray.size(); j++)
		            {
		                subVector.add(subArray.get(j));
		            }
		            dataVector.add(subVector);
		        }

		        for (int i = 0; i < columnNames.size(); i++ ){
		            columnNamesVector.add(columnNames.get(i));
		        }

		        //  Create table with database data    
		        
		        TableModel model = new DefaultTableModel(dataVector, columnNamesVector){
		        	public boolean isCellEditable(int row, int column){
		        		return false;
		        	}
		        };
		        JTable table = new JTable(model)
		        {
		            public Class getColumnClass(int column)
		            {
		            	System.out.println(getRowCount());
		                for (int row = 0; row < getRowCount(); row++)
		                {
		                    Object o = getValueAt(row, column);
		              
		                    System.out.println("HELLO!" + o);
		                    if (o != null)
		                    {
		                    	System.out.println(o);
		                        return o.getClass();
		                    }
		                }

		                return Object.class;
		            }
		        };

		        JScrollPane scrollPane = new JScrollPane( table );
		        scrollPane.setVisible(true);
		        listDialog.add( scrollPane );
		        
		        listDialog.setLayout(new BorderLayout());
		        listDialog.add(table.getTableHeader(), BorderLayout.PAGE_START);
		        listDialog.add(table, BorderLayout.CENTER);
		        table.setVisible(true);
		        
		        JPanel buttonPanel = new JPanel();
		        listDialog.add( buttonPanel, BorderLayout.SOUTH );
		        
		   
		        
		        listDialog.setVisible(true);
			}
		});
		
		JButton tally = new JButton("Get tally");
		tally.setHorizontalTextPosition(AbstractButton.CENTER);
		tally.setHorizontalTextPosition(AbstractButton.CENTER);
		tally.setBounds(300, 50, 200, 200);
		tally.setToolTipText("This shows the current standings.");
		tally.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList columnNames = new ArrayList();
		        ArrayList data = new ArrayList();
		        
				JDialog listDialog = new JDialog(myFrame, "List of candidates", true);
				listDialog.getContentPane().setLayout(null);
				listDialog.setSize(1280,720);

				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        String url = "jdbc:mysql://localhost:3306/voting?autoReconnect=true&useSSL=false";
		        String userid = "root";
		        String pw = databasePass;
		        String sql = "SELECT * FROM Candidates ORDER BY voteCount DESC";


		        try (Connection connection = DriverManager.getConnection( url, userid, pw );
		            Statement statement = connection.createStatement();			// TODO: CHANGE THIS
		            ResultSet rs = statement.executeQuery( sql ))
		        {
		            ResultSetMetaData md = rs.getMetaData();
		            int columns = md.getColumnCount();


		            for (int i = 1; i <= columns; i++)
		            {
		                columnNames.add( md.getColumnName(i) );
		            }


		            while (rs.next())
		            {
		                ArrayList row = new ArrayList(columns);

		                for (int i = 1; i <= columns; i++)
		                {
		                    row.add( rs.getObject(i) );
		                    
		                }

		                data.add( row );
		                System.out.println(row);
		            }
		        }
		        catch (SQLException z)
		        {
		            System.out.println( z.getMessage() );
		        }

		        Vector columnNamesVector = new Vector();
		        Vector dataVector = new Vector();

		        for (int i = 0; i < data.size(); i++)
		        {
		            ArrayList subArray = (ArrayList)data.get(i);
		            Vector subVector = new Vector();
		            for (int j = 0; j < subArray.size(); j++)
		            {
		                subVector.add(subArray.get(j));
		            }
		            dataVector.add(subVector);
		        }

		        for (int i = 0; i < columnNames.size(); i++ ){
		            columnNamesVector.add(columnNames.get(i));
		        }

		        //  Create table with database data    
		        
		        TableModel model = new DefaultTableModel(dataVector, columnNamesVector){
		        	public boolean isCellEditable(int row, int column){
		        		return false;
		        	}
		        };
		        JTable table = new JTable(model)
		        {
		            public Class getColumnClass(int column)
		            {
		            	System.out.println(getRowCount());
		                for (int row = 0; row < getRowCount(); row++)
		                {
		                    Object o = getValueAt(row, column);
		              
		                    System.out.println("HELLO!" + o);
		                    if (o != null)
		                    {
		                    	System.out.println(o);
		                        return o.getClass();
		                    }
		                }

		                return Object.class;
		            }
		        };

		        JScrollPane scrollPane = new JScrollPane( table );
		        scrollPane.setVisible(true);
		        listDialog.add( scrollPane );
		        
		        listDialog.setLayout(new BorderLayout());
		        listDialog.add(table.getTableHeader(), BorderLayout.PAGE_START);
		        listDialog.add(table, BorderLayout.CENTER);
		        table.setVisible(true);
		        
		        JPanel buttonPanel = new JPanel();
		        listDialog.add( buttonPanel, BorderLayout.SOUTH );
		        
		   
		        
		        listDialog.setVisible(true);
			}
		});
		
		
		JButton print = new JButton("Print results");
		print.setHorizontalTextPosition(AbstractButton.CENTER);
		print.setHorizontalTextPosition(AbstractButton.CENTER);
		print.setBounds(300, 300, 200, 200);
		print.setToolTipText("This prints the results.");
		print.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList columnNames = new ArrayList();
		        ArrayList data = new ArrayList();
		        
				JDialog listDialog = new JDialog(myFrame, "List of candidates", true);
				listDialog.getContentPane().setLayout(null);
				listDialog.setSize(1280,720);

				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        String url = "jdbc:mysql://localhost:3306/voting?autoReconnect=true&useSSL=false";
		        String userid = "root";
		        String pw = databasePass;
		        String sql = "SELECT * FROM Candidates";


		        try (Connection connection = DriverManager.getConnection( url, userid, pw );
		            Statement statement = connection.createStatement();			// TODO: CHANGE THIS
		            ResultSet rs = statement.executeQuery( sql ))
		        {
		            ResultSetMetaData md = rs.getMetaData();
		            int columns = md.getColumnCount();


		            for (int i = 1; i <= columns; i++)
		            {
		                columnNames.add( md.getColumnName(i) );
		            }


		            while (rs.next())
		            {
		                ArrayList row = new ArrayList(columns);

		                for (int i = 1; i <= columns; i++)
		                {
		                    row.add( rs.getObject(i) );
		                    
		                }

		                data.add( row );
		                System.out.println(row);
		            }
		        }
		        catch (SQLException z)
		        {
		            System.out.println( z.getMessage() );
		        }

		        Vector columnNamesVector = new Vector();
		        Vector dataVector = new Vector();

		        for (int i = 0; i < data.size(); i++)
		        {
		            ArrayList subArray = (ArrayList)data.get(i);
		            Vector subVector = new Vector();
		            for (int j = 0; j < subArray.size(); j++)
		            {
		                subVector.add(subArray.get(j));
		            }
		            dataVector.add(subVector);
		        }

		        for (int i = 0; i < columnNames.size(); i++ ){
		            columnNamesVector.add(columnNames.get(i));
		        }

		        //  Create table with database data    
		        
		        TableModel model = new DefaultTableModel(dataVector, columnNamesVector){
		        	public boolean isCellEditable(int row, int column){
		        		return false;
		        	}
		        };
		        JTable table = new JTable(model)
		        {
		            public Class getColumnClass(int column)
		            {
		            	System.out.println(getRowCount());
		                for (int row = 0; row < getRowCount(); row++)
		                {
		                    Object o = getValueAt(row, column);
		              
		                    System.out.println("HELLO!" + o);
		                    if (o != null)
		                    {
		                    	System.out.println(o);
		                        return o.getClass();
		                    }
		                }

		                return Object.class;
		            }
		        };

		        JScrollPane scrollPane = new JScrollPane( table );
		        scrollPane.setVisible(true);
		        listDialog.add( scrollPane );
		        
		        listDialog.setLayout(new BorderLayout());
		        listDialog.add(table.getTableHeader(), BorderLayout.PAGE_START);
		        listDialog.add(table, BorderLayout.CENTER);
		        table.setVisible(true);
		        
		        JPanel buttonPanel = new JPanel();
		        listDialog.add( buttonPanel, BorderLayout.SOUTH );
		        
		   
		        
		        listDialog.setVisible(true);
			}
		});

		
		JButton candidate = new JButton("Add candidate");
		candidate.setHorizontalTextPosition(AbstractButton.CENTER);
		candidate.setHorizontalTextPosition(AbstractButton.CENTER);
		candidate.setBounds(550, 300, 200, 200);
		candidate.setToolTipText("Brings up menu to add a new candidate.");
		candidate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addCandidate();
			}
		});
		
		dialog.add(regVoter);		
		dialog.add(check);
		dialog.add(tally);
		dialog.add(print);

		dialog.add(candidate);
		dialog.getContentPane().setLayout(null);
		dialog.setVisible(true);
	}
	
	public static void addCandidate(){
		JDialog dialog = new JDialog(myFrame, "Add candidate", true);
		dialog.setSize(1280, 720);
		dialog.setLocationRelativeTo(myFrame);
		dialog.setLayout(null);
		dialog.getContentPane().setLayout(null);
		
		JLabel first = new JLabel("First name: ");
		first.setBounds(10,10, 200,50);
		JLabel last = new JLabel("Last name: ");
		last.setBounds(10,70, 200,50);
		JLabel party = new JLabel("Party Affiliation: ");
		party.setBounds(10,130, 200,50);
		
		JTextField fFirst = new JTextField();
		JTextField fLast = new JTextField();
		JTextField fParty = new JTextField();
		fFirst.setBounds(250, 10, 200, 30);
		fLast.setBounds(250, 70, 200, 30);
		fParty.setBounds(250, 130, 100, 30);
		
		JButton close = new JButton("Close");
		close.setBounds(400, 350, 300, 100);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.dispose();
			}
		});
		
		dialog.add(close);
		
		JButton add = new JButton("Add");
		add.setHorizontalTextPosition(AbstractButton.CENTER);
		add.setVerticalTextPosition(AbstractButton.CENTER);
		add.setBounds(70, 350, 300, 100);
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String firstName = fFirst.getText();
				String lastName = fLast.getText();
				String partyAff = fParty.getText();
				//TODO: ADD CANDIDATE TO DATABASE 
				
				String url = "jdbc:mysql://localhost:3306/voting?autoReconnect=true&useSSL=false";
		        String userid = "root";
		        String pw = databasePass;
		        String sql = "INSERT INTO Candidates(firstName, lastName, partyAffiliation) VALUES( '" + firstName + "','" + lastName + "','" + partyAff + "')"; 


		        try (Connection connection = DriverManager.getConnection( url, userid, pw );
		            Statement statement = connection.createStatement())			// TODO: CHANGE THIS    
		        {
		        	statement.executeUpdate( sql );
		        } 
		        
		        catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		        dialog.dispose();

				JDialog cDialog = new JDialog(myFrame, "Confirmation", true);
				cDialog.getContentPane().setLayout(null);
				cDialog.setSize(500, 400);
				JButton close = new JButton("Close");
				close.setBounds(70, 100, 300, 100);
				close.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						cDialog.dispose();
					}
				});
				JLabel confirmMsg = new JLabel("Candidate successfully added.");
				confirmMsg.setBounds(10, 10, 200, 50);
				cDialog.add(close);
				cDialog.add(confirmMsg);
				cDialog.setVisible(true);
			}
			});		
		
		dialog.add(first);
		dialog.add(last);
		dialog.add(party);
		dialog.add(add);		
		dialog.add(fFirst);
		dialog.add(fLast);
		dialog.add(fParty);
		dialog.setVisible(true);
	}
	public static void registerVoter(){
		JDialog dialog = new JDialog(myFrame, "Register voter", true);
		dialog.setSize(1280, 720);
		dialog.setLocationRelativeTo(myFrame);
		dialog.setLayout(null);
		dialog.getContentPane().setLayout(null);
		
		JLabel first = new JLabel("First name: ");
		first.setBounds(10,10, 200,50);
		JLabel last = new JLabel("Last name: ");
		last.setBounds(10,70, 200,50);
		JLabel password = new JLabel("Social Security(no hyphens): ");
		password.setBounds(10,130, 200,50);
		
		JTextField fFirst = new JTextField();
		JTextField fLast = new JTextField();
		JPasswordField passwordField = new JPasswordField(9);
		fFirst.setBounds(250, 10, 200, 30);
		fLast.setBounds(250, 70, 200, 30);
		passwordField.setBounds(250, 130, 100, 30);
		
		JButton close = new JButton("Close");
		close.setBounds(400, 350, 300, 100);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.dispose();
			}
		});
		
		dialog.add(close);
		
		JButton register = new JButton("Register voter");
		register.setHorizontalTextPosition(AbstractButton.CENTER);
		register.setVerticalTextPosition(AbstractButton.CENTER);
		register.setBounds(70, 350, 300, 100);
		register.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String firstName = fFirst.getText();
				String lastName = fLast.getText();
				//String password = passwordField.getText();
				Boolean registrationStatus = true;
				String password = md5(new String (passwordField.getPassword()));


				String url = "jdbc:mysql://localhost:3306/voting?autoReconnect=true&useSSL=false";
		        String userid = "root";
		        String pw = databasePass;
		        String sql = "INSERT INTO Voter(firstName, lastName, pass, registrationStatus) VALUES( '" + firstName + "','" + lastName + "','" + password + "','" + 1 + "')"; 
		        System.out.println(sql);

		        try (Connection connection = DriverManager.getConnection( url, userid, pw );
		            Statement statement = connection.createStatement())			// TODO: CHANGE THIS    
		        {
		        	statement.executeUpdate( sql );
		        	
		        } 
		        catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				JDialog cDialog = new JDialog(myFrame, "Confirmation", true);
				cDialog.getContentPane().setLayout(null);
				cDialog.setSize(500, 400);
				JButton close = new JButton("Close");
				close.setBounds(70, 100, 300, 100);
				close.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						cDialog.dispose();
					}
				});
				JLabel confirmMsg = new JLabel("Voter successfully added.");
				confirmMsg.setBounds(10, 10, 200, 50);
				cDialog.add(close);
				cDialog.add(confirmMsg);
				cDialog.setVisible(true);
			}
			
			

		});
		
		
		
		dialog.add(first);
		dialog.add(last);
		dialog.add(password);
		dialog.add(register);	
		dialog.add(fFirst);
		dialog.add(fLast);
		dialog.add(passwordField);
		dialog.setVisible(true);
	}
	

	
	public static void voterMenu(){
		

		JDialog dialog = new JDialog(myFrame, "Voter Menu", true);
		dialog.getContentPane().setLayout(null);
		dialog.setSize(1280,720);
		
		JLabel fName = new JLabel("First name: ");
		fName.setBounds(10, 10, 200, 100);
		
		JLabel lName = new JLabel("Last name: ");
		lName.setBounds(10, 70, 200, 100);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setBounds(10, 130, 200, 100);
				
		JTextField fText = new JTextField();
		fText.setBounds(250, 50, 200,30);
		
		JTextField lText = new JTextField();
		lText.setBounds(250, 110, 200,30);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(250, 170,200,30);
		
		JButton close = new JButton("Close");
		close.setBounds(400, 350, 300, 100);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.dispose();
			}
		});
		
		dialog.add(close);
		
		JButton login =new JButton("Login");
		login.setHorizontalTextPosition(AbstractButton.CENTER);
		login.setVerticalTextPosition(AbstractButton.CENTER);
		login.setBounds(50, 350, 300, 100);
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Integer canVote = 0;
				String first = fText.getText();
				String last = lText.getText();				
				//String password = new String(passwordField.getPassword());				
				String password = md5(new String (passwordField.getPassword()));
				
				



				
				fText.setText("");
				lText.setText("");
				passwordField.setText("");
				// TODO: USE DATA TO CHECK IN DATABASE
				
				 	String url = "jdbc:mysql://localhost:3306/voting?autoReconnect=true&useSSL=false";
			        String userid = "root";
			        String pw = databasePass;
			        String sql = "SELECT * FROM voter WHERE firstName = '" + first + "' AND lastName = '" + last + "' AND pass = '" + password + "'";
			        String userpass = "SELECT pass FROM voter WHERE firstName = '" + first + "' AND lastName = '" + last + "' AND pass = '" + password + "'";
	
			        try (Connection connection = DriverManager.getConnection( url, userid, pw );
			            Statement statement = connection.createStatement();			// TODO: CHANGE THIS
			            ResultSet rs = statement.executeQuery( sql ))
			        {
			           if(rs != null){
			        	   rs.next();
			        	   canVote = rs.getInt(5);
			           }
			        }
			        catch (SQLException z)
			        {
			            System.out.println( z.getMessage() );
			        }				
				
				
				if (canVote == 0){
					JDialog dError = new JDialog(myFrame, "Error", true);
					dError.setSize(400, 500);
					
					JLabel msg = new JLabel("Voter has already voted.");
					msg.setBounds(50,50,200,30);			
					
					JButton close = new JButton("Close");
					close.setHorizontalTextPosition(AbstractButton.CENTER);
					close.setVerticalTextPosition(AbstractButton.CENTER);
					close.setBounds(50, 100, 300, 100);
					close.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							dError.dispose();
						}

					});
					
					dError.getContentPane().setLayout(null);
					dError.add(msg);
					dError.add(close);			
					dError.setVisible(true);
					
				}
				else{
			        ArrayList columnNames = new ArrayList();
			        ArrayList data = new ArrayList();
			        
					JDialog listDialog = new JDialog(myFrame, "List of candidates", true);
					listDialog.getContentPane().setLayout(null);
					listDialog.setSize(1280,720);

					try {
						Class.forName("com.mysql.jdbc.Driver");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				

			        

			        sql = "SELECT firstName, lastName, partyAffiliation FROM Candidates";


	
			        try (Connection connection = DriverManager.getConnection( url, userid, pw );
			            Statement statement = connection.createStatement();			
			            ResultSet rs = statement.executeQuery( sql ))
			        {
			            ResultSetMetaData md = rs.getMetaData();
			            int columns = md.getColumnCount();


			            for (int i = 1; i <= columns; i++)
			            {
			                columnNames.add( md.getColumnName(i) );
			            }


			            while (rs.next())
			            {
			                ArrayList row = new ArrayList(columns);

			                for (int i = 1; i <= columns; i++)
			                {
			                    row.add( rs.getObject(i) );
			                    
			                }

			                data.add( row );
			                System.out.println(row);
			            }
			        }
			        catch (SQLException z)
			        {
			            System.out.println( z.getMessage() );
			        }

			        Vector columnNamesVector = new Vector();
			        Vector dataVector = new Vector();

			        for (int i = 0; i < data.size(); i++)
			        {
			            ArrayList subArray = (ArrayList)data.get(i);
			            Vector subVector = new Vector();
			            for (int j = 0; j < subArray.size(); j++)
			            {
			                subVector.add(subArray.get(j));
			            }
			            dataVector.add(subVector);
			        }

			        for (int i = 0; i < columnNames.size(); i++ ){
			            columnNamesVector.add(columnNames.get(i));
			        }

			        //  Create table with database data    
			        TableModel model = new DefaultTableModel(dataVector, columnNamesVector){
			        	public boolean isCellEditable(int row, int column){
			        		return false;
			        	}
			        };
			        JTable table = new JTable(model)
			        
			        {
			            public Class getColumnClass(int column)
			            {
			            	System.out.println(getRowCount());
			                for (int row = 0; row < getRowCount(); row++)
			                {
			                    Object o = getValueAt(row, column);
			              
			                    System.out.println("HELLO!" + o);
			                    if (o != null)
			                    {
			                    	System.out.println(o);
			                        return o.getClass();
			                    }
			                }

			                return Object.class;
			            }
			        };

			      		
			        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	
						public void valueChanged(ListSelectionEvent event) {
							JDialog confirm = new JDialog(myFrame,"Confirmation", true);
							confirm.getContentPane().setLayout(null);
							confirm.setSize(800, 400);
							
							JLabel msg = new JLabel("Are you sure you want to vote for this candidate?");
							msg.setBounds(100, 10, 300,50);
							
							JButton yes = new JButton("Yes I do");
							yes.setBounds(10, 100, 300, 100);
							yes.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e){
									String candidateFirst = table.getValueAt(table.getSelectedRow(), 0).toString();
									String candidateLast = table.getValueAt(table.getSelectedRow(), 1).toString();
									String party = table.getValueAt(table.getSelectedRow(), 2).toString();
									//TODO: CHANGE VOTER REGISTRATION STATUS AND ADD VOTE COUNT TO CANDIDATE
									
									
									
									
									String sql = "UPDATE candidates SET voteCount = voteCount + 1 WHERE firstName = '" + candidateFirst + "' AND lastName = '" 
											+ candidateLast + "' AND partyAffiliation = '" + party + "'";
									 
											try (Connection connection = DriverManager.getConnection( url, userid, pw );
										            Statement statement = connection.createStatement())			// TODO: CHANGE THIS
													
													{
														statement.executeUpdate(sql);
													}
										        catch (SQLException z)
										        {
										            System.out.println( z.getMessage() );
										        }	
										
									
									
									confirm.dispose();
									listDialog.dispose();
									JDialog cDialog = new JDialog(myFrame, "Confirmation", true);
									cDialog.getContentPane().setLayout(null);
									cDialog.setSize(500, 400);
									
									JLabel confirmMsg = new JLabel("Vote successfully casted for " + table.getValueAt(table.getSelectedRow(), 0).toString() + " " + table.getValueAt(table.getSelectedRow(), 1).toString() + " " + table.getValueAt(table.getSelectedRow(), 2).toString());
									String printData = "Vote successfully casted for " + table.getValueAt(table.getSelectedRow(), 0).toString() + " " + table.getValueAt(table.getSelectedRow(), 1).toString() + " " + table.getValueAt(table.getSelectedRow(), 2).toString() + "\f";
									PrintService defaultPrinter = PrintServiceLookup.lookupDefaultPrintService();
									try {
										InputStream is = new ByteArrayInputStream(printData.getBytes("UTF8"));
										PrintRequestAttributeSet  pras = new HashPrintRequestAttributeSet();
									    pras.add(new Copies(1));
									    
										DocFlavor fl = DocFlavor.INPUT_STREAM.AUTOSENSE;
										Doc document = new SimpleDoc(is, fl, null);
										DocPrintJob job = defaultPrinter.createPrintJob();
										
										job.print(document, pras);
									} catch (UnsupportedEncodingException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (PrintException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
								
									
									confirmMsg.setBounds(10,10, 400,50);
									
									
									
									sql = "UPDATE voter SET registrationStatus = 0 WHERE firstName = '" + first + "' AND lastName = '" + last + "' AND pass = '" + password + "'";
									try (Connection connection = DriverManager.getConnection( url, userid, pw );
								            Statement statement = connection.createStatement())			// TODO: CHANGE THIS
											
											{
												statement.executeUpdate(sql);
											}
								        catch (SQLException z)
								        {
								            System.out.println( z.getMessage() );
								        }	
									JButton close = new JButton("Close");
									close.setBounds(70, 100, 300, 100);
									close.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent e){
											cDialog.dispose();
										}
									});
									cDialog.add(close);
									cDialog.add(confirmMsg);
									cDialog.setVisible(true);
								}
							});
							
							JButton no = new JButton("No I do not.");
							no.setBounds(400, 100, 300, 100);
							no.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e){
									confirm.dispose();
								}
							});
							
							confirm.add(yes);
							confirm.add(no);
							confirm.add(msg);
							confirm.setVisible(true);
						}
			        });
			        
			        listDialog.setLayout(new BorderLayout());
			        listDialog.add(table.getTableHeader(), BorderLayout.PAGE_START);
			        listDialog.add(table, BorderLayout.CENTER);
			        table.setVisible(true);
			        
			        JPanel buttonPanel = new JPanel();
			        listDialog.add( buttonPanel, BorderLayout.SOUTH );
			        
			   
			        
			        listDialog.setVisible(true);
			        
			    }
			}
		});
		


		
		dialog.add(fName);
		dialog.add(lName);
		dialog.add(passwordLabel);
		dialog.add(fText);
		dialog.add(lText);
		dialog.add(passwordField);
		dialog.add(login);
		dialog.setVisible(true);
		
	}

    public static String md5(String password) 
    {
        String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
	public static void main(String[] args){
		
		myFrame = new JFrame("Electronic Voting System");
		myFrame.getContentPane().setLayout(null);
		myFrame.setLayout(null);		
		//voterMenu();
		makeGUI();
		

	
	}
	

}