package com.ejb.sessions;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 19/03/15
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */

@JMSDestinationDefinition(
        name = OrderProcessingBean.CLASSIC_TOPIC,
        resourceAdapter = "jmsra",
        interfaceName = "javax.jms.Topic",
        destinationName = "classicTopic",
        description = "My Sync Topic")
@Stateless(name = "OrderProcessing")
public class OrderProcessingBean {

    public static final String CLASSIC_TOPIC = "java:jboss/jms/classicTopic";

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    /*
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    ConnectionFactory connectionFactory;
    */

    @Resource(mappedName = OrderProcessingBean.CLASSIC_TOPIC)
    private Topic statusTopic;

    public String SendOrderStatus()
    {
        String from = "giulianoc@catrasoftware.it";
        String to = "giulianoc@catrasoftware.it";
        String content =
                "Your order has been processed " + "If you have questions" +
                        " call EJB Application with order id # " + "1234567890";

        try (JMSContext context = connectionFactory.createContext())
        {
            MapMessage message = context.createMapMessage();
            message.setStringProperty("from", from);
            message.setStringProperty("to", to);
            message.setStringProperty("subject", "Status of your wine order");
            message.setStringProperty("content", content);

            context.createProducer().send(statusTopic, message);
        }
        catch (JMSException e) {
            e.printStackTrace();
        }

            /*
        try {
            System.out.println("Before status TopicCF connection");
            Connection connection = statusMessageTopicCF.createConnection();
            System.out.println("Created connection");
            connection.start();
            System.out.println("statted connection");
            System.out.println("Starting Topic Session");
            Session topicSession =
                    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer publisher = topicSession.createProducer(statusTopic);
            System.out.println("created producer");
            MapMessage message = topicSession.createMapMessage();
            message.setStringProperty("from", from);
            message.setStringProperty("to", to);
            message.setStringProperty("subject", "Status of your wine order");
            message.setStringProperty("content", content);
            System.out.println("before send");
            publisher.send(message);
            System.out.println("after send");
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
            */

        return "Created a MapMessage and sent it to StatusTopic";
    }
}
