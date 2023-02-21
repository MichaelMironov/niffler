package niffler.utils;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface PropertiesUtil extends Config {

    @Key("db.url")
    String dbUrl();

    @Key("db.user")
    String dbUser();

    @Key("db.password")
    String dbPassword();

    @Key("db.pool.size")
    @DefaultValue("5")
    int dbPoolSize();

    @Key("auth.url")
    String authUrl();

    @Key("auth.user")
    String login();

    @Key("auth.password")
    String password();
}