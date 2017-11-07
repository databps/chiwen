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

package com.databps.bigdaf.chiwen.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChiWenAccessRequestUtil {
	private static final Log LOG = LogFactory.getLog(ChiWenAccessRequestUtil.class);

	public static final String KEY_CONTEXT_TAGS                = "TAGS";
	public static final String KEY_CONTEXT_TAG_OBJECT          = "TAG_OBJECT";
	public static final String KEY_CONTEXT_RESOURCE            = "RESOURCE";
	public static final String KEY_CONTEXT_REQUESTED_RESOURCES = "REQUESTED_RESOURCES";
	public static final String KEY_TOKEN_NAMESPACE = "token:";
	public static final String KEY_USER = "USER";


	public static Map<String, Object> copyContext(Map<String, Object> context) {
		final Map<String, Object> ret;

		if(MapUtils.isEmpty(context)) {
			ret = new HashMap<String, Object>();
		} else {
			ret = new HashMap<String, Object>(context);

			ret.remove(KEY_CONTEXT_TAGS);
			ret.remove(KEY_CONTEXT_TAG_OBJECT);
			ret.remove(KEY_CONTEXT_RESOURCE);
			// don't remove REQUESTED_RESOURCES
		}

		return ret;
	}

	public static void setCurrentUserInContext(Map<String, Object> context, String user) {
		setTokenInContext(context, KEY_USER, user);
	}

	public static String getCurrentUserFromContext(Map<String, Object> context) {
		Object ret = getTokenFromContext(context, KEY_USER);
		return ret != null ? ret.toString() : "";
	}

	public static void setTokenInContext(Map<String, Object> context, String tokenName, Object tokenValue) {
		String tokenNameWithNamespace = KEY_TOKEN_NAMESPACE + tokenName;//token:USER   hbase
		context.put(tokenNameWithNamespace, tokenValue);
	}
	public static Object getTokenFromContext(Map<String, Object> context, String tokenName) {
		String tokenNameWithNamespace = KEY_TOKEN_NAMESPACE + tokenName;
		return MapUtils.isNotEmpty(context) ? context.get(tokenNameWithNamespace) : null;
	}
}
