package taewookim.WebGame.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import taewookim.WebGame.entity.User;

@Service
public class PageService {

    public String machingPage(
            User loginUser
    ) {
        if(loginUser==null) {
            return "redirect:/login";
        }
        return "gamematching";
    }

}
