package com.sinoif.esbimpl.port.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p></p>
 *
 * @author chenxj
 * @date 2019/10/22
 */
public class HttpUtils {

    private static Logger logger = Logger.getLogger(HttpUtils.class);

    public static String getRequestPostJson(HttpServletRequest request) {
        try {
            byte buffer[] = getRequestPostBytes(request);
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            return new String(buffer, charEncoding);
        } catch (Exception e) {
            logger.error("转换异常", e);
        }
        return "";
    }

    private static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }
}
