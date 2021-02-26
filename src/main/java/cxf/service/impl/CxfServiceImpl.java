package cxf.service.impl;

import cxf.dto.Input;
import cxf.dto.OutPut;
import cxf.service.ICxfService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;


@WebService(endpointInterface="cxf.service.ICxfService",serviceName="CxfServiceImpl")
public class CxfServiceImpl implements ICxfService {

    @Override
    @WebMethod(operationName="ouputInfo")
    public @WebResult(name="result") OutPut ouputInfo(@WebParam(name= "Input") Input input) {
        try {
            OutPut outPut = new OutPut();
            GregorianCalendar nowGregorianCalendar =new GregorianCalendar();
            //yyyy-MM-dd HH:mm:ss
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy");
            String dateTimeString=simpleDateFormat.format(nowGregorianCalendar.getTime());
            nowGregorianCalendar.setTime(simpleDateFormat.parse(dateTimeString));
            XMLGregorianCalendar xmlDatetime= DatatypeFactory.newInstance().newXMLGregorianCalendar(nowGregorianCalendar);
            outPut.setData(xmlDatetime);
            return outPut;
        } catch (Exception e) {

        }

        return null;
    }
}
