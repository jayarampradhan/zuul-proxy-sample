/*******************************************************************************
 * Copyright (c) 2016 Uimirror.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Uimirror license
 * which accompanies this distribution, and is available at
 * http://www.uimirror.com/legal
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors: Jay
 * Uimirror Team
 *******************************************************************************/

package com.uimirror.poc.zull.proxy.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.http.client.ClientHttpResponse;

import java.util.List;

import static com.uimirror.poc.zull.proxy.common.CommonUtils.X_REDIRECT_BY;
import static com.uimirror.poc.zull.proxy.common.CommonUtils.X_REDIRECT_TO;
import static com.uimirror.poc.zull.proxy.common.CommonUtils.getRouteServiceId;

/**
 * Created by Jay on 29/12/16.
 */
public class RobinRoute301RedirectFilter extends RibbonRoutingFilter {

    public RobinRoute301RedirectFilter (ProxyRequestHelper helper, RibbonCommandFactory<?> ribbonCommandFactory
            , List<RibbonRequestCustomizer> requestCustomizers) {
        super(helper, ribbonCommandFactory, requestCustomizers);
    }

    @Override
    public Object run () {

        final ClientHttpResponse lastResponse = (ClientHttpResponse) super.run();

        if(lastResponse != null){
            final int status = RequestContext.getCurrentContext().getResponse().getStatus();
            if(status == 301) {
                final String routePn = lastResponse.getHeaders().get(X_REDIRECT_TO).get(0);
                RequestContext ctx = RequestContext.getCurrentContext();
                ctx.setRouteHost(null);
                ctx.addZuulRequestHeader(X_REDIRECT_BY, (String) ctx.get("serviceId"));
                ctx.set("serviceId", getRouteServiceId(routePn));
                return super.run();
            } else
                return lastResponse;
        }
        return null;
    }

}
