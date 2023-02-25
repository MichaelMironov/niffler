package niffler.ui.pages;

import niffler.utils.PropertiesUtil;
import org.aeonbits.owner.ConfigFactory;

public abstract class BasePage <T extends BasePage>{

    protected static final PropertiesUtil PROPS = ConfigFactory.create(PropertiesUtil.class);

    public abstract T waitForPageLoaded();
}
