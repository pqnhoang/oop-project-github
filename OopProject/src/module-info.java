module OopProject {
	exports program.entity.person;
	exports program.entity.relic;
	exports program.scraping.person;
	exports program.scraping.relic;
	exports program.scraping.festival;
	exports program.scraping.event;
	exports program.scraping.dynasty;
	exports program.entity.event;
	exports program.entity.dynasty;
	exports program.entity.festival;
	exports program.scraping.web;
	exports program.screen.controller;
	exports program.screen.controller.base;
	exports program.screen.controller.components;

	
	requires java.desktop;
	requires javafx.base;
	requires org.jsoup;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires com.google.gson;
	

	opens app to javafx.graphics;
	opens program.scraping.person to com.google.gson;
	opens program.scraping.relic to com.google.gson;
	opens program.scraping.festival to com.google.gson;
	opens program.scraping.event to com.google.gson;
	opens program.scraping.dynasty to com.google.gson;
	opens program.entity.person to com.google.gson;
	opens program.entity.dynasty to com.google.gson;
	opens program.entity.event to com.google.gson;
	opens program.entity.festival to com.google.gson;
	opens program.entity.relic to com.google.gson;
	opens program.screen.controller to javafx.fxml;
	opens program.screen.controller.person to javafx.fxml;
	opens program.screen.controller.dynasty to javafx.fxml;
	opens program.screen.controller.event to javafx.fxml;
	opens program.screen.controller.relic to javafx.fxml;
	opens program.screen.controller.festival to javafx.fxml;
	opens program.screen.controller.components to javafx.fxml;
}
