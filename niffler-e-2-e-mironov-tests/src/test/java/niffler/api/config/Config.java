package niffler.api.config;

public interface Config {

    String PROJECT_NAME = "Niffler";

    static Config getConfig() {
        if (System.getProperty("test.env").equals("docker"))
            return new DockerConfig();
        else if (System.getProperty("test.env").equals("local"))
            return new LocalConfig();
        else throw new IllegalStateException("Configuration not loaded");
    }

    String authUrl();

    String frontUrl();

    String userdataUrl();

    String currencyGrpcUrl();

    String spendUrl();

}
