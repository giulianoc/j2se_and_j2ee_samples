package com.ejb.sessions;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 18/03/15
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "CreditCheckEndpointBean")
@WebService(serviceName = "CreditService", targetNamespace = "http://localhost:8080/ejb/credit")
public class CreditCheckEndpointBean {

    @WebMethod(operationName = "CreditCheck")
    public boolean validateCC(String cc)
    {
        return true;
    }
}
