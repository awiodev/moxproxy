package moxproxy.webservice.controllers;

import moxproxy.consts.MoxProxyRoutes;
import moxproxy.dto.MoxProxySessionIdMatchingStrategy;
import moxproxy.interfaces.IMoxProxyService;
import moxproxy.webservice.consts.ControllerConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(MoxProxyRoutes.API_ROUTE)
public final class SessionController extends BaseController {

    @Autowired
    IMoxProxyService moxProxyService;

    @RequestMapping(value = MoxProxyRoutes.SESSION_ROUTE, method = RequestMethod.DELETE, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> clearAllSessionEntries(){
        moxProxyService.clearAllSessionEntries();
        return new ResponseEntity<>(createResponseForRemovedItem(), HttpStatus.OK);
    }

    @RequestMapping(value = MoxProxyRoutes.SESSION_ID_ROUTE, method = RequestMethod.DELETE, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> clearSessionEntries(@PathVariable String sessionId){
        moxProxyService.clearSessionEntries(sessionId);
        return new ResponseEntity<>(createResponseForRemovedItem(), HttpStatus.OK);
    }

    @RequestMapping(value = MoxProxyRoutes.SESSION_ROUTE_MATCH_STRATEGY, method = RequestMethod.POST, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> modifyMatchingStrategy(@RequestBody MoxProxySessionIdMatchingStrategy strategy){
        moxProxyService.modifySessionMatchingStrategy(strategy);
        return new ResponseEntity<>(createResponseForModified(), HttpStatus.OK);
    }

    @RequestMapping(value = MoxProxyRoutes.SESSION_ROUTE_MATCH_STRATEGY, method = RequestMethod.GET, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> getMatchingStrategy(){
        MoxProxySessionIdMatchingStrategy result = moxProxyService.getSessionMatchingStrategy();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
