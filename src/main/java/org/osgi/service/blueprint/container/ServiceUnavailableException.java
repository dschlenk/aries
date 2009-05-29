/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.osgi.service.blueprint.container;

/**
 * TODO: need to update this one wrt the official API, but it brings a dependency on osgi 1.5
 */
public class ServiceUnavailableException extends RuntimeException {
    
    private Class serviceType;
    private String filter;

    public ServiceUnavailableException(String message, Class serviceType, String filter) {
        super(message);
        this.serviceType = serviceType;
        this.filter = filter;
    }
    
    public Class getServiceType() {
        return this.serviceType;
    }
    
    public String getFilter() {
        return this.filter;
    }

}
