package program.entity.event;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import program.entity.person.Person;
import program.entity.store.Store;

public class Event {

	public static int cnt = 0;
	private String name;
	private String time;
	private String destination;
	private String description;
	private List<String> relativeEventPersonsName = new ArrayList<String>();
	ObservableList<Person> relativeEventPersons = FXCollections.observableArrayList();
	private String imgEvent;
	private int id;

	public Event() {
		
	}
	public Event(String name, String time, String destination, String description, String imgEvent, List<String> relativeEventPersonsName) {
		this.id = cnt++;
		this.time = time;
		this.destination = destination;
		this.description = description;
		this.imgEvent = imgEvent;
		this.relativeEventPersonsName = relativeEventPersonsName;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public String getImgEvent() {
		return imgEvent; 
		}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Person> getRelativeEventPersons() {
		return relativeEventPersons;
	}

	public void setRelativeEventPersons(List<String> relativeEventPersonsName) {
		this.relativeEventPersonsName = relativeEventPersonsName;
	}

	public int getId() {
		return id;
	}

	public void addPerson() {
		for (String name: relativeEventPersonsName) {
			Person person = new Person(name);
			int index = Store.persons.indexOf(person);
			if (index != -1 && !relativeEventPersons.contains(person)){
				relativeEventPersons.add(Store.persons.get(index));
				System.out.println("Successfuly add " + name);
			} else {
				System.out.println("Fail to add " + name);
			}
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof Event) {
			Event t = (Event) obj;
			return this.getName().equals(t.getName());
		}
		return false;
	}

	public static void resetId() {
		cnt = 0;
	}
}
