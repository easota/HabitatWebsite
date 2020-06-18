package chat;


import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;





public class Client extends Application {
  // IO streams
  DataOutputStream toServer = null;
  DataInputStream fromServer = null;

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Panel p to hold the label and text field
    Image type= new Image("http://olddesignshop.com/wp-content/uploads/2013/06/OldDesignShop_DensmoreTypewriter1887.jpg");
    ImageView typewrite = new ImageView();
    typewrite.setImage(type);
    
    TextField chatname= new TextField();
    chatname.setPromptText("Enter your desired Chatname");
    VBox topbox= new VBox();
    topbox.setPadding(new Insets(5, 5, 5, 5)); 
    topbox.setSpacing(5);
    topbox.getChildren().add(chatname);
    topbox.getChildren().add(new Label("Enter a message here"));

    TextField tf = new TextField();
    tf.setAlignment(Pos.TOP_CENTER);
    topbox.getChildren().add(tf);
    VBox forchecks = new VBox();
    
 

    BorderPane mainPane = new BorderPane();
    // Text area to display contents
    TextArea ta = new TextArea();
    mainPane.setCenter(ta);
    ta.setBackground(new Background(new BackgroundImage(type,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
    mainPane.setTop(topbox);
    mainPane.setBackground(new Background(new BackgroundImage(type,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
    // Create a scene and place it in the stage
    Scene scene = new Scene(mainPane, 1000, 700);
    primaryStage.setTitle("Client");
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); 
    // Display the stage
    String[] chatnames = new String[100];
    modifychatnameslist(chatnames);
    String username= chatname.getText();
    
    addchatlist(username, chatnames);
    createcheckboxes(chatnames,username, forchecks);
    mainPane.setRight(forchecks);
    
    
    tf.setOnAction(e -> {
      try {
        // Get the radius from the text field
        String message = (chatname.getPromptText()+"   "+tf.getText().trim());
  
        // Send the radius to the server
        toServer.writeUTF(message);
        toServer.flush();
  
        /* Get area from the server
        String response = fromServer.readUTF();
  
        // Display to the text area
        ta.appendText("Radius is " + message + "\n");
        ta.appendText("Area received from the server is "
          + message + '\n');
        */
      }
      catch (IOException ex) {
        System.err.println(ex);
      }
    });
   class Receive implements Runnable {
    private Socket socket; // A connected socket

    /** Construct a thread */
    public Receive(Socket socket) {
      this.socket = socket;
    }

    /** Run a thread */
    public void run() {
       ta.appendText("Enter here");
      try {
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());

        // Continuously serve the client
        while (true) {
          // Receive radius from the client
          String message = inputFromClient.readUTF();
           ta.appendText("has sent " + message + "on "+new Date() + '\n');
          // Compute area
          

          // Send area back to the client
          
          
          Platform.runLater(() -> {
            ta.appendText("message received from client: " +
              message + '\n');
            
          });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }
      public void start(){
          run();
      
          
      
    }


  }
        
    try {
      // Create a socket to connect to the server
      Socket socket = new Socket("localhost", 283);
      
      Thread t = new Thread(new Receive(socket),"test");
      t.start();
      // Socket socket = new Socket("130.254.204.36", 8000);
      // Socket socket = new Socket("drake.Armstrong.edu", 8000);

      // Create an input stream to receive data from the server
      fromServer = new DataInputStream(socket.getInputStream());

      // Create an output stream to send data to the server
      toServer = new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException ex) {
      ta.appendText(ex.toString() + '\n');
    }
    
    
  }
        public void addchatlist(String name,String[] list){
         boolean j= true;
         boolean h= true;
         int i= 0;
         while(j==true){
             while(h == true){
                 if(list[i]== null ){
                     list[i]= name;
                     h=false;
                     j=false;
                 }
             i++;
             }
         }
         
     }
        public void modifychatnameslist(String[] list){
            for(int i =0; i<list.length; i++)
                list[i]= null;
        }
        public void createcheckboxes(String[] list, String chatname, VBox pane){
         int j=0;
         for(int i =0 ;i<list.length;i++){
             if ( chatname != list[i] && list[i]!= null)
             {
                 CheckBox cb = new CheckBox(list[i]);
                 pane.getChildren().add(cb);
                
             }
                
         }
    }
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
