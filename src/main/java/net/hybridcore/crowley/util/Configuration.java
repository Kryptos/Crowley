package net.hybridcore.crowley.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Configuration {
    private Properties props;

    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    public Configuration() {
        props = new Properties();
    }

    public void load(String file) {
        try {
            logger.info("Loading configuration file: " + file);
            props.load(new FileInputStream(file));
        } catch (IOException e) {
            logger.fatal("Failed to load configuration file (" + file + ")");
            System.out.println(e.getStackTrace());
        }
    }

    public int size() {
        return props.size();
    }

    public String getString(String key) {
        return props.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }
}
