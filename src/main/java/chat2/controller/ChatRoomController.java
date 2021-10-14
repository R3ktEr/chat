package chat2.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import chat2.model.Message;
import chat2.model.Room;
import chat2.model.RoomDAO;
import chat2.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChatRoomController implements Initializable{
	@FXML
	private TitledPane mainPane;
	@FXML
	private TitledPane usersPane;
	@FXML
	private TitledPane messagesPane;
	@FXML
	private ListView<String> usersListPane;
	@FXML
	private ListView<String> messageListPane;
	@FXML
	private Button login;
	@FXML
	private Button logout;
	@FXML
	private Button send;
	@FXML
	private TextField textBar;
	
	private String user;
	private Room room;
	private ObservableList<String> usersList;
	private ObservableList<String> messageList;
	
	public ChatRoomController() {
		this.room=new Room();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		this.mainPane.setText("Sala 1");
		this.logout.setDisable(true);
		this.send.setDisable(true);
		this.textBar.setDisable(true);
		load();
		this.usersList=FXCollections.observableArrayList(this.room.getUsers());
		this.messageList=FXCollections.observableArrayList(this.room.getAllMessagesList());
		this.usersListPane.getItems().addAll(usersList);
		this.messageListPane.getItems().addAll(messageList);
		refresh();
	}
	
	@FXML
	public void logIn(ActionEvent event) {
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Login.fxml"));
			Parent root=loader.load();
			LoginController controller=loader.getController();
			
			controller.initLoginController(this.room);
			
			Scene scene=new Scene(root);
			Stage stage=new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();	
			
			this.user=controller.getUser();
			if(!this.user.isEmpty()) {
				if(!this.room.getUsers().contains(this.user)) {
					this.room.getUsers().add(this.user);
				}
				updateObservableList();
				//this.usersListPane.getItems().add(this.user);
				//this.usersListPane.refresh();
				checkUsers();
				save();				
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void logOut(ActionEvent event) {
		String user;
		
		user=this.user;
		
		if(this.usersListPane.getItems().contains(user)) {
			this.usersListPane.getItems().remove(user);
			this.room.getUsers().remove(user);
			this.user="";
		}else{
			Utils.popError("El usuario "+user+" no existe!");
		}
		
		updateObservableList();
		this.usersListPane.refresh();
		checkUsers();
		save();
	}
	
	@FXML
	public void sendMessage(ActionEvent event) {
		this.room.newMessage(this.user, this.textBar.getText());
		setMessageList();
		this.textBar.clear();
		save();
	}
	
	
	public void setUserList() {
		List<String> usersList=this.room.getUsers();
		
		Iterator<String> myIterator=usersList.iterator();
		
		while(myIterator.hasNext()) {
			String user;
			user=myIterator.next();
			
			if(this.usersListPane.getItems().contains(user)==false) {
				this.usersListPane.getItems().add(user);			
			}
		}
		
		this.usersListPane.refresh();
	}
	
	
	public void setMessageList() {
		List<Message> messageList=this.room.getMessages();
		
		Iterator<Message> myIterator=messageList.iterator();
		
		while(myIterator.hasNext()) {
			Message message;
			message=myIterator.next();
			
			if(this.messageListPane.getItems().contains(message.toString())==false) {
				this.messageListPane.getItems().add(message.toString());
			}
		}
		
		this.messageListPane.refresh();
	}
	
	
	public void checkUsers() {
		if(this.user.isEmpty()) {
			this.logout.setDisable(true);
			this.login.setDisable(false);
			this.send.setDisable(true);
			this.textBar.setDisable(true);
		}else {
			this.logout.setDisable(false);
			this.login.setDisable(true);
			this.send.setDisable(false);
			this.textBar.setDisable(false);
		}
	}
	
	public void save() {
		RoomDAO.getMyRoomDAO().saveRoom("room.xml", room);
	}
	
	public void load() {
		this.room=RoomDAO.getMyRoomDAO().loadRoom("room.xml");
	}
	
	public void updateObservableList() {
		this.usersList=FXCollections.observableArrayList(this.room.getUsers());
		this.messageList=FXCollections.observableArrayList(this.room.getAllMessagesList());
		this.usersListPane.getItems().clear();
		this.usersListPane.getItems().addAll(this.usersList);
		this.messageListPane.getItems().clear();
		this.messageListPane.getItems().addAll(this.messageList);
	}
	
	public void refresh() {
		Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    load();
                    updateObservableList();
                    setUserList();
                    setMessageList();
                });
            }
        }, 0, 2000);
	}
}
