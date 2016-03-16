package listeners;

import javax.jms.TextMessage;

/**
 * Created by Prosner on 3/16/2016.
 *
 */
public interface MQListener extends Receiver {

    void listen();

}
