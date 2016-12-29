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

package com.uimirror.poc.zull.api.filter;

import org.springframework.util.StringUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Jay on 30/12/16.
 */
public class RedirectFilter implements ContainerRequestFilter {


    @Override
    public void filter (ContainerRequestContext rq) throws IOException {
        final String redirectTo = rq.getHeaderString("redirectTo");
        if( StringUtils.hasText(redirectTo) && rq.getHeaderString("X-redirected-by") == null){
            rq.abortWith(Response.status(302).cookie(new NewCookie("routePn", redirectTo))
                    .header("redirectTo",redirectTo).build());
        }
    }
}
