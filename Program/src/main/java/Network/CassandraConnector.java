package Network;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Cluster.Builder;

public class CassandraConnector {

    private Cluster cluster;

    private Session session;

    public void connect(String node, Integer port) {
        Builder b = Cluster.builder().addContactPoint(node);
        if (port != null) {
            b.withPort(port);
        }
        cluster = b.build();

        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

    private ResultSet query(String query) {
        return session.execute(query);
    }

    public ResultSet insert(
            String timestamp,
            int scooter_id,
            String status,
            int error_code,
            String error_message,
            double lan,
            double lon
    ) {
        //Validate input here later
        String values = String.format("(%s, %2d, %s, %2d, %s, %3.6f, %3.6f)", timestamp, scooter_id, status, error_code, error_message, lan, lon);
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("test123.errors")
                .append(" (time_stamp, scooter_id, status_text, error_code, error_message, lang, long)")
                .append("VALUES ").append(values).append(";");
        return this.query(sb.toString());
    }


    public void init() {
        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append("test123").append(" WITH replication = {").append("'class':'").append("SimpleStrategy").append("','replication_factor':").append("1").append("};");
        String query = sb.toString();
        String anotherquery = "CREATE TABLE errors(\n" +
                "\ttime_stamp timestamp PRIMARY KEY,\n" +
                "\tscooter_id int,\n" +
                "\tstatus text,\n" +
                "\terror_code int,\n" +
                "\terror_message text,\n" +
                "\tlang double,\n" +
                "\tlong double\n" +
                ");";
        this.query(query);
        this.query(anotherquery);
    }

    public void close() {
        session.close();
        cluster.close();
    }
}
