package kr.or.nia.openpaas.commonComponent.serviceBroker.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class CommonController {

	@ExceptionHandler({ServiceBrokerException.class})
	public String handleIOException(ServiceBrokerException ex, HttpServletRequest request, HttpServletResponse response) {
		
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorCode",  ex.getStatusCode());
		map.put("errorMessage", ex.getCodeMessage());
		request.setAttribute("error",  map);		
		
		System.err.println("Status:" + ex.getStatusCode() +", Message:" + ex.getCodeMessage());
		
		
		// TODO
		// It does not working. forward:error
		return "forward:/error";
	}	
}
