package com.sexycode.simplerpc.common.reuqest;

import java.io.Serializable;

/**
 * 承载了协议类型、调用协议的方法、参数等。
 */
public class CalculateRpcRequest implements Serializable {

    private static final long serialVersionUID = 7503710091945320739L;

    private String method;
    private int a;
    private int b;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "CalculateRpcRequest{" +
                "method='" + method + '\'' +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
