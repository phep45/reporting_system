package listeners;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Prosner on 3
 * /16/2016.
 */
public class JMSReceiver implements ExceptionListener {

    public static final int TIMEOUT = 1000;
    private String queueName;
    private String url;

    private Message message;

    public JMSReceiver(String queueName, String url) {
        this.queueName = queueName;
        this.url = url;
    }

    public void receiveMsg() {
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

            message = messageConsumer.receive(TIMEOUT);

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                messageConsumer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public Message getMessage() {
        return message;
    }

    public void onException(JMSException e) {
        e.printStackTrace();
    }
}
