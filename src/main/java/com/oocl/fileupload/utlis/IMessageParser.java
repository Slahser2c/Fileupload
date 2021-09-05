package com.oocl.fileupload.utlis;

import javax.xml.bind.UnmarshalException;

public interface IMessageParser {

    public static final String MSG_TYPE = "MSG_TYPE";
    public static final String IS_VALIDATE = "IS_VALIDATE";
    public Object unmarshal(String xmlContent) throws UnmarshalException;
}
