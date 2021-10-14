package chat2.model;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class RoomDAO extends Room{
	private static final long serialVersionUID = 1L;
	private static RoomDAO _myRoomDAO;
	
	private RoomDAO() {
		super();
	}
	
	public static RoomDAO getMyRoomDAO() {
		if(_myRoomDAO==null) {
			_myRoomDAO=new RoomDAO();
		}
		
		return _myRoomDAO;
	}
	
	public boolean saveRoom(String url, Room room)
	{
		boolean result=false;
		
		try {
			JAXBContext jaxbContext= JAXBContext.newInstance(Room.class);
			Marshaller marshaller=jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(room, new File(url));
			result=true;
		} catch (JAXBException e) {
			System.out.println("Error al guardar la Room");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Room loadRoom(String url)
	{	
		Room r=new Room();
		
		try {
			JAXBContext jaxbcontent=JAXBContext.newInstance(Room.class);
			Unmarshaller unmarshaller=jaxbcontent.createUnmarshaller();
			r=((Room) unmarshaller.unmarshal(new File(url)));		
		} catch (JAXBException e) {
			System.out.println("Error al cargar la Room: Fichero no encontrado!");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("Archivo no encontrado");
		}
		
		return r;
	}
}
