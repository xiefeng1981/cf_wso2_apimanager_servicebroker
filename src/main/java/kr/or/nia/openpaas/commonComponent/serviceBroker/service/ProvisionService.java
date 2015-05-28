package kr.or.nia.openpaas.commonComponent.serviceBroker.service;

import java.io.IOException;

import kr.or.nia.openpaas.commonComponent.serviceBroker.common.ServiceBrokerException;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.CatalogVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ProvisionRequestVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ProvisionVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ServiceVO;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ProvisionService {

	@Autowired 
	LoginService loginService;
	
	@Autowired
	CatalogService catalogService;
	
	/**
	 * OpenPaaS에서 provision 요청을 처리합니다.
	 * 
	 * 1) 사용자를 추가 : instance_id를 username으로 생성합니다.
	 * 2) provision을 위한 정보 수집 : API 리스트에서 상세한 정보를 가져옵니다.
	 * 3) API를 Subscribe합니다.
	 * 
	 * @param instance_id	OpenPaaS에서 생성하는 고유의 ID
	 * @param requestVO	서비스팩(Service Broker) 개발자 가이드에 정의되어 있는 Provision시 필요한 항목
	 * @return
	 */
	public ProvisionVO setProvision(String instance_id, ProvisionRequestVO requestVO) {
		
		ProvisionVO provision = new ProvisionVO();
		
		//------------------------
		// Singup user (username is instance_id)
		//------------------------

		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> entity = new HttpEntity<String>("action=addUser&username="+instance_id+"&password=openpaas&allFieldsValues="+instance_id+"|OpenPaaS|apiuser@openpass.org", headers);
		
		ResponseEntity<String> response = client.exchange("http://115.68.46.28:9763/store/site/blocks/user/sign-up/ajax/user-add.jag", 
										HttpMethod.POST, 
										entity, 
										String.class);		

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new ServiceBrokerException(500, "Signup failed. " + response.getStatusCode() + "," + response.getBody());
		}
		
		System.out.println("Signup Result:" + response.getBody());
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonResult = null;
		try {
			// Convert response body(string) to JSON Object
			jsonResult = mapper.readValue(response.getBody(), JsonNode.class);
			
		} catch (JsonParseException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (JsonMappingException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (IOException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		}
		
		if (jsonResult.get("error").asText() == "true") {
			throw new ServiceBrokerException(500, "" + "Can not singup USERNAME is " + instance_id + "("+ jsonResult.get("message").asText() + ")");
		}
		
		//------------------------
		// Subscribe API
		//------------------------
		CatalogVO catalog = catalogService.getCatalog(instance_id, "openpaas");
		String cookie = catalog.getCookie();
		
		// Find provider, tier...
		String apiname = "";
		String version = "";
		String provider = "";
		String plan_id = "";
		System.out.println("#1");
		
		for (ServiceVO service : catalog.getServices()) {
			if (service.getId().equals(requestVO.getService_id())) {
				
				apiname = requestVO.getService_id().split(" ")[0].split("API_")[1];
				version = requestVO.getService_id().split(" ")[1];
				
				provider = service.getMetadata().getProvider();
				
				System.out.println("Plan_id:" + requestVO.getPlan_id());
				
				plan_id = requestVO.getPlan_id().split(" ")[2];
				
				System.out.println("apiname:"+apiname+", version:"+version +", provider:"+provider+", plan_id:"+plan_id);
				
				break;
			}
		}
		
		if ("".equals(apiname)) {
			throw new ServiceBrokerException(400, "Can not find service(API). " + requestVO.toString());
		}
				
		headers.set("Cookie", cookie);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		entity = new HttpEntity<String>("action=addAPISubscription&name="+apiname+"&version="+version+"&provider="+provider+"&tier="+plan_id+"&applicationName=DefaultApplication", headers);
		
		response = client.exchange("http://115.68.46.28:9763/store/site/blocks/subscription/subscription-add/ajax/subscription-add.jag", 
										HttpMethod.POST, 
										entity, 
										String.class);		

		if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
			throw new ServiceBrokerException(500, "Subscription failed. " + response.getStatusCode() + "," + response.getBody());
		}
		
		System.out.println("Subscribe Result:" + response.getBody());		
		
		mapper = new ObjectMapper();
		jsonResult = null;
		try {
			// Convert response body(string) to JSON Object
			jsonResult = mapper.readValue(response.getBody(), JsonNode.class);
			
		} catch (JsonParseException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (JsonMappingException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (IOException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		}
		
		if (jsonResult.get("error").asText() == "true") {
			throw new ServiceBrokerException(500, "" + "Can not subscribe. username: " + instance_id + ", service_id: " + requestVO.getService_id() + ", plan_id: " + requestVO.getPlan_id() + "("+ jsonResult.get("message").asText() + ")");
		}

		
		// Dashboard url
		provision.setDashboard_url("http://115.68.46.28:9763/store/");
		return provision;
	}
}
