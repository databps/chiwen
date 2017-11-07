package com.databps.bigdaf.admin.util;

import com.databps.bigdaf.core.exception.StandbyException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * @author haipeng
 * @create 17-9-28 下午2:44
 */
public interface HAHttpClientFactory {

    /**
     * Get the Active Json
     * @param httpUrls the server urls
     * @param accessType get the paramter from AuditType Enum
     * @param paths  get the param from form
     * @return HttpResponse
     * @throws IOException
     */
    public HttpResponse doGetHaProvider(String httpUrls, String accessType, String paths) throws StandbyException, IOException;

    /**
     * Get the Active Json
     * @param httpUrls the server urls
     * @param accessType get the paramter from AuditType Enum
     * @param paths  get the param from form
     * @return HttpResponse
     * @throws IOException
     */
    public HttpResponse doPostHaProvider(String httpUrls,String accessType,String paths) throws StandbyException, IOException;

    /**
     * Get the Active Json
     * @param httpUrls the server urls
     * @param paths  get the param from form
     * @return HttpResponse
     * @throws IOException
     */
     String doGetHaProvider(String httpUrls, String paths) throws StandbyException, IOException;


    /**
     * Get the Active Json
     * @param httpUrls the server urls
     * @param paths  get the param from form
     * @return HttpResponse
     * @throws IOException
     */
    public HttpResponse doPostHaProvider(String httpUrls,String paths) throws StandbyException, IOException;

    /**
     * Get the Active Json
     * @param httpUrls the server urls
     * @param paths  get the param from form
     * @return HttpResponse
     * @throws IOException
     */
    public String doGetHaUrl(String httpUrls, String paths) throws StandbyException, IOException;

    /**
     * Get the Active Json
     * @param httpUrls the server urls
     * @param paths  get the param from form
     * @return HttpResponse
     * @throws IOException
     */
    public String doPostHaUrl(String httpUrls,String paths) throws StandbyException, IOException;
}
