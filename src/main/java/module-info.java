module com.david.midistudio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.david.midistudio to javafx.fxml;
    exports com.david.midistudio;
}