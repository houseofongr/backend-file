package com.hoo.file.api.out;

import com.hoo.file.domain.File;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface LoadFilePort {
    File loadFile(UUID fileID);

    List<File> loadAllFiles(Collection<UUID> fileIDs);
}
