package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientController {
    private String userName = System.getProperty("user.name");
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket sock = null;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="nameField"
    private TextField nameField; // Value injected by FXMLLoader

    @FXML // fx:id="btnName"
    private Button btnName; // Value injected by FXMLLoader

    @FXML // fx:id="inbox"
    private TextArea inbox; // Value injected by FXMLLoader

    @FXML // fx:id="msgBox"
    private TextArea msgBox; // Value injected by FXMLLoader

    @FXML
    void sendMsg(ActionEvent event) {
        writer.println(userName + "-> " + msgBox.getText());
        writer.flush();
        msgBox.setText("");
    }

    @FXML
    void setUserName(ActionEvent event) {
        userName = nameField.getText();
        nameField.setEditable(false);
        btnName.setDisable(true);
        System.out.println(userName);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        nameField.setText(userName);
        establishNetwork();
        Thread readTexts = new Thread(new ReadIncoming());
        readTexts.start();
    }

    private void establishNetwork() {
        try {
            sock = new Socket("127.0.0.1", 5000);
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    class ReadIncoming implements Runnable {

        @Override
        public void run() {
            try {
                String message = reader.readLine();
                while (message != null) {
                    inbox.appendText(message + "\n");
                    message = reader.readLine();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }

    }
}
