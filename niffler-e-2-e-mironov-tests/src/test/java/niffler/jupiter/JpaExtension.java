package niffler.jupiter;

import jakarta.persistence.EntityManagerFactory;
import niffler.database.jpa.EmfContext;
import niffler.jupiter.fixture.AroundAllTestsExtension;

public class JpaExtension implements AroundAllTestsExtension {
    @Override
    public void afterAllTests() {
        EmfContext.INSTANCE.storedEmf()
                .forEach(EntityManagerFactory::close);
    }
}
