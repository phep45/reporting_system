package listeners;

import javax.jms.*;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class SessionFactory {


    public static Session getSession() {
        try {
            InConnection.getConnection().start();
            return InConnection.getConnection().createSession(false, AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        finally {
            try {
                InConnection.getConnection().close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

