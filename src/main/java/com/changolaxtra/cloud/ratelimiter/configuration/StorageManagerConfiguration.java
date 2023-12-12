package com.changolaxtra.cloud.ratelimiter.configuration;

import com.changolaxtra.cloud.ratelimiter.core.PlanLimitDataRoot;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class StorageManagerConfiguration {

  @Bean
  public EmbeddedStorageManager.Default embeddedStorageManager() {
    final EmbeddedStorageManager.Default storageManager =
        (EmbeddedStorageManager.Default) EmbeddedStorage.start(new PlanLimitDataRoot());
    storageManager.storeRoot();
    return storageManager;
  }

}
