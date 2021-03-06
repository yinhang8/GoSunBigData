package com.hzgc.common.ftp.properties;

import com.hzgc.common.util.file.ResourceFileUtil;
import com.hzgc.common.util.io.IOUtil;
import com.hzgc.common.util.properties.ProperHelper;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class RocketMQProperties extends ProperHelper implements Serializable {

    private static Logger LOG = Logger.getLogger(RocketMQProperties.class);

    private static Properties props = new Properties();
    private static String address;
    private static String topic;
    private static String group;

    static {
        String properName = "rocketmq.properties";
        FileInputStream in = null;
        try {
            File file = ResourceFileUtil.loadResourceFile(properName);
            in = new FileInputStream(file);
            props.load(in);
            setAddress();
            setTopic();
            setGroup();
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Catch an unknown error, can't load the configuration file" + properName);
        } finally {
            IOUtil.closeStream(in);
        }
    }

    /**
     * set方法。验证配置文件中的值是否为符合条件的格式。
     */
    private static void setAddress() {
        address = verifyIpPlusPort("address", props, LOG);
    }

    private static void setTopic() {
        topic = verifyCommonValue("topic", "REALTIME_PIC_MESSAGE", props, LOG);
    }

    private static void setGroup() {
        group = verifyCommonValue("group", "FaceGroup", props, LOG);
    }

    /**
     * get方法。提供获取配置文件中的值的方法。
     */
    public static String getAddress() {
        return address;
    }

    public static String getTopic() {
        return topic;
    }

    public static String getGroup() {
        return group;
    }

    public static Properties getProps() {
        return props;
    }

}
