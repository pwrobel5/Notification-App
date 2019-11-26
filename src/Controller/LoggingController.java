package Controller;

import Model.Users.User;
import Model.main.Main;
import Query.QueryExecutor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oracle.jrockit.jfr.events.EventControl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoggingController {
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Label informationLabel;
    @FXML
    private Button loginButton;


    @FXML
    private void handleLoggingAction(ActionEvent event) throws SQLException, IOException {
        String findByLoginSql = String.format("SELECT * FROM '%s' WHERE login='%s' AND password='%s'", User.TABLE_NAME, login.getText(), password.getText());
        ResultSet resultSet = QueryExecutor.read(findByLoginSql);
        int id = QueryExecutor.readIdFromResultSet(resultSet);
        if(id == -1){
            informationLabel.setText("Bledny login/haslo");
            informationLabel.setVisible(true);

        }
        else if(id != -1) {
            informationLabel.setText("Zalogowano");
            informationLabel.setVisible(true);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddTicketPane.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene((Pane) loader.load()));
            AddTicketController addTicketController = loader.<AddTicketController>getController();
            addTicketController.setUserID(id);
            addTicketController.setLogin(login.getText());
            stage.show();

        }
    }




}