package hello.openapi.web;

import hello.openapi.api.ApiExplorer;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class ApiController {

    private final ApiExplorer apiExplorer;

    @GetMapping("/")
    public String home(Model model) throws IOException, ParseException {
        apiExplorer.apiRequest(model);
        return "restaurants";

    }

}
