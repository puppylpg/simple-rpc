package com.sexycode.simplerpc.client.app;

import com.sexycode.simplerpc.client.service.CalculatorRemoteImpl;
import com.sexycode.simplerpc.common.protocol.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * client，就像调用本地的类做计算一样，但是不知道本地的类其实只是个代理。
 * 它自己并不会这个服务，它是外包给别人做的。
 */
public class ClientApp {
    private static Logger log = LoggerFactory.getLogger(ClientApp.class);

    public static void main(String[] args) {
        // 静态代理
        Calculator calculator = new CalculatorRemoteImpl();
        int result = calculator.add(1, 2);
        log.info("result is {}", result);
    }
}
