package moxproxy.webservice.controllers;

import moxproxy.consts.MoxProxyRoutes;
import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.MoxProxyService;
import moxproxy.webservice.consts.ControllerConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MoxProxyRoutes.API_ROUTE)
public final class TrafficController extends BaseController {

    @Autowired
    MoxProxyService moxProxyService;

    @RequestMapping(value = MoxProxyRoutes.REQUESTS_ROUTE, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getAllProcessedRequests(){
        Iterable<MoxProxyProcessedTrafficEntry> result =  moxProxyService.getAllRequestTraffic();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = MoxProxyRoutes.REQUESTS_ROUTE_SESSION, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getProcessedRequests(@PathVariable String sessionId){
        Iterable<MoxProxyProcessedTrafficEntry> result = moxProxyService.getSessionRequestTraffic(sessionId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = MoxProxyRoutes.RESPONSES_ROUTE, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getAllProcessedResponses(){
        Iterable<MoxProxyProcessedTrafficEntry> result =  moxProxyService.getAllResponseTraffic();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = MoxProxyRoutes.RESPONSES_ROUTE_SESSION, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getProcessedResponses(@PathVariable String sessionId){
        Iterable<MoxProxyProcessedTrafficEntry> result = moxProxyService.getSessionResponseTraffic(sessionId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
