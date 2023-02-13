mike/mir
INSERT INTO categories (description) values ('Бары'), ('Рестораны'), ('Продукты'), ('Обучение в QA.GURU')

1. **BeforeAllCallback**: `RequiredTestClass`
2. **BeforeEachCallback**: `RequiredTestClass, RequiredTestInstance, RequiredTestMethod`
3. **BeforeTestExecutionCallback**: `RequiredTestClass, RequiredTestInstance, RequiredTestMethod`
4. **Test**: `RequiredTestClass`
5. **AfterTestExecutionCallback:** `RequiredTestClass, RequiredTestInstance, RequiredTestMethod`
6. **AfterEachCallback:** `RequiredTestClass, RequiredTestInstance, RequiredTestMethod`
7. **AfterAllCallback**: `RequiredTestClass`