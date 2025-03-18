/**
 *
 *
 *
 *
 *
 */

package com.aeye.common.validator;

import com.aeye.common.exception.XPException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 *
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new XPException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new XPException(message);
        }
    }
}
