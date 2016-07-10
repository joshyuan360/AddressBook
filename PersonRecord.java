/**
 * Allows the user to create and modify a person's record.
 * 
 * @author Ms. Dyke, Modified by Joshua Yuan
 * @version 1.0 March 4, 2014
 */ 
public class PersonRecord
{
  /** String reference: Contains the first name of this person. */
  private String first;
  /** String reference: Contains the last name of this person. */
  private String last;
  /** String reference: Contains the email address of this person. */
  private String email;
  /** String reference: Contains the phone number of this person. */
  private String phone;
  /** static int: Stores the number of person records created in the address book. */
  static int numRecords = 0;
  
  /** 
   * Creates a PersonRecord object with a first name, last name, phone number, and email. 
   * 
   * @param first String reference: Contains the person's first name.
   * @param last String reference: Contains the person's last name.
   * @param phone String reference: Contains the person's phone number.
   * @param email String reference: Contains the person's email address.
   */
  public PersonRecord (String first, String last, String phone, String email){
    this.first = formatName (first);
    this.last = formatName (last);
    this.email = email;
    this.phone = formatPhone (phone);
    numRecords++;
  }
  
  /** Empty constructor creates a PersonRecord object without assigning values to any instance variables. */
  public PersonRecord (){
    numRecords++;
  }
  
  /** 
   * Creates a PersonRecord object with a first name, last name, phone number, and email. 
   * 
   * @param first String reference: Contains the person's first name.
   */
  public PersonRecord (String first) {
    this.first = formatName (first);
    numRecords++;
  }
  
  /** 
   * Creates a PersonRecord object with a first name, last name, phone number, and email. 
   * 
   * @param first String reference: Contains the person's first name.
   * @param last String reference: Contains the person's last name.
   */
  public PersonRecord (String first, String last){
    this.first = formatName (first);
    this.last = formatName (last);
    numRecords++;
  }
  
  /** 
   * Creates a PersonRecord object with a first name, last name, and email. 
   * 
   * @param first String reference: Contains the person's first name.
   * @param last String reference: Contains the person's last name.
   * @param email String reference: Contains the person's email address.
   */
  public PersonRecord (String first, String last, String email){
    this.first = formatName (first);
    this.last = formatName (last);
    this.email = email;
    numRecords++;
  }
  
  /** 
   * Mutator method: sets this person's first name. The string is formatted before it is stored. 
   * 
   * @param newName String reference: Contains the new first name to be formatted and stored.
   */
  public void setFirst (String newFirstName){
    first = formatName (newFirstName);
  }
  
  /**
   * Mutator method: sets this person's last name. The string is formatted before it is stored.
   * 
   * @param newLastName String reference: Contains the new last name to be formatted and stored.
   */ 
  public void setLast (String newLastName){
    last = formatName (newLastName);
  }
  
  /**
   * Mutator method: sets this person's email.
   * 
   * @param newEmail String reference: Contains the new email to be stored.
   */ 
  public void setEmail (String newEmail){
    email = newEmail;
  }
  
  /**
   * Mutator method: sets this person's phone number. The string is formatted before it is stored.
   * 
   * @param newPhone String reference: Contains the new phone number to be formatted and stored.
   */ 
  public void setPhone (String newPhone){
    phone = formatPhone (newPhone);
  }
  
  /** 
   * Accessor method: returns this person's first name. 
   * 
   * @return String: Returns this person's first name.
   */
  public String getFirst (){
    return first;
  }
  
  /** 
   * Accessor method: returns this person's last name. 
   * 
   * @return String: Returns this person's last name.
   */
  public String getLast (){
    return last;
  }
  
  /** 
   * Accessor method: returns this person's email address.
   * 
   * @return String: Returns this person's email address.
   */
  public String getEmail (){
    return email;
  }
  
  /** 
   * Accessor method: returns this person's phone number. 
   * 
   * @return String: Returns this person's phone number.
   */
  public String getPhone (){
    return phone;
  }
  
  /**
   * Formats and returns the phone number entered by the user.
   * The first if structure checks if the 10 is the length of the string. The second if structure checks if 12 is the length of the string.
   * 
   * @param phoneToFormat String reference: Contains the phone number to be formatted.
   * @return String: Returns a string containing a phone number in the format xxx-xxx-xxxx, where x represents a digit.
   */ 
  private String formatPhone (String phoneToFormat){ //precondition: DataCheck ensures only the following formats are sent in: 000-000-0000 or 0000000000 or 000 000 0000.
    if (phoneToFormat.length () == 10)
      return phoneToFormat.substring (0, 3) + "-" + phoneToFormat.substring (3, 6) + "-" + phoneToFormat.substring (6);
    if (phoneToFormat.length () == 12)
      return phoneToFormat.substring (0, 3) + "-" + phoneToFormat.substring (4, 7) + "-" + phoneToFormat.substring (8);
    return "";
  }
  
  /**
   * Formats and returns the first or name last entered by the user.
   * The first if structure checks if the name is one character long.
   * The second if structure checks if the name is two or more characters long.
   * 
   * @param nameToFormat String reference: Contains the name to be formatted.
   * @return String: Returns a string with the first letter of the name in upper case, and the rest of the name in lower case.
   */ 
  private String formatName (String nameToFormat){
    if (nameToFormat.length () == 1) //prevents run-time error if user enters a single letter
      return ("" + nameToFormat.charAt (0)).toUpperCase ();
    if (nameToFormat.length () >= 2)
      return ("" + nameToFormat.charAt (0)).toUpperCase () + nameToFormat.substring (1).toLowerCase ();
    return "";
  }
}