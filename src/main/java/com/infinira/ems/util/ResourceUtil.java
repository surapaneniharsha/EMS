package com.infinira.ems.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;

public class ResourceUtil {

    private static volatile ResourceUtil instance;
    private  ResourceBundle resourceBundle;

    private static final String MESSAGES_BASE_NAME = "ems_messages";

    private ResourceUtil() {
        try {
            resourceBundle = ResourceBundle.getBundle(MESSAGES_BASE_NAME, Locale.getDefault());
        } catch (Exception ex) { 
            throw new RuntimeException(MessageFormat.format(EMS_001, MESSAGES_BASE_NAME), ex);
        }
    }

    public static ResourceUtil getInstance() {
        if (instance == null) {
            synchronized (ResourceUtil.class) {
                if (instance == null) {
                    instance = new ResourceUtil();
                }
            }
        }
        return instance;
    }
        
    public String getMessages(String key, Object... params) {
        
        if (key == null || key.isEmpty()) {
            return EMS_002;
        }

        String message;
        try {
            message = resourceBundle.getString(key);
            if (message == null || message.isEmpty()) {
                return MessageFormat.format(EMS_003, key);
            }
        } catch (Exception ex) {
               return MessageFormat.format(EMS_004, key, MESSAGES_BASE_NAME);
        }

        try {
            return MessageFormat.format(message, params);
        } catch (Exception ex) {
            return MessageFormat.format(EMS_005, key, params);
        }
    }

    private static final String EMS_001 = "Failed to load resource bundle properties : ''{0}'' . Please check.";
    private static final String EMS_002 = "keys cannot be null or empty in Message property file . Please check.";
    private static final String EMS_003 = "Message for key ''{0}''  cannot be null or empty.";
    private static final String EMS_004 = "Failed to find message key ''{0}'' in ''{1}''";
    private static final String EMS_005 = "Message formating issue for key ''{0}'' with parameters ''{1}''";
}