(self.webpackChunk_unified_account=self.webpackChunk_unified_account||[]).push([[5491],{95074:(t,e,r)=>{"use strict";r.r(e),r.d(e,{BrowserRouter:()=>p,HashRouter:()=>h,Link:()=>g,MemoryRouter:()=>n.VA,NavLink:()=>C,Prompt:()=>n.NL,Redirect:()=>n.l_,Route:()=>n.AW,Router:()=>n.F0,StaticRouter:()=>n.gx,Switch:()=>n.rs,generatePath:()=>n.Gn,matchPath:()=>n.LX,useHistory:()=>n.k6,useLocation:()=>n.TH,useParams:()=>n.UO,useRouteMatch:()=>n.$B,withRouter:()=>n.EN});var n=r(64149);function o(t,e){return(o=Object.setPrototypeOf||function(t,e){return t.__proto__=e,t})(t,e)}function i(t,e){t.prototype=Object.create(e.prototype),t.prototype.constructor=t,o(t,e)}var a=r(58172),c=r.n(a),u=r(2585);function l(){return(l=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var r=arguments[e];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(t[n]=r[n])}return t}).apply(this,arguments)}function s(t,e){if(null==t)return{};var r,n,o={},i=Object.keys(t);for(n=0;n<i.length;n++)r=i[n],e.indexOf(r)>=0||(o[r]=t[r]);return o}r(13980);var f=r(61898),p=function(t){function e(){for(var e,r=arguments.length,n=new Array(r),o=0;o<r;o++)n[o]=arguments[o];return(e=t.call.apply(t,[this].concat(n))||this).history=(0,u.lX)(e.props),e}return i(e,t),e.prototype.render=function(){return c().createElement(n.F0,{history:this.history,children:this.props.children})},e}(c().Component),h=function(t){function e(){for(var e,r=arguments.length,n=new Array(r),o=0;o<r;o++)n[o]=arguments[o];return(e=t.call.apply(t,[this].concat(n))||this).history=(0,u.q_)(e.props),e}return i(e,t),e.prototype.render=function(){return c().createElement(n.F0,{history:this.history,children:this.props.children})},e}(c().Component),v=function(t,e){return"function"==typeof t?t(e):t},y=function(t,e){return"string"==typeof t?(0,u.ob)(t,null,null,e):t},m=function(t){return t},d=c().forwardRef;void 0===d&&(d=m);var R=d((function(t,e){var r=t.innerRef,n=t.navigate,o=t.onClick,i=s(t,["innerRef","navigate","onClick"]),a=i.target,u=l({},i,{onClick:function(t){try{o&&o(t)}catch(e){throw t.preventDefault(),e}t.defaultPrevented||0!==t.button||a&&"_self"!==a||function(t){return!!(t.metaKey||t.altKey||t.ctrlKey||t.shiftKey)}(t)||(t.preventDefault(),n())}});return u.ref=m!==d&&e||r,c().createElement("a",u)})),g=d((function(t,e){var r=t.component,o=void 0===r?R:r,i=t.replace,a=t.to,u=t.innerRef,p=s(t,["component","replace","to","innerRef"]);return c().createElement(n.s6.Consumer,null,(function(t){t||(0,f.Z)(!1);var r=t.history,n=y(v(a,t.location),t.location),s=n?r.createHref(n):"",h=l({},p,{href:s,navigate:function(){var e=v(a,t.location);(i?r.replace:r.push)(e)}});return m!==d?h.ref=e||u:h.innerRef=u,c().createElement(o,h)}))})),w=function(t){return t},k=c().forwardRef;void 0===k&&(k=w);var C=k((function(t,e){var r=t["aria-current"],o=void 0===r?"page":r,i=t.activeClassName,a=void 0===i?"active":i,u=t.activeStyle,p=t.className,h=t.exact,m=t.isActive,d=t.location,R=t.sensitive,C=t.strict,_=t.style,b=t.to,O=t.innerRef,E=s(t,["aria-current","activeClassName","activeStyle","className","exact","isActive","location","sensitive","strict","style","to","innerRef"]);return c().createElement(n.s6.Consumer,null,(function(t){t||(0,f.Z)(!1);var r=d||t.location,i=y(v(b,r),r),s=i.pathname,N=s&&s.replace(/([.+*?=^!:${}()[\]|/\\])/g,"\\$1"),A=N?(0,n.LX)(r.pathname,{path:N,exact:h,sensitive:R,strict:C}):null,P=!!(m?m(A,r):A),j=P?function(){for(var t=arguments.length,e=new Array(t),r=0;r<t;r++)e[r]=arguments[r];return e.filter((function(t){return t})).join(" ")}(p,a):p,L=P?l({},_,{},u):_,x=l({"aria-current":P&&o||null,className:j,style:L,to:i},E);return w!==k?x.ref=e||O:x.innerRef=O,c().createElement(g,x)}))}))}}]);