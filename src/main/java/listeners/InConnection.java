package listeners;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class InConnection {


    private static final String URL = "tcp://localhost:61616";
    private static final ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
    private static Connection connection;

    public static Connection getConnection() {
        if(connection != null)
            return connection;
        else {
            try {
                connection = connectionFactory.createConnection();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}
