package kr.or.nia.openpaas.commonComponent.serviceBroker.common;

public class StringUtils {

	static public String nullToBlank(String value) {
		String result = "";
		
		if ("null".equals(value) || "".equals(value) || value.trim().equals("null") || value.trim().equals("")) {
			result = "";
		} else {
			result = value;
		}
		
		return result;
	}
}
