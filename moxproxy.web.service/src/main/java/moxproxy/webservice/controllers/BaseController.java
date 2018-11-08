package moxproxy.webservice.controllers;

import moxproxy.dto.MoxProxyStatusResponse;
import moxproxy.webservice.consts.ControllerConsts;

class BaseController {

    MoxProxyStatusResponse createResponseForCreatedItem(){
        return new MoxProxyStatusResponse(ControllerConsts.CREATED);
    }

    MoxProxyStatusResponse createResponseForRemovedItem(){
        return new MoxProxyStatusResponse(ControllerConsts.DELETED);
    }

    MoxProxyStatusResponse createResponseForModified(){
        return new MoxProxyStatusResponse(ControllerConsts.MODIFIED);
    }

    MoxProxyStatusResponse createResponseForError(String message){
        return new MoxProxyStatusResponse(message);
    }
}
