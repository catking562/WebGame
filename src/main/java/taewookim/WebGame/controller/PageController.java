package taewookim.WebGame.controller;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import taewookim.WebGame.controller.dto.request.http.UserRequest;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.proxyservice.PageProxyService;

@Controller
public class PageController {

    private final PageProxyService pageProxyService;

    public PageController(PageProxyService pageProxyService) {
        this.pageProxyService = pageProxyService;
    }

    @GetMapping("/gamematching")
    public String machingPage(
            @SessionAttribute(name = "loginUser", required = false)User loginUser
            ) {
        return pageProxyService.machingPage(loginUser);
    }

}
