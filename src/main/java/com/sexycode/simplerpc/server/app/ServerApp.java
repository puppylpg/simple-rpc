package com.sexycode.simplerpc.server.app;

import com.sexycode.simplerpc.common.protocol.Calculator;
import com.sexycode.simplerpc.server.service.CalculatorImpl;
import com.sexycode.simplerpc.common.reuqest.CalculateRpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 提供rpc服务。
 * <p>
 * 前期：
 * 接收请求 -> 反序列化 -> 解析协议相关信息（哪个协议，的哪个方法，参数（类型&值）是啥）
 * <p>
 * 后期：
 * 得到服务的结果 -> 序列化结果 -> 发送结果
 */
public class ServerApp {
    private static Logger log = LoggerFactory.getLogger(ServerApp.class);

    // 真正的实现者
    private Calculator calculator = new CalculatorImpl();

    public static void main(String[] args) throws IOException {
        new ServerApp().run();
    }

    private void run() throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    // 将请求反序列化
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Object object = objectInputStream.readObject();

                    log.info("request is {}", object);

                    // 调用服务
                    int result = 0;
                    if (object instanceof CalculateRpcRequest) {
                        CalculateRpcRequest calculateRpcRequest = (CalculateRpcRequest) object;
                        if ("add".equals(calculateRpcRequest.getMethod())) {
                            result = calculator.add(calculateRpcRequest.getA(), calculateRpcRequest.getB());
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    }

                    // 返回结果
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(new Integer(result));
                } catch (Exception e) {
                    log.error("fail", e);
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }

}
