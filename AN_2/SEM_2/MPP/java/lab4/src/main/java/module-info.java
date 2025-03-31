module ro.mpp.labfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires org.checkerframework.checker.qual;
    requires org.apache.logging.log4j.core;
    requires jbcrypt;

    opens ro.mpp.labfx to javafx.fxml;
    exports ro.mpp.labfx;
}