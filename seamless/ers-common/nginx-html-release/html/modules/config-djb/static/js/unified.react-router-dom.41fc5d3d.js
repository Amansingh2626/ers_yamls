(self.webpackChunk_unified_config_djb=self.webpackChunk_unified_config_djb||[]).push([[491],{5074:(e,t,n)=>{"use strict";n.d(t,{VK:()=>s});var r=n(4149);function o(e,t){return(o=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}var i=n(8172),a=n.n(i),c=n(2585);function u(){return(u=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}function l(e,t){if(null==e)return{};var n,r,o={},i=Object.keys(e);for(r=0;r<i.length;r++)n=i[r],t.indexOf(n)>=0||(o[n]=e[n]);return o}n(3980);var f=n(1898),s=function(e){function t(){for(var t,n=arguments.length,r=new Array(n),o=0;o<n;o++)r[o]=arguments[o];return(t=e.call.apply(e,[this].concat(r))||this).history=(0,c.lX)(t.props),t}return i=e,(n=t).prototype=Object.create(i.prototype),n.prototype.constructor=n,o(n,i),t.prototype.render=function(){return a().createElement(r.F0,{history:this.history,children:this.props.children})},t;var n,i}(a().Component);a().Component;var p=function(e,t){return"function"==typeof e?e(t):e},v=function(e,t){return"string"==typeof e?(0,c.ob)(e,null,null,t):e},y=function(e){return e},h=a().forwardRef;void 0===h&&(h=y);var m=h((function(e,t){var n=e.innerRef,r=e.navigate,o=e.onClick,i=l(e,["innerRef","navigate","onClick"]),c=i.target,f=u({},i,{onClick:function(e){try{o&&o(e)}catch(t){throw e.preventDefault(),t}e.defaultPrevented||0!==e.button||c&&"_self"!==c||function(e){return!!(e.metaKey||e.altKey||e.ctrlKey||e.shiftKey)}(e)||(e.preventDefault(),r())}});return f.ref=y!==h&&t||n,a().createElement("a",f)})),d=h((function(e,t){var n=e.component,o=void 0===n?m:n,i=e.replace,c=e.to,s=e.innerRef,d=l(e,["component","replace","to","innerRef"]);return a().createElement(r.s6.Consumer,null,(function(e){e||(0,f.Z)(!1);var n=e.history,r=v(p(c,e.location),e.location),l=r?n.createHref(r):"",m=u({},d,{href:l,navigate:function(){var t=p(c,e.location);(i?n.replace:n.push)(t)}});return y!==h?m.ref=t||s:m.innerRef=s,a().createElement(o,m)}))})),g=function(e){return e},b=a().forwardRef;void 0===b&&(b=g),b((function(e,t){var n=e["aria-current"],o=void 0===n?"page":n,i=e.activeClassName,c=void 0===i?"active":i,s=e.activeStyle,y=e.className,h=e.exact,m=e.isActive,C=e.location,_=e.sensitive,R=e.strict,j=e.style,k=e.to,w=e.innerRef,O=l(e,["aria-current","activeClassName","activeStyle","className","exact","isActive","location","sensitive","strict","style","to","innerRef"]);return a().createElement(r.s6.Consumer,null,(function(e){e||(0,f.Z)(!1);var n=C||e.location,i=v(p(k,n),n),l=i.pathname,E=l&&l.replace(/([.+*?=^!:${}()[\]|/\\])/g,"\\$1"),K=E?(0,r.LX)(n.pathname,{path:E,exact:h,sensitive:_,strict:R}):null,N=!!(m?m(K,n):K),x=N?function(){for(var e=arguments.length,t=new Array(e),n=0;n<e;n++)t[n]=arguments[n];return t.filter((function(e){return e})).join(" ")}(y,c):y,A=N?u({},j,{},s):j,P=u({"aria-current":N&&o||null,className:x,style:A,to:i},O);return g!==b?P.ref=t||w:P.innerRef=w,a().createElement(d,P)}))}))}}]);