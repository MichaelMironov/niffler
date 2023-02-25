package niffler.ui.component;

import com.codeborne.selenide.SelenideElement;

public abstract class Component<T extends Component> {

    protected final SelenideElement self;

    protected Component(SelenideElement self) {
        this.self = self;
    }

    public SelenideElement getSelf() {
        return self;
    }
}
