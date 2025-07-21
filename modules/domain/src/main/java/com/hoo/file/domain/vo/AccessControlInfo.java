package com.hoo.file.domain.vo;

import com.hoo.common.enums.AccessLevel;

import java.util.UUID;

public record AccessControlInfo(
        UUID ownerID,
        AccessLevel accessLevel
) {
}
