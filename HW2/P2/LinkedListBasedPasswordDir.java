import java.io.*;
import java.util.*;
 /** This is an implementation of the PhoneDirectory interface that uses
  *   an array to store the data.
  *   @author Koffman & Wolfgang
  */

public class LinkedListBasedPasswordDir
    implements PwdDirectory2 {


  /** The array to contain the directory data */
  private LinkedList<PasswordEntry> theDirectory = new LinkedList<PasswordEntry>();

  private ListIterator it = theDirectory.listIterator();

  /** The data file that contains the directory data */
  private String sourceName = null;

  /** Boolean flag to indicate whether the directory was
      modified since it was either loaded or saved. */
  private boolean modified = false;

  /** Method to load the data file.
       pre:  The directory storage has been created and it is empty.
        If the file exists, it consists of name-number pairs
        on adjacent lines.
       post: The data from the file is loaded into the directory.
       @param sourceName The name of the data file
   */
  public void loadData(String sourceName) {
    // Remember the source name.
    this.sourceName = sourceName;
    try {
      // Create a BufferedReader for the file.
      BufferedReader in = new BufferedReader(
          new FileReader(sourceName));
      String line;
      String[] lines;
      // Read each name and number and add the entry to the array.
      while ( (line = in.readLine()) != null) {
        lines = line.split(" ", 2);
        if (lines.length == 2){
            add(lines[0], lines[1]);
        }

      }

      // Close the file.
      in.close();
    }
    catch (FileNotFoundException ex) {
      // Do nothing no data to load.
      return;
    }
    catch (IOException ex) {
      System.err.println("Load of directory failed.");
      ex.printStackTrace();
      System.exit(1);
    }
  }

  /** Add an entry or change an existing entry.
      @param name The name of the person being added or changed
      @param number The new number to be assigned
      @return The old number or, if a new entry, null
   */
  public String addOrChangeEntry(String name, String newPass) {
    String oldPass = null;
    
    if (find(name) != null) {
      oldPass = find(name).getPassword();
      find(name).setPassword(newPass);
    }
    else {
      add(name, newPass);
    }
    modified = true;
    return oldPass;
  }

  /** Look up an entry.
    @param name The name of the person
    @return The number. If not in the directory, null is returned
   */
  public String lookupEntry(String name) {
      if (find(name) != null){
         return find(name).getPassword();
      }else{
          return null;
      }

  }

  /** Method to save the directory.
      pre:  The directory has been loaded with data.
      post: Contents of directory written back to the file in the
            form of name-number pairs on adjacent lines.
            modified is reset to false.
   */
  public void save() {
    if (modified) { // If not modified, do nothing.
      try {
        // Create PrintWriter for the file.
        PrintWriter out = new PrintWriter(
            new FileWriter(sourceName));

        // Write each directory entry to the file.
        for (int i = 0; i < theDirectory.size(); i++) {
          // Write the name and pass.
          if (!theDirectory.get(i).getName().isEmpty()) {
            out.printf("%s %s\n", theDirectory.get(i).getName(), theDirectory.get(i).getPassword());

          }
        }

        // Close the file and reset modified.
        out.close();
        modified = false;
      }
      catch (Exception ex) {
        System.err.println("Save of directory failed");
        ex.printStackTrace();
        System.exit(1);
      }
    }
  }

  /** Find an entry in the directory.
      @param name The name to be found
      @return The index of the entry with the requested name.
              If the name is not in the directory, returns -1
   */
  private PasswordEntry find(String name) {
    ListIterator<PasswordEntry> it = theDirectory.listIterator();

    while (it.hasNext()){
        PasswordEntry p = it.next();
        if (p.getName().equals(name)){
            return p;
        }
    }
    return null; // Name not found.
  }

  /** Add an entry to the directory.
      @param name The name of the new person
      @param number The number of the new person
   */
  private void add(String name, String number) {
    theDirectory.add(new PasswordEntry(name, number));
  }

  public String removeEntry(String name){
    if (theDirectory.remove(find(name))) {
      modified = true;
      return name + " has been removed.";

    }else{
      return name + " is not in the directory.";
    }

  }
  public boolean isSafe(String pswd){
    if (pswd.length() < 8){
      return false;
    }else {
      for (int i=0; i < pswd.length(); i++){
        if (Character.isDigit(pswd.charAt(i))){
          return true;
        }
      }
    }
    return false;
  }
  
  public String toString(){
    StringBuilder ret = new StringBuilder();
    int len = 0;
    for (PasswordEntry p : theDirectory){
      len++;
      ret.append(p.getName());
      ret.append(": ");
      ret.append(p.getPassword());
      ret.append('\n');
      if (len>30){
        ret.append("The directory is to long to list more.\n");
        return ret.toString();
      }
    }
    return ret.toString();
  }
}
