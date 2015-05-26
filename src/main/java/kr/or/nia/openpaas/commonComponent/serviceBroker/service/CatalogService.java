package kr.or.nia.openpaas.commonComponent.serviceBroker.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.or.nia.openpaas.commonComponent.serviceBroker.common.ServiceBrokerException;
import kr.or.nia.openpaas.commonComponent.serviceBroker.common.StringUtils;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.CatalogVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.PlanVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ServiceMetadataInMetadataVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ServiceMetadataVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ServiceVO;

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

@Service
public class CatalogService {

	@Autowired 
	LoginService loginService;
	
	public CatalogVO getCatalog() throws ServiceBrokerException {
		return getCatalog("apiuser", "bddhkdlwm12#");
	}
	
	public CatalogVO getCatalog(String username, String password) throws ServiceBrokerException {
		
		CatalogVO catalog = new CatalogVO();
		
		// TODO
		// 1. Check header (X-Broker-API-Version) ?
		// 2. login WSO2 API Manager (Common Module)
		// 3. Get API List in API Manager Store.
		//
		// A. get properties (cloud???)
		// B. 
		
		
		String cookie = "";
		try {
			cookie = loginService.getLogin(username, password);
			catalog.setCookie(cookie);
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
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		
		ResponseEntity<String> response = client.exchange("http://115.68.46.28:9763/store/site/blocks/api/listing/ajax/list.jag?action=getAllPaginatedPublishedAPIs&tenant=carbon.super&start=1&end=100", 
													HttpMethod.GET, 
													entity, 
													String.class);		
		
		System.out.println("status:"+response.getStatusCode());
		System.out.println("result:"+response.getBody());
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode catalogOrg = null;
		
		try {
			// Convert response body(string) to JSON Object
			catalogOrg = mapper.readValue(response.getBody(), JsonNode.class);
			
		} catch (JsonParseException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (JsonMappingException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		} catch (IOException e) {
			throw new ServiceBrokerException(500, "" + e.getMessage());
		}

		// Services VO initialization
		List<ServiceVO> services = new ArrayList<ServiceVO>();
		JsonNode apis = catalogOrg.get("apis");
		
		// Make services VO
		for (JsonNode api : apis) {
			ServiceVO service = new ServiceVO();
			
			service.setId("API_"+api.get("name").asText() + " " + api.get("version").asText());
			service.setName(api.get("name").asText());
			service.setDescription(StringUtils.nullToBlank(api.get("description").asText()));
			service.setBindable(true);
			
			// Meta Data
			ServiceMetadataVO metadata = new ServiceMetadataVO();
			metadata.setName(api.get("name").asText());
			metadata.setDescription(StringUtils.nullToBlank(api.get("description").asText()));
			metadata.setProvider(api.get("provider").asText()); // Customized field OpenPaaS
			
			ServiceMetadataInMetadataVO metadataInMetadata = new ServiceMetadataInMetadataVO();
			metadataInMetadata.setDisplayName(StringUtils.nullToBlank(api.get("name").asText()));
			metadataInMetadata.setDocumentationUrl("");
			metadataInMetadata.setImageUrl(StringUtils.nullToBlank(api.get("thumbnailurl").asText()));
			metadataInMetadata.setLongDescription(StringUtils.nullToBlank(api.get("description").asText()));
			metadataInMetadata.setSupportUrl("");
			metadataInMetadata.setProviderDisplayName(api.get("provider").asText());
			
			metadata.setMetadata(metadataInMetadata);
			service.setMetadata(metadata);
			
			// Plan Data
			// TODO : Get plan informations. (HOWTO??)
			// default : "Unlimited"
			List<PlanVO> plans = new ArrayList<PlanVO>();
			
			PlanVO plan = new PlanVO();
			plan.setId("Unlimited");
			plan.setDescription("Unlimited plan");
			plan.setFree(true);
			plan.setName("Unlimited");
			
			plans.add(plan);
			service.setPlans(plans);

			// Add service vo
			services.add(service);
		}
		
		catalog.setServices(services);

		return catalog;
	}
}
