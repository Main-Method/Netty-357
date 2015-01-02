package org.runescape.rs2;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.runescape.rs2.network.Rs2ChannelHandler;
import org.runescape.rs2.network.codec.DefaultPacketEncoder;
import org.runescape.rs2.network.codec.login.handshake.Rs2LoginHandshakeDecoder;
import org.runescape.rs2.task.TaskManager;
import org.runescape.rs2.task.impl.WorldProcessor;
import org.runescape.utility.Rs2Constants;
import org.runescape.utility.TimingUtility;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main core of the RuneScape #357 Server.
 *
 * @author Main Method
 */
public final class Rs2Server implements Runnable {

    /**
     * The logger utility for printing information.
     */
    private static final Logger logger =
            Logger.getLogger(Rs2Server.class.getSimpleName());

    /**
     * The singleton object for this class.
     */
    private static Rs2Server singleton;

    private final String host;
    private final int port;
    private final byte world;

    /**
     * Constructs a new Rs2Server.
     *
     * @param host
     * @param port
     * @param world
     */
    public Rs2Server(final String host, final int port, final byte world) {
        this.host = host;
        this.port = port;
        this.world = world;
    }

    /**
     * Boots up the Rs2Server. (Launches needed resources)
     *
     * @return
     *      this class.
     */
    public Rs2Server boot() {
        logger.info("Attempting to start "+ Rs2Constants.SERVER_NAME +"...");
        return this;
    }

    /**
     * Connects the Server to the specified port.
     *
     * @return
     *      this class.
     */
    public Rs2Server bind() {
        final ServerBootstrap serverBootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline channelPipeline = Channels.pipeline();

                channelPipeline.addLast("defaultPacketEncoder", new DefaultPacketEncoder());
                channelPipeline.addLast("rs2LoginHandshakeDecoder", new Rs2LoginHandshakeDecoder());
                channelPipeline.addLast("channelHandler", new Rs2ChannelHandler());
                return channelPipeline;
            }
        });
        serverBootstrap.setOption("keepAlive", true);
        serverBootstrap.bind(new InetSocketAddress(port));
        return this;
    }

    @Override
    public void run() {
        TimingUtility timer = new TimingUtility();

        boot().bind();
        TaskManager.register(new WorldProcessor());
        logger.info(Rs2Constants.SERVER_NAME +" is now connected to port: "+ port +" "+ timer);
    }

    /**
     * Gets the singleton object.
     *
     * @return
     *      the singleton object.
     */
    public static Rs2Server getSingleton() {
        return singleton;
    }

    /**
     * Sets a new singleton object.
     *
     * @param singleton
     *      the new singleton object.
     */
    public static void setSingleton(Rs2Server singleton) {
        if(Rs2Server.singleton != null)
            logger.log(Level.SEVERE, "singleton object already set!");
        Rs2Server.singleton = singleton;
    }
}
