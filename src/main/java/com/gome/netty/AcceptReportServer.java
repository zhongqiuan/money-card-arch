package com.gome.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class AcceptReportServer {

	private static final String Deafule_Url = "/azq";

	public void run(String url, int port) {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup work = new NioEventLoopGroup();
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(boss, work).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel sc) throws Exception {
							sc.pipeline().addLast(new HttpRequestDecoder()).addLast(new HttpResponseEncoder())
									.addLast(new HttpObjectAggregator(65536)).addLast(new ChunkedWriteHandler())
									.addLast(new HttpReportServerHander());
						}
					});

			ChannelFuture f = sb.bind(port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		int port = 8084;
		if (args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		new AcceptReportServer().run(Deafule_Url, port);
	}

}
