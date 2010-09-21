import java.util.*;

/** This class is an implementation of PDUserInterface
 *   that uses the console to display the menu of command choices.
 *   @author Koffman & Wolfgang
 */
public class PDConsoleUI
    implements PDUserInterface {

  /** A reference to the PhoneDirectory object to be processed.
      Globally available to the command-processing methods.
   */
  private PwdDirectory theDirectory = null;

  /** Scanner to read from input console. */
  private Scanner scIn = null;

  // Constructor
  /** Default constructor. */
  public PDConsoleUI() {
    scIn = new Scanner(System.in);
  }

  // Methods
  /** Method to display the command choices and process user
      commands.
      pre:  The directory exists and has been loaded with data.
      post: The directory is updated based on user commands.
      @param thePhoneDirectory A reference to the PhoneDirectory
             to be processed
   */
  public void processCommands(PwdDirectory thePhoneDirectory) {
    String[] commands = {
        "Add/Change Entry",
        "Look Up Entry",
        "Remove Entry",
        "Save Directory",
        "Exit"};

    theDirectory = thePhoneDirectory;
    int choice;
    do {
      for (int i = 0; i < commands.length; i++) {
        System.out.println("Select " + i + ": "
                           + commands[i]);
      }
      try {
        choice = scIn.nextInt(); // Read the next choice.
        scIn.nextLine(); // Skip trailing newline.
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
          case 4:
            doSave();
            break;
          default:
            System.out.println("*** Invalid choice "
                               + choice
                               + " - try again!");
        }
      }
      catch (InputMismatchException ex) {
        System.out.println("*** Incorrect data entry - "
                           + "enter an integer between 0 and "
                           + (commands.length-1));
        scIn.nextLine(); // Discard bad input.
        choice = -1;
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
    // Request the name.
    System.out.println("Enter name");
    String newName = scIn.nextLine();
    if (newName.equals("")) {
      return;
    }
    // Request the number.
    System.out.println("Enter number");
    String newNumber = scIn.nextLine();
    if (newNumber.equals("")) {
      return;
    }
    // Insert/change name-number.
    String oldNumber =
        theDirectory.addOrChangeEntry(newName, newNumber);
    String message;
    if (oldNumber == null) { // New entry.
      message = newName + " was added to the directory"
          + "\nNew number: " + newNumber;
    }
    else { // Changed entry.
      message = "Number for " + newName + " was changed"
          + "\nOld number: " + oldNumber
          + "\nNew number: " + newNumber;
    }
    // Display confirmation message.
    System.out.println(message);
  }

  /** Method to look up an entry.
      pre:  The directory has been loaded with data.
      post: No changes made to the directory.
   */
  private void doLookupEntry() {
    // Request the name.
    System.out.println("Enter name");
    String theName = scIn.nextLine();
    if (theName.equals("")) {
      return; // Dialog was cancelled.
    }
    // Look up the name.
    String theNumber = theDirectory.lookupEntry(theName);
    String message;
    if (theNumber != null) { // Name was found.
      message = "The number for " + theName + " is " + theNumber;
    }
    else { // Name was not found.
      message = theName + " is not listed in the directory";
    }
    // Display the result.
    System.out.println(message);
  }

  /** Method to remove an entry
      Pre:  The directory has been loaded with data.
      Post: The requested name is removed, modifed is set true
   */
  private void doRemoveEntry() {
/**** EXERCISE ****/
  }

  /** Method to save the directory to the data file.
      pre:  The directory has been loaded with data.
      post: The current contents of the directory have been saved
            to the data file.
   */
  private void doSave() {
    theDirectory.save();
  }
}
