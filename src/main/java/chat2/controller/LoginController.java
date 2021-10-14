package chat2.controller;

import chat2.model.Room;
import chat2.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField textfield;
	@FXML
	private Button enter;
	
	private String user;
	private Room room;
	
	public LoginController() {
		this.room=new Room();
	}
	
	public void initLoginController(@SuppressWarnings("exports") Room room) {
		this.room=room;
	}
	
	public void validateUser(ActionEvent event) {
		if(this.room.getUsers()!=null) {
			this.user=textfield.getText();
			
			if(this.room.getUsers().contains(this.user)) {
				Utils.popError("Error: El usuario ya existe");
				this.user="";
			}else {
				this.room.getUsers().add(this.user);
				Stage stage=(Stage) this.enter.getScene().getWindow();
				stage.close();
			}
		}
	}

	public String getUser() {
		return user;
	}
}
