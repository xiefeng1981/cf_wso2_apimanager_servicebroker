package kr.or.nia.openpaas.commonComponent.serviceBroker.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController extends CommonController {

	@RequestMapping(value = "/error", method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
	@ResponseBody
	public ErrorVO error(HttpServletRequest request, HttpServletResponse response) {		
		
		System.out.println("error page");
		
	    Map<String, Object> map = new HashMap<String, Object>();
	    ErrorVO resultVO = new ErrorVO();
	    
	    System.out.println("Error Controller request:" + request.toString());
	    
	    if(request.getAttribute("error") != null) {
	        map = (Map<String, Object>) request.getAttribute("error");

	        response.setStatus(Integer.parseInt(map.get("errorCode").toString()));
		    
		    resultVO.setStatus((Integer)map.get("errorCode"));	    
		    resultVO.setMessage(map.get("errorMessage").toString());
		    
		    System.err.println("CODE:" + map.get("errorCode").toString() +", MSG:" + map.get("errorMessage").toString());
	    } else {
	    	resultVO.setStatus(0);
	    	resultVO.setMessage("Error");
	    }
	    
	    return resultVO;		
	}
}
