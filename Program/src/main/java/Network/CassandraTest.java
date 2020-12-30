package Network;

import com.datastax.driver.core.Session;

public class CassandraTest {

    private Session session;

    public static void main(String args[]) {
        CassandraConnector connector = new CassandraConnector();
        System.out.println("Connecting...");
        connector.connect("127.0.0.1", null);
        System.out.println("Get session....");
        Session session = connector.getSession();

        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append("test123").append(" WITH replication = {").append("'class':'").append("SimpleStrategy").append("','replication_factor':").append("1").append("};");
        String query = sb.toString();
        System.out.println(session.execute(query));


        System.out.println("Closing....");
        connector.close();
    }

}
