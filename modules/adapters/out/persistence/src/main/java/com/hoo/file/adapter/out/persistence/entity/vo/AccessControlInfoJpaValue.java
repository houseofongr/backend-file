package com.hoo.file.adapter.out.persistence.entity.vo;

import com.hoo.common.enums.AccessLevel;
import com.hoo.file.domain.vo.AccessControlInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AccessControlInfoJpaValue {

    @Column(columnDefinition = "BINARY(16)", name = "OWNER_ID")
    private UUID ownerID;

    @Column
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    public static AccessControlInfoJpaValue from(AccessControlInfo accessControlInfo) {

        return new AccessControlInfoJpaValue(
                accessControlInfo.ownerID(),
                accessControlInfo.accessLevel()
        );
    }
}
