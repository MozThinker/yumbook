import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Subject

class RecipeApiIntegrationSpec extends BaseSpeificationTest{

    @Autowired
    private MockMvc mvcCon
}
