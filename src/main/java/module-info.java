module com.david.midistudio {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.david.midistudio to javafx.fxml;
    exports com.david.midistudio;
}