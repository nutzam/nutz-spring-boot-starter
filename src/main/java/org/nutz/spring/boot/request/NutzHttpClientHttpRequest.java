package org.nutz.spring.boot.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.nutz.http.ProxySwitcher;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kerbores
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NutzHttpClientHttpRequest extends AbstractClientHttpRequest {

    private ByteArrayOutputStream bufferedOutput = new ByteArrayOutputStream(1024);

    URI uri;
    HttpMethod httpMethod;
    ProxySwitcher proxySwitcher;

    /**
     * 
     */
    public NutzHttpClientHttpRequest(URI uri, HttpMethod httpMethod, ProxySwitcher proxySwitcher) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.proxySwitcher = proxySwitcher;
    }

    /**
     * @return
     * @see org.springframework.http.HttpRequest#getMethodValue()
     */
    @Override
    public String getMethodValue() {
        return httpMethod.name();
    }

    /**
     * @return
     * @see org.springframework.http.HttpRequest#getURI()
     */
    @Override
    public URI getURI() {
        return uri;
    }

    /**
     * @param headers
     * @return
     * @throws IOException
     * @see org.springframework.http.client.AbstractClientHttpRequest#getBodyInternal(org.springframework.http.HttpHeaders)
     */
    @Override
    protected OutputStream getBodyInternal(HttpHeaders headers) throws IOException {
        return bufferedOutput;
    }

    /**
     * @param headers
     * @return
     * @throws IOException
     * @see org.springframework.http.client.AbstractClientHttpRequest#executeInternal(org.springframework.http.HttpHeaders)
     */
    @Override
    protected ClientHttpResponse executeInternal(HttpHeaders headers) throws IOException {
        final Request request = Request.create(uri.toString(), METHOD.valueOf(getMethodValue()));
        headers.forEach((headerName, headerValues) -> {
            for (String headerValue : headerValues) {
                request.header(headerName, headerValue);
            }
        });
        if (bufferedOutput != null && bufferedOutput.size() > 0) {
            request.setData(bufferedOutput.toByteArray());
        }

        Response response;
        if (proxySwitcher != null) {
            response = Sender.create(request)
                             .setProxy(proxySwitcher.getProxy(request))
                             .send();
        } else {
            response = Sender.create(request).send();
        }
        return new AbstractClientHttpResponse() {

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                response.getHeader().keys().stream().forEach(key -> {
                    response.getHeader().getValues(key).stream().forEach(item -> {
                        headers.add(key, item);
                    });
                });
                return headers;
            }

            @Override
            public InputStream getBody() throws IOException {
                return response.getStream();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.valueOf(response.getStatus()).getReasonPhrase();
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return response.getStatus();
            }

            @Override
            public void close() {

            }
        };
    }

}
