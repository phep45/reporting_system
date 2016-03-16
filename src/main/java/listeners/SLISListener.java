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

    public void listen() {
        while(true) {
            super.receiveMsg();
            messages.add(super.getMessage());
            try {
                System.out.println(((TextMessage)super.getMessage()).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

            }

        }
    }
}
