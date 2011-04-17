/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mule.tools.cloudconnect.it;

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;
import org.mule.tools.cloudconnect.annotations.Parameter;
import org.mule.tools.cloudconnect.annotations.Property;
import org.mule.tools.cloudconnect.annotations.Return;
import org.mule.tools.cloudconnect.annotations.OAuth;
import org.mule.tools.cloudconnect.annotations.OAuthClientId;
import org.mule.tools.cloudconnect.annotations.OAuthRedirectUri;
import org.mule.tools.cloudconnect.annotations.OAuthScope;
import org.mule.tools.cloudconnect.annotations.OAuthVersion;
import org.mule.tools.cloudconnect.annotations.OAuthAuthorizationCode;
import org.mule.tools.cloudconnect.annotations.OAuthAccessToken;
import org.mule.tools.cloudconnect.annotations.OAuthClientSecret;

import java.util.Map;

@Connector(namespacePrefix="oauth")
@OAuth(version=OAuthVersion.OA20,
       authorizationUrl="http://oauth.muleion.com/authorize",
   	   accessTokenUrl="http://oauth.muleion.com/access_token")
public class OAuthCloudConnector
{
	@Property
	@OAuthClientId
	private String appId;

    @Property
    @OAuthClientSecret
    private String appSecret;

	@Property
	@OAuthRedirectUri
	private String redirectUri;

    @Property(optional=true)
    @OAuthScope
    private String scope;

    @OAuthAuthorizationCode
    private String authorizationCode;

    @OAuthAccessToken
    private String accessToken;

    @Operation
    public String empty()
    {
        return "empty";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
