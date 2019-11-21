package com.sexycode.simplerpc.client.service;

import com.sexycode.simplerpc.common.protocol.Calculator;
import com.sexycode.simplerpc.common.reuqest.CalculateRpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理类。碰到问题，就找外包。
 * 这里外包的联系方式是自己记在小本本上的（ArrayList）。
 * <p>
 * 请求：
 * 构造请求内容 -> 序列化 -> 发给外包 -> 等响应
 * <p>
 * 接收：
 * 接收响应结果 -> 反序列化 -> 返回值给调用者
 */
public class CalculatorRemoteImpl implements Calculator {
    public static final int PORT = 9090;
    private static Logger log = LoggerFactory.getLogger(CalculatorRemoteImpl.class);

    public int add(int a, int b) {
        List<String> addressList = lookupProviders("Calculator.add");
        String address = chooseTarget(addressList);
        try {
            Socket socket = new Socket(address, PORT);

            // 将请求序列化
            CalculateRpcRequest calculateRpcRequest = generateRequest(a, b);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // 将请求发给服务提供方
            objectOutputStream.writeObject(calculateRpcRequest);

            // 这是一个简单地阻塞式响应接收

            // 将响应体反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object response = objectInputStream.readObject();

            log.info("response is {}", response);

            if (response instanceof Integer) {
                return (Integer) response;
            } else {
                throw new InternalError();
            }

        } catch (Exception e) {
            log.error("fail", e);
            throw new InternalError();
        }
    }

    private CalculateRpcRequest generateRequest(int a, int b) {
        CalculateRpcRequest calculateRpcRequest = new CalculateRpcRequest();
        calculateRpcRequest.setA(a);
        calculateRpcRequest.setB(b);
        calculateRpcRequest.setMethod("add");
        return calculateRpcRequest;
    }

    private String chooseTarget(List<String> providers) {
        if (null == providers || providers.size() == 0) {
            throw new IllegalArgumentException();
        }
        return providers.get(0);
    }

    public static List<String> lookupProviders(String name) {
        List<String> providers = new ArrayList();
        providers.add("127.0.0.1");
        return providers;
    }
}
