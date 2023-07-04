package edson.mutombene.user;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
