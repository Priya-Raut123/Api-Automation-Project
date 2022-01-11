package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import hooks.Hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
//import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;

public class CRM_steps {
	private Properties prop;
	private FilterableRequestSpecification httpRequest;
	private Response response = null;
	private static String borrowerID = "";
	private static String leadID = "";
	private static String xAuthId =""; 
	private static String xAuthToken ="";
	private static String creditAuthId="";
	private static String creditAuthToken="";
	public CRM_steps(){
		prop = ConfigReader.getGlobalDataProperties();
		httpRequest = (FilterableRequestSpecification) RestAssured.given().filter(Hook.getFilter());
	}
	
	private void setHeader() {
		httpRequest.removeHeaders();
		httpRequest.header("x-auth-id", xAuthId);
		httpRequest.header("x-auth-token", xAuthToken);
	}
	private void setHeaderTwo() {
		httpRequest.removeHeaders();
		httpRequest.header("x-auth-id", creditAuthId);
		httpRequest.header("x-auth-token", creditAuthToken);
	}
	

	@Given("Login to crm")
	public void login(Map<String, String> data) {
		String url = prop.getProperty("BASE_URL1") + "/login";
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(data.get("body"));
		httpRequest.baseUri(url);
		response = httpRequest.log().all().post();
		ExcelUtils.printReport.printRowData(3, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		response.then().log().all();
		assertEquals(200, response.statusCode());
		JSONObject json_response = (JSONObject) JSONValue.parse(response.body().asString());
		JSONObject response_data = (JSONObject) JSONValue.parse(json_response.get("data").toString());
		xAuthId = response_data.get("user_id").toString();
		xAuthToken = response_data.get("token").toString();
	}

	@Given("Get the product types")
	public void get_product_types() {
		String url = prop.getProperty("BASE_URL1") + "/product-type";
		setHeader();
		httpRequest.body("");
		httpRequest.baseUri(url);
		response = httpRequest.log().all().get();
		ExcelUtils.printReport.printRowData(4, url, "get", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}
	
	@Given("Get the source")
	public void source() {
		String url = prop.getProperty("BASE_URL1") + "/source";
		setHeader();
		httpRequest.body("");
		httpRequest.baseUri(url);
		response = httpRequest.log().all().get();
		ExcelUtils.printReport.printRowData(5, url, "get", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}
	
	@Then("get sub source")
	public void subsource(Map<String, String> data) {
		String url = prop.getProperty("BASE_URL1") + "/subSource";
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(data.get("body"));
		httpRequest.baseUri(url);
		response = httpRequest.log().all().post();
		ExcelUtils.printReport.printRowData(6, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		response.then().log().all();
		assertEquals(200, response.statusCode());
	} 
	
	@Given("Create a borrower")
	public void create_borrower(Map<String, String> data) {
		String url = prop.getProperty("BASE_URL1") + "/borrowers";
		setHeader();
		httpRequest.header("Content-Type", "application/json;charset=UTF-8");
		httpRequest.baseUri(url);
		httpRequest.body(data.get("body"));
		response = httpRequest.log().all().post();
		ExcelUtils.printReport.printRowData(7, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		JSONObject json_response = (JSONObject) JSONValue.parse(response.body().asString());
		JSONObject response_data = (JSONObject) JSONValue.parse(json_response.get("data").toString());
		borrowerID = response_data.get("borrowerUuid").toString();
	}

	@Then("Create a lead")
	public void create_lead(Map<String, String> data) {
		String url = prop.getProperty("BASE_URL1") + "/leads/create/" + borrowerID;
		setHeader();
		httpRequest.header("Content-Type", "application/json;charset=UTF-8");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), borrowerID));
		response = httpRequest.log().all().post();
		ExcelUtils.printReport.printRowData(8, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
		JSONObject json_response = (JSONObject) JSONValue.parse(response.body().asString());
		JSONObject response_data = (JSONObject) JSONValue.parse(json_response.get("data").toString());
		leadID = response_data.get("id").toString();
		
	}
	
	@Given("History")
	public void History() {
		String url = prop.getProperty("BASE_URL1") + "/leads/" + leadID +"/history";
		setHeader();
		httpRequest.baseUri(url);
		response = httpRequest.log().all().get();
		ExcelUtils.printReport.printRowData(9, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
		
	}
	
	@Then("Get Consent")
	public void GetConsent() {
		String url = prop.getProperty("BASE_URL1") + "/consent/" + leadID;
		setHeader();
		httpRequest.baseUri(url);
		response = httpRequest.log().all().get();
		ExcelUtils.printReport.printRowData(10, url, "get", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
		
	}		

	@Then("Sent a mail")
	public void sentSMS(Map<String, String> data) {
		String url = prop.getProperty("BASE_URL1") + "/send_mail";
		setHeader();
		httpRequest.header("Content-Type", "application/json;charset=UTF-8");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), leadID));
		response = httpRequest.log().all().post();
		ExcelUtils.printReport.printRowData(11, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();

	}
	
	@Then("Upload Documents Details")
	public void UploadDocuments() {
		String url = prop.getProperty("BASE_URL1") + "/uploadeddocuments/" + leadID;
		setHeader();
		httpRequest.baseUri(url);
		response = httpRequest.log().all().get();
		ExcelUtils.printReport.printRowData(12, url, "get", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
		

	}
	
	@Then("Details of Lead")
	public void DetailsLead() {
		String url = prop.getProperty("BASE_URL1") + "/leads/" + leadID +"/details";
		setHeader();
		httpRequest.baseUri(url);
		response = httpRequest.log().all().get();
		ExcelUtils.printReport.printRowData(13, url, "get", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
		

	}
	
	@Then("Overview of Lead")
	public void OverviewLead() {
		String url = prop.getProperty("BASE_URL1") + "/overviewdata/" + leadID;
		setHeader();
		httpRequest.baseUri(url);
		response = httpRequest.log().all().get();
		ExcelUtils.printReport.printRowData(14, url, "get", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
		

	}
	
	@Then("City List of India")
	public void CityList() {
		String url = prop.getProperty("BASE_URL1") + "/StateCityList/India";
//		setHeader();
		httpRequest.header("x-auth-id","393");
		httpRequest.header("x-auth-token","d03ce27674c5e0d17567ad78dae52a8b");
		httpRequest.baseUri(url);
		response = httpRequest.log().all().get();
		ExcelUtils.printReport.printRowData(15, url, "get", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}
	
	@Then("Complete Fill Data")
	public void FillData() {
		String url = prop.getProperty("BASE_URL1") + "/completefillingdata";
		setHeader();
		httpRequest.baseUri(url);
		response = httpRequest.log().all().get();
		ExcelUtils.printReport.printRowData(16, url, "get", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
		

	}
	
//	@Then("Add a College")
//	public void AddCollege(Map<String, String> data) {
//		String url = prop.getProperty("BASE_URL1") + "/addcollege";
//		setHeader();
//		httpRequest.header("Content-Type", "application/json");
//		httpRequest.baseUri(url);
//		httpRequest.body(data.get("body"));
//		response = httpRequest.log().all().post();
//		assertEquals(200, response.statusCode());
//		response.then().log().all();
//
//	}
	
	@Then("edit Overview Data")
	public void SaveOverviewData(Map<String, String> data) {
		String url = prop.getProperty("BASE_URL1") + "/overviewdata" + leadID;
		setHeader();
		httpRequest.header("Content-Type", "application/json;charset=UTF-8");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), borrowerID, borrowerID));
		response = httpRequest.log().all().post();
		ExcelUtils.printReport.printRowData(17, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();

	}
	
	@Then("upload document")
	public void uploadDocu() {

		String url = prop.getProperty("BASE_URL1") + "/upload-multiple";
		setHeaderTwo();
		httpRequest.header("Content-Type", "multipart/form-data");
		httpRequest.baseUri(url);
		File file = new File(System.getProperty("user.dir") + "/data/heart.jpg");
		httpRequest.multiPart("file[0]", file);
		httpRequest.formParam("documentType", "12TH_CERTIFICATE");
		httpRequest.formParam("fileName", "12TH_CERTIFICATE");
		httpRequest.formParam("reference", "Borrower");
		httpRequest.formParam("referenceId", borrowerID);
		httpRequest.formParam("leadId", leadID);
		response = httpRequest.log().all().post();
		ExcelUtils.printReport.printRowData(18, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}

	
	
	@Given("Login to credit using {} and {}")
	public void loginCredit(String user_name,String password) {
		System.out.println(user_name);
		String url = prop.getProperty("BASE_URL2") + "/login";
		httpRequest.header("Content-Type", "multipart/form-data");
		httpRequest.multiPart("user_name",user_name);
		httpRequest.multiPart("password", password);
		httpRequest.baseUri(url);
		response = httpRequest.log().all().post();
		response.then().log().all();
		ExcelUtils.printReport.printRowData(19, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		JsonPath res = response.jsonPath();
		creditAuthId = res.getString("data.user_id");
		creditAuthToken = res.getString("data.token");
		assertEquals(200, response.statusCode());
	}
	

	@Then("Edit Basic Details")
	public void editBasicDetails(Map<String, String> data) {
		String url = prop.getProperty("BASE_URL2") + "/borrowers/" + borrowerID;
		setHeaderTwo();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), borrowerID, leadID));
		response = httpRequest.log().all().put();
		ExcelUtils.printReport.printRowData(20, url, "put", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}

	@Then("Edit Borrower Advance")
	public void editBorrowerAd(Map<String, String> data) {
		String url = prop.getProperty("BASE_URL2") + "/borrowers/advance/" + borrowerID;
		setHeaderTwo();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), borrowerID, leadID));
		response = httpRequest.log().all().put();
		ExcelUtils.printReport.printRowData(21, url, "put", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}

	@Then("Edit Borrowers Address")
	public void editBorrowerAddress(Map<String, String> data) {
		String url = prop.getProperty("BASE_URL2") + "/borrowers/address/" + borrowerID;
		setHeaderTwo();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), borrowerID, leadID));
		response = httpRequest.log().all().put();
		ExcelUtils.printReport.printRowData(22, url, "put", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}

	@Then("Edit Bank Details")
	public void editBankDetails(Map<String, String> data) {

		String url = prop.getProperty("BASE_URL2") + "/banking/" + borrowerID;
		setHeaderTwo();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), borrowerID));
		response = httpRequest.log().all().post();
		ExcelUtils.printReport.printRowData(23, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}

