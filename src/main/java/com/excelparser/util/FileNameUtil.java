package com.excelparser.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class FileNameUtil {

    private FileNameUtil() {}

    public static String generateFileName() {
        // Generate a random 8-character alphanumeric code
        String uuid = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, 8);

        SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy");
        String date = sdf.format(new Date());

        return uuid + "_" + date + ".txt";
    }

}
