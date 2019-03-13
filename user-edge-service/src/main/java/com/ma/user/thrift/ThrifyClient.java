package com.ma.user.thrift;

import com.ma.thrift.message.MessageService;
import com.ma.thrift.user.UserService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by mh on 2019/3/11.
 */
@Component
public class ThrifyClient {
    @Value("${thrift.user.ip}")
    private String serverIp;

    @Value("${thrift.user.port}")
    private int serverPort;

    @Value("${thrift.message.ip}")
    private String messageServerIp;

    @Value("${thrift.message.port}")
    private int messageServerPort;


    private enum ServiceType {
        USER,
        MESSAGE
    }

//    public UserService.Client getUserService(){
//        TSocket tsocket = new TSocket(serverIp,serverPort,3000);
//        tsocket.setSocketTimeout(1000000);
//        TTransport tTransport = new TFramedTransport(tsocket);
//        try {
//            tTransport.open();
//        } catch (TTransportException e) {
//            e.printStackTrace();
//            return null;
//        }
//        TProtocol tProtocol = new TBinaryProtocol(tTransport);
//        UserService.Client client = new UserService.Client(tProtocol);
//        return client;
//    }


    public UserService.Client getUserService() {

        return getService(serverIp, serverPort, ServiceType.USER);
    }

    public MessageService.Client getMessasgeService() {

        return getService(messageServerIp, messageServerPort, ServiceType.MESSAGE);
    }

    public <T> T getService(String ip, int port, ServiceType serviceType) {
        TSocket socket = new TSocket(ip, port, 3000);
        socket.setSocketTimeout(1000000);
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);

        TServiceClient result = null;
        switch (serviceType) {
            case USER:
                result = new UserService.Client(protocol);
                break;
            case MESSAGE:
                result = new MessageService.Client(protocol);
                break;
        }
        return (T)result;
    }


}
