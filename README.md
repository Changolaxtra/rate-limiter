# Spring Boot Library - Rate Limiter

This Rate Limiter uses the Token Bucket Algorithm to determine if the request can be processed or
not.

## How to use.

### Add the dependency.

TBD

### Add the `@EnableApiRateLimiter`.

By adding this annotation to main Spring Boot class the required beans will be activated.

```java
@EnableApiRateLimiter
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
```

### Configure default policy.

The library default policy can be overridden by adding the below configuration to
the `application.yaml` or `application.properties` file.

The default policy reads the `X-API-Key`, the **API Key** is `blank` and _only one request can be
processed by minute_.

````yaml
rate-limiter:
  apiKey-header-name: "X-API-Key"
  default-policy:
    apiKey: ""
    isUnlimited: false
    allowedRequests: 1
    windowSizeInMilliSeconds: 60000
````

### Create more policies.

This library allows to create more **API Key Policies**, by creating the model and calling the `saveOrUpdate` method in the repository.

- Example from DB Repository:

````java

@Component
public class ClientApiInitializer implements InitializingBean {

    private final EmbeddedPlanLimitStorage embeddedPlanLimitStorage;
    private final MyDataBaseRepository dbRepository;

    public ClientApiInitializer(final EmbeddedPlanLimitStorage embeddedPlanLimitStorage,
                                final MyDataBaseRepository dbRepository) {
        this.embeddedPlanLimitStorage = embeddedPlanLimitStorage;
        this.dbRepository = dbRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Optional.ofNullable(dbRepository)
                .map(MyDataBaseRepository::getPlanLimits)
                .orElse(new ArrayList<>())
                .forEach(embeddedPlanLimitStorage::saveOrUpdate);
    }
}
````

- Example from manual way:

````java
@Component
public class ClientApiInitializer {

    private final EmbeddedPlanLimitStorage embeddedPlanLimitStorage;


    public ClientApiInitializer(final EmbeddedPlanLimitStorage embeddedPlanLimitStorage) {
        this.embeddedPlanLimitStorage = embeddedPlanLimitStorage;
    }

    @PostConstruct
    public void init() {
        final RateLimitPolicy rateLimitPolicy = RateLimitPolicy
                .builder()
                .apiKey("a1919bfc-9b12-4927-9455-a0231ba953d5")
                .allowedRequests(25L)
                .isUnlimited(false)
                .windowSizeInMilliSeconds(120_000)
                .build();
        
        embeddedPlanLimitStorage.saveOrUpdate(new PlanLimitBucket(rateLimitPolicy));
    }
}
````

### Add the `@ApiRateLimited`.

Use the annotation `@ApiRateLimited` in the methods that should be limited using the policies.

````java

@RestController
@RequestMapping("/card")
public class CardController {

  @ApiRateLimited
  @ResponseBody
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CreateCardResponse create(@RequestBody final CreateCardRequest request) {
    return cardService.create(request);
  }
  
}
````

