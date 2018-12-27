package com.gome.netty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;

public class HttpReportServerHander extends SimpleChannelInboundHandler<FullHttpRequest> {

	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk
	private HttpPostRequestDecoder decoder;
	 private static final String FILE_UPLOAD = "/data/";
	    private static final String URI = "/upload";
	@Override
	public void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (!request.getDecoderResult().isSuccess()) {
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
	
		
		if (request.getMethod().equals(HttpMethod.GET)) {
			get(ctx, request);
			
		}
		if (request.getMethod().equals(HttpMethod.POST)) {
			System.err.println("===this is http post===");
			try {
				/**
				 * 通过HttpDataFactory和request构造解码器
				 */
				decoder = new HttpPostRequestDecoder(factory, request);
			} catch (ErrorDataDecoderException e1) {
				e1.printStackTrace();
				ctx.channel().close();
				return;
			}
		}
		
		
        if (decoder != null) {
        	writeChunk(ctx);
            if (request instanceof HttpContent) {
                // New chunk is received
                HttpContent chunk = (HttpContent) request;
                System.out.println(chunk);
                try {
                    decoder.offer(chunk);
                } catch (ErrorDataDecoderException e1) {
                    e1.printStackTrace();
                
                    writeResponse(ctx.channel());
                    ctx.channel().close();
                    return;
                }
                try {
                    while (decoder.hasNext()) {
                        InterfaceHttpData data = decoder.next();
                        if (data != null) {
                            try {
                                writeHttpData(data);
                                System.out.println(data);
                            } finally {
                                data.release();
                            }
                        }
                    }
                } catch (EndOfDataDecoderException e1) {
                }
 
                // example of reading only if at the end
                if (chunk instanceof LastHttpContent) {
                    writeResponse(ctx.channel());
                    reset(request);
                }
            }
        }
        
        sendListing(ctx, "azq is handsome");
	}

	private void reset(FullHttpRequest request) {
		request=null;
	        // destroy the decoder to release all resources
	        decoder.destroy();
	        decoder = null;

		
	}
	
	private void writeChunk(ChannelHandlerContext ctx) throws IOException {
        while (decoder.hasNext()) {
            InterfaceHttpData data = decoder.next();
            if (data != null && HttpDataType.FileUpload.equals(data.getHttpDataType())) {
                final FileUpload fileUpload = (FileUpload) data;
                final File file = new File(FILE_UPLOAD + fileUpload.getFilename());
                try (FileChannel inputChannel = new FileInputStream(fileUpload.getFile()).getChannel();
                     FileChannel outputChannel = new FileOutputStream(file).getChannel()) {
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                    ctx.writeAndFlush("成功");
                }
            }
        }
    }

	private void writeHttpData(InterfaceHttpData data) {
		/**
         * HttpDataType有三种类型
         * Attribute, FileUpload, InternalAttribute
         */
        if (data.getHttpDataType() == HttpDataType.Attribute) {
            Attribute attribute = (Attribute) data;
            String value;
            try {
                value = attribute.getValue();
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
            if (value.length() > 100) {
              
            } else {
               
            }
        }
		
	}

	private void writeResponse(Channel channel) {
	
		
       
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if (ctx.channel().isActive()) {
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * post 请求处理参数
	 * @param ctx
	 * @param request
	 */
	private void post(ChannelHandlerContext ctx){
		
		
	}
	/**
	 * get 请求处理参数
	 * @param ctx
	 * @param request
	 */
	private void  get(ChannelHandlerContext ctx ,FullHttpRequest request){
		
		/**处理url中的参数将携带的参数进行格式化到map中
		 * List<String>表示当参数相同时，把相同的参数的值放在list中
		 */
		QueryStringDecoder decoderQuery = new QueryStringDecoder(request.getUri());
		Map<String, List<String>> uriAttributes = decoderQuery.parameters();
	/*	for (Entry<String, List<String>> attr : uriAttributes.entrySet()) {
			for (String attrVal : attr.getValue()) {
				System.out.println(("URI: " + attr.getKey() + '=' + attrVal + "\r\n"));
			}
		}*/
		sendListing(ctx, "azq is handsome");
		
		
		
	}
	
	private static void sendListing(ChannelHandlerContext ctx,String text) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().add("CONTENT-TYPE", "text/html; charset=Utf-8");
		StringBuilder buf = new StringBuilder();
		buf.append("success +请求成功" +text );
		ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
		response.content().writeBytes(buffer);
		buffer.release();
		
		 
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		
		
	}

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
				Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}
