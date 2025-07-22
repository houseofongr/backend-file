package com.hoo.file.adapter.out.persistence.entity.vo;

import com.hoo.file.domain.vo.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LocationJpaValue {

    @Column
    private String bucket;

    @Column
    private String storageKey;

    public static LocationJpaValue from(Location location) {

        return new LocationJpaValue(
                location.bucket(),
                location.storageKey()
        );
    }
}
