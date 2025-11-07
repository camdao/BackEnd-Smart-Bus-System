package com.SmartBus.domain.router.api;

import com.SmartBus.domain.router.application.RouterService;
import com.SmartBus.domain.router.dto.request.RouterCreateRequest;
import com.SmartBus.domain.router.dto.request.RouterUpdateRequest;
import com.SmartBus.domain.router.dto.response.RouterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/router")
@RequiredArgsConstructor
public class RouterController {
    private final RouterService routerService;

    @PostMapping
    public ResponseEntity<RouterResponse> createRouter(@Valid @RequestBody RouterCreateRequest request) {
        return ResponseEntity.ok(routerService.createRouter(request));
    }

    @GetMapping
    public ResponseEntity<List<RouterResponse>> getAllRouters() {
        return ResponseEntity.ok(routerService.getAllRouters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouterResponse> getRouterById(@PathVariable Long id) {
        return ResponseEntity.ok(routerService.getRouterById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouterResponse> updateRouter(
            @PathVariable Long id,
            @Valid @RequestBody RouterUpdateRequest request
    ) {
        return ResponseEntity.ok(routerService.updateRouter(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRouter(@PathVariable Long id) {
        routerService.deleteRouter(id);
        return ResponseEntity.noContent().build();
    }
}