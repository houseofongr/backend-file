package com.hoo.file.api.out;

import com.hoo.file.domain.File;

import java.io.InputStream;
import java.net.URI;

public interface StoreFilePort {
    void storeFile(File file);
}
