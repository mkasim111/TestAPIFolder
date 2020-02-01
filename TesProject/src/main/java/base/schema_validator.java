package base;

import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.util.JsonLoader;
import com.google.gson.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.*;
import java.util.*;

public class schema_validator {
	
	@SuppressWarnings("unused")
	public ProcessingReport schema_Validation_Report(String jsonApi, String jsonSchema, int i, String url) {

		String content = null;
		try {
			InputStream io = getClass().getClassLoader().getResourceAsStream(jsonSchema);
			BufferedReader br = new BufferedReader(new InputStreamReader(io));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			content = sb.toString();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("-------- File not found: Input file : " + jsonSchema);
		} catch (IOException e) {
			System.out.println("-------- File Read Error: Unable to read Input file : " + jsonSchema);
			e.printStackTrace();
		}
		ProcessingReport report = null;
		boolean result = false;
		try {
			JsonNode schemaNode = JsonLoader.fromString(content);
			JsonNode data = JsonLoader.fromString(jsonApi);
			JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			JsonSchema schema = factory.getJsonSchema(schemaNode);
			report = schema.validate(data);

		} catch (JsonParseException jpex) {
			jpex.printStackTrace();
		} catch (ProcessingException pex) {
			pex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (report != null) {
			Iterator<ProcessingMessage> iter = report.iterator();
			while (iter.hasNext()) {
				ProcessingMessage pm = iter.next();
				System.out.println("************Schema Validation failed for object number :" + i + "******************");
				System.out.println("Processing Message: " +url+" "+ pm.toString());
				System.out.println("Processing Message: " +url+" "+  pm.getMessage());
				System.out.println("---------------------------------------------------------------------------------");
			}
		}
		return report;
	}

}
