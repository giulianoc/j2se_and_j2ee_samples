package com.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 01/09/15
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Veda {

    // Once the deploy is done, you can test the service using:
    //		http://127.0.0.1:8080/louiseAndVedaWorkFlow/veda
    @WebMethod(operationName = "onAirService")
    public String sdkServiceOnAirNotify()
    {
        return null;
    }
}
