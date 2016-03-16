package listeners;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;

/**
 * Created by Prosner on 3
 * /16/2016.
 */
public class JMSReceiver implements ExceptionListener {

    private String queueName;
    private String url;

    private Message message;

    public JMSReceiver(String queueName, String url) {
        this.queueName = queueName;
        this.url = url;
    }

    public void receiveMsg() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        connection.setExceptionListener(this);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageConsumer messageConsumer = session.createConsumer(destination);

        message = messageConsumer.receive(1000);

        messageConsumer.close();
        session.close();
        connection.close();
    }

    public Message getMessage() {
        return message;
    }

    public void onException(JMSException e) {
        e.printStackTrace();
    }
}
