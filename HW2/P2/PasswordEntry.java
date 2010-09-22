/** The PasswordEntry contains the name and number, both
 *  stored as strings. The name is not changable.
 *  @author Koffman & Wolfgang
 */

public class PasswordEntry {
    private String name, password;

    public PasswordEntry(String n, String p){
        name = n;
        password = p;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String newpass){
        password = newpass;
    }
    public void remove(){
        name = "";
        password = "";
    }
}

