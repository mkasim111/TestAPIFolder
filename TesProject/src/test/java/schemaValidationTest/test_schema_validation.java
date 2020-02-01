package schemaValidationTest;

import org.testng.annotations.Test;
import com.utils.ExcelUtility;
import base.schema_validator;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.json.simple.parser.JSONParser;
import java.util.concurrent.atomic.AtomicInteger;

public class test_schema_validation {

	public static void count_all_null_occurences(JSONObject jobj, AtomicInteger aint) {
		/*
		 * here we are taking each object in response and then traversing each and every
		 * field for null values and counting the number of occurences
		 */

		JSONArray web_pages = (JSONArray) jobj.get("web_pages");
		JSONArray domains = (JSONArray) jobj.get("domains");

		for (int i = 0; i < web_pages.size(); i++) {
			if (web_pages.get(i) == null) {
				aint.incrementAndGet();
			}
		}
		for (int i = 0; i < domains.size(); i++) {
			if (domains.get(i) == null) {
				aint.incrementAndGet();
			}
		}

		if (jobj.get("name") == null) {
			aint.incrementAndGet();
		}

		if (jobj.get("alpha_two_code") == null) {
			aint.incrementAndGet();
		}

		if (jobj.get("state-province") == null) {
			aint.incrementAndGet();
		}
		if (jobj.get("country") == null) {
			aint.incrementAndGet();
		}
	}

	@Test(dataProvider = "url_reader_xl")
	public void test_schema1(String url) {

		AtomicInteger object_Count = new AtomicInteger(0);
		AtomicInteger aint = new AtomicInteger(0);

		Response resp = given().when().get(url);
		schema_validator schema_val = new schema_validator();

		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) jsonParser.parse(resp.asString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < jsonArray.size(); i++) {

			count_all_null_occurences((JSONObject) (jsonArray.get(i)), aint);
			schema_val.schema_Validation_Report(jsonArray.get(i).toString(), "json_schema.json", i, url);
			object_Count.incrementAndGet();
		}

		System.out.println();
		System.out.println("_________________________________________________________________________________");
		System.out.println(" Total number of objects parsed   : " +" url "+url+" "+ object_Count.get());
		System.out.println(" Total number of null occurences  : " +" url "+url+" "+ aint.get());
		System.out.println("_________________________________________________________________________________");

	}

	@DataProvider(name = "url_reader_xl", parallel= true)
	public Object[][] url_reader() {

		return ExcelUtility.readXLFile("/src/main/resource/data_provider_sheet.xlsx", "urls");

	}

}
