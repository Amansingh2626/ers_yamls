/*! For license information please see unified.react-router.49f3f489.chunk.js.LICENSE.txt */
(self.webpackChunk_unified_settings=self.webpackChunk_unified_settings||[]).push([[4486],{64149:(t,n,e)=>{"use strict";function r(t,n){return(r=Object.setPrototypeOf||function(t,n){return t.__proto__=n,t})(t,n)}function o(t,n){t.prototype=Object.create(n.prototype),t.prototype.constructor=t,r(t,n)}e.d(n,{VA:()=>g,NL:()=>E,l_:()=>k,AW:()=>w,F0:()=>b,gx:()=>N,rs:()=>j,s6:()=>v,Gn:()=>x,LX:()=>U,k6:()=>T,TH:()=>W,UO:()=>D,$B:()=>F,EN:()=>H});var i=e(58172),a=e.n(i),c=(e(13980),e(2585)),u=e(85053),s=e(61898);function l(){return(l=Object.assign||function(t){for(var n=1;n<arguments.length;n++){var e=arguments[n];for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&(t[r]=e[r])}return t}).apply(this,arguments)}var p=e(20099),f=e.n(p);function h(t,n){if(null==t)return{};var e,r,o={},i=Object.keys(t);for(r=0;r<i.length;r++)e=i[r],n.indexOf(e)>=0||(o[e]=t[e]);return o}e(89744);var m=e(73463),d=e.n(m),y=function(t){var n=(0,u.Z)();return n.displayName="Router-History",n}(),v=function(t){var n=(0,u.Z)();return n.displayName="Router",n}(),b=function(t){function n(n){var e;return(e=t.call(this,n)||this).state={location:n.history.location},e._isMounted=!1,e._pendingLocation=null,n.staticContext||(e.unlisten=n.history.listen((function(t){e._isMounted?e.setState({location:t}):e._pendingLocation=t}))),e}o(n,t),n.computeRootMatch=function(t){return{path:"/",url:"/",params:{},isExact:"/"===t}};var e=n.prototype;return e.componentDidMount=function(){this._isMounted=!0,this._pendingLocation&&this.setState({location:this._pendingLocation})},e.componentWillUnmount=function(){this.unlisten&&this.unlisten()},e.render=function(){return a().createElement(v.Provider,{value:{history:this.props.history,location:this.state.location,match:n.computeRootMatch(this.state.location.pathname),staticContext:this.props.staticContext}},a().createElement(y.Provider,{children:this.props.children||null,value:this.props.history}))},n}(a().Component),g=function(t){function n(){for(var n,e=arguments.length,r=new Array(e),o=0;o<e;o++)r[o]=arguments[o];return(n=t.call.apply(t,[this].concat(r))||this).history=(0,c.PP)(n.props),n}return o(n,t),n.prototype.render=function(){return a().createElement(b,{history:this.history,children:this.props.children})},n}(a().Component),C=function(t){function n(){return t.apply(this,arguments)||this}o(n,t);var e=n.prototype;return e.componentDidMount=function(){this.props.onMount&&this.props.onMount.call(this,this)},e.componentDidUpdate=function(t){this.props.onUpdate&&this.props.onUpdate.call(this,this,t)},e.componentWillUnmount=function(){this.props.onUnmount&&this.props.onUnmount.call(this,this)},e.render=function(){return null},n}(a().Component);function E(t){var n=t.message,e=t.when,r=void 0===e||e;return a().createElement(v.Consumer,null,(function(t){if(t||(0,s.Z)(!1),!r||t.staticContext)return null;var e=t.history.block;return a().createElement(C,{onMount:function(t){t.release=e(n)},onUpdate:function(t,r){r.message!==n&&(t.release(),t.release=e(n))},onUnmount:function(t){t.release()},message:n})}))}var S={},_=0;function x(t,n){return void 0===t&&(t="/"),void 0===n&&(n={}),"/"===t?t:function(t){if(S[t])return S[t];var n=f().compile(t);return _<1e4&&(S[t]=n,_++),n}(t)(n,{pretty:!0})}function k(t){var n=t.computedMatch,e=t.to,r=t.push,o=void 0!==r&&r;return a().createElement(v.Consumer,null,(function(t){t||(0,s.Z)(!1);var r=t.history,i=t.staticContext,u=o?r.push:r.replace,p=(0,c.ob)(n?"string"==typeof e?x(e,n.params):l({},e,{pathname:x(e.pathname,n.params)}):e);return i?(u(p),null):a().createElement(C,{onMount:function(){u(p)},onUpdate:function(t,n){var e=(0,c.ob)(n.to);(0,c.Hp)(e,l({},p,{key:e.key}))||u(p)},to:e})}))}var M={},P=0;function U(t,n){void 0===n&&(n={}),("string"==typeof n||Array.isArray(n))&&(n={path:n});var e=n,r=e.path,o=e.exact,i=void 0!==o&&o,a=e.strict,c=void 0!==a&&a,u=e.sensitive,s=void 0!==u&&u;return[].concat(r).reduce((function(n,e){if(!e&&""!==e)return null;if(n)return n;var r=function(t,n){var e=""+n.end+n.strict+n.sensitive,r=M[e]||(M[e]={});if(r[t])return r[t];var o=[],i={regexp:f()(t,o,n),keys:o};return P<1e4&&(r[t]=i,P++),i}(e,{end:i,strict:c,sensitive:s}),o=r.regexp,a=r.keys,u=o.exec(t);if(!u)return null;var l=u[0],p=u.slice(1),h=t===l;return i&&!h?null:{path:e,url:"/"===e&&""===l?"/":l,isExact:h,params:a.reduce((function(t,n,e){return t[n.name]=p[e],t}),{})}}),null)}var w=function(t){function n(){return t.apply(this,arguments)||this}return o(n,t),n.prototype.render=function(){var t=this;return a().createElement(v.Consumer,null,(function(n){n||(0,s.Z)(!1);var e=t.props.location||n.location,r=l({},n,{location:e,match:t.props.computedMatch?t.props.computedMatch:t.props.path?U(e.pathname,t.props):n.match}),o=t.props,i=o.children,c=o.component,u=o.render;return Array.isArray(i)&&0===i.length&&(i=null),a().createElement(v.Provider,{value:r},r.match?i?"function"==typeof i?i(r):i:c?a().createElement(c,r):u?u(r):null:"function"==typeof i?i(r):null)}))},n}(a().Component);function O(t){return"/"===t.charAt(0)?t:"/"+t}function A(t,n){if(!t)return n;var e=O(t);return 0!==n.pathname.indexOf(e)?n:l({},n,{pathname:n.pathname.substr(e.length)})}function R(t){return"string"==typeof t?t:(0,c.Ep)(t)}function L(t){return function(){(0,s.Z)(!1)}}function Z(){}var N=function(t){function n(){for(var n,e=arguments.length,r=new Array(e),o=0;o<e;o++)r[o]=arguments[o];return(n=t.call.apply(t,[this].concat(r))||this).handlePush=function(t){return n.navigateTo(t,"PUSH")},n.handleReplace=function(t){return n.navigateTo(t,"REPLACE")},n.handleListen=function(){return Z},n.handleBlock=function(){return Z},n}o(n,t);var e=n.prototype;return e.navigateTo=function(t,n){var e=this.props,r=e.basename,o=void 0===r?"":r,i=e.context,a=void 0===i?{}:i;a.action=n,a.location=function(t,n){return t?l({},n,{pathname:O(t)+n.pathname}):n}(o,(0,c.ob)(t)),a.url=R(a.location)},e.render=function(){var t=this.props,n=t.basename,e=void 0===n?"":n,r=t.context,o=void 0===r?{}:r,i=t.location,u=void 0===i?"/":i,s=h(t,["basename","context","location"]),p={createHref:function(t){return O(e+R(t))},action:"POP",location:A(e,(0,c.ob)(u)),push:this.handlePush,replace:this.handleReplace,go:L(),goBack:L(),goForward:L(),listen:this.handleListen,block:this.handleBlock};return a().createElement(b,l({},s,{history:p,staticContext:o}))},n}(a().Component),j=function(t){function n(){return t.apply(this,arguments)||this}return o(n,t),n.prototype.render=function(){var t=this;return a().createElement(v.Consumer,null,(function(n){n||(0,s.Z)(!1);var e,r,o=t.props.location||n.location;return a().Children.forEach(t.props.children,(function(t){if(null==r&&a().isValidElement(t)){e=t;var i=t.props.path||t.props.from;r=i?U(o.pathname,l({},t.props,{path:i})):n.match}})),r?a().cloneElement(e,{location:o,computedMatch:r}):null}))},n}(a().Component);function H(t){var n="withRouter("+(t.displayName||t.name)+")",e=function(n){var e=n.wrappedComponentRef,r=h(n,["wrappedComponentRef"]);return a().createElement(v.Consumer,null,(function(n){return n||(0,s.Z)(!1),a().createElement(t,l({},r,n,{ref:e}))}))};return e.displayName=n,e.WrappedComponent=t,d()(e,t)}var B=a().useContext;function T(){return B(y)}function W(){return B(v).location}function D(){var t=B(v).match;return t?t.params:{}}function F(t){var n=W(),e=B(v).match;return t?U(n.pathname,t):e}},61897:(t,n)=>{"use strict";var e="function"==typeof Symbol&&Symbol.for;e&&Symbol.for("react.element"),e&&Symbol.for("react.portal"),e&&Symbol.for("react.fragment"),e&&Symbol.for("react.strict_mode"),e&&Symbol.for("react.profiler"),e&&Symbol.for("react.provider"),e&&Symbol.for("react.context"),e&&Symbol.for("react.async_mode"),e&&Symbol.for("react.concurrent_mode"),e&&Symbol.for("react.forward_ref"),e&&Symbol.for("react.suspense"),e&&Symbol.for("react.suspense_list"),e&&Symbol.for("react.memo"),e&&Symbol.for("react.lazy"),e&&Symbol.for("react.block"),e&&Symbol.for("react.fundamental"),e&&Symbol.for("react.responder"),e&&Symbol.for("react.scope")},89744:(t,n,e)=>{"use strict";e(61897)}}]);