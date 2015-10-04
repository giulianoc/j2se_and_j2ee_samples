package com.ejb.sessions;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 19/03/15
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = OrderProcessingBean.CLASSIC_TOPIC),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})

public class StatusMailerBean implements javax.jms.MessageListener
{

    public void onMessage(Message message) {
        try {
            if (message instanceof MapMessage) {
                MapMessage orderMessage = (MapMessage)message;
                String from = orderMessage.getStringProperty("from");
                String to = orderMessage.getStringProperty("to");
                String subject = orderMessage.getStringProperty("subject");
                String content = orderMessage.getStringProperty("content");

                System.out.println("MDB: Sending Message... from: " + from + ", to: " + to + ", subject: " + subject + ", content: " + content);
                // ...
                System.out.println("MDB: Message Sent");
            }
            else {
                System.out.println("Invalid message ");
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
