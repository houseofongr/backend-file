package com.hoo.file.adapter.out.persistence.entity.vo;

import com.hoo.common.enums.MediaType;
import com.hoo.file.domain.vo.MediaInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MediaInfoJpaValue {

    @Column
    private String contentType;

    @Column
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    public static MediaInfoJpaValue from(MediaInfo mediaInfo) {

        return new MediaInfoJpaValue(
                mediaInfo.contentType(),
                mediaInfo.mediaType()
        );
    }

}
