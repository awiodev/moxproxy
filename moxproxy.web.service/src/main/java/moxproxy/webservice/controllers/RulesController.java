package moxproxy.webservice.controllers;

import moxproxy.consts.MoxProxyRoutes;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyService;
import moxproxy.webservice.consts.ControllerConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(MoxProxyRoutes.API_ROUTE)
public class RulesController extends BaseController {

    @Autowired
    IMoxProxyService moxProxyService;

    @RequestMapping(value = MoxProxyRoutes.RULES_ROUTE, method = RequestMethod.POST, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> createRule(@RequestBody MoxProxyRule rule){
        String id = moxProxyService.createRule(rule);
        return new ResponseEntity<>(createResponseForCreatedItem(id), HttpStatus.OK);
    }

    @RequestMapping(value = MoxProxyRoutes.RULES_ROUTE_ID, method = RequestMethod.DELETE, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> cancelRule(@PathVariable String ruleId){
        moxProxyService.cancelRule(ruleId);
        return new ResponseEntity<>(createResponseForRemovedItem(), HttpStatus.OK);
    }

    @RequestMapping(value = MoxProxyRoutes.RULES_SESSION_ID_ROUTE, method = RequestMethod.DELETE, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> clearSessionRules(@PathVariable String sessionId){
        moxProxyService.clearSessionRules(sessionId);
        return new ResponseEntity<>(createResponseForRemovedItem(), HttpStatus.OK);
    }
}
