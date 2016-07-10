import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Allows the user to view, modify, and add records in an address book.
 *
 * @author Joshua Yuan
 * @version 1.0 March 4, 2014
 */ 
class AddressBook extends JPanel implements ActionListener
{
  /** boolean: Stores true if the address book that is currently being displayed has been saved. Otherwise, it stores false. */
  boolean saved = true;
  /** static final int: Stores the maximum number of records allowed in one address book: 50. */
  static final int MAX_RECORD = 50;
  /** static int: Stores the record number currently being displayed. */
  static int currentRecord = 0;
  
  /** Array of PersonRecord references: Stores all the records in this address book. */
  PersonRecord [] person = new PersonRecord [MAX_RECORD];
  
  /** SpringLayout reference: Controls the layout of the JPanel. */
  SpringLayout layout = new SpringLayout ();
  
  /** JTextField reference: Allows the user to input a person's first name. */
  JTextField firstNameField = new JTextField ();
  /** JTextField reference: Allows the user to input a person's last name. */
  JTextField lastNameField = new JTextField ();
  /** JTextField reference: Allows the user to input a person's phone number. */
  JTextField phoneField = new JTextField ();
  /** JTextField reference: Allows the user to input a person's email address. */
  JTextField emailField = new JTextField ();
  
  /** JLabel reference: Displays the record number that is currently being displayed. */
  JLabel currentRecordLabel = new JLabel ("Current Record Number: 1"); //currentRecord variable not used so that label resets to 1 after File - New
  /** JLabel reference: Displays the total number of records in the address book currently being displayed. */
  JLabel numRecordsLabel = new JLabel ("Number of Records: " + PersonRecord.numRecords);
  
  /**
   * Constructor sets the layout of the panel, and runs the labelsAndFields and buttons method.
   */ 
  public AddressBook (){
    setLayout (layout);
    labelsAndFields ();
    buttons ();
  }
  
  /**
   * Displays the four text fields, their corresponding labels, and the two labels below the text fields. The first block of code (59 to 86) adds the four labels that are located beside the text fields.
   * The second block of code (89 to 116) adds the four text fields, and the two labels below those text fields.
   * 
   * @param TEXT_FONT final Font reference: Contains the font used for the field labels.
   * @param firstNameLabel JLabel reference: Contains the label "Enter first name:" located left of the first text field.
   * @param lastNameLabel JLabel reference: Contains the label "Enter last name:" located left of the second text field.
   * @param phoneLabel JLabel reference: Contains the label "Enter phone number:" located left of the third text field.
   * @param emailLabel JLabel reference: Contains the label "Enter e-mail address:" located left of the fourth text field.
   */ 
  public void labelsAndFields (){ //Labels and fields have been combined into one method to prevent excess instance variables. The edges of labels and fields are set relative to each other using the putConstraints method.
    final Font TEXT_FONT = new Font ("Century Gothic", Font.PLAIN, 14);
    
    JLabel firstNameLabel = new JLabel ("Enter first name:");
    JLabel lastNameLabel = new JLabel ("Enter last name:");
    JLabel phoneLabel = new JLabel ("Enter phone number:");
    JLabel emailLabel = new JLabel ("Enter e-mail address:");
    
    firstNameLabel.setFont (TEXT_FONT);
    lastNameLabel.setFont (TEXT_FONT);
    phoneLabel.setFont (TEXT_FONT);
    emailLabel.setFont (TEXT_FONT);
    
    layout.putConstraint (SpringLayout.NORTH, firstNameLabel, 100, SpringLayout.NORTH, this);
    layout.putConstraint (SpringLayout.WEST, firstNameLabel, 10, SpringLayout.WEST, this);
    
    layout.putConstraint (SpringLayout.NORTH, lastNameLabel, 10, SpringLayout.SOUTH, firstNameLabel);
    layout.putConstraint (SpringLayout.WEST, lastNameLabel, 10, SpringLayout.WEST, this);
    
    layout.putConstraint (SpringLayout.NORTH, phoneLabel, 10, SpringLayout.SOUTH, lastNameLabel);
    layout.putConstraint (SpringLayout.WEST, phoneLabel, 10, SpringLayout.WEST, this);
    
    layout.putConstraint (SpringLayout.NORTH, emailLabel, 10, SpringLayout.SOUTH, phoneLabel);
    layout.putConstraint (SpringLayout.WEST, emailLabel, 10, SpringLayout.WEST, this);
    
    add (firstNameLabel);
    add (lastNameLabel);
    add (phoneLabel);
    add (emailLabel);
    //End of labels. Begin text fields.
    layout.putConstraint (SpringLayout.NORTH, firstNameField, 0, SpringLayout.NORTH, firstNameLabel);
    layout.putConstraint (SpringLayout.WEST, firstNameField, 10, SpringLayout.EAST, firstNameLabel);
    layout.putConstraint (SpringLayout.EAST, firstNameField, -10, SpringLayout.EAST, this);
    
    layout.putConstraint (SpringLayout.NORTH, lastNameField, 0, SpringLayout.NORTH, lastNameLabel);
    layout.putConstraint (SpringLayout.WEST, lastNameField, 10, SpringLayout.EAST, lastNameLabel);
    layout.putConstraint (SpringLayout.EAST, lastNameField, -10, SpringLayout.EAST, this);
    
    layout.putConstraint (SpringLayout.NORTH, phoneField, 0, SpringLayout.NORTH, phoneLabel);
    layout.putConstraint (SpringLayout.WEST, phoneField, 10, SpringLayout.EAST, phoneLabel);
    layout.putConstraint (SpringLayout.EAST, phoneField, -10, SpringLayout.EAST, this);
    
    layout.putConstraint (SpringLayout.NORTH, emailField, 0, SpringLayout.NORTH, emailLabel);
    layout.putConstraint (SpringLayout.WEST, emailField, 10, SpringLayout.EAST, emailLabel);
    layout.putConstraint (SpringLayout.EAST, emailField, -10, SpringLayout.EAST, this);
    
    layout.putConstraint (SpringLayout.NORTH, currentRecordLabel, 10, SpringLayout.SOUTH, emailLabel);
    layout.putConstraint (SpringLayout.WEST, currentRecordLabel, 10, SpringLayout.WEST, this);
    
    layout.putConstraint (SpringLayout.NORTH, numRecordsLabel, 10, SpringLayout.SOUTH, currentRecordLabel);
    layout.putConstraint (SpringLayout.WEST, numRecordsLabel, 10, SpringLayout.WEST, this);
    
    add (firstNameField);
    add (lastNameField);
    add (phoneField);
    add (emailField);
    add (currentRecordLabel);
    add (numRecordsLabel);
  }
  
