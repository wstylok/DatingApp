import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

public class SearchController {
    
	boolean firstUse = true;
	
    @FXML
    private ComboBox<String> age;
    public void start() throws Exception {
    }

    @FXML
    void onChanged(ActionEvent event) {
    	
    	Object co_zmieniono = event.getSource();
    	RadioButton plec = new RadioButton();
    	ComboBox<String> cechy = new ComboBox<String>();
    	String szukane = "";
    	if(tryParseRadio(co_zmieniono)) 
	    {
	    	plec = (RadioButton) co_zmieniono;
	    	szukane = plec.getText();
	    	
	    	if(szukane.equals("kobiety"))
	    	{
	    		szukane = "k ";
	    	}
	    	else if (szukane.equals("mężczyzny"))
	    	{
				szukane = "m ";
			}
	    	/*
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Zmiana");
			alert.setHeaderText("Wykryto zmiane");
			alert.setContentText("Zmieniono : " + plec.getText());
			alert.showAndWait();
			*/
	    }
    	else if(tryParseCombo(co_zmieniono)) 
    	{
	    	cechy = (ComboBox<String>) co_zmieniono;
	    	szukane = cechy.getValue();
	    	/*
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Zmiana");
			alert.setHeaderText("Wykryto zmiane");
			alert.setContentText("Zmieniono : " + cechy.getValue());
			alert.showAndWait();
			*/
    	}
    	szukajDalej(szukane);
    }
    
    @FXML
    void onReset(ActionEvent event) throws IOException
    {
		Parent searchPageParent = FXMLLoader.load(getClass().getResource("SearchFXML.fxml"));
		Scene searchPageScene = new Scene(searchPageParent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.hide();
        appStage.setScene(searchPageScene);
        appStage.show();
    }

    @FXML
    private ComboBox<String> children;

    @FXML
    private ComboBox<String> education;

    @FXML
    private ComboBox<String> eyes;

    @FXML
    private ComboBox<String> hair;

    @FXML
    private ComboBox<String> height;
    
    @FXML
    public TextArea matches;
    
    public void szukajDalej(String szukane)
    {
    	if(firstUse)
    	{
    		Scanner szukaj_C;
    		try 
    		{
    			int nrKandydata = 0;
    			String wynik = "";
    			szukaj_C = new Scanner(new File("Dane.txt"));
    	    	while(szukaj_C.hasNext()) 
    	    	{
    	    		String linia = szukaj_C.nextLine();
    			   	if(linia.contains(szukane))
    			   	{
    			   		nrKandydata++;
    			   		wynik = wynik + " " + nrKandydata+ ". " + linia + "\n";
    			    }
    	    	}

    	    	PrintWriter zapis_U = new PrintWriter("Wyniki.txt");
    	    	zapis_U.print(wynik);
    	    	zapis_U.close();
    	    	matches.setText(wynik);
    		    szukaj_C.close();
    		    firstUse = false;
    		}
    	    catch (FileNotFoundException e) 
    	    {
    	   		Alert alert = new Alert(AlertType.INFORMATION);
    	    	alert.setTitle("Blad przeszukiwania");
    	    	alert.setHeaderText("Nie znaleziono bazy");
    	    	alert.setContentText("Sprawdz czy baza istnieje");
    	    	alert.showAndWait();
    			e.printStackTrace();
    		}
    	}
    	else
    	{
			Scanner szukaj_C;
			String wynik = "";
			try 
			{
				int nrKandydata = 0;
				szukaj_C = new Scanner(new File("Wyniki.txt"));
		    	while(szukaj_C.hasNext()) 
		    	{
		    		String linia = szukaj_C.nextLine();
		    		linia = linia.substring(3);
				   	if(linia.contains(szukane))
				   	{
				   		nrKandydata++;
				   		wynik = wynik + " " + nrKandydata+ " " + linia + "\n";
				    }
		    	}
    	    	PrintWriter zapis_U = new PrintWriter("Wyniki.txt");
    	    	zapis_U.print(wynik);
    	    	zapis_U.close();
		    	matches.setText(wynik);
			    szukaj_C.close();
			}
		    catch (FileNotFoundException e) 
		    {
		   		Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Blad przeszukiwania");
		    	alert.setHeaderText("Nie znaleziono bazy");
		    	alert.setContentText("Sprawdz czy baza istnieje");
		    	alert.showAndWait();
				e.printStackTrace();
			}
    	}
    }
    
    boolean tryParseRadio(Object tryParseMe)
    {
    	try
    	{
    		RadioButton button = (RadioButton) tryParseMe;
    		return true;
    	}
    	catch (Exception e)
    	{
    		return false;
    	}
    }
    
    boolean tryParseCombo(Object tryParseMe)
    {
    	try
    	{
    		ComboBox<String> combo = new ComboBox<String>();
			combo = (ComboBox<String>) tryParseMe;
    		return true;
    	}
    	catch (Exception e)
    	{
    		return false;
    	}
    }
}