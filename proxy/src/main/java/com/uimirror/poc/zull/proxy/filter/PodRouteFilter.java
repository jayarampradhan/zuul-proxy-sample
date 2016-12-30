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

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static com.uimirror.poc.zull.proxy.common.CommonUtils.ROUTE_ID_HEADER_KEY;
import static com.uimirror.poc.zull.proxy.common.CommonUtils.getRouteServiceId;

/**
 * Created by Jay on 29/12/16.
 */
@Component
public class PodRouteFilter extends ZuulFilter {

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

    /**
     * Gets the header key for the route, accordingly process the service id
     * @return <code>null</code>
     */
    @Override
    public Object run () {
        final RequestContext context = getCurrentContext();
        final String routePn = context.getRequest().getHeader(ROUTE_ID_HEADER_KEY);
        context.setRouteHost(null);
        context.set("serviceId", getRouteServiceId(routePn));
        return null;
    }

}
