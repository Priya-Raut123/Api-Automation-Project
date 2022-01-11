package hooks;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.filter.Filter;
import utilities.CustomLogFilter;

public class Hook {
	private static Filter filter;
	
	public static Filter getFilter() {
		return filter;
	}
	
	@Before
	public void initCustomFilter() {
		filter = new CustomLogFilter();
	}
	
	@AfterStep
	public void logCustomFilter(Scenario scenario) {
		CustomLogFilter logfilter = (CustomLogFilter) filter;
		scenario.log(logfilter.getRequestBuilder()+"\n"+logfilter.getResponseBuilder());
	}
	
	
}
