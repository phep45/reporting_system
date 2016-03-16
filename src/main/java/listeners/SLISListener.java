package listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prosner on 3/16/2016.
 *
 */
public class SLISListener extends JMSReceiver implements MQListener {

    private List<Message> messages;

    public SLISListener(String queueName, String url) {
        super(queueName, url);
        messages = new ArrayList<Message>();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void listen() {
        while(true) {
            super.receiveMsg();
            Message msg = super.getMessage();
            if(msg == null)
                continue;
            messages.add(msg);
            try {
                System.out.println(((TextMessage)msg).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    public List<Message> getMessages() {
        return messages;
    }
}
