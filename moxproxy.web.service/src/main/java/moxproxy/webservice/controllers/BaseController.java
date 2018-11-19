package moxproxy.webservice.controllers;

import moxproxy.dto.MoxProxyStatusMessage;
import moxproxy.dto.MoxProxyStatusResponse;
import moxproxy.webservice.consts.ControllerConsts;

class BaseController {

    MoxProxyStatusResponse createResponseForCreatedItem(String entityId){
        var status = new MoxProxyStatusResponse();
        status.setMessage(MoxProxyStatusMessage.CREATED);
        status.setEntityId(entityId);
        return status;
    }

    MoxProxyStatusResponse createResponseForRemovedItem(){
        var status = new MoxProxyStatusResponse();
        status.setMessage(MoxProxyStatusMessage.DELETED);
        return status;
    }

    MoxProxyStatusResponse createResponseForModified(){
        var status = new MoxProxyStatusResponse();
        status.setMessage(MoxProxyStatusMessage.MODIFIED);
        return status;
    }

    MoxProxyStatusResponse createResponseForError(String message){
        var status = new MoxProxyStatusResponse();
        status.setMessage(message);
        return status;
    }
}
