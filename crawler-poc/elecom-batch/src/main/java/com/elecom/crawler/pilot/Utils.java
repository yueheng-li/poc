package com.elecom.crawler.pilot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Marker sendmail = MarkerFactory.getMarker("Send_Mail");

    public static void read() {
        logger.info("info log");
        
        logger.debug("debug log");

        logger.warn(sendmail, "send mail warn");

        logger.warn("no send mail warn");

        logger.error("send mail error");
    }
}
