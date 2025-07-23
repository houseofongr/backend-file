package com.hoo.file.application;

import com.hoo.file.api.in.GetFileUrlUseCase;
import com.hoo.file.api.out.GetProxyUrlPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFileUrlService implements GetFileUrlUseCase {

    private final GetProxyUrlPort getProxyUrlPort;

    @Override
    public String getTempUrl(String token) {
        return getProxyUrlPort.getTempUrl(token).toString();
    }
}
