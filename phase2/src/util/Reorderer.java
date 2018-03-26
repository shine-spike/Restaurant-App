package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

public class Reorderer {
  private static final String REORDER_REQUEST_FILE = "phase2/requests.txt";

  /**
   * Prints to file the need to reorder the given ingredient.
   *
   * @param ingredientName the name of the ingredient to reorder.
   */
  public static void reorder(String ingredientName, int amount) {
    // Find the file we want to write to
    File file = new File(REORDER_REQUEST_FILE);

    try {
      // Attempt to create the file and notify if it was created now
      if (file.createNewFile()) {
        System.out.println(file.getName() + " not found. Created a new one.");
      }

      // Write out to the file
      PrintWriter out = new PrintWriter(new FileWriter(file));
      out.println("Reorder request for " + amount + " of " + ingredientName + ".");
      out.close();
    } catch (IOException e) {
      // TODO: remove print
      System.out.println(file.getName() + " not found and could not be created.");
    }
  }

  public static String getReorders(){
    StringBuilder out = new StringBuilder();

    File file = new File(REORDER_REQUEST_FILE);

    try {
      // Write out to the file
      BufferedReader in = getReader();
      String line = (in != null ? in.readLine() : null);

      while(line != null){
        out.append(line);
        line = in.readLine();
      }
    } catch (IOException e) {

    }

    return out.toString();
  }

  private static BufferedReader getReader() {
    // Find the file we want to get the reader for
    File file = new File(REORDER_REQUEST_FILE);

    try {
      // Attempt to create the file and notify if it was created now
      if (file.createNewFile()) {
      }

      // Return the reader of the file whether or not we just created it
      return new BufferedReader(new FileReader(file));
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * Sends an email to reorder ingredients
   * @param host the host sending the email
   * @param to the email's recipient
   * @param from the email's sender
   * @param content the content of the email
   */
  public static void sendEmail(String host, String to, String from, String content){
    Properties properties = System.getProperties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    Session session = Session.getDefaultInstance(properties,
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,"+<E;d^Jk+,c:[8`m");
              }
            });

    try{
      MimeMessage email = new MimeMessage(session);
      email.setFrom(new InternetAddress(from));
      email.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      email.setSubject("Reorder Request");
      email.setText(content);

      Transport.send(email);
      // TODO add logging
    } catch (MessagingException e){
      // TODO add logging
      e.printStackTrace();
    }
  }
}