  /**
   * Displays the four buttons on the panel, and adds an action listener to each button.
   * 
   * @param previous JButton reference: Contains the button "Previous Record". When pressed, the previous record is displayed.
   * @param next JButton reference: Contains the button "Next Record". When pressed, the next record is displayed.
   * @param update JButton reference: Contains the button "Save or Update Recod". When pressed, it updates a record or saves a new record.
   * @param addRecord JButton reference: Contains the button "Add Record". When pressed, it allows the user to create a new record in the address book.
   */ 
  public void buttons (){
    JButton previous = new JButton ("Previous Record");
    JButton next = new JButton ("Next Record");
    JButton update = new JButton ("Save or Update Record");
    JButton addRecord = new JButton ("Add Record");
    
    layout.putConstraint (SpringLayout.WEST, previous, 10, SpringLayout.WEST, this);
    layout.putConstraint (SpringLayout.SOUTH, previous, -10, SpringLayout.SOUTH, this);
    
    layout.putConstraint (SpringLayout.EAST, next, -10, SpringLayout.EAST, this);
    layout.putConstraint (SpringLayout.SOUTH, next, -10, SpringLayout.SOUTH, this);
    
    layout.putConstraint (SpringLayout.EAST, update, -10, SpringLayout.WEST, next);
    layout.putConstraint (SpringLayout.SOUTH, update, -10, SpringLayout.SOUTH, this);
    layout.putConstraint (SpringLayout.WEST, update, 5, SpringLayout.HORIZONTAL_CENTER, this);
    
    layout.putConstraint (SpringLayout.WEST, addRecord, 10, SpringLayout.EAST, previous);
    layout.putConstraint (SpringLayout.SOUTH, addRecord, -10, SpringLayout.SOUTH, this);
    layout.putConstraint (SpringLayout.EAST, addRecord, -5, SpringLayout.HORIZONTAL_CENTER, this);
    
    add (previous);
    add (next);
    add (update);
    add (addRecord);
    
    previous.addActionListener (this);
    next.addActionListener (this);
    update.addActionListener (this);
    addRecord.addActionListener (this);
  }
  
