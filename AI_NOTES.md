# AI_NOTES.md

## AI Usage Disclosure

I used an AI assistant (DeepSeek) to accelerate parts of the development.  
The assistance focused on three areas: integration tests, API documentation, and project documentation (README + this file).  
In each case I provided specific prompts – the same way a prompt engineer would – and then reviewed, adjusted, and integrated the output.

---

## 1. Integration Tests

**Prompts used:**

- `"Write integration tests for the AccountController using Spring Boot's MockMvc. Include tests for creating an account (expect 201 Created), validation error (400 Bad Request), getting a non-existing account (404 Not Found), and listing all accounts when empty."`

- `"Write integration tests for the TransactionController that verify: adding an income transaction increases the account balance, deleting a transaction reverts the balance, requesting a non-existing transaction returns 404, and the summary endpoint returns correct totals and category breakdown."`

**AI contribution:**  
The AI generated the initial test class skeletons with proper `@SpringBootTest`, `@AutoConfigureMockMvc`, `@Transactional`, and `MockMvc` request builders. It also suggested using `jsonPath` for assertions.

**My work:**  
I manually corrected assertion values (e.g., `BigDecimal` formatting), added `@BeforeEach` setup for creating test accounts, and ensured test isolation with `@Transactional` rollback. I also debugged occasional test order issues and confirmed all tests pass.

---

## 2. API Documentation (Swagger / OpenAPI)

**Prompts used:**

- `"I have a Spring Boot REST API. I want to add OpenAPI documentation using springdoc-openapi. Provide the Maven dependency and a configuration class that sets the API title, description, and version."`

- `"I want to document error responses in my Swagger UI. Create custom annotations like @ApiError400, @ApiError404, @ApiError409 that include an example JSON response using ProblemDetail fields. Show me how to use them in a controller."`

**AI contribution:**  
It suggested the exact `springdoc-openapi-starter-webmvc-ui` dependency (with version), the `OpenApiConfig` bean, and the pattern for custom `@ApiError*` annotations. It also provided the initial code for the `@ApiResponse` examples.

## 3. README.md and AI_NOTES.md

I used GitRead to generate most of README and DeepSeek to generate some of AI_NOTES