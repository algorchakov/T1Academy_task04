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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final SecurityService securityService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<String> admin() {
        final JwtAuthentication authInfo = securityService.getAuthInfo();
        return ResponseEntity.ok("Admin " + authInfo.getName() + " is logged in");
    }
}
