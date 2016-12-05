//package evoting;

import java.awt.*;
import java.awt.event.*;
import java.sql.Statement;

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
/*TODO: ADD STUFF TO ALL BUTTONS Ctrl+F JButtons
 * CHANGE DIMENSIONS EVENTUALLY Ctrl+F setBounds
 * ADD getTallys,printResults to adminMenu() and implement them outside.
 * 
 * Listing tuples in a database from here: http://stackoverflow.com/questions/21898053/display-records-from-mysql-database-using-jtable-in-java
*/
public class GUI {	
	
	static JFrame myFrame;	
	
	
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
		
		JTextField passwordField = new JTextField();
		passwordField.setBounds(250, 170,200,30);
		
		JButton login =new JButton("Login");
		login.setHorizontalTextPosition(AbstractButton.CENTER);
		login.setVerticalTextPosition(AbstractButton.CENTER);
		login.setBounds(50, 350, 300, 100);
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String first = fText.getText();
				String last = lText.getText();
				String password = passwordField.getText();
				//TODO: DO STUFF WITH ADMIN INFO WITH DATABASE
				
				pollWorker worker = new pollWorker();
				if(worker != null){  			// CHECK IF WORKER IS IN DATABASE CHANGE THIS
					adminMenu();
				}
				else{
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
		        String pw = "password";
		        String sql = "SELECT * FROM Voter";


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
		        JTable table = new JTable(dataVector, columnNamesVector)
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

//		        JScrollPane scrollPane = new JScrollPane( table );
//		        listDialog.add( scrollPane );
		        
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
		
		
		JButton print = new JButton("Print results");
		print.setHorizontalTextPosition(AbstractButton.CENTER);
		print.setHorizontalTextPosition(AbstractButton.CENTER);
		print.setBounds(300, 300, 200, 200);
		print.setToolTipText("This prints the results.");
		
		JButton ballot = new JButton("New ballot");
		ballot.setHorizontalTextPosition(AbstractButton.CENTER);
		ballot.setHorizontalTextPosition(AbstractButton.CENTER);
		ballot.setBounds(550, 50, 200, 200);
		ballot.setToolTipText("This brings up menu to create new ballot.");
		
		JButton candidate = new JButton("Add candidate");
		candidate.setHorizontalTextPosition(AbstractButton.CENTER);
		candidate.setHorizontalTextPosition(AbstractButton.CENTER);
		candidate.setBounds(550, 300, 200, 200);
		candidate.setToolTipText("Brings up menu to add a new candidate.");
		
		dialog.add(regVoter);		
		dialog.add(check);
		dialog.add(tally);
		dialog.add(print);
		dialog.add(ballot);
		dialog.add(candidate);
		dialog.getContentPane().setLayout(null);
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
		JTextField fPassword = new JTextField(9);
		fFirst.setBounds(250, 10, 200, 30);
		fLast.setBounds(250, 70, 200, 30);
		fPassword.setBounds(250, 130, 100, 30);
		
		JButton register = new JButton("Register voter");
		register.setHorizontalTextPosition(AbstractButton.CENTER);
		register.setVerticalTextPosition(AbstractButton.CENTER);
		register.setBounds(490, 500, 300, 100);
		
		
		
		dialog.add(first);
		dialog.add(last);
		dialog.add(password);
		dialog.add(register);	
		dialog.add(fFirst);
		dialog.add(fLast);
		dialog.add(fPassword);
		dialog.setVisible(true);
	}
	

	
	public static void voterMenu(){
		
		Voter voter = new Voter(); 		// This is just a voter object to code. It doesn't do anything.
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
		
		JTextField passwordField = new JTextField();
		passwordField.setBounds(250, 170,200,30);
		
		JButton login =new JButton("Login");
		login.setHorizontalTextPosition(AbstractButton.CENTER);
		login.setVerticalTextPosition(AbstractButton.CENTER);
		login.setBounds(50, 350, 300, 100);
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Boolean canVote = false;
				String first = fText.getText();
				String last = lText.getText();
				String password = passwordField.getText();
				// TODO: USE DATA TO CHECK IN DATABASE
				
				 	String url = "jdbc:mysql://localhost:3306/voting?autoReconnect=true&useSSL=false";
			        String userid = "root";
			        String pw = "helex12";
			        String sql = "SELECT * FROM voter WHERE firstName = '" + first + "' AND lastName = '" + last + "' AND pass = '" + password + "'";

	
			        try (Connection connection = DriverManager.getConnection( url, userid, pw );
			            Statement statement = connection.createStatement();			// TODO: CHANGE THIS
			            ResultSet rs = statement.executeQuery( sql ))
			        {
			           if(rs != null){
			        	   rs.next();
			        	   canVote = rs.getBoolean(5);
			           }
			        }
			        catch (SQLException z)
			        {
			            System.out.println( z.getMessage() );
			        }				
				
				
				if (canVote == false){
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

			        

			        String userid = "root";
			        String pw = "password";
			        String sql = "SELECT firstName, lastName, partyAffiliation FROM Candidates";


	
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

						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							JDialog confirm = new JDialog(myFrame,"Confirmation", true);
							
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

	public static void main(String[] args){
		myFrame = new JFrame("Electronic Voting System");
		myFrame.getContentPane().setLayout(null);
		myFrame.setLayout(null);		
		//voterMenu();
		makeGUI();
		
	
	}
	

}