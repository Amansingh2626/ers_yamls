/*! For license information please see unified.react-redux.2f09fd51.chunk.js.LICENSE.txt */
(self.webpackChunk_unified_voucher=self.webpackChunk_unified_voucher||[]).push([[3251],{21725:(e,r,n)=>{"use strict";n.r(r),n.d(r,{Provider:()=>f,ReactReduxContext:()=>u,batch:()=>X.unstable_batchedUpdates,connect:()=>A,connectAdvanced:()=>O,createDispatchHook:()=>I,createSelectorHook:()=>G,createStoreHook:()=>K,shallowEqual:()=>x,useDispatch:()=>L,useSelector:()=>Q,useStore:()=>z});var t=n(93304),o=n.n(t),u=(n(13980),o().createContext(null)),a=function(e){e()},i=function(){return a},c={notify:function(){}},s=function(){function e(e,r){this.store=e,this.parentSub=r,this.unsubscribe=null,this.listeners=c,this.handleChangeWrapper=this.handleChangeWrapper.bind(this)}var r=e.prototype;return r.addNestedSub=function(e){return this.trySubscribe(),this.listeners.subscribe(e)},r.notifyNestedSubs=function(){this.listeners.notify()},r.handleChangeWrapper=function(){this.onStateChange&&this.onStateChange()},r.isSubscribed=function(){return Boolean(this.unsubscribe)},r.trySubscribe=function(){this.unsubscribe||(this.unsubscribe=this.parentSub?this.parentSub.addNestedSub(this.handleChangeWrapper):this.store.subscribe(this.handleChangeWrapper),this.listeners=function(){var e=i(),r=null,n=null;return{clear:function(){r=null,n=null},notify:function(){e((function(){for(var e=r;e;)e.callback(),e=e.next}))},get:function(){for(var e=[],n=r;n;)e.push(n),n=n.next;return e},subscribe:function(e){var t=!0,o=n={callback:e,next:null,prev:n};return o.prev?o.prev.next=o:r=o,function(){t&&null!==r&&(t=!1,o.next?o.next.prev=o.prev:n=o.prev,o.prev?o.prev.next=o.next:r=o.next)}}}}())},r.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null,this.listeners.clear(),this.listeners=c)},e}();const f=function(e){var r=e.store,n=e.context,a=e.children,i=(0,t.useMemo)((function(){var e=new s(r);return e.onStateChange=e.notifyNestedSubs,{store:r,subscription:e}}),[r]),c=(0,t.useMemo)((function(){return r.getState()}),[r]);(0,t.useEffect)((function(){var e=i.subscription;return e.trySubscribe(),c!==r.getState()&&e.notifyNestedSubs(),function(){e.tryUnsubscribe(),e.onStateChange=null}}),[i,c]);var f=n||u;return o().createElement(f.Provider,{value:i},a)};function p(){return(p=Object.assign||function(e){for(var r=1;r<arguments.length;r++){var n=arguments[r];for(var t in n)Object.prototype.hasOwnProperty.call(n,t)&&(e[t]=n[t])}return e}).apply(this,arguments)}function l(e,r){if(null==e)return{};var n,t,o={},u=Object.keys(e);for(t=0;t<u.length;t++)n=u[t],r.indexOf(n)>=0||(o[n]=e[n]);return o}var d=n(73463),v=n.n(d),h=n(63920),b="undefined"!=typeof window&&void 0!==window.document&&void 0!==window.document.createElement?t.useLayoutEffect:t.useEffect,m=[],y=[null,null];function S(e,r){var n=e[1];return[r.payload,n+1]}function P(e,r,n){b((function(){return e.apply(void 0,r)}),n)}function g(e,r,n,t,o,u,a){e.current=t,r.current=o,n.current=!1,u.current&&(u.current=null,a())}function w(e,r,n,t,o,u,a,i,c,s){if(e){var f=!1,p=null,l=function(){if(!f){var e,n,l=r.getState();try{e=t(l,o.current)}catch(e){n=e,p=e}n||(p=null),e===u.current?a.current||c():(u.current=e,i.current=e,a.current=!0,s({type:"STORE_UPDATED",payload:{error:n}}))}};return n.onStateChange=l,n.trySubscribe(),l(),function(){if(f=!0,n.tryUnsubscribe(),n.onStateChange=null,p)throw p}}}var C=function(){return[null,0]};function O(e,r){void 0===r&&(r={});var n=r,a=n.getDisplayName,i=void 0===a?function(e){return"ConnectAdvanced("+e+")"}:a,c=n.methodName,f=void 0===c?"connectAdvanced":c,d=n.renderCountProp,b=void 0===d?void 0:d,O=n.shouldHandleStateChanges,E=void 0===O||O,x=n.storeKey,M=void 0===x?"store":x,R=(n.withRef,n.forwardRef),N=void 0!==R&&R,T=n.context,q=void 0===T?u:T,k=l(n,["getDisplayName","methodName","renderCountProp","shouldHandleStateChanges","storeKey","withRef","forwardRef","context"]),D=q;return function(r){var n=r.displayName||r.name||"Component",u=i(n),a=p({},k,{getDisplayName:i,methodName:f,renderCountProp:b,shouldHandleStateChanges:E,storeKey:M,displayName:u,wrappedComponentName:n,WrappedComponent:r}),c=k.pure,d=c?t.useMemo:function(e){return e()};function O(n){var u=(0,t.useMemo)((function(){var e=n.reactReduxForwardedRef,r=l(n,["reactReduxForwardedRef"]);return[n.context,e,r]}),[n]),i=u[0],c=u[1],f=u[2],v=(0,t.useMemo)((function(){return i&&i.Consumer&&(0,h.isContextConsumer)(o().createElement(i.Consumer,null))?i:D}),[i,D]),b=(0,t.useContext)(v),O=Boolean(n.store)&&Boolean(n.store.getState)&&Boolean(n.store.dispatch);Boolean(b)&&Boolean(b.store);var x=O?n.store:b.store,M=(0,t.useMemo)((function(){return function(r){return e(r.dispatch,a)}(x)}),[x]),R=(0,t.useMemo)((function(){if(!E)return y;var e=new s(x,O?null:b.subscription),r=e.notifyNestedSubs.bind(e);return[e,r]}),[x,O,b]),N=R[0],T=R[1],q=(0,t.useMemo)((function(){return O?b:p({},b,{subscription:N})}),[O,b,N]),k=(0,t.useReducer)(S,m,C),_=k[0][0],j=k[1];if(_&&_.error)throw _.error;var B=(0,t.useRef)(),H=(0,t.useRef)(f),W=(0,t.useRef)(),F=(0,t.useRef)(!1),U=d((function(){return W.current&&f===H.current?W.current:M(x.getState(),f)}),[x,_,f]);P(g,[H,B,F,f,U,W,T]),P(w,[E,x,N,M,H,B,F,W,T,j],[x,N,M]);var A=(0,t.useMemo)((function(){return o().createElement(r,p({},U,{ref:c}))}),[c,r,U]);return(0,t.useMemo)((function(){return E?o().createElement(v.Provider,{value:q},A):A}),[v,A,q])}var x=c?o().memo(O):O;if(x.WrappedComponent=r,x.displayName=u,N){var R=o().forwardRef((function(e,r){return o().createElement(x,p({},e,{reactReduxForwardedRef:r}))}));return R.displayName=u,R.WrappedComponent=r,v()(R,r)}return v()(x,r)}}function E(e,r){return e===r?0!==e||0!==r||1/e==1/r:e!=e&&r!=r}function x(e,r){if(E(e,r))return!0;if("object"!=typeof e||null===e||"object"!=typeof r||null===r)return!1;var n=Object.keys(e),t=Object.keys(r);if(n.length!==t.length)return!1;for(var o=0;o<n.length;o++)if(!Object.prototype.hasOwnProperty.call(r,n[o])||!E(e[n[o]],r[n[o]]))return!1;return!0}var M=n(82883);function R(e){return function(r,n){var t=e(r,n);function o(){return t}return o.dependsOnOwnProps=!1,o}}function N(e){return null!==e.dependsOnOwnProps&&void 0!==e.dependsOnOwnProps?Boolean(e.dependsOnOwnProps):1!==e.length}function T(e,r){return function(r,n){n.displayName;var t=function(e,r){return t.dependsOnOwnProps?t.mapToProps(e,r):t.mapToProps(e)};return t.dependsOnOwnProps=!0,t.mapToProps=function(r,n){t.mapToProps=e,t.dependsOnOwnProps=N(e);var o=t(r,n);return"function"==typeof o&&(t.mapToProps=o,t.dependsOnOwnProps=N(o),o=t(r,n)),o},t}}const q=[function(e){return"function"==typeof e?T(e):void 0},function(e){return e?void 0:R((function(e){return{dispatch:e}}))},function(e){return e&&"object"==typeof e?R((function(r){return(0,M.bindActionCreators)(e,r)})):void 0}],k=[function(e){return"function"==typeof e?T(e):void 0},function(e){return e?void 0:R((function(){return{}}))}];function D(e,r,n){return p({},n,e,r)}const _=[function(e){return"function"==typeof e?function(e){return function(r,n){n.displayName;var t,o=n.pure,u=n.areMergedPropsEqual,a=!1;return function(r,n,i){var c=e(r,n,i);return a?o&&u(c,t)||(t=c):(a=!0,t=c),t}}}(e):void 0},function(e){return e?void 0:function(){return D}}];function j(e,r,n,t){return function(o,u){return n(e(o,u),r(t,u),u)}}function B(e,r,n,t,o){var u,a,i,c,s,f=o.areStatesEqual,p=o.areOwnPropsEqual,l=o.areStatePropsEqual,d=!1;return function(o,v){return d?function(o,d){var v,h,b=!p(d,a),m=!f(o,u);return u=o,a=d,b&&m?(i=e(u,a),r.dependsOnOwnProps&&(c=r(t,a)),s=n(i,c,a)):b?(e.dependsOnOwnProps&&(i=e(u,a)),r.dependsOnOwnProps&&(c=r(t,a)),s=n(i,c,a)):m?(v=e(u,a),h=!l(v,i),i=v,h&&(s=n(i,c,a)),s):s}(o,v):(i=e(u=o,a=v),c=r(t,a),s=n(i,c,a),d=!0,s)}}function H(e,r){var n=r.initMapStateToProps,t=r.initMapDispatchToProps,o=r.initMergeProps,u=l(r,["initMapStateToProps","initMapDispatchToProps","initMergeProps"]),a=n(e,u),i=t(e,u),c=o(e,u);return(u.pure?B:j)(a,i,c,e,u)}function W(e,r,n){for(var t=r.length-1;t>=0;t--){var o=r[t](e);if(o)return o}return function(r,t){throw new Error("Invalid value of type "+typeof e+" for "+n+" argument when connecting component "+t.wrappedComponentName+".")}}function F(e,r){return e===r}function U(e){var r=void 0===e?{}:e,n=r.connectHOC,t=void 0===n?O:n,o=r.mapStateToPropsFactories,u=void 0===o?k:o,a=r.mapDispatchToPropsFactories,i=void 0===a?q:a,c=r.mergePropsFactories,s=void 0===c?_:c,f=r.selectorFactory,d=void 0===f?H:f;return function(e,r,n,o){void 0===o&&(o={});var a=o,c=a.pure,f=void 0===c||c,v=a.areStatesEqual,h=void 0===v?F:v,b=a.areOwnPropsEqual,m=void 0===b?x:b,y=a.areStatePropsEqual,S=void 0===y?x:y,P=a.areMergedPropsEqual,g=void 0===P?x:P,w=l(a,["pure","areStatesEqual","areOwnPropsEqual","areStatePropsEqual","areMergedPropsEqual"]),C=W(e,u,"mapStateToProps"),O=W(r,i,"mapDispatchToProps"),E=W(n,s,"mergeProps");return t(d,p({methodName:"connect",getDisplayName:function(e){return"Connect("+e+")"},shouldHandleStateChanges:Boolean(e),initMapStateToProps:C,initMapDispatchToProps:O,initMergeProps:E,pure:f,areStatesEqual:h,areOwnPropsEqual:m,areStatePropsEqual:S,areMergedPropsEqual:g},w))}}const A=U();function $(){return(0,t.useContext)(u)}function K(e){void 0===e&&(e=u);var r=e===u?$:function(){return(0,t.useContext)(e)};return function(){return r().store}}var z=K();function I(e){void 0===e&&(e=u);var r=e===u?z:K(e);return function(){return r().dispatch}}var L=I(),V=function(e,r){return e===r};function G(e){void 0===e&&(e=u);var r=e===u?$:function(){return(0,t.useContext)(e)};return function(e,n){void 0===n&&(n=V);var o=r(),u=function(e,r,n,o){var u,a=(0,t.useReducer)((function(e){return e+1}),0)[1],i=(0,t.useMemo)((function(){return new s(n,o)}),[n,o]),c=(0,t.useRef)(),f=(0,t.useRef)(),p=(0,t.useRef)(),l=(0,t.useRef)(),d=n.getState();try{u=e!==f.current||d!==p.current||c.current?e(d):l.current}catch(e){throw c.current&&(e.message+="\nThe error may be correlated with this previous error:\n"+c.current.stack+"\n\n"),e}return b((function(){f.current=e,p.current=d,l.current=u,c.current=void 0})),b((function(){function e(){try{var e=f.current(n.getState());if(r(e,l.current))return;l.current=e}catch(e){c.current=e}a()}return i.onStateChange=e,i.trySubscribe(),e(),function(){return i.tryUnsubscribe()}}),[n,i]),u}(e,n,o.store,o.subscription);return(0,t.useDebugValue)(u),u}}var J,Q=G(),X=n(10529);J=X.unstable_batchedUpdates,a=J},98559:(e,r)=>{"use strict";var n="function"==typeof Symbol&&Symbol.for,t=n?Symbol.for("react.element"):60103,o=n?Symbol.for("react.portal"):60106,u=n?Symbol.for("react.fragment"):60107,a=n?Symbol.for("react.strict_mode"):60108,i=n?Symbol.for("react.profiler"):60114,c=n?Symbol.for("react.provider"):60109,s=n?Symbol.for("react.context"):60110,f=n?Symbol.for("react.async_mode"):60111,p=n?Symbol.for("react.concurrent_mode"):60111,l=n?Symbol.for("react.forward_ref"):60112,d=n?Symbol.for("react.suspense"):60113,v=(n&&Symbol.for("react.suspense_list"),n?Symbol.for("react.memo"):60115),h=n?Symbol.for("react.lazy"):60116;n&&Symbol.for("react.block"),n&&Symbol.for("react.fundamental"),n&&Symbol.for("react.responder"),n&&Symbol.for("react.scope");function b(e){if("object"==typeof e&&null!==e){var r=e.$$typeof;switch(r){case t:switch(e=e.type){case f:case p:case u:case i:case a:case d:return e;default:switch(e=e&&e.$$typeof){case s:case l:case h:case v:case c:return e;default:return r}}case o:return r}}}r.isContextConsumer=function(e){return b(e)===s}},63920:(e,r,n)=>{"use strict";e.exports=n(98559)}}]);