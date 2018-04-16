package consumer;/**
 * @Author: kyle
 * @Description:
 * @Date: Created in 17:10 2018/4/16
 * @Modified By:
 */

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *消息消费者
 *@author kyle
 *@create 2018 - 04 - 16 17:10
 */
public class JMSConsumer {

    /**
     * 默认连接用户名 admin
     */
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;

    /**
     * 默认密码 admin
     */
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    /**
     * 默认链接地址
     */
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        //连接
        Connection connection;
        //session
        Session session;
        //消息目的地 Quene 或者 Topic
        Destination destination;
        //消息生产者
        MessageConsumer messageConsumer;
        //实例化连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME,JMSConsumer.PASSWORD,JMSConsumer.BROKEURL);

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个连接HelloWorld的消息队列
            destination = session.createQueue("HelloWorld");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);

            while (true){
                TextMessage textMessage = (TextMessage) messageConsumer.receive(1000);
                if (textMessage != null){
                    System.out.println("消费者收到消息并消费："+textMessage.getText());
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
