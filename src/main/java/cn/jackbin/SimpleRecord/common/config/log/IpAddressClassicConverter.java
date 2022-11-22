package cn.jackbin.SimpleRecord.common.config.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpAddressClassicConverter extends ClassicConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpAddressClassicConverter.class);

    public IpAddressClassicConverter() {
    }

    @Override
    public String convert(ILoggingEvent event) {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error("获取日志Ip异常", e);
            return null;
        }
    }
}