(self.webpackChunk_unified_thresholds=self.webpackChunk_unified_thresholds||[]).push([[5491],{95074:(t,e,r)=>{"use strict";r.r(e),r.d(e,{BrowserRouter:()=>h,HashRouter:()=>p,Link:()=>g,MemoryRouter:()=>n.MemoryRouter,NavLink:()=>C,Prompt:()=>n.Prompt,Redirect:()=>n.Redirect,Route:()=>n.Route,Router:()=>n.Router,StaticRouter:()=>n.StaticRouter,Switch:()=>n.Switch,generatePath:()=>n.generatePath,matchPath:()=>n.matchPath,useHistory:()=>n.useHistory,useLocation:()=>n.useLocation,useParams:()=>n.useParams,useRouteMatch:()=>n.useRouteMatch,withRouter:()=>n.withRouter});var n=r(7909);function o(t,e){return(o=Object.setPrototypeOf||function(t,e){return t.__proto__=e,t})(t,e)}function a(t,e){t.prototype=Object.create(e.prototype),t.prototype.constructor=t,o(t,e)}var i=r(58172),c=r.n(i),u=r(2585);function s(){return(s=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var r=arguments[e];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(t[n]=r[n])}return t}).apply(this,arguments)}function l(t,e){if(null==t)return{};var r,n,o={},a=Object.keys(t);for(n=0;n<a.length;n++)r=a[n],e.indexOf(r)>=0||(o[r]=t[r]);return o}r(13980);var f=r(61898),h=function(t){function e(){for(var e,r=arguments.length,n=new Array(r),o=0;o<r;o++)n[o]=arguments[o];return(e=t.call.apply(t,[this].concat(n))||this).history=(0,u.lX)(e.props),e}return a(e,t),e.prototype.render=function(){return c().createElement(n.Router,{history:this.history,children:this.props.children})},e}(c().Component),p=function(t){function e(){for(var e,r=arguments.length,n=new Array(r),o=0;o<r;o++)n[o]=arguments[o];return(e=t.call.apply(t,[this].concat(n))||this).history=(0,u.q_)(e.props),e}return a(e,t),e.prototype.render=function(){return c().createElement(n.Router,{history:this.history,children:this.props.children})},e}(c().Component),v=function(t,e){return"function"==typeof t?t(e):t},y=function(t,e){return"string"==typeof t?(0,u.ob)(t,null,null,e):t},m=function(t){return t},R=c().forwardRef;void 0===R&&(R=m);var d=R((function(t,e){var r=t.innerRef,n=t.navigate,o=t.onClick,a=l(t,["innerRef","navigate","onClick"]),i=a.target,u=s({},a,{onClick:function(t){try{o&&o(t)}catch(e){throw t.preventDefault(),e}t.defaultPrevented||0!==t.button||i&&"_self"!==i||function(t){return!!(t.metaKey||t.altKey||t.ctrlKey||t.shiftKey)}(t)||(t.preventDefault(),n())}});return u.ref=m!==R&&e||r,c().createElement("a",u)})),g=R((function(t,e){var r=t.component,o=void 0===r?d:r,a=t.replace,i=t.to,u=t.innerRef,h=l(t,["component","replace","to","innerRef"]);return c().createElement(n.__RouterContext.Consumer,null,(function(t){t||(0,f.Z)(!1);var r=t.history,n=y(v(i,t.location),t.location),l=n?r.createHref(n):"",p=s({},h,{href:l,navigate:function(){var e=v(i,t.location);(a?r.replace:r.push)(e)}});return m!==R?p.ref=e||u:p.innerRef=u,c().createElement(o,p)}))})),w=function(t){return t},_=c().forwardRef;void 0===_&&(_=w);var C=_((function(t,e){var r=t["aria-current"],o=void 0===r?"page":r,a=t.activeClassName,i=void 0===a?"active":a,u=t.activeStyle,h=t.className,p=t.exact,m=t.isActive,R=t.location,d=t.sensitive,C=t.strict,P=t.style,k=t.to,b=t.innerRef,O=l(t,["aria-current","activeClassName","activeStyle","className","exact","isActive","location","sensitive","strict","style","to","innerRef"]);return c().createElement(n.__RouterContext.Consumer,null,(function(t){t||(0,f.Z)(!1);var r=R||t.location,a=y(v(k,r),r),l=a.pathname,E=l&&l.replace(/([.+*?=^!:${}()[\]|/\\])/g,"\\$1"),j=E?(0,n.matchPath)(r.pathname,{path:E,exact:p,sensitive:d,strict:C}):null,x=!!(m?m(j,r):j),N=x?function(){for(var t=arguments.length,e=new Array(t),r=0;r<t;r++)e[r]=arguments[r];return e.filter((function(t){return t})).join(" ")}(h,i):h,S=x?s({},P,{},u):P,A=s({"aria-current":x&&o||null,className:N,style:S,to:a},O);return w!==_?A.ref=e||b:A.innerRef=b,c().createElement(g,A)}))}))}}]);