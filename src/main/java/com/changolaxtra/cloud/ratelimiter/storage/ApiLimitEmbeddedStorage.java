package com.changolaxtra.cloud.ratelimiter.storage;

import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.stereotype.Repository;

@Repository
public class ApiLimitEmbeddedStorage implements ApiLimitStorage {

    private final EmbeddedStorageManager.Default embeddedStorageManager;


    public ApiLimitEmbeddedStorage(final EmbeddedStorageManager.Default embeddedStorageManager) {
        this.embeddedStorageManager = embeddedStorageManager;
    }


}
