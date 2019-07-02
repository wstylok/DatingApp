import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoginControler {

	//Logowaie na przycisku
    @FXML
    void onLogin(ActionEvent event) throws IOException {
    	Scanner szukaj_U = new Scanner(new File("Uzytkownicy.txt"));
		String user_Record = getLogin() + " " + getPassword() + " ";
		Parent searchPageParent = FXMLLoader.load(getClass().getResource("SearchFXML.fxml"));
		Scene searchPageScene = new Scene(searchPageParent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		//System.out.println("\"" + user_Record + "\" \"" + szukaj_U.nextLine() + "\"" );
		//Testowa funkcja do konsoli
		
boolean userExists = false;
        
        while(szukaj_U.hasNext()) {
            if(user_Record.equals(szukaj_U.nextLine()))
            {
                userExists = true;
                break;
            }
            else
            {
                userExists = false;
            }
        }
        szukaj_U.close();
        
        if(userExists == true) {
            appStage.hide();
            appStage.setScene(searchPageScene);
            appStage.show();
        }
        else
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Logowanie");
            alert.setHeaderText("Nie zalogowales sie");
            alert.setContentText("Niepoprawne dane");
            alert.showAndWait();
        }
    }
    //Rejestracja na przycisku
    @FXML
    void onSubmit(ActionEvent event) throws IOException {
    	String user_Record = getLogin() + " " + getPassword() + " ";
    	FileWriter fileWriter = new FileWriter("Uzytkownicy.txt", true);
    	PrintWriter zapis_U = new PrintWriter(fileWriter);
    	zapis_U.println(user_Record);
    	zapis_U.close();
    	
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Rejestracja");
		alert.setHeaderText("Zarejestrowales sie");
		alert.setContentText("Udalo ci sie zarejestrowac jako : " + getLogin());
		alert.showAndWait();

    }


    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passField;
    //Pobranie loginu
	public String getPassword() {
		return passField.getText();
	}
	//Pobranie hasla
    public String getLogin() {
    	return loginField.getText();
    }

}