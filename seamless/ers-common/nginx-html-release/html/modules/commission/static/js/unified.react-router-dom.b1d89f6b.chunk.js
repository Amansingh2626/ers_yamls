(self.webpackChunk_unified_commission=self.webpackChunk_unified_commission||[]).push([[5491],{95074:(e,t,r)=>{"use strict";r.r(t),r.d(t,{BrowserRouter:()=>p,HashRouter:()=>h,Link:()=>g,MemoryRouter:()=>n.VA,NavLink:()=>C,Prompt:()=>n.NL,Redirect:()=>n.l_,Route:()=>n.AW,Router:()=>n.F0,StaticRouter:()=>n.gx,Switch:()=>n.rs,generatePath:()=>n.Gn,matchPath:()=>n.LX,useHistory:()=>n.k6,useLocation:()=>n.TH,useParams:()=>n.UO,useRouteMatch:()=>n.$B,withRouter:()=>n.EN});var n=r(64149);function o(e,t){return(o=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function i(e,t){e.prototype=Object.create(t.prototype),e.prototype.constructor=e,o(e,t)}var a=r(58172),c=r.n(a),u=r(2585);function s(){return(s=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(this,arguments)}function l(e,t){if(null==e)return{};var r,n,o={},i=Object.keys(e);for(n=0;n<i.length;n++)r=i[n],t.indexOf(r)>=0||(o[r]=e[r]);return o}r(13980);var f=r(61898),p=function(e){function t(){for(var t,r=arguments.length,n=new Array(r),o=0;o<r;o++)n[o]=arguments[o];return(t=e.call.apply(e,[this].concat(n))||this).history=(0,u.lX)(t.props),t}return i(t,e),t.prototype.render=function(){return c().createElement(n.F0,{history:this.history,children:this.props.children})},t}(c().Component),h=function(e){function t(){for(var t,r=arguments.length,n=new Array(r),o=0;o<r;o++)n[o]=arguments[o];return(t=e.call.apply(e,[this].concat(n))||this).history=(0,u.q_)(t.props),t}return i(t,e),t.prototype.render=function(){return c().createElement(n.F0,{history:this.history,children:this.props.children})},t}(c().Component),v=function(e,t){return"function"==typeof e?e(t):e},y=function(e,t){return"string"==typeof e?(0,u.ob)(e,null,null,t):e},m=function(e){return e},d=c().forwardRef;void 0===d&&(d=m);var R=d((function(e,t){var r=e.innerRef,n=e.navigate,o=e.onClick,i=l(e,["innerRef","navigate","onClick"]),a=i.target,u=s({},i,{onClick:function(e){try{o&&o(e)}catch(t){throw e.preventDefault(),t}e.defaultPrevented||0!==e.button||a&&"_self"!==a||function(e){return!!(e.metaKey||e.altKey||e.ctrlKey||e.shiftKey)}(e)||(e.preventDefault(),n())}});return u.ref=m!==d&&t||r,c().createElement("a",u)})),g=d((function(e,t){var r=e.component,o=void 0===r?R:r,i=e.replace,a=e.to,u=e.innerRef,p=l(e,["component","replace","to","innerRef"]);return c().createElement(n.s6.Consumer,null,(function(e){e||(0,f.Z)(!1);var r=e.history,n=y(v(a,e.location),e.location),l=n?r.createHref(n):"",h=s({},p,{href:l,navigate:function(){var t=v(a,e.location);(i?r.replace:r.push)(t)}});return m!==d?h.ref=t||u:h.innerRef=u,c().createElement(o,h)}))})),w=function(e){return e},k=c().forwardRef;void 0===k&&(k=w);var C=k((function(e,t){var r=e["aria-current"],o=void 0===r?"page":r,i=e.activeClassName,a=void 0===i?"active":i,u=e.activeStyle,p=e.className,h=e.exact,m=e.isActive,d=e.location,R=e.sensitive,C=e.strict,_=e.style,b=e.to,O=e.innerRef,E=l(e,["aria-current","activeClassName","activeStyle","className","exact","isActive","location","sensitive","strict","style","to","innerRef"]);return c().createElement(n.s6.Consumer,null,(function(e){e||(0,f.Z)(!1);var r=d||e.location,i=y(v(b,r),r),l=i.pathname,N=l&&l.replace(/([.+*?=^!:${}()[\]|/\\])/g,"\\$1"),A=N?(0,n.LX)(r.pathname,{path:N,exact:h,sensitive:R,strict:C}):null,P=!!(m?m(A,r):A),j=P?function(){for(var e=arguments.length,t=new Array(e),r=0;r<e;r++)t[r]=arguments[r];return t.filter((function(e){return e})).join(" ")}(p,a):p,L=P?s({},_,{},u):_,x=s({"aria-current":P&&o||null,className:j,style:L,to:i},E);return w!==k?x.ref=t||O:x.innerRef=O,c().createElement(g,x)}))}))}}]);