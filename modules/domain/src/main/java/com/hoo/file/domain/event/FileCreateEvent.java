package com.hoo.file.domain.event;

import com.hoo.file.domain.File;

public record FileCreateEvent(
    File newFile
) {
}
