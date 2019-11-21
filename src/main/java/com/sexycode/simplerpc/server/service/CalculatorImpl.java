package com.sexycode.simplerpc.server.service;

import com.sexycode.simplerpc.common.protocol.Calculator;

/**
 * 真正的{@link Calculator}服务实现。
 */
public class CalculatorImpl implements Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
