package listeners;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Prosner on 3
 * /16/2016.
 */
public class JMSReceiver implements Receiver, ExceptionListener {

    private static final int TIMEOUT = 1000;
    private String queueName;
    private String url;

    public JMSReceiver(String queueName, String url) {
        this.queueName = queueName;
        this.url = url;
    }

    public Message receive() {
        MessageConsumer messageConsumer = null;
        Session session = null;
        Connection connection = null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();
            connection.setExceptionListener(this);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            messageConsumer = session.createConsumer(destination);

            return messageConsumer.receive(TIMEOUT);

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (messageConsumer != null) {
                    messageConsumer.close();
                }
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void onException(JMSException e) {
        e.printStackTrace();
    }
}
