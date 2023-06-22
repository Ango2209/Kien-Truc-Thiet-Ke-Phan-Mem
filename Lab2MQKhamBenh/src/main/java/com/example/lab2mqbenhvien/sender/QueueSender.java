package com.example.lab2mqbenhvien.sender;
import java.util.Date;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.example.lab2mqbenhvien.data.Patient;
import com.example.lab2mqbenhvien.data.Person;
import com.example.lab2mqbenhvien.helper.XMLConvert;
import org.apache.log4j.BasicConfigurator;

public class QueueSender {
    private static Session session;
    private static Connection con;
    private static Destination destination;
    private static MessageProducer producer;
    private final ConnectionFactory factory;

    public QueueSender() throws Exception{
         //config environment for JMS
         BasicConfigurator.configure();
         //config environment for JNDI
         Properties settings = new Properties();
         settings.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                 "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
         settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
         //create context
         Context ctx = new InitialContext(settings);
         //lookup JMS connection factory
          factory =
                  
                 (ConnectionFactory) ctx.lookup("ConnectionFactory");
         //lookup destination. (If not exist-->ActiveMQ create once)
         destination =
                 (Destination) ctx.lookup("dynamicQueues/thanthidet");
         //get connection using credential
      
     }
    public void senRequest(Patient patient) throws Exception{

        con = factory.createConnection("admin", "admin");
        //connect to MOM
        con.start();
        //create session
        session = con.createSession(
                /*transaction*/false,
                /*ACK*/Session.AUTO_ACKNOWLEDGE
        );
        //create producer
        producer = session.createProducer(destination);
        //create text message
        String xml = new XMLConvert<Patient>(patient).object2XML(patient);
        Message msg = session.createTextMessage(xml);
        producer.send(msg);
//        shutdown connection
        session.close();
        con.close();
        System.out.println("Finished...");
    }


}