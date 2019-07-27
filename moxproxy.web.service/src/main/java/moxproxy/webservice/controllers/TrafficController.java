package moxproxy.webservice.controllers;

import moxproxy.consts.MoxProxyRoutes;
import moxproxy.interfaces.MoxProxyService;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.webservice.consts.ControllerConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(MoxProxyRoutes.API_ROUTE)
public final class TrafficController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TrafficController.class);

    @Autowired
    private MoxProxyService moxProxyService;

    @RequestMapping(value = MoxProxyRoutes.REQUESTS_ROUTE, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getAllProcessedRequests(){
        try {
            List<MoxProxyProcessedTrafficEntry> result = moxProxyService.getAllRequestTraffic();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during request trafficentry retrieval", e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = MoxProxyRoutes.REQUESTS_ROUTE_SESSION, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getProcessedRequests(@PathVariable String sessionId){
        try {
            List<MoxProxyProcessedTrafficEntry> result = moxProxyService.getSessionRequestTraffic(sessionId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during request trafficentry retrieval for session {}", sessionId, e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = MoxProxyRoutes.RESPONSES_ROUTE, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getAllProcessedResponses(){
        try {
            List<MoxProxyProcessedTrafficEntry> result = moxProxyService.getAllResponseTraffic();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during response trafficentry retrieval", e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = MoxProxyRoutes.RESPONSES_ROUTE_SESSION, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getProcessedResponses(@PathVariable String sessionId){
        try {
            List<MoxProxyProcessedTrafficEntry> result = moxProxyService.getSessionResponseTraffic(sessionId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during response trafficentry retrieval for session {}", sessionId, e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
