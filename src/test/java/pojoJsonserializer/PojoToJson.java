package pojoJsonserializer;

import java.util.Arrays;
import java.util.List;

import org.apache.juneau.html.HtmlSerializer;
import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.parser.ParseException;
import org.apache.juneau.serializer.SerializeException;
import org.apache.juneau.xml.XmlSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojoObjectMapper.AddPlace;

public class PojoToJson {
public static void main(String[] args ) throws SerializeException, ParseException, JsonMappingException, JsonProcessingException {
		
		/************************************Serialization**************************************************/
		
		//pojo to json
		
		JsonSerializer js=JsonSerializer.DEFAULT_READABLE;
		String sellerNames[] ={"ABC","PQR","XYZ"};
		PojoClass pc=new PojoClass("MacBook",1000,"Blue",sellerNames);
		String json=js.serialize(pc);
		System.out.println("json file "+json);
		
		
		//pojo to xml 
		
		XmlSerializer xmls=XmlSerializer.DEFAULT_NS_SQ_READABLE;
		String xml=xmls.serialize(pc);
		System.out.println("XML file"+xml);
		
		//pojo to html
		
		HtmlSerializer htmls=HtmlSerializer.DEFAULT_SQ_READABLE;
		String html=htmls.serialize(pc);
		System.out.println("HTML file"+html);
		
/************************************DeSerialization**************************************************/
		
		JsonParser jsonParser = JsonParser.DEFAULT;
		String jsonVal = "{\r\n"
				+ "	\"color\": \"Blue\",\r\n"
				+ "	\"name\": \"MacBook\",\r\n"
				+ "	\"price\": 1000,\r\n"
				+ "	\"sellerName\": [\r\n"
				+ "		\"ABC\",\r\n"
				+ "		\"PQR\",\r\n"
				+ "		\"XYZ\"\r\n"
				+ "	]\r\n"
				+ "}";
	PojoClass pro = jsonParser.parse(jsonVal, PojoClass.class);
	System.out.println("seller names ="+Arrays.toString(pro.getSellerName()));
	
		
		
	}
}
