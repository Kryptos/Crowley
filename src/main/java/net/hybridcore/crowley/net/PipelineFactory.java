package net.hybridcore.crowley.net;

import net.hybridcore.crowley.net.codec.Decoder;
import net.hybridcore.crowley.net.codec.Encoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class PipelineFactory implements ChannelPipelineFactory {
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("decoder", new Decoder());
        pipeline.addLast("encoder", new Encoder());
        pipeline.addLast("handler", new ChannelHandler());
        pipeline.addLast("pipelineExecutor", new ExecutionHandler(
                new OrderedMemoryAwareThreadPoolExecutor(
                        200,
                        1048576,
                        1073741824,
                        100,
                        TimeUnit.MILLISECONDS,
                        Executors.defaultThreadFactory()
                )
        ));

        return pipeline;
    }
}
