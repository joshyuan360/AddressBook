/**
 * Checks whether or not the user has entered a valid email address or phone number.
 * 
 * @author Joshua Yuan
 * @version 1.0 March 4, 2014
 */ 
class DataCheck {
  /**
   * Checks if the user has entered a valid email. 
   * The for loop is used to loop through each character in the email. Start: 0 Stop: email.length ()
   * The if structure checks for conditions that will make the email invalid. 
   * 
   * @param email String reference: Contains the email typed by the user.
   * @param atSymbolLoc int: Stores the location of the first @ symbol in the string.
   * @param i int: Used to iterate through all of the characters in the string.
   * @return boolean: True if the following conditions are true:
   * <p> The email cannot contain consecutive periods, such as "..".
   * <p> A period cannot be the first or last character of the string.
   * <p> A period cannot be found immediately before or after the @ symbol.
   * <p> There must be exactly one @ symbol.
   * <p> There must be text before and after the @ symbol.
   * <p> Except for the @ symbol and the periods, all characters must be alphanumeric.
   * <p> No spaces are allowed.
   */ 
  public static boolean checkEmail (String email){
    int atSymbolLoc = email.indexOf ('@');
    
    for (int i = 0 ; i < email.length () ; i++){
      if (email.charAt (i) == '.' && (i == 0 || i == atSymbolLoc - 1 || i == atSymbolLoc + 1 || i == email.length () - 1 || (i < email.length () - 1 && email.charAt (i + 1) == '.')) || email.charAt (i) != '.' && email.charAt (i) != '@' && (email.charAt (i) <= 47 || email.charAt (i) >= 58 && email.charAt (i) <= 64 || email.charAt (i) >= 91 && email.charAt (i) <= 96 || email.charAt (i) >= 123))
        return false;
    }
    
    return atSymbolLoc > 0 && !email.substring (atSymbolLoc + 1).contains ("@") && email.substring (atSymbolLoc + 1).contains (".");
  }
  
  /**
   * Checks if the user has entered a valid phone number.
   * The first if structure checks if the length of the string is 10 or 12.
   * The second if structure checks if a character which is expected to contain a digit does not contain a digit.
   * The first and second ternary operators are used to alter the position of the characters that are checked, depending on the length of the string.
   * The for loop iterates through the 10 characters in the string that are expected to contain a digit. Start: 0 Stop: 10
   * 
   * @param phone String reference: Contains the phone number entered by the user.
   * @param digit array of ints: Contains the expected character position of digits for the xxx-xxx-xxxx and xxx xxx xxxx formats.
   * @param i int: Used to iterate through all of the characters in the string that are expected to contain digits.
   * @return boolean: True for the following formats, where x represents a digit: xxx-xxx-xxxx, xxx xxx xxxx, or xxxxxxxxxx. Otherwise, it returns false.
   */ 
  public static boolean checkPhone (String phone){
    int [] digit = {0, 1, 2, 4, 5, 6, 8, 9, 10, 11};
    
    if (phone.length () == 10 || phone.length () == 12){
      for (int i = 0 ; i < 10 ; i++){  
        if (phone.charAt (phone.length () == 10 ? i : digit [i]) < 48 || phone.charAt (phone.length () == 10 ? i : digit [i]) > 57)
          return false;
      }
    }
    
    return phone.length () == 10 || phone.length () == 12 && (phone.charAt (3) == '-' && phone.charAt (7) == '-' || phone.charAt (7) == ' ' && phone.charAt (7) == ' '); //check phone length, in case of xxx-xxx-xxxxx
  }
  
}