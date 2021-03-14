import edu.ted.web.movieland.service.impl.DefaultSecurityService;
import edu.ted.web.movieland.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;

@Slf4j
public class V1_1_0__TestUserCreation extends BaseJavaMigration {


    private static final String insertUser = "INSERT INTO movie.\"user\"(usr_id, usr_name, usr_email, usr_sole, usr_password_enc) VALUES (220, 'testUser', '${email}', '<sole>', '<password>');";
    @Override
    public void migrate(Context context) throws Exception {
        //context.getConfiguration().isPlaceholderReplacement()
        String password = "testUserPassword";
        String sole = GeneralUtils.generateString(10);
        var encryptedPassword = DefaultSecurityService.getEncrypted(password + sole);
        var preparedQuery = insertUser.replace("<sole>",sole).replace("<password>", encryptedPassword);
        log.debug("Adding test user as a part of FlyWay migration {}", preparedQuery);
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement(preparedQuery)) {
            statement.execute();
        }
    }
}
