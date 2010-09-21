import javax.swing.*;

/** This class is an implementation of PDUserInterface
 *   that uses JOptionPane to display the menu of command choices.
 *   @author Koffman & Wolfgang
 */

public class PDGUI
    implements PDUserInterface {

  /** A reference to the PasswordDirectory object to be processed.
      Globally available to the command-processing methods.
   */

  private PwdDirectory theDirectory = null;

  // Methods

  /** Method to display the command choices and process user
      commands.
      pre:  The directory exists and has been loaded with data.
      post: The directory is updated based on user commands.
      @param thePhoneDirectory A reference to the PhoneDirectory
             to be processed
   */

  public void processCommands(PwdDirectory thePWDirectory) {

    String[] commands = {

        "Add/Change Entry",
        "Look Up Entry",
        "Remove Entry",
        "Save Directory",
        "Exit"};

    theDirectory = thePWDirectory;

    int choice;
    do {

      choice = JOptionPane.showOptionDialog(
          null, // No parent
          "Select a Command", // Prompt message
          "PasswordDirectory", // Window title
          JOptionPane.YES_NO_CANCEL_OPTION, // Option type
          JOptionPane.QUESTION_MESSAGE, // Message type
          null, // Icon
          commands, // List of commands
          commands[commands.length - 1]); // Default choice
      switch (choice) {
        case 0:
          doAddChangeEntry();
          break;
        case 1:
          doLookupEntry();
          break;
        case 2:
          doRemoveEntry();
          break;
        case 3:
          doSave();
          break;
        case 4:
          break;
        default: // Do nothing.
      }
    }
    while (choice != commands.length - 1);
    System.exit(0);
  }

  /** Method to add or change an entry.
      pre:  The directory exists and has been loaded with data.
      post: A new name is added, or the value for the name is
            changed, modified is set to true.
   */

  private void doAddChangeEntry() {
    // Request the name
    String newName = JOptionPane.showInputDialog("Enter name");
    if (newName == null) {
      return; // Dialog was cancelled.
    }
    // Request the password
    String newNumber = JOptionPane.showInputDialog("Enter new password");
    if (newNumber == null) {
      return; // Dialog was cancelled.
    }
	String message = null;

    // Check for safe password
    if (!theDirectory.isSafe(newNumber)) { //password rejected
    	message = newNumber + "is not a safe password" +
    	"\nPasswords must contain at least 8 characters" +
    	"\nand contain at least one numeral.";
    }
    else {

    	// Insert/change name-password
    	String oldNumber = theDirectory.addOrChangeEntry(newName,
    			newNumber);
    	if (oldNumber == null) { // New entry.
    		message = newName + " was added to the directory"
    		+ "\nwith password: " + newNumber;
    	}
    	else { // Changed entry.
    		message = "Password for " + newName + " was changed "
    		+ "\nOld password: " + oldNumber
    		+ "\nNew password: " + newNumber;
    	}
    }

    // Display confirmation message.
    JOptionPane.showMessageDialog(null, message);
  }

  /** Method to look up an entry.
      pre:  The directory has been loaded with data.
      post: No changes made to the directory.
   */

  private void doLookupEntry() {
    // Request the name.
    String theName = JOptionPane.showInputDialog("Enter name");
    if (theName == null) {
      return; // Dialog was cancelled.
    }

    // Look up the name.
    String theNumber = theDirectory.lookupEntry(theName);
    String message = null;
    if (theNumber != null) { // Name was found.
      message = "The password for " + theName + " is " + theNumber;
    }
    else { // Name was not found.
      message = theName + " is not listed in the directory";
    }

    // Display the result.
    JOptionPane.showMessageDialog(null, message);
  }

  /** Method to remove an entry
       Pre:  The directory has been loaded with data.
       Post: The requested name is removed, modifed is set true
   */

  private void doRemoveEntry() {
    // Request the name.
    String message = null;
    String theName = JOptionPane.showInputDialog("Enter name");
    
    message = theDirectory.removeEntry(theName);
    // Display the result.
    JOptionPane.showMessageDialog(null, message);
  }

  /** Method to save the directory to the data file.
      pre:  The directory has been loaded with data.
      post: The current contents of the directory have been saved
            to the data file.
   */

  private void doSave() {
    theDirectory.save();
    String message = "The password directory has been saved";
	// Display the result.
	JOptionPane.showMessageDialog(null, message);
  }

  public static void main(String[] args){
	  // Is Eclipse telling you your text file doesn't exist?
	  // Uncomment the following line and find out where Eclipse is looking for it.
	  // System.out.println(System.getProperty("user.dir"));
	  // Then just move your file to that location and you won't get any more file I/O errors.
	  PDGUI gui = new PDGUI();
	  PwdDirectory ourdir = new ArrayBasedPasswordDir();
	  ourdir.loadData("pwds.txt");
	  gui.processCommands(ourdir);
  }

}
