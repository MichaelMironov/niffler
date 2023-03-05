mike/mir
INSERT INTO categories (category, username) values ('Бары', 'mike'), ('Рестораны','mike'), ('Продукты','mike'), ('Обучение в QA.GURU','mike')

1. **BeforeAllCallback**: `RequiredTestClass`
2. **BeforeEachCallback**: `RequiredTestClass, RequiredTestInstance, RequiredTestMethod`
3. **BeforeTestExecutionCallback**: `RequiredTestClass, RequiredTestInstance, RequiredTestMethod`
4. **Test**: `RequiredTestClass`
5. **AfterTestExecutionCallback:** `RequiredTestClass, RequiredTestInstance, RequiredTestMethod`
6. **AfterEachCallback:** `RequiredTestClass, RequiredTestInstance, RequiredTestMethod`
7. **AfterAllCallback**: `RequiredTestClass`