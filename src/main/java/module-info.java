module com.example.introduccio_jocs {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.introduccio_jocs to javafx.fxml;
    exports com.example.introduccio_jocs;
}