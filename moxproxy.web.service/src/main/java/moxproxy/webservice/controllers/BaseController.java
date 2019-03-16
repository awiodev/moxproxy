package moxproxy.webservice.controllers;

import moxproxy.model.MoxProxyStatusMessage;
import moxproxy.model.MoxProxyStatusResponse;

class BaseController {

    MoxProxyStatusResponse createResponseForCreatedItem(String entityId){
        MoxProxyStatusResponse status = new MoxProxyStatusResponse();
        status.setMessage(MoxProxyStatusMessage.CREATED);
        status.setEntityId(entityId);
        return status;
    }

    MoxProxyStatusResponse createResponseForRemovedItem(){
        MoxProxyStatusResponse status = new MoxProxyStatusResponse();
        status.setMessage(MoxProxyStatusMessage.DELETED);
        return status;
    }

    MoxProxyStatusResponse createResponseForModified(){
        MoxProxyStatusResponse status = new MoxProxyStatusResponse();
        status.setMessage(MoxProxyStatusMessage.MODIFIED);
        return status;
    }

    MoxProxyStatusResponse createResponseForError(String message){
        MoxProxyStatusResponse status = new MoxProxyStatusResponse();
        status.setMessage(message);
        return status;
    }
}
