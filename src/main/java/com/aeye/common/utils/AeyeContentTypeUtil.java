package com.aeye.common.utils;

/**
 * @author shenxingping
 * @date 2021/08/06
 */
public class AeyeContentTypeUtil {
    public AeyeContentTypeUtil() {
    }

    public static String contentType(String filenameExtension) {
        if (filenameExtension.lastIndexOf(".") > -1 && !filenameExtension.startsWith(".")) {
            filenameExtension = filenameExtension.substring(filenameExtension.lastIndexOf("."), filenameExtension.length());
        }

        if (".bmp".equals(filenameExtension.toLowerCase())) {
            return "image/bmp";
        } else if (".gif".equals(filenameExtension.toLowerCase())) {
            return "image/gif";
        } else if (!".jpeg".equals(filenameExtension.toLowerCase()) && !".jpg".equals(filenameExtension.toLowerCase()) && !".png".equals(filenameExtension.toLowerCase())) {
            if (".html".equals(filenameExtension.toLowerCase())) {
                return "text/html";
            } else if (".txt".equals(filenameExtension.toLowerCase())) {
                return "text/plain";
            } else if (".vsd".equals(filenameExtension.toLowerCase())) {
                return "application/vnd.visio";
            } else if (!".pptx".equals(filenameExtension.toLowerCase()) && !".ppt".equals(filenameExtension.toLowerCase())) {
                if (!".docx".equals(filenameExtension.toLowerCase()) && !".doc".equals(filenameExtension.toLowerCase())) {
                    if (".xml".equals(filenameExtension.toLowerCase())) {
                        return "text/xml";
                    } else {
                        return ".pdf".equals(filenameExtension.toLowerCase()) ? "application/pdf" : "";
                    }
                } else {
                    return "application/msword";
                }
            } else {
                return "application/vnd.ms-powerpoint";
            }
        } else {
            return "image/jpeg";
        }
    }

    public static String imgTypeValid(String dataPrix) {
        if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {
            return ".jpeg";
        } else if ("data:image/jpg;".equalsIgnoreCase(dataPrix)) {
            return ".jpg";
        } else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {
            return ".gif";
        } else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {
            return ".png";
        } else if ("data:image/apng;".equalsIgnoreCase(dataPrix)) {
            return ".apng";
        } else if ("data:image/svg;".equalsIgnoreCase(dataPrix)) {
            return ".svg";
        } else {
            return "data:image/bmp;".equalsIgnoreCase(dataPrix) ? ".bmp" : "";
        }
    }
}
