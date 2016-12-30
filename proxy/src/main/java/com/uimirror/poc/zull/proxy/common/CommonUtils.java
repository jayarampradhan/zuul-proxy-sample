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

package com.uimirror.poc.zull.proxy.common;

import org.springframework.util.StringUtils;

/**
 * Created by Jay on 30/12/16.
 */
public class CommonUtils {

    private CommonUtils (){
        //NOP
    }

    public static final String ROUTE_ID_HEADER_KEY = "X-Route-Id";
    public static final String X_REDIRECT_TO = "X-Redirect-To";
    public static final String X_REDIRECT_BY = "X-Redirect-By";

    public static String getRouteServiceId(String routePn){
        String routeId="pod";
        routeId += StringUtils.hasText(routePn) ? routePn : "any";
        return routeId;
    }

}
