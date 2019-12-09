package moxproxy.webservice.controllers;

import moxproxy.model.MoxProxyStatusMessage;
import moxproxy.model.MoxProxyStatusResponse;

class BaseController {

    MoxProxyStatusResponse createResponseForCreatedItem(String entityId){
        return new MoxProxyStatusResponse(entityId, MoxProxyStatusMessage.CREATED);
    }

    MoxProxyStatusResponse createResponseForRemovedItem(){
        return new MoxProxyStatusResponse(null, MoxProxyStatusMessage.DELETED);
    }

    MoxProxyStatusResponse createResponseForModified(){
        return new MoxProxyStatusResponse(null, MoxProxyStatusMessage.MODIFIED);
    }

    MoxProxyStatusResponse createResponseForError(String message){
        return new MoxProxyStatusResponse(null, message);
    }
}
