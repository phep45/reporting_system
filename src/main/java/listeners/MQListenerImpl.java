package listeners;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prosner on 3/16/2016.
 *
 */
public class MQListenerImpl implements MQListener {

    private List<Message> messages;
    private Session session;
    private String mqName;

    public MQListenerImpl(String mqName) {
        this.mqName = mqName;
        this.messages = new ArrayList<Message>();
        session = SessionFactory.getSession();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void listen() {
        Message msg;
        MessageConsumer consumer;
        while(true) {
            try {
                Destination destination = session.createQueue(mqName);
                consumer = session.createConsumer(destination);
                msg = consumer.receive();
                messages.add(msg);
                System.out.println(((TextMessage)msg).getText());
            }catch (JMSException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    InConnection.getConnection().close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Message> getMessages() {
        return messages;
    }
}
