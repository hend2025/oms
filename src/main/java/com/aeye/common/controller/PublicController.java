/**
 *
 *
 *
 *
 *
 */

package com.aeye.common.controller;


import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置信息
 *
 *
 */
@RestController
@RequestMapping
public class PublicController {

	@Value("${spring.application.version:}")
	private String version;
	@Value("${spring.application.buildTime:}")
	private String buildTime;

	@GetMapping({"/api/version"})
	@ResponseBody
	public WrapperResponse version(HttpServletRequest request) {
		Map<String, Object> result = new HashMap();
		result.put("version", this.version);
		result.put("buildTime", this.modifyTime(this.buildTime));
		return WrapperResponse.success(result);
	}

	private String modifyTime(String date) {
		Date oldDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			oldDate = simpleDateFormat.parse(date);
		} catch (ParseException var5) {
			;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(oldDate);
		calendar.add(11, 8);
		return simpleDateFormat.format(calendar.getTime());
	}
}
