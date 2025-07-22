package com.hoo.file.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.file.dto.GetFileInfoCommand;
import com.hoo.file.api.out.GenerateUrlPort;
import com.hoo.file.api.out.LoadFilePort;
import com.hoo.file.domain.File;
import com.hoo.file.test.domain.FileTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetFileInfoServiceTest {

    LoadFilePort loadFilePort = mock();
    GenerateUrlPort generateUrlPort = mock();
    ApplicationMapper applicationMapper = mock();

    GetFileInfoService sut = new GetFileInfoService(loadFilePort, generateUrlPort, applicationMapper);

    @Test
    @DisplayName("공개 파일정보 불러오기 서비스")
    void getPublicFileInfo() {
        // given
        GetFileInfoCommand command = new GetFileInfoCommand(List.of(new GetFileInfoCommand.FileOwnership(UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch())));

        // when
        when(loadFilePort.loadFile(any())).thenReturn(FileTestData.defaultFile().build());
        sut.getFileInfo(command);

        // then
        verify(generateUrlPort, times(1)).generateUrlMap(any());
    }
}