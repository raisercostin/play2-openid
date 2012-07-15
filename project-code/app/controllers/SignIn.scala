package controllers

import play.api._
import play.api.mvc._
import org.openid4java.consumer.ConsumerManager
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.openid4java.message.ax.FetchRequest
import org.openid4java.message.ParameterList
import org.openid4java.consumer.VerificationResult
import org.openid4java.message.AuthSuccess
import org.openid4java.message.ax.AxMessage
import org.openid4java.message.ax.FetchResponse
import org.openid4java.OpenIDException
import java.util.Arrays
import org.openid4java.discovery.DiscoveryInformation
import org.openid4java.discovery.Identifier
import ro.raisercostin.play.module.signin.User

object SignIn extends Controller {
    val manager = new ConsumerManager();
    def showLogin = Action {request=>
        Ok(views.html.showstuff(User.authenticated(request.session.get("user-id"))))
    }
    var discovered2: DiscoveryInformation = null

    def login(action: String, openIdIdentifier: String) = Action { request =>
        // configure the return_to URL where your application will receive
        // the authentication responses from the OpenID provider
        //request.domain + "/signin/authenticated" //
        val returnToUrl = "http://localhost:9000/authenticated";

        // --- Forward proxy setup (only if needed) ---
        // ProxyProperties proxyProps = new ProxyProperties();
        // proxyProps.setProxyName("proxy.example.com");
        // proxyProps.setProxyPort(8080);
        // HttpClientFactory.setProxyProperties(proxyProps);

        // perform discovery on the user-supplied identifier
        val discoveries = manager.discover(openIdIdentifier);

        // attempt to associate with the OpenID provider
        // and retrieve one service endpoint for authentication
        val discovered = manager.associate(discoveries);

        // obtain a AuthRequest message to be sent to the OpenID provider
        val authReq = manager.authenticate(discovered, returnToUrl);
        discovered2 = discovered

        // Attribute Exchange example: fetching the 'email' attribute
        val fetch = FetchRequest.createFetchRequest();
        fetch.addAttribute("email",
            // attribute alias
            "http://schema.openid.net/contact/email", // type URI
            true); // required

        // attach the extension to the authentication request
        authReq.addExtension(fetch);

        //        if (!discovered.isVersion2()) {
        // Option 1: GET HTTP-redirect to the OpenID Provider endpoint
        // The only method supported in OpenID 1.x
        // redirect-URL usually limited ~2048 bytes
        //store the discovery information in the user's session
        Logger.info("aici")
        Redirect(authReq.getDestinationUrl(true)).withSession("openid-disc" -> openIdIdentifier)
        //        } else {
        //            // Option 2: HTML FORM Redirection (Allows payloads >2048 bytes)
        //            val dispatcher =
        //                getServletContext().getRequestDispatcher("formredirection.jsp");
        //            httpReq.setAttribute("parameterMap", authReq.getParameterMap());
        //            httpReq.setAttribute("destinationUrl", authReq.getDestinationUrl(false));
        //            dispatcher.forward(httpReq, httpResp);
        //        }
        //        Ok(views.html.showstuff(action, openid_identifier))
    }
    def authenticated() = Action { request =>
        Logger.info("authenticated")
        try {
            // extract the parameters from the authentication response
            // (which comes in as a HTTP request from the OpenID provider)
            import collection.JavaConversions._
            val p = request.queryString
            Logger.info("parameters:" + p)

            def convert(src: Map[String, Seq[String]]): java.util.Map[String, Object] = {
                val result = new java.util.HashMap[String, Object]()
                for (a <- src.keys) {
                    val extractedLocalValue = src(a).toArray
                    val value = if (extractedLocalValue.length > 1) {
                        extractedLocalValue
                    } else {
                        extractedLocalValue(0)
                    }
                    result.put(a, value)
                    Logger.info(a + "=" + value)
                }
                //src.transform(value => value.asInstanceOf[String[]]);
                result;
            }

            val response = new ParameterList(convert(p))
            //             request.session.get("connected").map { user =>
            //    Ok("Hello " + user)
            //  }.getOrElse {
            //    Unauthorized("Oops, you are not connected")
            //  }
            // perform discovery on the user-supplied identifier
            val openIdIdentifier = request.session.get("openid-disc").get
            val discoveries = manager.discover(openIdIdentifier);

            // attempt to associate with the OpenID provider
            // and retrieve one service endpoint for authentication
            val discovered = discovered2 //manager.associate(discoveries);

            // retrieve the previously stored discovery information
            //            val discovered = (DiscoveryInformation)
            //                    httpReq.getSession().getAttribute("openid-disc");

            // extract the receiving URL from the HTTP request
            //            var receivingURL = request.uri //.getRequestURL();
            Logger.info(request.toString())
            Logger.info("aiciiiii")
            Logger.info(request.domain)
            Logger.info(request.host)
            Logger.info(request.method)
            Logger.info(request.path)
            Logger.info(request.rawQueryString)
            Logger.info(request.uri)
            val receivingURL = "http://" + request.host + request.uri
            Logger.info("receivingURL=" + receivingURL)
            //
            //request.rawQueryString //httpReq.getQueryString();
            //if (queryString != null && queryString.length() > 0)
            //    receivingURL += "?" + request.rawQueryString //httpReq.getQueryString();

            // verify the response; ConsumerManager needs to be the same
            // (static) instance used to place the authentication request
            val verification: VerificationResult = manager.verify(
                receivingURL.toString(),
                response, discovered);
            // examine the verification result and extract the verified identifier
            val verified: Identifier = verification.getVerifiedId();
            if (verified != null) {
                val authSuccess: AuthSuccess =
                    verification.getAuthResponse().asInstanceOf[AuthSuccess];

                val user:User = if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                    val fetchResp: FetchResponse = authSuccess
                        .getExtension(AxMessage.OPENID_NS_AX).asInstanceOf[FetchResponse]

                    val emails = fetchResp.getAttributeValues("email");
                    val email = emails.get(0).asInstanceOf[String];
                    User.authenticated(emails)
                } else {
                    User.authenticated(Option(verified.getIdentifier()))
                }
                Redirect(routes.SignIn.showLogin).withSession("user-id" -> user.id)
            } else {
                Unauthorized("not valid[" + verified + "]")
            }
        } catch {
            case e: OpenIDException =>
                Unauthorized("error to the user" + e)
        }
    }
}