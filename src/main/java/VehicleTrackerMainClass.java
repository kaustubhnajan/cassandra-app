import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VehicleTrackerMainClass {

    private static final String cassandraNode = "15.207.222.24";

    public static void main(String[] args) throws IOException {

        Cluster cluster = Cluster.builder().addContactPoint(cassandraNode).build();
        Session session = cluster.connect();

        // vehicle id = CA6AFL218
        // date = 2014-05-19

        System.out.println("Enter vehicle id: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String vehicle_id = reader.readLine();

        System.out.println("Enter date: ");
        String date = reader.readLine();

        System.out.println("SELECT time, latitude, longitude FROM vehicle_tracker.location WHERE vehicle_id = '" + vehicle_id + "' AND date = '" + date + "'");


        ResultSet result = session.execute("SELECT time, latitude, longitude FROM vehicle_tracker.location WHERE vehicle_id = '" + vehicle_id + "' AND date = '" + date + "' LIMIT 1");
        Row row = result.one();
        System.out.println(row.getTimestamp("time") + " " + row.getDouble("latitude") + " " + row.getDouble("longitude"));

        session.close();
        cluster.close();
    }
}
