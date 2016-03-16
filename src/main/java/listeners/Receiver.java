package listeners;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by Prosner on 3/16/2016.
 *
 */
public interface Receiver {

    Message receive();
//    Message getMessage();
}
