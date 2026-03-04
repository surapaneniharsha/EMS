package com.infinira.ems.util;

import com.infinira.ems.exception.EmsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {

    private static Logger logger;

    private static final String EMS_001 = "Failed to load the log configuration file";

    static {
        try {
            logger = LogManager.getLogger(Util.class); 
        } catch (Exception ex) {
            throw new RuntimeException(EMS_001, ex);
        }
    }
	
    public static EmsException createEx(String messageCode, Throwable cause, Object...params) {
        String errorMessage = ResourceUtil.getInstance().getMessages(messageCode, params);
        logger.error(errorMessage, cause);
        return new EmsException(errorMessage, cause);
    }
    
}



