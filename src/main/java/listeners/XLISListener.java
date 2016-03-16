package listeners;

import javax.jms.TextMessage;

/**
 * Created by Prosner on 3/16/2016.
 *
 */
public class XLISListener extends JMSReceiver implements MQListener {

    public XLISListener(String queueName, String url) {
        super(queueName, url);
    }

    public void listen() {

    }
}
