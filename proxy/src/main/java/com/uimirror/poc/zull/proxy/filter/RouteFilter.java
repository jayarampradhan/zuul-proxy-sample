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

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by Jay on 29/12/16.
 */
@Component
public class RouteFilter extends ZuulFilter {

    @Override
    public String filterType () {
        return "pre";
    }

    @Override
    public int filterOrder () {
        return 1;
    }

    @Override
    public boolean shouldFilter () {
        return Boolean.TRUE;
    }

    @Override
    public Object run () {
        final String routePn = RequestContext.getCurrentContext().getRequest().getHeader("routePn");
        String routeId="pod";
        if(StringUtils.hasText(routePn)){
            routeId += routePn;
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setRouteHost(null);
        ctx.set("serviceId",routeId);
        return null;
    }

}
