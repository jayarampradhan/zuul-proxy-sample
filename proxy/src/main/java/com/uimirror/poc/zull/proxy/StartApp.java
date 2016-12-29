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

package com.uimirror.poc.zull.proxy;

import com.uimirror.poc.zull.proxy.filter.PreRobinRouteFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.context.annotation.Bean;

/**
 * Created by Jay on 27/12/16.
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableZuulProxy
public class StartApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new StartApp()
                .configure(new SpringApplicationBuilder(StartApp.class))
                .run(args);
    }

    @Bean
    public RibbonRoutingFilter ribbonRoutingFilter(ProxyRequestHelper helper,
                                                   RibbonCommandFactory<?> ribbonCommandFactory) {
        RibbonRoutingFilter filter = new PreRobinRouteFilter(helper,
                ribbonCommandFactory);
        return filter;
    }

}
