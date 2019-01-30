package io.dev;

import io.dev.gen.Calculator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.UUID;

/**
 * @author evan
 * @date 2019-01-30
 */
public class Client {

    public static class DecoratorBinaryProtocol extends TBinaryProtocol {

        public DecoratorBinaryProtocol(TTransport trans) {
            super(trans);
        }

        public DecoratorBinaryProtocol(TTransport trans, boolean strictRead, boolean strictWrite) {
            super(trans, strictRead, strictWrite);
        }

        public DecoratorBinaryProtocol(TTransport trans, long stringLengthLimit, long containerLengthLimit) {
            super(trans, stringLengthLimit, containerLengthLimit);
        }

        public DecoratorBinaryProtocol(TTransport trans, long stringLengthLimit, long containerLengthLimit, boolean strictRead, boolean strictWrite) {
            super(trans, stringLengthLimit, containerLengthLimit, strictRead, strictWrite);
        }

        @Override
        public void writeMessageBegin(TMessage message) throws TException {
            String s = UUID.randomUUID().toString();
            System.out.println("建立 RequestId："+s);
            writeString(s);
            super.writeMessageBegin(message);
        }
    }

    public static void main(String[] args) throws Exception {

        TSocket socket = new TSocket("localhost", Const.PORT);
        TProtocol protocol = new DecoratorBinaryProtocol(socket);
        Calculator.Client client = new Calculator.Client(protocol);
        socket.open();
        client.ping();
        socket.close();

    }
}
