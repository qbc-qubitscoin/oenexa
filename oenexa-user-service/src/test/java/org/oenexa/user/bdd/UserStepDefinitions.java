package org.oenexa.user.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.oenexa.user.config.TestConfig;
import org.oenexa.user.controller.UserController;
import org.oenexa.user.dto.response.UserProfileDto;
import org.oenexa.user.entity.UserProfileEntity;
import org.oenexa.user.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
public class UserStepDefinitions {

    @Autowired
    private UserController userController;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private UUID userId;
    private ResponseEntity<UserProfileDto> response;

    @Given("a registered user profile exists with first name {string} and last name {string}")
    public void a_registered_user_profile_exists(String firstName, String lastName) {
        userProfileRepository.deleteAll();

        userId = UUID.randomUUID();
        UserProfileEntity entity = new UserProfileEntity();
        entity.setUserId(userId);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        userProfileRepository.save(entity);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userId.toString(),
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @When("the user retrieves their profile")
    public void the_user_retrieves_their_profile() {
        response = userController.getMyProfile();
    }

    @Then("the profile first name should be {string} and last name should be {string}")
    public void the_profile_first_name_and_last_name_should_be(String expectedFirst, String expectedLast) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(expectedFirst, response.getBody().firstName());
        assertEquals(expectedLast, response.getBody().lastName());
    }
}
