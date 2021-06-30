package org.nutz.spring.boot.request;

import java.io.IOException;
import java.net.URI;

import org.nutz.http.ProxySwitcher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kerbores
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutzHttpRequestFactory implements ClientHttpRequestFactory, DisposableBean {

    ProxySwitcher proxySwitcher;

    /**
     * @throws Exception
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {

    }

    /**
     * @param uri
     * @param httpMethod
     * @return
     * @throws IOException
     * @see org.springframework.http.client.ClientHttpRequestFactory#createRequest(java.net.URI,
     *      org.springframework.http.HttpMethod)
     */
    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return new NutzHttpClientHttpRequest(uri, httpMethod, proxySwitcher);
    }

}
