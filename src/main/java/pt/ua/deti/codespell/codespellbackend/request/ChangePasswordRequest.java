package pt.ua.deti.codespell.codespellbackend.request;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class ChangePasswordRequest {
    private final String email;
    private final String password;
}