	@Then("Edit Eductaion Details")
	public void editEducationDetails(Map<String, String> data) {

		String url = prop.getProperty("BASE_URL2") + "/borrowers/education/" + borrowerID;
		setHeaderTwo();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), borrowerID, leadID));
		response = httpRequest.log().all().put();
		ExcelUtils.printReport.printRowData(24, url, "put", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}

	@Then("Course Edit")
	public void editCourseDetails(Map<String, String> data) {

		String url = prop.getProperty("BASE_URL2") + "/program/" + borrowerID;
		setHeaderTwo();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), borrowerID, leadID));
		response = httpRequest.log().all().put();
		ExcelUtils.printReport.printRowData(25, url, "put", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}

	@Then("Edit Work Details")
	public void editWorkDetails(Map<String, String> data) {

		String url = prop.getProperty("BASE_URL2") + "/borrowers/work/" + borrowerID;
		setHeaderTwo();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.baseUri(url);
		httpRequest.body(String.format(data.get("body"), borrowerID, leadID));
		response = httpRequest.log().all().put();
		ExcelUtils.printReport.printRowData(26, url, "put", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}

	@Then("Upload Multiple Documents")
	public void editmMultipleDocument() {

		String url = prop.getProperty("BASE_URL2") + "/upload-multiple";
		setHeaderTwo();
		httpRequest.header("Content-Type", "multipart/form-data");
		httpRequest.baseUri(url);
		File file = new File(System.getProperty("user.dir") + "/data/heart.jpg");

		httpRequest.multiPart("file[0]", file);
		httpRequest.formParam("documentType", "10TH_CERTIFICATE");
		httpRequest.formParam("fileName", "10TH_CERTIFICATE");
		httpRequest.formParam("reference", "Borrower");
		httpRequest.formParam("referenceId", borrowerID);
		httpRequest.formParam("leadId", leadID);
		response = httpRequest.log().all().post();
		ExcelUtils.printReport.printRowData(27, url, "post", response.body().asString(), response.statusLine(), response.statusCode(), response.getTime());
		assertEquals(200, response.statusCode());
		response.then().log().all();
	}

}
