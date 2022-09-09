package org.nutz.spring.boot.request;

import java.io.IOException;
import java.net.URI;

import org.nutz.http.ProxySwitcher;
import org.nutz.log.Logs;
import org.nutz.spring.boot.request.NutzHttpAutoConfigurationProperties.Http;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NutzHttpRequestFactory implements ClientHttpRequestFactory, DisposableBean {

    ProxySwitcher proxySwitcher;

    Http http;

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
        return new NutzHttpClientHttpRequest(uri, httpMethod, http, proxySwitcher);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
        Logs.get().debug("destroy");
    }

}
