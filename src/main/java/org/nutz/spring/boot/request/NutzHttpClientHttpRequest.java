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
 * @author kerbores
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

        SenderFactory senderFactory = applicationContext.getBean(SenderFactory.class);
        if (senderFactory != null) {
            Sender.setFactory(senderFactory);
        }

        ExecutorService executorService = applicationContext.getBean(ExecutorService.class);
        if (executorService != null) {
            Sender.setup(executorService);
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

        HostnameVerifier hostnameVerifier = applicationContext.getBean(HostnameVerifier.class);
        if (hostnameVerifier != null) {
            sender.setHostnameVerifier(hostnameVerifier);
        }

        HttpReqRespInterceptor interceptor = applicationContext.getBean(HttpReqRespInterceptor.class);
        if (interceptor != null) {
            sender.setInterceptor(interceptor);
        }

        return new NutzClientHttpResponse(sender.send());
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

}
