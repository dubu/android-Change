

package com.change.dubu.core.core;

import static com.change.dubu.core.Constants.Http.*;
import static com.change.dubu.core.Constants.Http.PARSE_REST_API_KEY;
import static com.github.kevinsawicki.http.HttpRequest.get;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.change.dubu.core.BootstrapService;
import com.change.dubu.core.User;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.change.dubu.util.Ln;
import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests of client API
 */
public class BootstrapApiClientUtilTest {

    /**
     * PARAM_CONFIRMCREDENTIALS
     */
    public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";

    /**
     * PARAM_PASSWORD
     */
    public static final String PARAM_PASSWORD = "password";

    /**
     * PARAM_USERNAME
     */
    public static final String PARAM_USERNAME = "username";

    /**
     * PARAM_AUTHTOKEN_TYPE
     */
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";


    @Test
    @Ignore("Requires the API to use basic authentication. Parse.com api does not. See BootstrapService for more info.")
    public void shouldCreateClient() throws Exception {
        //List<User> users = new BootstrapService("mandu@abyang.com", "aa").getUsers();
        List<User> users = new BootstrapService("dubu@hanmail.net", "007000").getUsers();

        assertThat(users.get(0).getUsername(), notNullValue());
    }

    @Test
    @Ignore("Requires the API to use basic authentication. Parse.com api does not. See BootstrapService for more info.")
    public void addUser() throws Exception {
        //new BootstrapService("mandu@abyang.com", "aa").addUsers();

        assertThat(new BootstrapService("mandu@abyang.com", "aa").addUsers(), notNullValue());
    }



    @Test
    public void httpTest() throws Exception {
        HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://api.parse.com/1/users").openConnection()));
        httpcon.setDoOutput(true);
        httpcon.setRequestProperty("X-Parse-Application-Id", "2H847uPdm5TNwkAlUHB0igF5kw3xXSdcjXzFUtGt");
        httpcon.setRequestProperty("X-Parse-REST-API-Key", "S48bYyCWLpl38kPZG0YsoCNrIh0zEtaAPrAZtuaI");
        httpcon.setRequestProperty("Content-Type", "application/json");
        httpcon.setRequestMethod("POST");
        httpcon.connect();

        byte[] outputBytes = "{\"username\":\"dubu\",\"password\":\"007000\",\"email\":\"dubu@hanmail.net\"}".getBytes("UTF-8");
        OutputStream os = httpcon.getOutputStream();
        os.write(outputBytes);

        assertThat(httpcon.getResponseMessage(), notNullValue());


        os.close();
    }

    @Test
    public void loginTest() throws Exception {

        String email = URLEncoder.encode("dubu@hanmail.net","UTF-8");
        String password = URLEncoder.encode("007000","UTF-8");

        final String query = String.format("%s=%s&%s=%s", PARAM_USERNAME, email, PARAM_PASSWORD, password);
        HttpRequest request = get(URL_AUTH + "?" + query)
                .header(HEADER_PARSE_APP_ID, PARSE_APP_ID)
                .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY);


        Ln.d("Authentication response=%s", request.code());
    }


    @Test
    public void httpLoginLoginTest() throws Exception {
        HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://api.parse.com/1/login?username=dubu&password=007000").openConnection()));
        httpcon.setDoOutput(true);
        httpcon.setRequestProperty("X-Parse-Application-Id", "2H847uPdm5TNwkAlUHB0igF5kw3xXSdcjXzFUtGt");
        httpcon.setRequestProperty("X-Parse-REST-API-Key", "S48bYyCWLpl38kPZG0YsoCNrIh0zEtaAPrAZtuaI");
        httpcon.setRequestMethod("GET");
        httpcon.connect();

        assertThat(httpcon.getResponseMessage(), notNullValue());
    }

}
