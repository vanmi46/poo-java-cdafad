import com.exemple.database.Mysql;

import java.util.ArrayList;

public class Main {
    static void main() {
        try {
            Mysql.getConnexion();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
