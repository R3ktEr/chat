module chat2.controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires java.sql;
	requires java.xml.bind;
	requires jakarta.xml.bind;
	requires java.desktop;
	requires java.base;
    
	opens chat2.model to java.xml.bind;
    opens chat2.controller to javafx.fxml;
    exports chat2.controller;
    exports chat2.utils;
}
