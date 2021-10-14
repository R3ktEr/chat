package chat2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="room")
@XmlAccessorType(XmlAccessType.FIELD)
public class Room implements Serializable{
	private static final long serialVersionUID = 1L;
	@XmlElement(name="users")
	private List<String> users;
	@XmlElement(name="messages")
	private List<Message> messages;
	
	public Room() {
		users=new ArrayList<String>();
		messages=new ArrayList<Message>();
	}
	
	public Room(List<String> users, List<Message> messages) {
		super();
		this.users = users;
		this.messages = messages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
	
	public List<Message> getMessages() {
		return messages;
	}

	public List<String> getAllMessagesList() {
		List<String> messages=new ArrayList<String>();
		
		Iterator<Message> myIterator=this.messages.iterator();
		
		while(myIterator.hasNext()) {
			messages.add(myIterator.next().toString());
		}
		
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public void newMessage(String user, String message) {
		Message m=new Message(user, message);
		this.messages.add(m);
	}
}
