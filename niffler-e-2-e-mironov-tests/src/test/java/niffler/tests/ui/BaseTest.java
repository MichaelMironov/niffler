package niffler.tests.ui;

import io.qameta.allure.junit5.AllureJunit5;
import niffler.jupiter.JpaExtension;
import niffler.jupiter.fixture.BrowserExtension;
import niffler.utils.PropertiesUtil;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserExtension.class, JpaExtension.class, AllureJunit5.class})
public abstract class BaseTest {
    protected static final PropertiesUtil PROPS = ConfigFactory.create(PropertiesUtil.class);
}
