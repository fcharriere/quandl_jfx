package org.quandl.jfx.view;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author frederic
 */
public class Splash extends Preloader {

    Stage stage;
    ProgressBar pb = new ProgressBar();

    private Scene createPreloaderScene() {
        BorderPane p = new BorderPane();
        ImageView imageView = new ImageView(new Image("/img/jquandl.png"));
        p.setCenter(imageView);
        pb.setMaxWidth(Double.MAX_VALUE);
        HBox hBox = new HBox(pb);
        p.setBottom(hBox);
        HBox.setHgrow(pb, Priority.ALWAYS);

        return new Scene(p);
    }

    private void createDatasetDB() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.createDatasetDB();
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        System.out.println(pn);
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }

}
