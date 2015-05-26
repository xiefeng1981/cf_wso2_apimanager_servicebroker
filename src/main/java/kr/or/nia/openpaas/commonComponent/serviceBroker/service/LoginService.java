package kr.or.nia.openpaas.commonComponent.serviceBroker.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.or.nia.openpaas.commonComponent.serviceBroker.common.ServiceBrokerException;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.CatalogVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ServiceVO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoginService {

	public String getLogin(String username, String password) {
		
		String cookie = "";
		
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> entity = new HttpEntity<String>("action=login&username="+username+"&password="+password, headers);
		
		ResponseEntity<String> response = client.exchange("http://115.68.46.28:9763/store/site/blocks/user/login/ajax/login.jag", 
										HttpMethod.POST, 
										entity, 
										String.class);		

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new ServiceBrokerException(401, "Login failed. " + response.getStatusCode() + "," + response.getBody());
		}
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode loginResult = null;
		try {
			// Convert response body(string) to JSON Object
			loginResult = mapper.readValue(response.getBody(), JsonNode.class);
			
		} catch (JsonParseException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (JsonMappingException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (IOException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		}
		
		if (loginResult.get("error").asText() == "true") {
			throw new ServiceBrokerException(401, "" + loginResult.get("message").asText());
		}
		
		System.out.println("status:"+response.getStatusCode() + "result:"+response.getBody());
		
		cookie = response.getHeaders().getFirst("Set-Cookie");
		System.out.println("Cookie:" + cookie);		
		
		return cookie;
	}
}
