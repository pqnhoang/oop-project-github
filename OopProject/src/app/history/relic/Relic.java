package app.history.relic;

import java.util.ArrayList;
import java.util.List;

import app.history.person.Person;
import app.history.storage.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Relic {
	// id = ++cnt; cnt dùng để đếm
	public static int cnt = 0;
	int id;

	String title;
	String content;
	String address;
	ObservableList<Person> relatedHistoricalPerson = FXCollections.observableArrayList();
	// danh sách tên người liên quan
	List<String> nameList = new ArrayList<String>();
	// danh sách ảnh liên quan đến di tích
	String imgUrl;

	public Relic() {}

	public Relic(String title, String content, String address, List<String> nameList, String imgUrl) {
		this.id = ++cnt;
		this.title = title;
		this.content = content;
		this.address = address;
		this.nameList = nameList;
		this.imgUrl = imgUrl;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setRelatedHistoricalPerson(ObservableList<Person> relatedHistoricalPerson) {
		this.relatedHistoricalPerson = relatedHistoricalPerson;
	}

	/**
	 * Đinh nghĩa bằng nhau khi title của chúng bằng nhau
	 *
	 * @return true : if (name2 == name2)
	 *
	 */
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

	public void addPerson() {
		for (String name : nameList) {
			Person person = new Person(name);
			int index = Storage.persons.indexOf(person);
			if (index != -1 && !relatedHistoricalPerson.contains(person)) {
				relatedHistoricalPerson.add(Storage.persons.get(index));
				System.out.println("Them thanh cong " + name);
			} else {
				System.out.println("Khong thanh cong " + name);
			}
		}
	}

	/**
	 * Hàm này dùng để thêm nhân vật lịch sử vào relatedHistoricalPerson
	 *
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
