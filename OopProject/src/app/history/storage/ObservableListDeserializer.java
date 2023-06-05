package app.history.storage;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListDeserializer<T> implements JsonDeserializer<ObservableList>{

	public ObservableList deserialize(JsonElement json, Type typeOff, JsonDeserializationContext context)
			throws JsonParseException {
		JsonArray jsonArray = json.getAsJsonArray();
		ArrayList<T> list = context.deserialize(jsonArray, new TypeToken<ArrayList<T>>() {
		}.getType());
		return FXCollections.observableArrayList(list);
	}
  }
