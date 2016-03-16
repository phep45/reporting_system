package listeners;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class MQListenerImpl implements MQListener {

    private static final int TIMEOUT = 1000;

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
        if (session == null) {
            System.err.println("session failed");
            return;
        }
        Message msg;
        MessageConsumer consumer = null;
        try {
            Destination destination = session.createQueue(mqName);
            consumer = session.createConsumer(destination);
            while(true) {
                msg = consumer.receive(TIMEOUT);
                if (msg == null)
                    continue;
                messages.add(msg);
                System.out.println(((TextMessage) msg).getText());
            }
        }catch (JMSException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (consumer != null) {
                    consumer.close();
                }
                session.close();
                InConnection.getConnection().close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Message> getMessages() {
        return messages;
    }
}