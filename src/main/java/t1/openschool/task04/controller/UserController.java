package t1.openschool.task04.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import t1.openschool.task04.model.JwtAuthentication;
import t1.openschool.task04.service.SecurityService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final SecurityService securityService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<String> user() {
        final JwtAuthentication authInfo = securityService.getAuthInfo();
        return ResponseEntity.ok("User " + authInfo.getName() + " is logged in");
    }
}
