
public interface PwdDirectory2 {

    /** Load the data file containing the directory.
     *
     * @param sourcename The name of the file with the password directory entries
     */
    void loadData(String sourcename);

    /** Look up an entry.
     *
     * @param name The userid
     * @return The password or null if name is not in the directory
     */
    String lookupEntry(String name);

    /** Add an entry or change an existing entry.
     *
     * @param name The userid of the entry being added or changed
     * @param pwd The new password being assigned
     * @return The old password, or if this is a new entry, null
     */
    String addOrChangeEntry(String name, String pwd);

    /** Remove an entry from the directory.
     *
     * @param name The userid of the entry to be removed
     * @return The current password, or if not in the directory, null
     */
    String removeEntry(String name);

    /** Method write the current directory to file.
     * pre: The directory contains data
     * post: Contents of the directory written back to file in the form of userid-password pairs,
     *       one paired entry per line with a space separating the userid and the password.
     *       modified is reset to false.
     */
    void save();

    boolean isSafe(String pswd);
    /** Check a password string to see if it is secure
     *
     * @param pswd A proposed password
     * @return true if the password is secure, else false
     */

    String toString();
    /** produce a String representation of a list-based directory
     *
     * @return a representation of the list
     */
}
