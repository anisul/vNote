package com.grasshopper.vnote.Util;

import java.text.SimpleDateFormat;

/**
 * Created by Anisul on 8/14/2015.
 */
public class Utils {
    public static final String APP_VERSION = "1.0.0.0";
    public static String ROOT_USER_EMAIL = "anis.2803@gmail.com";

    static SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a");

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof String) {
            return "".equals(((String)o).trim());
        } else {
            return false;
        }
    }

    public static String includeWithVersion(String url) {
        return url + "?version=" + APP_VERSION;
    }

}
