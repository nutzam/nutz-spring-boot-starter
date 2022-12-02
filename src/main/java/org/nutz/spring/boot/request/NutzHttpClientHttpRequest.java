package org.nutz.spring.boot.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.concurrent.ExecutorService;

import javax.net.ssl.HostnameVerifier;

import org.nutz.http.HttpReqRespInterceptor;
import org.nutz.http.ProxySwitcher;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Sender;
import org.nutz.http.SenderFactory;
import org.nutz.lang.Lang;
import org.nutz.spring.boot.request.NutzHttpAutoConfigurationProperties.Http;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NutzHttpClientHttpRequest extends AbstractClientHttpRequest implements ApplicationContextAware {

    private ByteArrayOutputStream bufferedOutput = new ByteArrayOutputStream(1024);
    ApplicationContext applicationContext;
    URI uri;
    HttpMethod httpMethod;
    ProxySwitcher proxySwitcher;
    Http http;

    /**
     * 
     */
    public NutzHttpClientHttpRequest(URI uri, HttpMethod httpMethod, Http http, ProxySwitcher proxySwitcher) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.http = http;
        this.proxySwitcher = proxySwitcher;
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
        final Request request = Request.create(uri.toString(),  METHOD.valueOf(getMethod().name()) );
        headers.forEach((headerName, headerValues) -> {
            for (String headerValue : headerValues) {
                request.header(headerName, headerValue);
            }
        });
        if (bufferedOutput != null && bufferedOutput.size() > 0) {
            request.setData(bufferedOutput.toByteArray());
        }

        if (hasBean(SenderFactory.class)) {
            Sender.setFactory(applicationContext.getBean(SenderFactory.class));
        }

        if (hasBean(ExecutorService.class)) {
            Sender.setup(applicationContext.getBean(ExecutorService.class));
        }

        Sender sender = Sender.create(request)
                              .setTimeout(http.getTimeout())
                              .setFollowRedirects(http.isFollowRedirects())
                              .setConnTimeout(http.getConnectionTimeout());
        // 禁用ssl证书检查
        if (!http.isJvmHttpsCheck()) {
            try {
                sender.setSSLSocketFactory(org.nutz.http.Http.nopSSLSocketFactory());
            }
            catch (Exception e) {
                throw Lang.wrapThrow(e);
            }
        }
        // 添加代理
        if (proxySwitcher != null) {
            sender.setProxy(proxySwitcher.getProxy(request));
        }

        if (hasBean(HostnameVerifier.class)) {
            sender.setHostnameVerifier(applicationContext.getBean(HostnameVerifier.class));
        }

        if (hasBean(HttpReqRespInterceptor.class)) {
            sender.setInterceptor(applicationContext.getBean(HttpReqRespInterceptor.class));
        }

        return new NutzClientHttpResponse(sender.send());
    }

    public boolean hasBean(Class<?> clazz) {
        try {
            applicationContext.getBean(clazz);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * @return
     * @see org.springframework.http.HttpRequest#getMethod()
     */
    @Override
    public HttpMethod getMethod() {
        return httpMethod;
    }

}
