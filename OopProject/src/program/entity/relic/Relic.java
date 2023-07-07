package program.entity.relic;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import program.entity.person.Person;
import program.entity.store.Store;

public class Relic {
	// cnt dùng để đếm
	public static int cnt = 0;
	int id;
	String title;
	String content;
	String address;
	ObservableList<Person> relatedHistoricalPerson = FXCollections.observableArrayList();
	List<String> nameList = new ArrayList<String>();
	// danh sách ảnh liên quan đến di tích
	String imgRelic;

	public Relic() {}

	public Relic(String title, String content, String address, List<String> nameList, String imgRelic) {
		this.id = ++cnt;
		this.title = title;
		this.content = content;
		this.address = address;
		this.nameList = nameList;
		this.imgRelic = imgRelic;
	}
	public int getId() {return id;}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getAddress() {
		return address;
	}

	public List<String> getNameList() {
		return nameList;
	}

	public String getImgRelic() {
		return imgRelic;
	}

	public void setRelatedHistoricalPerson(ObservableList<Person> relatedHistoricalPerson) {
		this.relatedHistoricalPerson = relatedHistoricalPerson;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Relic) {
			Relic relic = (Relic) obj;
			return relic.getTitle().equals(this.title);
		}
		return false;
	}

	public ObservableList<Person> getRelatedHistoricalPerson() {
		return relatedHistoricalPerson;
	}

	public void updatePerson() {
		for (String name : nameList) {
			Person person = new Person(name);
			int index = Store.persons.indexOf(person);
			if (index != -1 && !relatedHistoricalPerson.contains(person)) {
				relatedHistoricalPerson.add(Store.persons.get(index));
				System.out.println("Successfuly add " + name);
			} else {
				System.out.println("Fail to add " + name);
			}
		}
	}

	/**
	 * Hàm này dùng để thêm nhân vật lịch sử vào relatedHistoricalPerson
	 */
	public void addHistoricalPerson(Person person) {
		// check if person not exist in relatedHistoricalPerson. If not I will add
		if (!relatedHistoricalPerson.contains(person)) {
			relatedHistoricalPerson.add(person);
		} else
			System.out.print("This person has existed");
	}

	public static void resetId() {
		cnt = 0;
	}
}
