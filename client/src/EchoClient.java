import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class EchoClient {
    private final String host;
    private final int port;
    private String commandStr;
    public EchoClient() {
        this(0);
    }

    public EchoClient(int port) {
        this("localhost", port);
    }

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start(String commandNumber,String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            try{
                                System.out.println("正在连接中...");
                                ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                                ch.pipeline().addLast(new EchoClientHandler(commandNumber,host));
                                ch.pipeline().addLast(new ByteArrayEncoder());
                                ch.pipeline().addLast(new ChunkedWriteHandler());
                            }catch (Exception e){
                                System.out.println("无法连接服务器或服务器没有开启服务...");
                                e.printStackTrace();
                            }
                        }
                    });
            // System.out.println("服务端连接成功..");

            ChannelFuture cf = b.connect().sync(); // 异步连接服务器
            System.out.println("服务端连接成功..."); // 连接完成

            cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
            System.out.println("连接已关闭.."); // 关闭完成

        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }
    }

    public static void main(String[] args) throws Exception {
       /* if (args.length > 0) {
            String commandNumber = args[0].toString();
            if(commandNumber.equals("0000")){
                new EchoClient("120.53.240.110", 65534).start(commandNumber); // 连接127.0.0.1/65535，并启动
            }else{
                System.out.println("parameter error");
            }
        } else {
            System.out.println("Please enter the command parameters");
        }*/
       //arg[0]
        String arg0 = "127.0.0.1";
        String arg1 = "0000";
        new EchoClient(arg0, 65534).start(arg1,arg0); // 连接127.0.0.1/65535，并启动
    }
}