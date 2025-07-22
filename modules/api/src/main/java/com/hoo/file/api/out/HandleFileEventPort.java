package com.hoo.file.api.out;

import com.hoo.file.domain.event.FileCreateEvent;

public interface HandleFileEventPort {

    void handleCreateFile(FileCreateEvent event);
}
