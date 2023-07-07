package program.entity.store;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import program.entity.dynasty.Dynasty;
import program.entity.event.Event;
import program.entity.festival.Festival;
import program.entity.person.Person;
import program.entity.relic.Relic;
import program.scraping.dynasty.DynastyScrap;
import program.scraping.event.EventScrap;
import program.scraping.festival.FestivalScrap;
import program.scraping.person.PersonScrap;
import program.scraping.relic.RelicScrap;
import program.scraping.web.ScrapException;

import java.io.File;
import java.io.IOException;

public class Store<T> {
	public static ObservableList<Person> persons = FXCollections.observableArrayList();
	public static ObservableList<Relic> relics = FXCollections.observableArrayList();
	public static ObservableList<Event> events = FXCollections.observableArrayList();
	public static ObservableList<Festival> festivals = FXCollections.observableArrayList();
	public static ObservableList<Dynasty> dynasties = FXCollections.observableArrayList();
	public static FilteredList<Person> filteredPersons = new FilteredList<>(persons, p -> true);
	public static FilteredList<Relic> filteredRelics = new FilteredList<>(relics, p -> true);
	public static FilteredList<Event> filteredEvents = new FilteredList<>(events, p -> true);
	public static FilteredList<Festival> filteredFestivals = new FilteredList<>(festivals, p -> true);
	public static FilteredList<Dynasty> filteredDynasties = new FilteredList<>(dynasties, p -> true);

	public static void searchPerson(String value) {
		filteredPersons.setPredicate(person -> {
			String newValue = value.toLowerCase();
			return person.getName().toLowerCase().contains(newValue);
		});
	}

	public static void searchRelic(String value) {
		filteredRelics.setPredicate(relic -> {
			String newValue = value.toLowerCase();
			return relic.getTitle().toLowerCase().contains(newValue);
		});
	}

	public static void searchEvent(String value) {
		filteredEvents.setPredicate(event -> {
			String newValue = value.toLowerCase();
			return event.getName().toLowerCase().contains(newValue);
		});
	}

	public static void searchFestival(String value) {
		filteredFestivals.setPredicate(festival -> {
			String newValue = value.toLowerCase();
			return festival.getName().toLowerCase().contains(newValue);
		});
	}

	public static void searchDynasty(String value) {
		filteredDynasties.setPredicate(dynasty -> {
			String newValue = value.toLowerCase();
			return dynasty.getName().toLowerCase().contains(newValue);
		});
	}

	public static void scrap() throws IOException {
		// Person.resetId();
		// Relic.resetId();
		// Event.resetId();
		// Festival.resetId();
		Dynasty.resetId();

		// new PersonScraper().scrap();
		// new RelicScraper().scrap();
		// new EventScraper().scrap();
		// new FestivalScraper().scrap();
		new DynastyScrap().scrap();
	}

	public static void init() throws IOException {
		File directoryDynasty = new File("src/program/data/json/dynasty.json");
		File directoryPerson = new File("src/program/data/json/person.json");
		File directoryRelic = new File("src/program/data/json/relic.json");
		File directoryFestival = new File("src/appication/data/json/festival.json");
		File directoryEvent = new File("src/program/data/json/event.json");

		if (!directoryDynasty.exists()) {
			ScrapException dynastyScraper = new DynastyScrap();
			dynastyScraper.scrap();
		}
		if (!directoryPerson.exists()) {
			ScrapException personScraper = new PersonScrap();
			personScraper.scrap();
		}
		if (!directoryRelic.exists()) {
			ScrapException relicScraper = new RelicScrap();
			relicScraper.scrap();
		}
		if (!directoryFestival.exists()) {
			ScrapException festivalScraper = new FestivalScrap();
			festivalScraper.scrap();
		}
		if (!directoryEvent.exists()) {
			ScrapException eventScraper = new EventScrap();
			eventScraper.scrap();
		}

		dynasties = readFromFile("src/program/data/json/dynasty.json", Dynasty[].class);
		persons = readFromFile("src/program/data/json/person.json", Person[].class);
		events = readFromFile("src/program/data/json/event.json", Event[].class);
		relics = readFromFile("src/program/data/json/relic.json", Relic[].class);
		festivals = readFromFile("src/program/data/json/festival.json", Festival[].class);

		for (int i = 0; i < dynasties.size(); i++) {
			dynasties.get(i).addKing();
		}
		for (int i = 0; i < persons.size(); i++) {
			persons.get(i).setDynasty();
		}
		for (int i = 0; i < events.size(); i++) {
			events.get(i).addPerson();
		}
		for (int i = 0; i < relics.size(); i++) {
			relics.get(i).updatePerson();
		}

		filteredDynasties = new FilteredList<>(dynasties, dynasty -> true);
		filteredEvents = new FilteredList<>(events, event -> true);
		filteredFestivals = new FilteredList<>(festivals, festival -> true);
		filteredPersons = new FilteredList<>(persons, person -> true);
		filteredRelics = new FilteredList<>(relics, relic -> true);
	}

	public static <T> ObservableList<T> readFromFile(String fileName, Class<T[]> clazz) {
		FileReader reader;
		try {
			reader = new FileReader(fileName);
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(ObservableList.class, new ObservableListDeserializer<T>());
			Gson gson = gsonBuilder.create();
			T[] arr = gson.fromJson(reader, clazz);
			return FXCollections.observableArrayList(arr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
