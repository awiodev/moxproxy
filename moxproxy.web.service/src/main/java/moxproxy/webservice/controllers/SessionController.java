package moxproxy.webservice.controllers;

import moxproxy.consts.MoxProxyRoutes;
import moxproxy.interfaces.MoxProxyService;
import moxproxy.model.MoxProxySessionIdMatchingStrategy;
import moxproxy.webservice.consts.ControllerConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(MoxProxyRoutes.API_ROUTE)
public final class SessionController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    MoxProxyService moxProxyService;

    @RequestMapping(value = MoxProxyRoutes.SESSION_ROUTE, method = RequestMethod.DELETE, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> clearAllSessionEntries(){
        try {
            moxProxyService.clearAllSessionEntries();
            return new ResponseEntity<>(createResponseForRemovedItem(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during all session entries cleanup", e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = MoxProxyRoutes.SESSION_ID_ROUTE, method = RequestMethod.DELETE, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> clearSessionEntries(@PathVariable String sessionId){
        try {
            moxProxyService.clearSessionEntries(sessionId);
            return new ResponseEntity<>(createResponseForRemovedItem(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during session {} entries cleanup", sessionId, e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = MoxProxyRoutes.SESSION_ROUTE_MATCH_STRATEGY, method = RequestMethod.POST, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> modifyMatchingStrategy(@RequestBody MoxProxySessionIdMatchingStrategy strategy){
        try {
            moxProxyService.modifySessionMatchingStrategy(strategy);
            return new ResponseEntity<>(createResponseForModified(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during matching strategy modification", e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = MoxProxyRoutes.SESSION_ROUTE_MATCH_STRATEGY, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getMatchingStrategy(){
        try {
            MoxProxySessionIdMatchingStrategy result = moxProxyService.getSessionMatchingStrategy();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during matching strategy retrieval", e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
