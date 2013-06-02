package com.change.dubu.core.core;

import com.github.kevinsawicki.http.HttpRequest;
import junit.framework.Assert;
import org.junit.Test;

import static com.change.dubu.core.Constants.Http.*;
import static com.change.dubu.core.Constants.Http.PARSE_APP_ID;

/**
 * User: kingkingdubu
 * Date: 13. 6. 2
 * Time: 오후 1:29
 */
public class HttpTest {


    @Test
    public void sendPutRequestTest(){

        // = "https://api.parse.com";

        //URL_BASE + "/1/classes/Book";

        String url =  "https://api.parse.com/1/classes/Book/rZuYUPWZFY";
        HttpRequest request = HttpRequest.put(url);

        request.header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY );
        request.header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
        request.header("Content-Type","application/json");
        String data = "{\"content\":\"qqqqsdfsd\"}";
        request  = request.send(data);
        Assert.assertEquals(true, request.ok());


    }
}
