package com.changolaxtra.cloud.ratelimiter.configuration;

import com.changolaxtra.cloud.ratelimiter.core.PlanLimitDataRoot;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot configuration class to initialize the {@link EmbeddedStorageManager}.
 */
@Slf4j
@Configuration
public class StorageManagerConfiguration {

  /**
   * Creates the default {@link EmbeddedStorageManager} bean to be available for injection.
   */
  @Bean
  public EmbeddedStorageManager.Default embeddedStorageManager() {
    final EmbeddedStorageManager.Default storageManager =
        (EmbeddedStorageManager.Default) EmbeddedStorage.start(new PlanLimitDataRoot());
    storageManager.storeRoot();
    return storageManager;
  }

}
