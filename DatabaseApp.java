// The "FrameTest" class.
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Allows the user to create, open, and edit an address book.
 * 
 * @author Joshua Yuan
 * @author Ms. Dyke (contributed to the getFile method)
 * @version 1.0 March 4, 2014
 */
public class DatabaseApp extends JFrame implements ActionListener
{
  /** AddressBook reference: The address book is a panel that is added to this frame. */
  AddressBook a;
  /** File reference: Contains the file the user wishes to open or save. */
  File file;
  /** Stores the file that the user is attempting to open or save. If the file is good, then the value of tempFile is given to 'file'. Otherwise, 'file' is unchanged. */
  File tempFile;
  /** int: Stores the number returned by the showOpenDialog and showSaveDialog method. */ 
  int chooserChoice;
  /** int: Stores the number returned by the dialog that asks the user if they wish to overwrite. */
  int overrideChoice;
  /** int: Stores the number returned by the dialog that asks the user if they wish to try again, after they enter a file that is not found. */
  int notFoundChoice;
  
  /**
   * Constructor sets up the menu bar. It adds an AddressBook to the frame, allowing the user to enter data into an address book, open an address book, or modify an existing one.
   * 
   * @param saveItem JMenuItem reference: Menu item lets user save the address book into the file.
   * @param saveAsItem JMenuItem reference: Menu item lets user save the address book into a new text file.
   * @param openItem JMenuItem reference: Menu item lets user open an address book from a file.
   * @param newBookItem JMenuItem reference: Menu item lets user create a new address book.
   * @param quitItem JMenuItem reference: Menu item lets user quit the program.
   * @param fileMenu JMenu reference: Menu allows user to select various menu items.
   * @param myMenus JMenuBar reference: Contains the file menu.
   */
  public DatabaseApp ()
  {
    super ("Address Book");
    
    a = new AddressBook ();
    add (a);
    
    JMenuItem saveAsItem = new JMenuItem ("Save As");
    JMenuItem saveItem = new JMenuItem ("Save");
    JMenuItem openItem = new JMenuItem ("Open");
    JMenuItem newBookItem = new JMenuItem ("New");
    JMenuItem quitItem = new JMenuItem ("Quit");
    
    JMenu fileMenu = new JMenu ("File");
    
    fileMenu.add (newBookItem);
    fileMenu.add (openItem);
    fileMenu.add (saveItem);
    fileMenu.add (saveAsItem);
    fileMenu.add (quitItem);
    
    JMenuBar myMenus = new JMenuBar ();
    myMenus.add (fileMenu); //adds menus to the menu bar
    setJMenuBar (myMenus);
    
    quitItem.addActionListener (this);
    saveItem.addActionListener (this);
    saveAsItem.addActionListener (this);
    openItem.addActionListener (this);
    newBookItem.addActionListener (this);
    
    setSize (650, 400);
    setVisible (true);    
    setDefaultCloseOperation (DISPOSE_ON_CLOSE); //destroys object when window is closed
  }
  
