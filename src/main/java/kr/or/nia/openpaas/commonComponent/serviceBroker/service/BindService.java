package kr.or.nia.openpaas.commonComponent.serviceBroker.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.nia.openpaas.commonComponent.serviceBroker.common.ServiceBrokerException;
import kr.or.nia.openpaas.commonComponent.serviceBroker.common.StringUtils;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.BindRequestVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.BindVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.CredentialVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.PlanVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ServiceMetadataInMetadataVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ServiceMetadataVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ServiceVO;

@Service
public class BindService {

	@Autowired
	LoginService loginService;
	
	public BindVO setBind(String instance_id, String binding_id, BindRequestVO requestVO) {

		BindVO bind = new BindVO();

		String cookie = "";
		try {
			cookie = loginService.getLogin(instance_id, "openpaas");
		} catch (ServiceBrokerException se) {
			System.err.println("" +  se.getStatusCode() + "," + se.getCodeMessage());
			throw se;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();	
		
		// Get published APIs
		headers.set("Cookie", cookie);
//		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		
		ResponseEntity<String> response = client.exchange("http://115.68.46.28:9763/store/site/blocks/subscription/subscription-list/ajax/subscription-list.jag?action=getAllSubscriptions", 
													HttpMethod.GET, 
													entity, 
													String.class);		
		
		System.out.println("status:"+response.getStatusCode());
		System.out.println("result:"+response.getBody());
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode subscriptionOrg = null;
		
		try {
			// Convert response body(string) to JSON Object
			subscriptionOrg = mapper.readValue(response.getBody(), JsonNode.class);
			
		} catch (JsonParseException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (JsonMappingException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (IOException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		}

		// Credential VO initialization
		List<CredentialVO> credentials = new ArrayList<CredentialVO>();
		JsonNode subscriptions = subscriptionOrg.get("subscriptions").get(0).get("subscriptions");
		
		// Make Credential VO
		for (JsonNode subscription : subscriptions) {
			
			CredentialVO credential = new CredentialVO();
			
			// TODO
			// 아래의 부분을 Store 정의서의 (WEB) Get API Information, (WEB) Get API detail information  을 이용하여 더 구체적인 정보를 가져올수 있게 합니다.
			// path 마다의 URI, Parameter(이 부분은 VO를 추가)
			
			credential.setName(subscription.get("name").asText());
			credential.setUri("https://115.68.46.28:8243/" + subscription.get("name").asText() + "/" + subscription.get("version").asText());
			credential.setUsername(instance_id);
			credential.setPassword("openpaas");
			
			// Add credential VO
			credentials.add(credential);
		}
		
		bind.setCredentials(credentials);
		
		return bind;
	}

}
