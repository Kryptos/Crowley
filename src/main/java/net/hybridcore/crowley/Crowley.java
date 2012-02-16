package net.hybridcore.crowley;

import net.hybridcore.crowley.habbo.HabboHotel;
import net.hybridcore.crowley.net.PipelineFactory;
import net.hybridcore.crowley.util.Configuration;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Crowley {
    private static final Logger logger = Logger.getLogger(Crowley.class.getName());

    public static final int VERSION_MAJOR = 0;
    public static final int VERSION_MINOR = 1;
    public static final int VERSION_BUILD = 2;
    public static final int VERSION_REVISION = 31;

    public static final String TARGET_CLIENT = "RELEASE63-35255-34886-201108111108";
    public static final String DEFAULT_CONFIG = "src/main/resources/styx.props";

    private static final Configuration configuration = new Configuration();
    private static final HabboHotel habboHotel = new HabboHotel();
    private static SessionFactory sessionFactory;
    private static ExecutorService executorService;

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static SessionFactory getDatastore() {
         return sessionFactory;
    }
    
    public static HabboHotel getHabbo() {
        return habboHotel;
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static Session getDatabaseSession() {
        Session session = getDatastore().getCurrentSession();

        if (session == null) {
            session = getDatastore().openSession();
        }

        return session;
    }

    public static void main(String[] args) {

        System.out.println("");
        System.out.printf(" Crowley \"Styx\" (v%d.%d.%d-%d)\r\n Supported client: %s\r\n", VERSION_MAJOR, VERSION_MINOR, VERSION_BUILD, VERSION_REVISION, TARGET_CLIENT);
        System.out.println("");

        setupConfiguration(args);
        setupHibernate();
        setupExecutorService();
        setupNetty();
    }

    public static void setupConfiguration(String[] propFiles) {
        if (new File(DEFAULT_CONFIG).exists()) {
            logger.info("Default configuration file found (" + DEFAULT_CONFIG + ")");
            getConfiguration().load(DEFAULT_CONFIG);
        } else {
            logger.warn("Failed to load default configuration file (" + DEFAULT_CONFIG + ")");
        }

        for (String configFile : propFiles) {
            getConfiguration().load(configFile);
        }

        logger.info("Configuration built with " + getConfiguration().size() + " properties");
    }

    public static void setupHibernate() {
        logger.info("Attempting to configure hibernate");
        sessionFactory = (new org.hibernate.cfg.Configuration()).configure().buildSessionFactory();
    }

    public static void setupExecutorService() {
        logger.info("Attempting to configure the executor service");
        int threadCount = getConfiguration().getInt("net.hybridcore.crowley.util.executor-service.thread-count");

        if (! (threadCount > 4)) {
            threadCount = 4;
        }

        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public static void setupNetty() {
        logger.info("Creating channel factory");
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()
        );

        logger.info("Attempting to bootstrap server");
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new PipelineFactory());

        String serverAddress = getConfiguration().getString("net.hybridcore.crowley.net.serverAddress");
        int serverPort = getConfiguration().getInt("net.hybridcore.crowley.net.serverPort");

        try {
            bootstrap.bind(new InetSocketAddress(serverAddress, serverPort));
        } catch (ChannelException ce) {
            logger.fatal(ce.getMessage());
            getDatastore().close();
            return;
        }

        logger.info("Server bound on " + serverAddress + ":" + serverPort);
    }
}
