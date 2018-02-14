package ua.kas.main.all;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ua.kas.main.Database;

public class ControllerAll implements Initializable {

	@FXML
	ListView<String> lv_mainDishes;

	@FXML
	TextArea ta_mainProc;
	@FXML
	TextArea ta_mainIngr;

	@FXML
	Label lb_mainName;
	@FXML
	Label lb_mainTime;

	private ObservableList<String> obsListMainItems = FXCollections.observableArrayList();

	private LinkedList<ListView<String>> linkListAll = new LinkedList<>();

	private Connection connect;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Database db = Database.getDB();
		this.connect = db.getConnect();

		linkListAll.add(lv_mainDishes);

		for (ListView<String> listView : linkListAll) {
			listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		}

		try {
			Statement statement = connect.createStatement();
			String query = "SELECT * FROM dishes";
			ResultSet res = statement.executeQuery(query);
			while (res.next()) {
				obsListMainItems.add(res.getString("name"));
			}

			FXCollections.sort(obsListMainItems);
			lv_mainDishes.setItems(obsListMainItems);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void select() throws SQLException, IOException {

		ObservableList<String> all = null;

		for (ListView<String> lv : linkListAll) {
			all = lv.getSelectionModel().getSelectedItems();

			if (all.size() != 0)
				break;
		}

		for (String name : all) {

			java.sql.PreparedStatement myStmt = connect.prepareStatement("select * from dishes where name =?");
			myStmt.setString(1, name);
			ResultSet myRs = null;
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				lb_mainName.setText(myRs.getString("name"));
				lb_mainTime.setText(myRs.getString("times"));
				ta_mainIngr.setText(myRs.getString("ingr"));
				ta_mainProc.setText(myRs.getString("proc"));

			}
		}
	}

	public void back(ActionEvent e) throws IOException {
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../main.fxml")));
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

}
