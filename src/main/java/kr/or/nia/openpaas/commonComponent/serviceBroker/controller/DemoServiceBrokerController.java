package kr.or.nia.openpaas.commonComponent.serviceBroker.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nia.openpaas.commonComponent.serviceBroker.common.CommonController;
import kr.or.nia.openpaas.commonComponent.serviceBroker.service.BindService;
import kr.or.nia.openpaas.commonComponent.serviceBroker.service.CatalogService;
import kr.or.nia.openpaas.commonComponent.serviceBroker.service.ProvisionService;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.BindRequestVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.BindVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.CatalogVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ProvisionRequestVO;
import kr.or.nia.openpaas.commonComponent.serviceBroker.vo.ProvisionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DemoServiceBrokerController extends CommonController {

	
	@Autowired
	CatalogService catalogService;
	
	@Autowired
	ProvisionService provisionService;
	
	@Autowired
	BindService bindService;
	
	@RequestMapping("/v2/catalog")
	@ResponseBody 
	public CatalogVO getCatalog() {
		
		CatalogVO catalog = catalogService.getCatalog();
		
		return catalog;
	}
	
	@RequestMapping(value="/v2/service_instances/{instance_id}", method=RequestMethod.PUT)
	@ResponseBody
	public ProvisionVO setProvision(@PathVariable String instance_id, 
			@RequestBody ProvisionRequestVO requestVO, 
			HttpServletRequest request) {
		
		System.out.println("instance_id:" + instance_id + "body:" + requestVO.toString());
		
		// TODO
		// Username is limited 30 characters.
		instance_id = instance_id.substring(30);
		
		ProvisionVO provision = provisionService.setProvision(instance_id, requestVO);
		
		return provision;
	}

	@RequestMapping(value="/v2/service_instances/{instance_id}/service_bindings/{binding_id}", method=RequestMethod.PUT)
	@ResponseBody
	public BindVO setBind(@PathVariable (value="instance_id") String instance_id,
			@PathVariable (value="binding_id") String binding_id, 
			@RequestBody BindRequestVO requestVO,
			HttpServletRequest request) {
		
		System.out.println("instance_id:" + instance_id + ", binding_id:" + binding_id + ", body:" + requestVO.toString());
		
		// TODO
		// Username is limited 30 characters.
		instance_id = instance_id.substring(30);

		BindVO bind = bindService.setBind(instance_id, binding_id, requestVO);
		
		return bind;
		
	}
}
