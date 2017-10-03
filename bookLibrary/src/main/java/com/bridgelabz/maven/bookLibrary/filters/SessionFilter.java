package com.bridgelabz.maven.bookLibrary.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet Filter implementation class SessionFilter
 */
@WebFilter(urlPatterns = { "/homepage.jsp", "/LibraryController" })
public class SessionFilter implements Filter {

	Log logger = LogFactory.getLog(SessionFilter.class);

	/**
	 * 
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.debug("Session filter started");
	}

	/**
	 * @param request
	 * @param response
	 * @param chain
	 *            Only allow registered and logged in user's to access the given
	 *            pages.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		logger.info("Inside dofilter()");

		// downcast request and response
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		// We are setting the browser to request the mapped url's everytime
		// the user requests for the given pages.
		// This is needed in case after logout, if the user
		// presses back button after logout
		
		  logger.info("Trying to resolve cache issue");
		  httpServletResponse.setHeader("Cache-Control",
		  "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		  httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		  httpServletResponse.setDateHeader("Expires", 0);
		 

		// check if user is logged in or
		HttpSession httpSession = httpServletRequest.getSession();
		logger.info("HttpSession: " + httpSession);
		logger.info("User: " + httpSession.getAttribute("user"));
		if ((httpSession.getAttribute("user") == null)) {
			logger.info("Sorry you cannot access this webpage");
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp?message=NOACCESS");
		} else {

			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		logger.info("SessionFilter destroyed");
	}

}
