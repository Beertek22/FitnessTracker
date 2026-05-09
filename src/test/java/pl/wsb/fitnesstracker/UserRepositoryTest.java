package pl.wsb.fitnesstracker;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private List<User> prepareUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String domain = (i % 2 == 0) ? "gmail.com" : "wp.pl";
            String email = "test%d@%s".formatted(i, domain);
            users.add(new User("John%d".formatted(i), "Smith%d".formatted(i), LocalDate.now(), email));
        }

        return users;
    }

    @ParameterizedTest
    @ValueSource(strings = {"gmail.com", "wp.pl"})
    void shouldSuccessfullyFindUsersWithGivenEmailDomain(String domain) {
        // Given
        List<User> users = prepareUsers(10);
        userRepository.saveAll(users);

        // When
        List<User> usersByEmailDomain = userRepository.findByEmailDomainNative(domain);

        // Then
        assertNotNull(usersByEmailDomain);
        assertEquals(usersByEmailDomain.size(), users.size() / 2);
    }
}
