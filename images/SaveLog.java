package chat;

import static java.awt.SystemColor.text;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Brett
 */
public class SaveLog{
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          try{
              DataOutputStream output =
                    new DataOutputStream(new FileOutputStream("C:\\output\\outfile.txt"));
              output.writeUTF("Text output");

        Scanner s = new Scanner("C:\\output\\outfile.txt");
        System.out.println(s.nextLine());
      
        }
        catch(Exception e){
            System.out.print(e.getStackTrace());
        }
    }
    }
    

