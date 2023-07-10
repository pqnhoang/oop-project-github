package program.entity.dynasty;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import program.entity.person.Person;
import program.entity.store.Store;

public class Dynasty {
	// id = ++cnt dùng để đếm
	public static int cnt = 0;
	private int id;
	private String name; // tên triều đại
	private String timeExist; // thời gian triều đại tồn tại
	private String capital; // nơi đóng đô 
	private List <String> kingNameL;
	private ObservableList <Person> kingList = FXCollections.observableArrayList(); // vua

	public Dynasty(String name, String exitedTime, String capital, List <String> kingNameL) {
		id = ++cnt;
		this.name = name;
		this.timeExist = exitedTime;
		this.capital = capital;
		this.kingNameL = kingNameL;
	}

	public Dynasty() {
		id = ++cnt;
	}

	public Dynasty(String name) {
		this.name = name;
	}

	public List <Person> getKing() {
		return kingList;
	}
	public String getTimeExist()
	{
		return timeExist;
	}
	public String getName() {
		return name;
	}
	public String getCapital() {
		return capital;
	}
	public int getId() {
		return id;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}
	public void setTimeExist(String exitedTime) {
		this.timeExist = exitedTime;
	}
	public void setKingNameL(List<String> kingNameL) {
		this.kingNameL = kingNameL;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setkingList(ObservableList<Person> kingList) {
		this.kingList = kingList;
	}

	public void addKing() {
		for (String name: kingNameL) {
			Person person = new Person(name);
			int index = Store.persons.indexOf(person);
			if (index != -1 && !kingList.contains(person)) {
				kingList.add(Store.persons.get(index));
				System.out.println("Successfuly add " + name);
			} else {
				System.out.println("Fail to add" + name);
			}
		}
	}

	public static void resetId() {
		cnt = 0;
	}
	
	 // Nếu tên triều đại lấy được giống nhau thì chỉ lấy 1 lần
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Dynasty) {
			Dynasty dynasty = (Dynasty) obj;
			return dynasty.getName().equals(this.name);
		}
		return false;
	}
}
