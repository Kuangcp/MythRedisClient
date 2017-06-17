package redis.manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * 应用主程序.
 *
 */
public class Main extends Application {


    private AnchorPane rootLayout;
    private FXMLLoader rootLoader;



    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage stage = primaryStage;
        stage.setTitle("Redis客户端");

        rootLoader = new FXMLLoader();
        rootLoader.setLocation(this.getClass().getResource("/views/MainLayout.fxml"));
        rootLayout = rootLoader.load();

        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.show();

    }




    public static void main( String[] args ) {
        launch(args);
    }
}
