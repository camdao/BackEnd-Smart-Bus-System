package com.SmartBus.domain.router.application;


import com.SmartBus.domain.router.dao.RouterRepository;
import com.SmartBus.domain.router.domain.Router;
import com.SmartBus.domain.router.dto.request.RouterCreateRequest;
import com.SmartBus.domain.router.dto.request.RouterUpdateRequest;
import com.SmartBus.domain.router.dto.response.RouterResponse;
import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RouterService {
    private final RouterRepository routerRepository;

    @Transactional
    public RouterResponse createRouter(RouterCreateRequest request) {
        Router router = Router.createRouter(
            request.name(),
            request.startLocation(),
            request.endLocation(),
            request.distance()
        );
        return RouterResponse.from(routerRepository.save(router));
    }

    public List<RouterResponse> getAllRouters() {
        return routerRepository.findAll().stream()
                .map(RouterResponse::from)
                .toList();
    }

    public RouterResponse getRouterById(Long id) {
        return routerRepository.findById(id)
                .map(RouterResponse::from)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTER_NOT_FOUND));
    }

    @Transactional
    public RouterResponse updateRouter(Long id, RouterUpdateRequest request) {
        Router router = routerRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTER_NOT_FOUND));

        router.update(
            request.name(),
            request.startLocation(),
            request.endLocation(),
            request.distance()
        );

        return RouterResponse.from(router);
    }

    @Transactional
    public void deleteRouter(Long id) {
        routerRepository.deleteById(id);
    }
}