package utilities;

import java.util.concurrent.TimeUnit;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CustomLogFilter implements Filter {
	private StringBuilder requestBuilderLogs;
	private StringBuilder responseBuilderLogs;

	public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
		Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
		requestBuilderLogs = new StringBuilder();
		requestBuilderLogs.append("\n");
		requestBuilderLogs.append("Request:");
		requestBuilderLogs.append("----------------------------");
		requestBuilderLogs.append("\n");
		requestBuilderLogs.append("Method: " + objectValidation(filterableRequestSpecification.getMethod()));
		requestBuilderLogs.append(System.lineSeparator());
		requestBuilderLogs.append("URI: " + objectValidation(filterableRequestSpecification.getURI()));
		requestBuilderLogs.append("\n");
//		requestBuilderLogs.append("Form Params: " + objectValidation(filterableRequestSpecification.getFormParams()));
//		requestBuilderLogs.append("\n");
//		requestBuilderLogs.append("Request Param: " + objectValidation(filterableRequestSpecification.getRequestParams()));
//		requestBuilderLogs.append("\n");
//		requestBuilderLogs.append("Headers: " + objectValidation(filterableRequestSpecification.getHeaders()));
//		requestBuilderLogs.append("\n");
		requestBuilderLogs.append("Body: " + objectValidation(filterableRequestSpecification.getBody()));
		requestBuilderLogs.append("\n");
		
 		responseBuilderLogs = new StringBuilder();
 		responseBuilderLogs.append("Response:");
 		responseBuilderLogs.append("----------------------------");
 		requestBuilderLogs.append("\n");
		responseBuilderLogs.append("Status: "+response.getStatusLine());
		responseBuilderLogs.append("\n");
		responseBuilderLogs.append("Response Time: "+response.getTime()+" ms");
//		responseBuilderLogs.append("\n");
//		responseBuilderLogs.append("Response Content Type: "+response.getContentType());
//		responseBuilderLogs.append("\n");
//		responseBuilderLogs.append("Response Headers: "+response.getHeaders());
		responseBuilderLogs.append("\n");
		responseBuilderLogs.append("Response Body: "+"\n"+response.getBody().prettyPrint());
		return response;
	}

	public String getRequestBuilder() {
		if(requestBuilderLogs.length() == 0) {
			return null;
		} else {
			return requestBuilderLogs.toString();
		}
	}

	public String getResponseBuilder() {
		if(responseBuilderLogs.length() == 0) {
			return null;
		} else {
			return responseBuilderLogs.toString();
		}
	}

	public String objectValidation(Object o) {
		if (o == null)
			return null;
		else
			return o.toString();
	}

	public void cleanRequestandResponseBuilders() {
		requestBuilderLogs.setLength(0);
		responseBuilderLogs.setLength(0);
	}



}
