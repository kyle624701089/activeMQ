package producter;/**
 * @Author: kyle
 * @Description:
 * @Date: Created in 16:08 2018/4/16
 * @Modified By:
 */

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *消息生产者
 *@author kyle
 *@create 2018 - 04 - 16 16:08
 */
public class JMSProducer {

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
    /**
     * 发送的消息数量
     */
    private static final int SENDNUM = 10;

    /**
     * @author: kyle
     * @date: 2018/4/16 17:04
     * @description:
     * @param: [args]
     * @return: void
     * @throws: 
     */
    public static void main(String[] args) {
        //连接
        Connection connection = null;
        //session
        Session session;
        //消息目的地 Quene 或者 Topic
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;
        //实例化连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME,JMSProducer.PASSWORD,JMSProducer.BROKEURL);

        try{
            //获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //获取session
            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            //通过session创建队列
            destination = session.createQueue("HelloWorld");
            //创建生产者
            messageProducer = session.createProducer(destination);
            //封装发送消息方法
            sendMessage(session,messageProducer);
            //提交事务
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                try{
                    connection.close();
                }catch (JMSException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @author: kyle
     * @date: 2018/4/16 17:09
     * @description:封装消息发送方法
     * @param: [session, messageProducer]
     * @return: void
     * @throws:
     */
    private static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
        for (int i = 0; i < JMSProducer.SENDNUM; i++) {
            //发送
            TextMessage textMessage = session.createTextMessage("ActiveMQ 消息：" + i);
            System.out.println("发送消息：ActiveMQ "+i);
            //通过生产者发送消息
            messageProducer.send(textMessage);
        }
    }
}
