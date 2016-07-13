package com.eMart.aws.sqs;


import com.amazonaws.services.elasticbeanstalk.model.SystemStatus;
import com.amazonaws.services.sqs.AmazonSQS;
import com.eMart.model.AuditLog;
import com.eMart.services.SQSUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by maharshigor on 11/07/16.
 */

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

	private long startTime;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod ) handler;
		startTime = System.currentTimeMillis ();
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		try {
			long timestamp = System.currentTimeMillis();
			long duration = timestamp - startTime;
			String url = request.getRequestURL().toString();
			String requestType = request.getMethod ();
			String ipAddress = request.getRemoteAddr();
			Map<String,List<String>> paramData = new HashMap<String,List<String>> ();
			Enumeration<String> parameterNames = request.getParameterNames();

			while (parameterNames.hasMoreElements()) {
				String paramName = parameterNames.nextElement();
				List<String> paramValues = Arrays.asList (request.getParameterValues(paramName));
				paramData.put (paramName,paramValues);
			}
			ObjectMapper mapper = new ObjectMapper();
			String paramJson = mapper.writeValueAsString(paramData);
			int responseCode = response.getStatus();

			AuditLog log = new AuditLog(url, ipAddress, timestamp, responseCode,duration, requestType,paramJson);
			String logJson = mapper.writeValueAsString (log);
			SQSUtils.sendMessage(logJson);

		} catch(Exception e) {
			//TODO: catch IOException for getReader()
		}
	}
}