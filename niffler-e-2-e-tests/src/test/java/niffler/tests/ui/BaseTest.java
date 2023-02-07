package niffler.tests.ui;

import niffler.jupiter.BeforeSuiteExtension;
import niffler.utils.PropertiesUtil;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BeforeSuiteExtension.class)
public abstract class BaseTest {
    protected static final PropertiesUtil PROPS = ConfigFactory.create(PropertiesUtil.class);
}
