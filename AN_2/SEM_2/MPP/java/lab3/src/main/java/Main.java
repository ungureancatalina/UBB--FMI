import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props=new Properties();
        try {
            props.load( Main.class.getClassLoader().getResourceAsStream("bd.config"));
        } catch (IOException e) {
            System.out.println("Nu gaseste bd.config "+e);
        }

        System.out.println("Hello world!");
    }
}