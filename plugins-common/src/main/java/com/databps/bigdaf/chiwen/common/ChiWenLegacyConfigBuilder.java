/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.databps.bigdaf.chiwen.common;

import com.databps.bigdaf.chiwen.plugin.store.EmbeddedServiceDefsUtil;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;

public class ChiWenLegacyConfigBuilder {

	private static final Logger LOG = Logger.getLogger(ChiWenLegacyConfigBuilder.class);

	static String serviceType;
	static String legacyResource;

	public static Configuration getSecurityConfig(String serviceType) {

		ChiWenLegacyConfigBuilder.legacyResource = getPropertyName(ChiWenConfigConstants.XASECURE_SECURITY_FILE,serviceType);
		ChiWenLegacyConfigBuilder.serviceType	 = serviceType;

		Configuration	ret				= null;
		Configuration   legacyConfig	= new Configuration();
		URL 			legacyFileUrl	= getFileURL(legacyResource);

		if(LOG.isDebugEnabled()) {
			LOG.debug("==> getSecurityConfig() " + legacyResource + " FileName: " + legacyFileUrl);
		}

		if ( legacyFileUrl  != null) {
			legacyConfig.addResource(legacyFileUrl);
			Configuration chiwenDefaultProp =  buildChiWenSecurityConf(serviceType);
					   ret	 			    =  mapLegacyConfigToChiWen(chiwenDefaultProp,legacyConfig);
		}
		if(LOG.isDebugEnabled()) {
			LOG.debug("<== getSecurityConfig() " + legacyResource + " FileName: " + legacyFileUrl);
		}

		return ret;
	}

	public static URL getAuditConfig(String serviceType) throws Throwable {
		
		ChiWenLegacyConfigBuilder.legacyResource = getPropertyName(ChiWenConfigConstants.XASECURE_AUDIT_FILE,serviceType);
		ChiWenLegacyConfigBuilder.serviceType	 = serviceType;
		
		URL ret = null;
		try {
			ret = getAuditResource(legacyResource);
		} catch (Throwable t) {
		   throw t;
		}
		return ret;
	}

