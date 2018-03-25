package GUI.manager;

import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import GUI.elements.CustomGridPane;
import GUI.elements.CustomPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import util.Reorderer;

import java.util.Properties;

public class ItemRequestsPage extends CustomPage{
    private Button backButton, sendButton;
    private TextArea email;

    @Override
    public void populateTab(Tab tab) {
        CustomGridPane grid = new CustomGridPane(0);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Text Area
        email = new TextArea(Reorderer.getReorders());

        // Buttons
        backButton = new Button("Back");
        backButton.setOnAction(e -> new MangerMenuPage().populateTab(tab));

        sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendEmail(
                "localhost", "matthewverreault@yahoo.com", "csc207restaurantmarch2018@gmail.com")
        );

        grid.add(email, 0, 0, 2, 1);
        grid.add(sendButton, 0, 1);
        grid.add(backButton, 1, 1);

        tab.setContent(grid);
    }

    private void sendEmail(String host, String to, String from){
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
            email.setText(this.email.getText());

            Transport.send(email);
            // TODO add logging
        } catch (MessagingException e){
            // TODO add logging
            e.printStackTrace();
        }
    }

    @Override
    public void update() {}
}
