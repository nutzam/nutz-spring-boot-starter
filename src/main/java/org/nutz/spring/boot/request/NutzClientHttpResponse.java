package org.nutz.spring.boot.request;

import java.io.IOException;
import java.io.InputStream;

import org.nutz.http.Response;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@RequiredArgsConstructor
public class NutzClientHttpResponse implements ClientHttpResponse {

 private final   Response response;

    /**
     * @return
     * @throws IOException
     * @see org.springframework.http.HttpInputMessage#getBody()
     */
    @Override
    public InputStream getBody() throws IOException {
        return response.getStream();
    }

    /**
     * @return
     * @see org.springframework.http.HttpMessage#getHeaders()
     */
    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        response.getHeader().keys().stream().forEach(key -> {
            if (Strings.isNotBlank(key)) {
                response.getHeader().getValues(key).stream().forEach(item -> headers.add(key, item));
            }
        });
        return headers;
    }

    /**
     * @return
     * @throws IOException
     * @see org.springframework.http.client.ClientHttpResponse#getStatusCode()
     */
    @Override
    public HttpStatusCode getStatusCode() throws IOException {
        return HttpStatus.valueOf(response.getStatus());
    }

    /**
     * @return
     * @throws IOException
     * @deprecated
     * @see org.springframework.http.client.ClientHttpResponse#getRawStatusCode()
     */
    @Deprecated
    @Override
    public int getRawStatusCode() throws IOException {
        return response.getStatus();
    }

    /**
     * @return
     * @throws IOException
     * @see org.springframework.http.client.ClientHttpResponse#getStatusText()
     */
    @Override
    public String getStatusText() throws IOException {
        return HttpStatus.valueOf(response.getStatus()).getReasonPhrase();
    }

    /**
     * 
     * @see org.springframework.http.client.ClientHttpResponse#close()
     */
    @Override
    public void close() {
        try {
            response.getStream().close();
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
    }

}
