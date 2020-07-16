package mpavlovic.sandbox;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class APILoginTest {

	static Logger logger = Logger.getLogger(APILoginTest.class);
	
	public static void main(String[] args) {
		String baseRestURL = ConfigurationStrings.URL;
		String username = ConfigurationStrings.USERNAME;
		String password = ConfigurationStrings.PASSWORD;

		HttpClient httpClient = null;
		CookieStore httpCookieStore = new BasicCookieStore();
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore);
		httpClient = builder.build();

		String authToken = createAuthToken(baseRestURL, httpClient, username, password);
		logger.debug("Auth Token: " + authToken);
		
		if(authToken == null){
			logger.error("Unable to generate authToken.");
		}
	}

	public static String createAuthToken(String baseRestURL, HttpClient httpClient, String username, String password) {
		String APIPath = "api/users/login";
		String completeRestURL = baseRestURL + APIPath;
		logger.debug("REST API URL: " + completeRestURL + " user: " + username + " pass: " + password);
		String token = null;

		try {
			HttpPost httpRequest = new HttpPost(completeRestURL);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Accept", "application/json");
			StringEntity body = new StringEntity("{\"email\":\"" + username + "\",\"password\":\"" + password + "\"}");
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			
			String responseBody = EntityUtils.toString(entity);
			
			return responseBody;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return token;
	}
}
