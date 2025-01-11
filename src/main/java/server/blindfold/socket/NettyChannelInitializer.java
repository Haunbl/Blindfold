package server.blindfold.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.blindfold.chat.decoder.TestDecoder;
import server.blindfold.chat.handler.TestHandler;

@Component
@RequiredArgsConstructor
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final TestHandler testHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        TestDecoder testDecoder = new TestDecoder();

        pipeline.addLast(new IdleStateHandler(60,30,0));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8), testDecoder, testHandler);

    }
}
