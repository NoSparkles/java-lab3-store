module store {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    
    opens store to javafx.fxml;
    exports store;
    exports store.product;
    exports store.order;
}
