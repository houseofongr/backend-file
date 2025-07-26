package com.hoo.file.application;

import com.hoo.file.api.in.GetFileUrlUseCase;
import com.hoo.file.api.out.GetProxyUrlInCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFileUrlService implements GetFileUrlUseCase {

    private final GetProxyUrlInCase getProxyUrlInCase;

    @Override
    public String getTempUrl(String token) {
        return getProxyUrlInCase.getRealFileUrl(token).toString();
    }
}
