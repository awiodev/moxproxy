package moxproxy.webservice.controllers;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.IMoxProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest")
public class TrafficController {

    @Autowired
    IMoxProxyService moxProxyService;

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProcessedRequests(){
        Iterable<MoxProxyProcessedTrafficEntry> received =  moxProxyService.getAllRequestTraffic();
        return new ResponseEntity<>(received, HttpStatus.OK);
    }
}
