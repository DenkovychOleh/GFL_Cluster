package executor.service.controllers;

import executor.service.model.ProxyConfigHolder;
import executor.service.services.ProxySourcesClient;
import executor.service.services.ProxySourcesClientImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/proxy")
public class ProxySourceController {
private final ProxySourcesClient proxySourcesClient;

    public ProxySourceController(ProxySourcesClient proxySourcesClient) {
        this.proxySourcesClient = proxySourcesClient;
    }
    @PostMapping(value = "/list", produces = "Application/json")
    public List<ProxyConfigHolder> getProxyConfigHolders (@RequestBody ProxySourcesClientImpl proxySourcesClient) {
    return proxySourcesClient.getListProxy();
    }
    @GetMapping(produces = "Application/json")
    public ResponseEntity<ProxyConfigHolder> getProxyHttp(){
        return ResponseEntity.ok(proxySourcesClient.getProxy());
    }
   }
