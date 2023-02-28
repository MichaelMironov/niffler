package niffler.jupiter.di.profile;

import io.qameta.allure.AllureId;
import niffler.database.dao.PostgresSpringJdbcUsersDAO;
import niffler.database.entity.BaseEntity;
import niffler.database.entity.userdata.ProfileEntity;
import org.junit.jupiter.api.extension.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityGenerator implements ParameterResolver, BeforeTestExecutionCallback, AfterTestExecutionCallback {

    public static final ExtensionContext.Namespace NAMESPACE_PROFILE = ExtensionContext.Namespace.create(Profile.class);
    PostgresSpringJdbcUsersDAO springJdbcUsersDAO = new PostgresSpringJdbcUsersDAO();


    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        String id = getId(context);

        final List<Profile> annotated = Arrays.stream(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(Profile.class))
                .map(parameter -> parameter.getAnnotation(Profile.class))
                .toList();
        List<ProfileEntity> profileEntityList = new ArrayList<>();
        for (Profile profile : annotated) {
            profileEntityList.add(initProfile(profile));
        }
        context.getStore(NAMESPACE_PROFILE).put(id, profileEntityList);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        @SuppressWarnings("unchecked") List<ProfileEntity> profileEntities =
                (ArrayList<ProfileEntity>) extensionContext.getStore(NAMESPACE_PROFILE).get(getId(extensionContext));
        profileEntities.forEach(profileEntity -> springJdbcUsersDAO.remove(profileEntity));
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(ProfileEntity.class)
                && parameterContext.getParameter().isAnnotationPresent(Profile.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Profile annotation = parameterContext.getParameter().getAnnotation(Profile.class);
        @SuppressWarnings("unchecked") List<ProfileEntity> profileEntities =
                (ArrayList<ProfileEntity>) extensionContext.getStore(NAMESPACE_PROFILE).get(getId(extensionContext));
        return profileEntities.stream()
                .filter(profileEntity -> profileEntity.getUsername().equals(annotation.username()))
                .findFirst()
                .orElseThrow();
    }

    private ProfileEntity initProfile(Profile profile) {
        return ProfileEntity.builder()
                .username(profile.username())
                .currency(profile.currency())
                .firstname(profile.firstname())
                .build();
    }

    /**
     * @param context by current test
     * @return string value from allure_id annotation
     */
    private String getId(final ExtensionContext context) {
        return context.getRequiredTestMethod().getAnnotation(AllureId.class).value();
    }
}
