package taewookim.WebGame.proxyservice;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.service.PageService;

@Service
public class PageProxyService {

    private final PageService pageService;

    public PageProxyService(PageService pageService) {
        this.pageService = pageService;
    }

    public String machingPage(User loginUser) {
        return pageService.machingPage(loginUser);
    }

}
