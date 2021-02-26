package cxf.service;

import cxf.dto.Input;
import cxf.dto.OutPut;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface ICxfService {

    @WebMethod(operationName="ouputInfo")
    @WebResult(name="result")OutPut ouputInfo(@WebParam(name= "Input") Input input);

}
