package uagrm.si2.colegio.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String email;
    private String username;
    private String password;
    private Set<String> roles;

}
