package com.crestasom.pps.filter;

import java.io.IOException;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

import com.crestasom.pps.util.PPSUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class RequestFilter implements Filter {
	/**
	 * to put the req id for logging purpose
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		MDC.put("reqId", PPSUtils.getUUID());
		chain.doFilter(request, response);
	}
}