package listeners;

import javax.jms.TextMessage;

/**
 * Created by Prosner on 3/16/2016.
 *
 */
public class SLISListener extends JMSReceiver implements MQListener {

    public SLISListener(String queueName, String url) {
        super(queueName, url);
    }

    public void listen(TextMessage message) {

    }
}
