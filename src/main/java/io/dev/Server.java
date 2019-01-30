package io.dev;

import io.dev.gen.Calculator;
import io.dev.server.CalcService;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * @author evan
 * @date 2019-01-30
 */
public class Server {


    public static class DecoratorProcessor implements TProcessor{
        TProcessor processor;

        public DecoratorProcessor(TProcessor processor) {
            this.processor = processor;
        }

        @Override
        public boolean process(TProtocol in, TProtocol out) throws TException {
            String w = in.readString();
            System.out.println("来自客户端：" +w);
            return processor.process(in,out);
        }
    }

    public static void main(String[] args) {

        CalcService calcService = new CalcService();
        Calculator.Processor<CalcService> processor = new Calculator.Processor<>(calcService);
        DecoratorProcessor decoratorProcessor = new DecoratorProcessor(processor);
        try {
            TServerTransport serverTransport = new TServerSocket(Const.PORT);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(decoratorProcessor));

            // Use this for a multithreaded server
            // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