  /**
   * Sets the text fields to display the info of the person that is passed into the method through parameter passing. This method has public access for use in DatabaseApp.
   * The if structure checks if person is null or if the fields should be cleared. If so, it clears the text fields.
   * 
   * @param clearFields boolean: True if the text fields should be cleared.
   */ 
  public void setTextFields (boolean clearFields){
    if (clearFields || person [currentRecord] == null){
      firstNameField.setText (null);
      lastNameField.setText (null);
      phoneField.setText (null);
      emailField.setText (null);
    }
    else {
      firstNameField.setText (person [currentRecord].getFirst ());
      lastNameField.setText (person [currentRecord].getLast ());
      phoneField.setText (person [currentRecord].getPhone ());
      emailField.setText (person [currentRecord].getEmail ());
    }
  }
  
  
  /**
   * Erases the text in the field specified by 'field', and moves the cursor to the specified text field.
   * 
   * @param field JTextField reference: Contains the JTextField to be erased and focused.
   */ 
  private void eraseAndFocus (JTextField field){
    field.setText ("");
    field.requestFocus ();
  }
  
  /**
   * Saves the new record or updates the existing record.
   * The if structure checks for invalid input in the following order: no information entered, invalid phone number, and invalid email.
   * The inner if structure determines whether to save a new PersonRecord, or update the current record if it has already been created.
   */ 
  public void saveOrUpdateRecord (){
    if (firstNameField.getText ().equals ("") && lastNameField.getText ().equals ("") && phoneField.getText ().equals ("") && emailField.getText().equals (""))
      JOptionPane.showMessageDialog (this, "There is no information to save!", "ERROR", JOptionPane.ERROR_MESSAGE);
    else if (!phoneField.getText ().equals ("") && !DataCheck.checkPhone (phoneField.getText ())){
      JOptionPane.showMessageDialog (this, "The phone number must be entered as 10 digits - with spaces, dashes, or no punctuation.", "ERROR", JOptionPane.ERROR_MESSAGE);
      eraseAndFocus (phoneField);
    }
    else if (!emailField.getText ().equals ("") && !DataCheck.checkEmail (emailField.getText ())){
      JOptionPane.showMessageDialog (this, "Please enter a valid email address.", "ERROR", JOptionPane.ERROR_MESSAGE);
      eraseAndFocus (emailField);
    }
    else { //at this point, the info entered is valid
      if (person [currentRecord] == null){
        person [currentRecord] = new PersonRecord (firstNameField.getText (), lastNameField.getText (), phoneField.getText (), emailField.getText()); //save a new record
        numRecordsLabel.setText ("Number of Records: " + PersonRecord.numRecords);
      }
      else {
        person [currentRecord].setFirst (firstNameField.getText ()); //update current record
        person [currentRecord].setLast (lastNameField.getText ());
        person [currentRecord].setPhone (phoneField.getText ());
        person [currentRecord].setEmail (emailField.getText ());
      }
      setTextFields (false);
      saved = false; //the address book has been updated so the file should be saved
    }
  }
  
  /**
   * Displays the next or previous record in the address book.
   * The if structure checks if the user wishes to view the next or previous record.
   * The first ternary operator redirects the user to the first record if they click "Next Record" when they are on the last record.
   * The second ternary operator redirects the user to the last record if they click "Previous Record" when they are on the first record.
   * The nested ternary operator prevents a run-time error by preventing currentRecord from having a value of -1.
   * 
   * @param isNext boolean: True if the user wishes to view the next record. False if the user wishes to view the previous record.
   */ 
  public void traverseAddressBook (boolean isNext){
    if (isNext)
      currentRecord = currentRecord >= PersonRecord.numRecords - 1 ? 0 : currentRecord + 1;
    else
      currentRecord = currentRecord == 0 ? PersonRecord.numRecords == 0 ? 0 : PersonRecord.numRecords - 1 : currentRecord - 1;
    
    setTextFields (false);
    currentRecordLabel.setText ("Current Record Number: " + (currentRecord + 1));
  }
  
  /**
   * Displays a blank record to the user.
   * The if structure ensures that the user has not exceeded the maximum number of records.
   */ 
  public void addRecord (){
    if (PersonRecord.numRecords < MAX_RECORD){
      currentRecord = PersonRecord.numRecords;
      setTextFields (true);
      currentRecordLabel.setText ("Current Record Number: " + (currentRecord + 1));
    }
    else
      JOptionPane.showMessageDialog (this, "You cannot store more than " + MAX_RECORD + " records in an address book.", "ERROR", JOptionPane.ERROR_MESSAGE);
  }
  
  /**
   * Determines the method to be invoked when a button is pressed.
   * The if structure determines which button the user pressed.
   * 
   * @param ae ActionEvent reference: Stores information about the action event, which is used to determine which action to perform.
   */ 
  public void actionPerformed (ActionEvent ae){
    if (ae.getActionCommand ().equals ("Save or Update Record"))
      saveOrUpdateRecord ();
    else if (ae.getActionCommand ().equals ("Previous Record"))
      traverseAddressBook (false);
    else if (ae.getActionCommand ().equals ("Next Record"))
      traverseAddressBook (true);
    else
      addRecord ();
  }
}