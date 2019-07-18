import { string } from "prop-types";

export function authHeader():Headers {
    // return authorization header with basic auth credentials
    let user = JSON.parse(localStorage.getItem('user') || '{}');
    let requestHeaders: HeadersInit = new Headers();
    // TODO: In actual project you would want more than Basic authentication
    //       This could include SSO etc.
    
    if (user && user.authdata) {
        requestHeaders.set('Authorization', 'Basic ' + user.authdata);
    }
    return requestHeaders;
    
}