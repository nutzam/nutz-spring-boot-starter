package org.nutz.spring.boot.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.nutz.http.Response;
import org.nutz.lang.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.AbstractClientHttpResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NutzClientHttpResponse extends AbstractClientHttpResponse {

    Response response;

    /**
     * @return
     * @throws IOException
     * @see org.springframework.http.client.ClientHttpResponse#getRawStatusCode()
     */
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
        // DO NOTHING
    }

    /**
     * @return
     * @throws IOException
     * @see org.springframework.http.HttpInputMessage#getBody()
     */
    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(response.getContent().getBytes());
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

}
