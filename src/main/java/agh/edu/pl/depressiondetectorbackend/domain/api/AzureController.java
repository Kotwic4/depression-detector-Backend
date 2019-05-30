package agh.edu.pl.depressiondetectorbackend.domain.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/api/azure")
public class AzureController {

    private final AzureProperties azureProperties;

    @Autowired
    public AzureController(AzureProperties azureProperties) {
        this.azureProperties = azureProperties;
    }

    @GetMapping
    @Transactional
    public String getToken() {
        return azureProperties.getToken();
    }
}
