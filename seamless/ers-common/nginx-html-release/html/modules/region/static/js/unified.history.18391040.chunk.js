(self.webpackChunk_unified_region=self.webpackChunk_unified_region||[]).push([[393],{2585:(n,t,e)=>{"use strict";function o(){return(o=Object.assign||function(n){for(var t=1;t<arguments.length;t++){var e=arguments[t];for(var o in e)Object.prototype.hasOwnProperty.call(e,o)&&(n[o]=e[o])}return n}).apply(this,arguments)}e.d(t,{lX:()=>P,q_:()=>A,ob:()=>d,PP:()=>E,Ep:()=>h,Hp:()=>l});var i=e(7613),r=e(838),a=e(1898);function c(n){return"/"===n.charAt(0)?n:"/"+n}function u(n){return"/"===n.charAt(0)?n.substr(1):n}function s(n,t){return function(n,t){return 0===n.toLowerCase().indexOf(t.toLowerCase())&&-1!=="/?#".indexOf(n.charAt(t.length))}(n,t)?n.substr(t.length):n}function f(n){return"/"===n.charAt(n.length-1)?n.slice(0,-1):n}function h(n){var t=n.pathname,e=n.search,o=n.hash,i=t||"/";return e&&"?"!==e&&(i+="?"===e.charAt(0)?e:"?"+e),o&&"#"!==o&&(i+="#"===o.charAt(0)?o:"#"+o),i}function d(n,t,e,r){var a;"string"==typeof n?(a=function(n){var t=n||"/",e="",o="",i=t.indexOf("#");-1!==i&&(o=t.substr(i),t=t.substr(0,i));var r=t.indexOf("?");return-1!==r&&(e=t.substr(r),t=t.substr(0,r)),{pathname:t,search:"?"===e?"":e,hash:"#"===o?"":o}}(n)).state=t:(void 0===(a=o({},n)).pathname&&(a.pathname=""),a.search?"?"!==a.search.charAt(0)&&(a.search="?"+a.search):a.search="",a.hash?"#"!==a.hash.charAt(0)&&(a.hash="#"+a.hash):a.hash="",void 0!==t&&void 0===a.state&&(a.state=t));try{a.pathname=decodeURI(a.pathname)}catch(n){throw n instanceof URIError?new URIError('Pathname "'+a.pathname+'" could not be decoded. This is likely caused by an invalid percent-encoding.'):n}return e&&(a.key=e),r?a.pathname?"/"!==a.pathname.charAt(0)&&(a.pathname=(0,i.Z)(a.pathname,r.pathname)):a.pathname=r.pathname:a.pathname||(a.pathname="/"),a}function l(n,t){return n.pathname===t.pathname&&n.search===t.search&&n.hash===t.hash&&n.key===t.key&&(0,r.Z)(n.state,t.state)}function v(){var n=null,t=[];return{setPrompt:function(t){return n=t,function(){n===t&&(n=null)}},confirmTransitionTo:function(t,e,o,i){if(null!=n){var r="function"==typeof n?n(t,e):n;"string"==typeof r?"function"==typeof o?o(r,i):i(!0):i(!1!==r)}else i(!0)},appendListener:function(n){var e=!0;function o(){e&&n.apply(void 0,arguments)}return t.push(o),function(){e=!1,t=t.filter((function(n){return n!==o}))}},notifyListeners:function(){for(var n=arguments.length,e=new Array(n),o=0;o<n;o++)e[o]=arguments[o];t.forEach((function(n){return n.apply(void 0,e)}))}}}var p=!("undefined"==typeof window||!window.document||!window.document.createElement);function w(n,t){t(window.confirm(n))}var g="popstate",m="hashchange";function y(){try{return window.history.state||{}}catch(n){return{}}}function P(n){void 0===n&&(n={}),p||(0,a.Z)(!1);var t,e=window.history,i=(-1===(t=window.navigator.userAgent).indexOf("Android 2.")&&-1===t.indexOf("Android 4.0")||-1===t.indexOf("Mobile Safari")||-1!==t.indexOf("Chrome")||-1!==t.indexOf("Windows Phone"))&&window.history&&"pushState"in window.history,r=!(-1===window.navigator.userAgent.indexOf("Trident")),u=n,l=u.forceRefresh,P=void 0!==l&&l,O=u.getUserConfirmation,x=void 0===O?w:O,k=u.keyLength,b=void 0===k?6:k,T=n.basename?f(c(n.basename)):"";function A(n){var t=n||{},e=t.key,o=t.state,i=window.location,r=i.pathname+i.search+i.hash;return T&&(r=s(r,T)),d(r,o,e)}function L(){return Math.random().toString(36).substr(2,b)}var E=v();function C(n){o(q,n),q.length=e.length,E.notifyListeners(q.location,q.action)}function S(n){(function(n){return void 0===n.state&&-1===navigator.userAgent.indexOf("CriOS")})(n)||I(A(n.state))}function U(){I(A(y()))}var H=!1;function I(n){H?(H=!1,C()):E.confirmTransitionTo(n,"POP",x,(function(t){t?C({action:"POP",location:n}):function(n){var t=q.location,e=M.indexOf(t.key);-1===e&&(e=0);var o=M.indexOf(n.key);-1===o&&(o=0);var i=e-o;i&&(H=!0,F(i))}(n)}))}var R=A(y()),M=[R.key];function _(n){return T+h(n)}function F(n){e.go(n)}var Z=0;function B(n){1===(Z+=n)&&1===n?(window.addEventListener(g,S),r&&window.addEventListener(m,U)):0===Z&&(window.removeEventListener(g,S),r&&window.removeEventListener(m,U))}var j=!1,q={length:e.length,action:"POP",location:R,createHref:_,push:function(n,t){var o="PUSH",r=d(n,t,L(),q.location);E.confirmTransitionTo(r,o,x,(function(n){if(n){var t=_(r),a=r.key,c=r.state;if(i)if(e.pushState({key:a,state:c},null,t),P)window.location.href=t;else{var u=M.indexOf(q.location.key),s=M.slice(0,u+1);s.push(r.key),M=s,C({action:o,location:r})}else window.location.href=t}}))},replace:function(n,t){var o="REPLACE",r=d(n,t,L(),q.location);E.confirmTransitionTo(r,o,x,(function(n){if(n){var t=_(r),a=r.key,c=r.state;if(i)if(e.replaceState({key:a,state:c},null,t),P)window.location.replace(t);else{var u=M.indexOf(q.location.key);-1!==u&&(M[u]=r.key),C({action:o,location:r})}else window.location.replace(t)}}))},go:F,goBack:function(){F(-1)},goForward:function(){F(1)},block:function(n){void 0===n&&(n=!1);var t=E.setPrompt(n);return j||(B(1),j=!0),function(){return j&&(j=!1,B(-1)),t()}},listen:function(n){var t=E.appendListener(n);return B(1),function(){B(-1),t()}}};return q}var O="hashchange",x={hashbang:{encodePath:function(n){return"!"===n.charAt(0)?n:"!/"+u(n)},decodePath:function(n){return"!"===n.charAt(0)?n.substr(1):n}},noslash:{encodePath:u,decodePath:c},slash:{encodePath:c,decodePath:c}};function k(n){var t=n.indexOf("#");return-1===t?n:n.slice(0,t)}function b(){var n=window.location.href,t=n.indexOf("#");return-1===t?"":n.substring(t+1)}function T(n){window.location.replace(k(window.location.href)+"#"+n)}function A(n){void 0===n&&(n={}),p||(0,a.Z)(!1);var t=window.history,e=(window.navigator.userAgent.indexOf("Firefox"),n),i=e.getUserConfirmation,r=void 0===i?w:i,u=e.hashType,l=void 0===u?"slash":u,g=n.basename?f(c(n.basename)):"",m=x[l],y=m.encodePath,P=m.decodePath;function A(){var n=P(b());return g&&(n=s(n,g)),d(n)}var L=v();function E(n){o(j,n),j.length=t.length,L.notifyListeners(j.location,j.action)}var C=!1,S=null;function U(){var n,t,e=b(),o=y(e);if(e!==o)T(o);else{var i=A(),a=j.location;if(!C&&(t=i,(n=a).pathname===t.pathname&&n.search===t.search&&n.hash===t.hash))return;if(S===h(i))return;S=null,function(n){if(C)C=!1,E();else{L.confirmTransitionTo(n,"POP",r,(function(t){t?E({action:"POP",location:n}):function(n){var t=j.location,e=M.lastIndexOf(h(t));-1===e&&(e=0);var o=M.lastIndexOf(h(n));-1===o&&(o=0);var i=e-o;i&&(C=!0,_(i))}(n)}))}}(i)}}var H=b(),I=y(H);H!==I&&T(I);var R=A(),M=[h(R)];function _(n){t.go(n)}var F=0;function Z(n){1===(F+=n)&&1===n?window.addEventListener(O,U):0===F&&window.removeEventListener(O,U)}var B=!1,j={length:t.length,action:"POP",location:R,createHref:function(n){var t=document.querySelector("base"),e="";return t&&t.getAttribute("href")&&(e=k(window.location.href)),e+"#"+y(g+h(n))},push:function(n,t){var e="PUSH",o=d(n,void 0,void 0,j.location);L.confirmTransitionTo(o,e,r,(function(n){if(n){var t=h(o),i=y(g+t);if(b()!==i){S=t,function(n){window.location.hash=n}(i);var r=M.lastIndexOf(h(j.location)),a=M.slice(0,r+1);a.push(t),M=a,E({action:e,location:o})}else E()}}))},replace:function(n,t){var e="REPLACE",o=d(n,void 0,void 0,j.location);L.confirmTransitionTo(o,e,r,(function(n){if(n){var t=h(o),i=y(g+t);b()!==i&&(S=t,T(i));var r=M.indexOf(h(j.location));-1!==r&&(M[r]=t),E({action:e,location:o})}}))},go:_,goBack:function(){_(-1)},goForward:function(){_(1)},block:function(n){void 0===n&&(n=!1);var t=L.setPrompt(n);return B||(Z(1),B=!0),function(){return B&&(B=!1,Z(-1)),t()}},listen:function(n){var t=L.appendListener(n);return Z(1),function(){Z(-1),t()}}};return j}function L(n,t,e){return Math.min(Math.max(n,t),e)}function E(n){void 0===n&&(n={});var t=n,e=t.getUserConfirmation,i=t.initialEntries,r=void 0===i?["/"]:i,a=t.initialIndex,c=void 0===a?0:a,u=t.keyLength,s=void 0===u?6:u,f=v();function l(n){o(P,n),P.length=P.entries.length,f.notifyListeners(P.location,P.action)}function p(){return Math.random().toString(36).substr(2,s)}var w=L(c,0,r.length-1),g=r.map((function(n){return d(n,void 0,"string"==typeof n?p():n.key||p())})),m=h;function y(n){var t=L(P.index+n,0,P.entries.length-1),o=P.entries[t];f.confirmTransitionTo(o,"POP",e,(function(n){n?l({action:"POP",location:o,index:t}):l()}))}var P={length:g.length,action:"POP",location:g[w],index:w,entries:g,createHref:m,push:function(n,t){var o="PUSH",i=d(n,t,p(),P.location);f.confirmTransitionTo(i,o,e,(function(n){if(n){var t=P.index+1,e=P.entries.slice(0);e.length>t?e.splice(t,e.length-t,i):e.push(i),l({action:o,location:i,index:t,entries:e})}}))},replace:function(n,t){var o="REPLACE",i=d(n,t,p(),P.location);f.confirmTransitionTo(i,o,e,(function(n){n&&(P.entries[P.index]=i,l({action:o,location:i}))}))},go:y,goBack:function(){y(-1)},goForward:function(){y(1)},canGo:function(n){var t=P.index+n;return t>=0&&t<P.entries.length},block:function(n){return void 0===n&&(n=!1),f.setPrompt(n)},listen:function(n){return f.appendListener(n)}};return P}}}]);