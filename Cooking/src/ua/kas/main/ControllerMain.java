package ua.kas.main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerMain {
	@FXML
	Button btnAll;
	@FXML
	Button btnAdd;
	@FXML
	Button btnChange;

	public void select(ActionEvent e) throws IOException {
		if (e.getSource() == btnAll)
			open("all/all.fxml", e);
		else if (e.getSource() == btnAdd)
			open("add/add.fxml", e);
		else if (e.getSource() == btnChange)
			open("change/change.fxml", e);
	}

	private void open(String path, ActionEvent e) throws IOException {
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource(path)));
		scene.getStylesheets().add(getClass().getResource("./application.css").toExternalForm());
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}
