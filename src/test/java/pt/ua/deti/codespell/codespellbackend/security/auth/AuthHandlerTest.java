package pt.ua.deti.codespell.codespellbackend.security.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthHandlerTest {

    @Mock
    private AuthHandler authHandler;

    @Test
    void getCurrentUsername() {
        when(authHandler.getCurrentUsername()).thenReturn("Hugo1307");
        assertThat(authHandler.getCurrentUsername()).isEqualTo("Hugo1307");
    }

}