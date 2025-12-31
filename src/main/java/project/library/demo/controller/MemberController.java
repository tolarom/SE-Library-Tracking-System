package project.library.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.annotation.Secured;

import project.library.demo.entity.User;
import project.library.demo.service.BookService;
import project.library.demo.service.BorrowService;
import project.library.demo.service.UserService;
@Controller
@RequestMapping("/member")
@Secured("ROLE_MEMBER")  // Only users with ROLE_MEMBER can access
public class MemberController {

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    // Changed endpoint from /dashboard to /home
    @GetMapping("/home")
    public String home(Model model, Authentication auth) {
        String username = auth.getName();
        User member = userService.findByUsername(username);

        if (member == null) {
            throw new RuntimeException("Member not found: " + username);
        }

        // Load data for the member's dashboard (now home)
        model.addAttribute("currentBorrows", borrowService.getCurrentBorrows(member));
        model.addAttribute("overdueCount", borrowService.getOverdueCount(member));
        model.addAttribute("member", member);

        return "member/home";  // ← Make sure the template file is now: src/main/resources/templates/member/home.html
    }

    @GetMapping("/borrows/current")
    public String currentBorrows(Model model, Authentication auth) {
        User member = getCurrentMember(auth);

        model.addAttribute("currentBorrows", borrowService.getCurrentBorrows(member));
        model.addAttribute("member", member);

        return "member/borrows-current";
    }

    @GetMapping("/borrows/history")
    public String borrowingHistory(Model model, Authentication auth) {
        User member = getCurrentMember(auth);

        model.addAttribute("history", borrowService.getBorrowingHistory(member));
        model.addAttribute("member", member);

        return "member/borrows-history";
    }

    private User getCurrentMember(Authentication auth) {
        String username = auth.getName();
        User member = userService.findByUsername(username);
        if (member == null) {
            throw new RuntimeException("Authenticated user not found in database: " + username);
        }
        return member;
    }
}