   private static  Configuration mapLegacyConfigToChiWen(Configuration chiwenInConf, Configuration legacyConf) {
	
	    Configuration ret 				   = chiwenInConf;
	
	    HashMap<String,String>  chgMap     = getConfigChangeMap(serviceType);
	    if(LOG.isDebugEnabled()) {
			LOG.debug("<== mapLegacyConfigToChiWen() MAP Size:  " + chgMap.size());
		}
		for(Map.Entry<String, String> entry : chgMap.entrySet()) {
			String legacyKey 	 = entry.getKey();
			String chiwenKey 	 = entry.getValue();
			
			String legacyConfVal = null;
			
			if ( chiwenKey.equals(getPropertyName(ChiWenConfigConstants.CHIWEN_SERVICE_NAME,serviceType)) )  {
				//For getting the service
				String serviceURL = legacyConf.get(getPropertyName(ChiWenConfigConstants.XASECURE_POLICYMGR_URL,serviceType));
				legacyConfVal = fetchLegacyValue(serviceURL,chiwenKey);
			} else if  ( chiwenKey.equals(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_REST_URL,serviceType)) ||
					     chiwenKey.equals(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_CACHE_DIR,serviceType)) ) {
				// For Getting Admin URL and CacheDir
				legacyConfVal = fetchLegacyValue(legacyConf.get(legacyKey),chiwenKey);
			} else {
				legacyConfVal = legacyConf.get(legacyKey);
			}
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("<== mapLegacyConfigToChiWen() ChiWen Key: " + chiwenKey + "Legacy Key:" + legacyKey +  "Legacy Value:" + legacyConfVal);
			}
			
			ret.set(chiwenKey, legacyConfVal);	
		}
		return ret;
	}


	public  static URL getAuditResource(String fName) throws Throwable {
		URL ret = null;

		try {
			for(String  cfgFile : 	new String[] {  "hive-site.xml",  "hbase-site.xml",  "hdfs-site.xml" } ) {
				String loc = getFileLocation(cfgFile);
				if (loc != null) {
					File f = new File(loc);
					if ( f.exists() && f.canRead()) {
						File parentFile = new File(loc).getParentFile();
								   ret  = new File(parentFile, ChiWenConfigConstants.XASECURE_AUDIT_FILE).toURI().toURL();
						break;
					}
				}
			}
		}
		catch(Throwable t) {
			LOG.error("Missing ChiWen Audit configuration files...");
			throw t;
		}
		return ret;
	}

	public static Configuration  buildChiWenSecurityConf(String serviceType) {
		Configuration chiwenConf = new Configuration();

		chiwenConf.set(getPropertyName(ChiWenConfigConstants.CHIWEN_SERVICE_NAME,serviceType),"");
		if (EmbeddedServiceDefsUtil.EMBEDDED_SERVICEDEF_KNOX_NAME.equals(serviceType) )  {
			chiwenConf.set(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_SOURCE_IMPL,serviceType),ChiWenConfigConstants.CHIWEN_KNOX_PLUGIN_POLICY_SOURCE_IMPL_DEFAULT);
		} else {
			chiwenConf.set(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_SOURCE_IMPL,serviceType),"");
		}
		chiwenConf.set(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_REST_URL,serviceType),"");
		chiwenConf.set(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_REST_SSL_CONFIG_FILE,serviceType), getPropertyName(ChiWenConfigConstants.XASECURE_POLICYMGR_SSL_FILE,serviceType));
		chiwenConf.set(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_POLLINVETERVALMS,serviceType), "");
		chiwenConf.set(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_CACHE_DIR,serviceType), "");
		chiwenConf.set(ChiWenConfigConstants.CHIWEN_PLUGIN_ADD_HADDOOP_AUTHORIZATION,"");

		return chiwenConf;
	}

	public static HashMap<String, String> getConfigChangeMap(String serviceType) {
		// ConfigMap for moving legacy Configuration to ChiWen Configuration
		HashMap<String, String> changeMap = new HashMap<>();
		
		changeMap.put(serviceType,
					  getPropertyName(ChiWenConfigConstants.CHIWEN_SERVICE_NAME,serviceType));
		changeMap.put(getPropertyName(ChiWenConfigConstants.XASECURE_POLICYMGR_URL,serviceType),
					  getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_REST_URL,serviceType));
		changeMap.put(getPropertyName(ChiWenConfigConstants.XASECURE_POLICYMGR_GRL_RELOADINTERVALINMILLIS,serviceType),
					  getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_POLLINVETERVALMS,serviceType));
		changeMap.put(getPropertyName(ChiWenConfigConstants.XASECURE_POLICYMGR_URL_LASTSTOREDFILE,serviceType),
					  getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_CACHE_DIR,serviceType));
		
				if (EmbeddedServiceDefsUtil.EMBEDDED_SERVICEDEF_HDFS_NAME.equals(serviceType)) {
			changeMap.put(ChiWenConfigConstants.XASECURE_ADD_HADDOP_AUTHORZATION,
				      ChiWenConfigConstants.CHIWEN_PLUGIN_ADD_HADDOOP_AUTHORIZATION);
		}
		
		if (EmbeddedServiceDefsUtil.EMBEDDED_SERVICEDEF_HBASE_NAME.equals(serviceType) ||
			EmbeddedServiceDefsUtil.EMBEDDED_SERVICEDEF_HIVE_NAME.equals(serviceType)) {
			changeMap.put(getPropertyName(ChiWenConfigConstants.XASECURE_UPDATE_XAPOLICIES_ON_GRANT,serviceType),
					getPropertyName(ChiWenConfigConstants.XASECURE_UPDATE_XAPOLICIES_ON_GRANT,serviceType));
		}
		
		if ( LOG.isDebugEnabled()) {
			for(Map.Entry<String, String> entry : changeMap.entrySet()) {
				String legacyKey 	 = entry.getKey();
				String chiwenKey 	 = entry.getValue();
				LOG.debug("<== getConfigChangeMap() ChiWenConfig Key: " + chiwenKey + " Legacy Key: " + legacyKey);
			}
		}
		
		return changeMap;
	}

	public static String getFileLocation(String fileName) {
		String ret = null;
		
		URL lurl = ChiWenLegacyConfigBuilder.class.getClassLoader().getResource(fileName);
		if (lurl == null ) {
			lurl = ChiWenLegacyConfigBuilder.class.getClassLoader().getResource("/" + fileName);
		}
		if (lurl != null) {
			ret = lurl.getFile();
		}
		return ret;
	}

	public static URL getFileURL(String fileName) {
		return ChiWenLegacyConfigBuilder.class.getClassLoader().getResource(fileName);
	}

	public static String getPropertyName(String chiwenProp, String serviceType) {
		return chiwenProp.replace("<ServiceType>", serviceType);
	}	

	public static String getPolicyMgrURL(String url) {
		int index = url.indexOf("/",url.lastIndexOf(":"));
		
		return url.substring(0,index);
	}

	public static String getServiceNameFromURL(String url) {
		int index = url.lastIndexOf("/");
		
		return url.substring(index+1);
	}

	
	public static String getCacheFileURL(String cacheFile) {
		int index = cacheFile.lastIndexOf("/");
		
		return cacheFile.substring(0,index);
	}

	public static String fetchLegacyValue(String legacyVal, String chiwenKey) {
		String ret = null;
	
		if ( chiwenKey.equals(getPropertyName(ChiWenConfigConstants.CHIWEN_SERVICE_NAME,serviceType)) ) {
			// To Fetch ServiceName
			ret = getServiceNameFromURL(legacyVal);
		} else if ( chiwenKey.equals(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_REST_URL,serviceType)) ) {
		   // To Fetch PolicyMgr URL
		   ret = getPolicyMgrURL(legacyVal);		
		} else if  ( chiwenKey.equals(getPropertyName(ChiWenConfigConstants.CHIWEN_PLUGIN_POLICY_CACHE_DIR,serviceType)) ) {
			  ret = getCacheFileURL(legacyVal);
	   }
	
	   return ret;
	}
}
