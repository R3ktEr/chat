package chat2.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import chat2.utils.LocalDateTimeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	@XmlElement(name="user")
	private String user;
	@XmlElement(name="message")
	private String message;
	@XmlElement(name = "time", required = true)
	@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
	private LocalDateTime time;
	
	@XmlTransient
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public Message() {
		this.user="";
		this.message="";
		this.time=LocalDateTime.now();
	}
	
	public Message(String user, String message) {
		super();
		this.user = user;
		this.message = message;
		this.time=LocalDateTime.now();
	}

	public Message(String user, String message, LocalDateTime time) {
		this.user=user;
		this.message = message;
		this.time = time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		Message other = (Message) obj;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return time.format(formatter)+" "+user+": "+message;
	}
}
