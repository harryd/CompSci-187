/** The interface for the phone directory user interface.
 *  @author Koffman & Wolfgang
 */

public interface PDUserInterface {
  /** Abstract method that processes user's commands.
      @param thePhoneDirectory The PhoneDirectory object that
             contains the data to be displayed and/or changed
   */
  void processCommands(PwdDirectory thePhoneDirectory);
}
