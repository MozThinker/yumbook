package edson.mutombene.user;

import org.springframework.stereotype.Service;

@Service
public record UserService() {
    public void registerUser(UserRegistrationRequest request) {
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
    }
}
