module hr.javafx.model.remenar7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;


    opens hr.javafx.model.remenar7 to javafx.fxml;
    exports hr.javafx.model.remenar7;
    exports hr.javafx.model.remenar7.controller;
    opens hr.javafx.model.remenar7.controller to javafx.fxml;
}