  /**
   * Opens a dialog that allows the user to select a file. The file is then stored in the 'file' instance variable. 
   * The ternary operator is used to decide whether a save or open dialog should pop up.
   * The first if structure exits the method if the user does not select a file.
   * The second if structure determines whether or not an extension should be added to the file name, if the user did not enter one.
   * The third if structure checks if the user is trying to save.
   * The if structure within the third if structure checks for problems with the file name that has been entered.
   * The if structure nested in the if structure last described determines which error message should be displayed.
   * The if structure that begins with if (!tempFile.equals (file)) checks if an overwrite warning is needed.
   * The if structure within the one last described determines the appropriate action based upon the user's response to the overwrite dialog.
   * The last if structure checks if the user is trying to open a file that does not exists.
   * 
   * @param command String reference: Determines whether a save or an open dialog should pop up.
   * @param fileChooser JFileChooser reference: Used to allow the user to select a file.
   */ 
  private void getFile (String command){ 
    JFileChooser fileChooser = new JFileChooser (".\\");
    
    fileChooser.setFileFilter (new FileNameExtensionFilter ("Address Book Files (*.ab)", "ab"));
    fileChooser.setAcceptAllFileFilterUsed (false);
    
    chooserChoice = command.equals ("save") ? fileChooser.showSaveDialog (this) : fileChooser.showOpenDialog (this);
    
    if (chooserChoice != JFileChooser.APPROVE_OPTION)
      return;
    
    tempFile = fileChooser.getSelectedFile ();
    
    if (!tempFile.getName ().endsWith (".ab"))
      tempFile = new File (tempFile.getParentFile (), tempFile.getName () + ".ab");
    
    if (command.equals ("save")) {//In Windows, the max file length includes the file path and drive name.
      if (tempFile.getPath ().length () > 255 || (tempFile.getName ().contains ('"'+"") || tempFile.getName ().contains (":") || tempFile.getName ().contains ("<") || tempFile.getName ().contains (">") || tempFile.getName ().contains ("|"))){ //JFileChooser does not allow users to enter the other illegal characters that are not checked here
        if (tempFile.getPath ().length () > 255)
          JOptionPane.showMessageDialog (this, "The file name you entered is too long.\nPlease try again.", "ERROR", JOptionPane.WARNING_MESSAGE);
        else
          JOptionPane.showMessageDialog (this, "The file name you entered contains an invalid character.\nPlease try again.", "ERROR", JOptionPane.WARNING_MESSAGE);
        getFile ("save");
      }
      else if (!tempFile.exists ())
        file = tempFile;                                                                                                                                                   //if everything ok
      else {
        if (!tempFile.equals (file)){                                                                                                        //trying to overwrite to a file that is not the current file
          overrideChoice = JOptionPane.showConfirmDialog (this, "Are you sure you want to overwrite the existing file?", "Overwrite File?", JOptionPane.YES_NO_CANCEL_OPTION);
          if (overrideChoice == JOptionPane.YES_OPTION)
            file = tempFile;                                                                                                                                              //user wants to overwrite
          else {
            if (overrideChoice == JOptionPane.NO_OPTION)
              getFile ("save");                                                                                                                                           //let user pick another file
          }
        }
      }
    }
    else {
      if (!tempFile.exists () && (notFoundChoice = JOptionPane.showConfirmDialog (this, "File " + tempFile.getName () + " was not found.\nWould you like to try again?", "ERROR", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION)
        getFile ("open");                                                                                                                                              //let user open another file
    }    
  }
  
  /**
   * Allows the user to save the current address book. The first if structure checks if the user wants to save to a new file.
   * The first if structure exits the method if the user has not made any changes, and does not wish to change the file name.
   * The second if structure checks if the user wishes to specify a file name.
   * The if structure within the second outer if structure checks if the user is attempting to save an empty address book.
   * The next if structure within the second outer if structure exits the method if the user cancels the overwrite dialog.
   * The third outer if structure checks if the program can precede to writing to a file.
   * The try-catch is used to catch an IOException.
   * The for loop is used to iterate through all of the records when writing information to the file. Start: 0 Stop: PersonRecord.numRecords
   * 
   * @param askFile True if the user clicks Save As. False if the user clicks Save.
   * @param output PrintWriter reference: Used to write information to a text field.
   * @param i int: Used to loop through all of the records when writing information to the file.
   * @param e IOException reference: Used in the event of an IOException error.
   * @throws IOException if an error occurs while writing to the file.
   */ 
  public void save (boolean askFile){  
    if (!askFile && a.saved == true)
      return;
    
    if (askFile){ //not else if because it is after a return statement
      if (PersonRecord.numRecords == 0){
        JOptionPane.showMessageDialog (this, "There are no records to save!", "ERROR", JOptionPane.ERROR_MESSAGE);
        return;
      }      
      getFile ("save");
      if (overrideChoice == JOptionPane.CANCEL_OPTION || overrideChoice == JOptionPane.CLOSED_OPTION){
        overrideChoice = JOptionPane.NO_OPTION;
        return;
      }
    }
    
    if (!askFile || askFile && chooserChoice == JFileChooser.APPROVE_OPTION && file != null){ 
      try {
        PrintWriter output = new PrintWriter (new FileWriter (file));
        output.println ("Address Book File Verification Header.");
        for (int i = 0 ; i < PersonRecord.numRecords ; i++){
          output.println (a.person [i].getFirst ());
          output.println (a.person [i].getLast ());
          output.println (a.person [i].getPhone ());
          output.println (a.person [i].getEmail ());
          output.println ();
        }
        output.close ();
      }
      catch (IOException e){
      }
      a.saved = true;
    }
  }
  
  
  /**
   * Allows the user to open a file made by this program. The file that the user is trying to open must have the extension '.ab'.
   * The first if structure exits the method if the entered a file that is not found, and never tried again.
   * The second outer if structure checks if the user has not selected a file. The if structure in the try-catch checks if the file selected has a valid file header.
   * The for loop is used to continually read the next line in the file until there are no more records stored in the file. Start: 0 Stop: line in file is null
   * The try-catch is used to catch IOException.
   * 
   * @param badFileChoice int: Stores the number returned by the dialog that asks the user if they want to try again, after opening a bad file.
   * @param input Reference to BufferedReader object: Used to read from the file.
   * @param line Reference to String object: Used to store the info that is read from the file.
   * @param i int: Used to loop through all the information to be stored in the array 'person'.
   * @param e FileNotFoundException and IOException reference: Used in the event that the file is not found, or another error occurs during the input operation.
   * @throws IOException if another error occurs while trying to access or read a file.
   */ 
  public void open (){
    String line;
    
    getFile ("open");
    
    if (notFoundChoice != JOptionPane.YES_OPTION){
      notFoundChoice = JOptionPane.YES_OPTION;
      return;
    }
    if (chooserChoice != JFileChooser.APPROVE_OPTION)
      return;
    
    try {
      int badFileChoice = JOptionPane.YES_OPTION;
      BufferedReader input = new BufferedReader (new FileReader (tempFile));
      if (((line = input.readLine ()) == null || !line.equals ("Address Book File Verification Header.")) && (badFileChoice = JOptionPane.showConfirmDialog (this, "The file you selected was not created by this program.\nWould you like to select another file?", "ERROR", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION){
        open ();
      }
      else if (badFileChoice != JOptionPane.YES_OPTION)
        return;
      else {
        file = tempFile;
        newBook ();
        
        for (int i = 0 ; (line = input.readLine ()) != null ; i++){
          a.person [i] = new PersonRecord (line, input.readLine (), input.readLine (), input.readLine ());
          input.readLine ();
        }
        
        a.numRecordsLabel.setText ("Number of Records: " + PersonRecord.numRecords);         
        a.setTextFields (false);
      }
      input.close ();
    }
    catch (IOException e){ 
    }
  }
  
  /**
   * Displays a new address book to the user.
   */ 
  public void newBook (){
    a.person = new PersonRecord [AddressBook.MAX_RECORD];
    AddressBook.currentRecord = 0;
    PersonRecord.numRecords = 0;
    a.currentRecordLabel.setText ("Current Record Number: " + (AddressBook.currentRecord + 1));
    a.numRecordsLabel.setText ("Number of Records: " + PersonRecord.numRecords);
    a.setTextFields (true);
    a.saved = true;
  }
  
  /**
   * Determines which action to perform when a menu item is clicked. The outer if structure is used to determine if the user has clicked "Save As", "Save", or something else. 
   * If they have not pressed "Save as" or "Save", the program first checks if the user has saved the current address book (if it was modified).
   * The first inner if structure prompts the user to save if the user has made unsaved changes and wishes to save the current address book.
   * The second inner if structure verifies that the user did not cancel the save dialog, or did not wish to save.
   * The if structure within the one above decides if the program should quit, display a new address book, or open the open dialog, depending on which button the user pressed.
   * 
   * @param ae ActionEvent reference: Used in method to access the String which identifies the action command.
   * @param saveChoice int: Stores 1 if the user clicks "Yes", 2 if the user clicks "No", and 3 if the user clicks "Cancel".
   */ 
  public void actionPerformed (ActionEvent ae){   
    if (ae.getActionCommand ().equals ("Save As"))
      save (true);
    else if (ae.getActionCommand ().equals ("Save"))
      save (file == null);
    else { //check if user has saved before doing anything else
      int saveChoice = JOptionPane.NO_OPTION;
      
      if (!a.saved && (saveChoice = JOptionPane.showConfirmDialog (this, "Would you like to save your file?", "Save File", JOptionPane.YES_NO_CANCEL_OPTION)) == JOptionPane.YES_OPTION)
        save (file == null); //if the user has not saved and wants to save, it redirects to save or save as as needed.
      
      if (saveChoice == JOptionPane.NO_OPTION || saveChoice == JOptionPane.YES_OPTION && file != null){ //if user wishes to save and does not cancel the save dialog, or if the user does not wish to save, perform the original task
        if (ae.getActionCommand ().equals ("Quit"))
          System.exit (0);
        else if (ae.getActionCommand ().equals ("New")){
          newBook ();
          file = null;
        }
        else
          open ();
      }
    }
  }
  
  /**
   * Instantiates the DatabaseApp class.
   * 
   * @param args Array of references to String objects: used for main method execution.
   */ 
  public static void main (String[] args){
    new DatabaseApp ();       // Create a FrameTest frame
  } // main method
} // FrameTest class by Josh
