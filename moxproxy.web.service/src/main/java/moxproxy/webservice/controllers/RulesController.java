package moxproxy.webservice.controllers;

import moxproxy.consts.MoxProxyRoutes;
import moxproxy.interfaces.MoxProxyService;
import moxproxy.model.MoxProxyRule;
import moxproxy.webservice.consts.ControllerConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(MoxProxyRoutes.API_ROUTE)
public class RulesController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(RulesController.class);

    @Autowired
    MoxProxyService moxProxyService;

    @RequestMapping(value = MoxProxyRoutes.RULES_ROUTE, method = RequestMethod.POST, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> createRule(@RequestBody MoxProxyRule rule){
        try {
            String id = moxProxyService.createRule(rule);
            return new ResponseEntity<>(createResponseForCreatedItem(id), HttpStatus.CREATED);
        } catch (Exception e) {
            LOG.error("Error during rule {} creation", rule.getId(), e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = MoxProxyRoutes.RULES_ROUTE_ID, method = RequestMethod.DELETE, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> cancelRule(@PathVariable String ruleId){
        try {
            moxProxyService.cancelRule(ruleId);
            return new ResponseEntity<>(createResponseForRemovedItem(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during rule {} cancellation", ruleId, e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = MoxProxyRoutes.RULES_SESSION_ID_ROUTE, method = RequestMethod.DELETE, produces = ControllerConsts.APPLICATION_JSON)
    public ResponseEntity<?> clearSessionRules(@PathVariable String sessionId){
        try {
            moxProxyService.clearSessionRules(sessionId);
            return new ResponseEntity<>(createResponseForRemovedItem(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error during session {} rules cleanup", sessionId, e);
            return new ResponseEntity<>(createResponseForError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
