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
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

/**
 * Created by Jay on 29/12/16.
 */
public class PreRobinRouteFilter extends RibbonRoutingFilter {

    public PreRobinRouteFilter(ProxyRequestHelper helper,
                               RibbonCommandFactory<?> ribbonCommandFactory) {
        super(helper, ribbonCommandFactory);
    }

    public PreRobinRouteFilter(RibbonCommandFactory<?> ribbonCommandFactory) {
        this(new ProxyRequestHelper(), ribbonCommandFactory);
    }

    @Override
    public Object run () {
        final ClientHttpResponse run = (ClientHttpResponse) super.run();
        if(run != null){
            final int status = RequestContext.getCurrentContext().getResponse().getStatus();
            if(status == 302){
                final String routePn = run.getHeaders().get("redirectTo").get(0);
                String routeId="pod";
                if( StringUtils.hasText(routePn)){
                    routeId += routePn;
                }
                RequestContext ctx = RequestContext.getCurrentContext();
                ctx.setRouteHost(null);
                ctx.addZuulRequestHeader("X-redirected-by", ctx.getRequest().getHeader("redirectTo"));
                RequestContext.getCurrentContext().set("serviceId",routeId);
                return super.run();
            }else
                return run;
        }
        return null;
    }

}
