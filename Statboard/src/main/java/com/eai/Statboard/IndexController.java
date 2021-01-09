package com.eai.Statboard;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private Cluster cluster;

    private Session session;

    @GetMapping("/")
    public String index(Model model) {

        if(this.session == null){
            this.connect("cassandra-db-1", null);
        }
        String query_broken = "SELECT count(*) \n" +
        "FROM test123.errors \n" +
        "WHERE status='broken' ALLOW FILTERING";

        String query_fixed = "SELECT count(*) \n" +
                "FROM test123.errors \n" +
                "WHERE status='fixed' ALLOW FILTERING";

        Long broken = (session.execute(query_broken)).one().getLong(0);
        Long fixed = (session.execute(query_fixed)).one().getLong(0);

        model.addAttribute("sum_broken", broken);
        model.addAttribute("sum_fixed", fixed);
        return "index";
    }

    private void connect(String node, Integer port) {
        Cluster.Builder b = Cluster.builder().addContactPoint(node);
        if (port != null) {
            b.withPort(port);
        }
        cluster = b.withoutJMXReporting().build();

        session = cluster.connect();
    }
}