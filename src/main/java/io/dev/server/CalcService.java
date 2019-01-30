package io.dev.server;

import io.dev.gen.Calculator;
import org.apache.thrift.TException;

/**
 * @author evan
 * @date 2019-01-30
 */
public class CalcService implements Calculator.Iface {

    @Override
    public void ping() throws TException {

        System.out.println("DDDDDDDDDDDD");
    }
}
