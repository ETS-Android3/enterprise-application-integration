package Network;

import com.datastax.driver.core.Session;

public class CassandraTest {

    private Session session;

    //RUn message queue adapter for receiving end
    public static void main(String args[]) {
        Producer producer = new Producer();
        CassandraConnector connector = new CassandraConnector();
        connector.connect("127.0.0.1", null);


        String values = String.format("{timestamp: %s, scooter_id: %2d, status: %s, error_code: %2d, error_message: %s, lan: %3.6f, lon: %3.6f}", "2018-04-26 12:59", 1, "broken", 1111, "broken", 10.1234, 19.1234);

        producer.sendBrokenNotification(values);



//        CassandraConnector connector = new CassandraConnector();
//        System.out.println("Connecting...");
//        connector.connect("127.0.0.1", null);
//        System.out.println("Get session....");
//        Session session = connector.getSession();
//
//        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append("test123").append(" WITH replication = {").append("'class':'").append("SimpleStrategy").append("','replication_factor':").append("1").append("};");
//        String query = sb.toString();
//        String anotherquery = "CREATE TABLE errors(\n" +
//                "\ttime_stamp timestamp PRIMARY KEY,\n" +
//                "\tscooter_id int,\n" +
//                "\tstatus text,\n" +
//                "\terror_code int,\n" +
//                "\terror_message text,\n" +
//                "\tlang double,\n" +
//                "\tlong double\n" +
//                ");";
//
//        System.out.println(session.execute(query));
//        System.out.println(session.execute("USE test123;"));
//        //System.out.println(session.execute("DROP table errors;"));
//        System.out.println(session.execute(anotherquery));
//
//        System.out.println("Closing....");
//        connector.close();
    }

}
