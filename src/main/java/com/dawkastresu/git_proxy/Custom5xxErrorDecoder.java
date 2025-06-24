package com.dawkastresu.git_proxy;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import feign.FeignException;

import java.util.Date;

public class Custom5xxErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response);
        int status = response.status();
        if (status == 500 || status == 503) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    new Date(System.currentTimeMillis() + 50L),
                    response.request()
            );
        }
        return defaultDecoder.decode(methodKey, response);
    }

}
