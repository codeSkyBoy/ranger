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

package org.apache.ranger.services.solr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ranger.plugin.model.RangerService;
import org.apache.ranger.plugin.model.RangerServiceDef;
import org.apache.ranger.plugin.service.RangerBaseService;
import org.apache.ranger.plugin.service.ResourceLookupContext;
import org.apache.ranger.services.solr.client.ServiceSolrClient;
import org.apache.ranger.services.solr.client.ServiceSolrConnectionMgr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RangerServiceSolr extends RangerBaseService {

	private static final Log LOG = LogFactory.getLog(RangerServiceSolr.class);

	public RangerServiceSolr() {
		super();
	}

	@Override
	public void init(RangerServiceDef serviceDef, RangerService service) {
		super.init(serviceDef, service);
	}

	@Override
	public Map<String, Object> validateConfig() throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		String serviceName = getServiceName();
		if (LOG.isDebugEnabled()) {
			LOG.debug("==> RangerServiceSolr.validateConfig Service: ("
					+ serviceName + " )");
		}
		if (configs != null) {
			try {
				ret = ServiceSolrConnectionMgr.connectionTest(serviceName,
						configs);
			} catch (Exception e) {
				LOG.error("<== RangerServiceSolr.validateConfig Error:", e);
				throw e;
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("<== RangerServiceSolr.validateConfig Response : (" + ret
					+ " )");
		}
		return ret;
	}

	@Override
	public List<String> lookupResource(ResourceLookupContext context)
			throws Exception {

		ServiceSolrClient serviceSolrClient = ServiceSolrConnectionMgr
				.getSolrClient(serviceName, configs);
		return serviceSolrClient.getResources(context);
	}
}
