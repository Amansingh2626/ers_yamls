(self.webpackChunk_unified_trip=self.webpackChunk_unified_trip||[]).push([[4253],{15729:(t,e,r)=>{"use strict";function n(t){for(var e=arguments.length,r=Array(e>1?e-1:0),n=1;n<e;n++)r[n-1]=arguments[n];throw Error("[Immer] minified error nr: "+t+(r.length?" "+r.map((function(t){return"'"+t+"'"})).join(","):"")+". Find the full error at: https://bit.ly/3cXEKWf")}function o(t){return!!t&&!!t[X]}function i(t){return!!t&&(function(t){if(!t||"object"!=typeof t)return!1;var e=Object.getPrototypeOf(t);return!e||e===Object.prototype}(t)||Array.isArray(t)||!!t[T]||!!t.constructor[T]||s(t)||p(t))}function u(t,e,r){void 0===r&&(r=!1),0===f(t)?(r?Object.keys:Z)(t).forEach((function(n){r&&"symbol"==typeof n||e(n,t[n],t)})):t.forEach((function(r,n){return e(n,r,t)}))}function f(t){var e=t[X];return e?e.i>3?e.i-4:e.i:Array.isArray(t)?1:s(t)?2:p(t)?3:0}function c(t,e){return 2===f(t)?t.has(e):Object.prototype.hasOwnProperty.call(t,e)}function a(t,e,r){var n=f(t);2===n?t.set(e,r):3===n?(t.delete(e),t.add(r)):t[e]=r}function l(t,e){return t===e?0!==t||1/t==1/e:t!=t&&e!=e}function s(t){return $&&t instanceof Map}function p(t){return U&&t instanceof Set}function v(t){return t.o||t.t}function h(t){if(Array.isArray(t))return Array.prototype.slice.call(t);var e=q(t);delete e[X];for(var r=Z(e),n=0;n<r.length;n++){var o=r[n],i=e[o];!1===i.writable&&(i.writable=!0,i.configurable=!0),(i.get||i.set)&&(e[o]={configurable:!0,writable:!0,enumerable:i.enumerable,value:t[o]})}return Object.create(Object.getPrototypeOf(t),e)}function y(t,e){return void 0===e&&(e=!1),b(t)||o(t)||!i(t)||(f(t)>1&&(t.set=t.add=t.clear=t.delete=d),Object.freeze(t),e&&u(t,(function(t,e){return y(e,!0)}),!0)),t}function d(){n(2)}function b(t){return null==t||"object"!=typeof t||Object.isFrozen(t)}function g(t){var e=B[t];return e||n(18,t),e}function P(){return W}function O(t,e){e&&(g("Patches"),t.u=[],t.s=[],t.v=e)}function m(t){j(t),t.p.forEach(A),t.p=null}function j(t){t===W&&(W=t.l)}function w(t){return W={p:[],l:W,h:t,m:!0,_:0}}function A(t){var e=t[X];0===e.i||1===e.i?e.j():e.g=!0}function S(t,e){e._=e.p.length;var r=e.p[0],o=void 0!==t&&t!==r;return e.h.O||g("ES5").S(e,t,o),o?(r[X].P&&(m(e),n(4)),i(t)&&(t=k(e,t),e.l||_(e,t)),e.u&&g("Patches").M(r[X],t,e.u,e.s)):t=k(e,r,[]),m(e),e.u&&e.v(e.u,e.s),t!==J?t:void 0}function k(t,e,r){if(b(e))return e;var n=e[X];if(!n)return u(e,(function(o,i){return D(t,n,e,o,i,r)}),!0),e;if(n.A!==t)return e;if(!n.P)return _(t,n.t,!0),n.t;if(!n.I){n.I=!0,n.A._--;var o=4===n.i||5===n.i?n.o=h(n.k):n.o;u(3===n.i?new Set(o):o,(function(e,i){return D(t,n,o,e,i,r)})),_(t,o,!1),r&&t.u&&g("Patches").R(n,r,t.u,t.s)}return n.o}function D(t,e,r,n,u,f){if(o(u)){var l=k(t,u,f&&e&&3!==e.i&&!c(e.D,n)?f.concat(n):void 0);if(a(r,n,l),!o(l))return;t.m=!1}if(i(u)&&!b(u)){if(!t.h.N&&t._<1)return;k(t,u),e&&e.A.l||_(t,u)}}function _(t,e,r){void 0===r&&(r=!1),t.h.N&&t.m&&y(e,r)}function x(t,e){var r=t[X];return(r?v(r):t)[e]}function E(t,e){if(e in t)for(var r=Object.getPrototypeOf(t);r;){var n=Object.getOwnPropertyDescriptor(r,e);if(n)return n;r=Object.getPrototypeOf(r)}}function F(t){t.P||(t.P=!0,t.l&&F(t.l))}function I(t){t.o||(t.o=h(t.t))}function z(t,e,r){var n=s(e)?g("MapSet").T(e,r):p(e)?g("MapSet").F(e,r):t.O?function(t,e){var r=Array.isArray(t),n={i:r?1:0,A:e?e.A:P(),P:!1,I:!1,D:{},l:e,t,k:null,o:null,j:null,C:!1},o=n,i=G;r&&(o=[n],i=H);var u=Proxy.revocable(o,i),f=u.revoke,c=u.proxy;return n.k=c,n.j=f,c}(e,r):g("ES5").J(e,r);return(r?r.A:P()).p.push(n),n}function K(t){return o(t)||n(22,t),function t(e){if(!i(e))return e;var r,n=e[X],o=f(e);if(n){if(!n.P&&(n.i<4||!g("ES5").K(n)))return n.t;n.I=!0,r=M(e,o),n.I=!1}else r=M(e,o);return u(r,(function(e,o){n&&function(t,e){return 2===f(t)?t.get(e):t[e]}(n.t,e)===o||a(r,e,t(o))})),3===o?new Set(r):r}(t)}function M(t,e){switch(e){case 2:return new Map(t);case 3:return Array.from(t)}return h(t)}function N(){function t(t,e){var r=i[t];return r?r.enumerable=e:i[t]=r={configurable:!0,enumerable:e,get:function(){var e=this[X];return G.get(e,t)},set:function(e){var r=this[X];G.set(r,t,e)}},r}function e(t){for(var e=t.length-1;e>=0;e--){var o=t[e][X];if(!o.P)switch(o.i){case 5:n(o)&&F(o);break;case 4:r(o)&&F(o)}}}function r(t){for(var e=t.t,r=t.k,n=Z(r),o=n.length-1;o>=0;o--){var i=n[o];if(i!==X){var u=e[i];if(void 0===u&&!c(e,i))return!0;var f=r[i],a=f&&f[X];if(a?a.t!==u:!l(f,u))return!0}}var s=!!e[X];return n.length!==Z(e).length+(s?0:1)}function n(t){var e=t.k;if(e.length!==t.t.length)return!0;var r=Object.getOwnPropertyDescriptor(e,e.length-1);return!(!r||r.get)}var i={};!function(t,e){B[t]||(B[t]=e)}("ES5",{J:function(e,r){var n=Array.isArray(e),o=function(e,r){if(e){for(var n=Array(r.length),o=0;o<r.length;o++)Object.defineProperty(n,""+o,t(o,!0));return n}var i=q(r);delete i[X];for(var u=Z(i),f=0;f<u.length;f++){var c=u[f];i[c]=t(c,e||!!i[c].enumerable)}return Object.create(Object.getPrototypeOf(r),i)}(n,e),i={i:n?5:4,A:r?r.A:P(),P:!1,I:!1,D:{},l:r,t:e,k:o,o:null,g:!1,C:!1};return Object.defineProperty(o,X,{value:i,writable:!0}),o},S:function(t,r,i){i?o(r)&&r[X].A===t&&e(t.p):(t.u&&function t(e){if(e&&"object"==typeof e){var r=e[X];if(r){var o=r.t,i=r.k,f=r.D,a=r.i;if(4===a)u(i,(function(e){e!==X&&(void 0!==o[e]||c(o,e)?f[e]||t(i[e]):(f[e]=!0,F(r)))})),u(o,(function(t){void 0!==i[t]||c(i,t)||(f[t]=!1,F(r))}));else if(5===a){if(n(r)&&(F(r),f.length=!0),i.length<o.length)for(var l=i.length;l<o.length;l++)f[l]=!1;else for(var s=o.length;s<i.length;s++)f[s]=!0;for(var p=Math.min(i.length,o.length),v=0;v<p;v++)void 0===f[v]&&t(i[v])}}}}(t.p[0]),e(t.p))},K:function(t){return 4===t.i?r(t):n(t)}})}r.d(e,{ZP:()=>Y,Vk:()=>K,pV:()=>N,vV:()=>y,mv:()=>o,o$:()=>i});var R,W,C="undefined"!=typeof Symbol&&"symbol"==typeof Symbol("x"),$="undefined"!=typeof Map,U="undefined"!=typeof Set,V="undefined"!=typeof Proxy&&void 0!==Proxy.revocable&&"undefined"!=typeof Reflect,J=C?Symbol.for("immer-nothing"):((R={})["immer-nothing"]=!0,R),T=C?Symbol.for("immer-draftable"):"__$immer_draftable",X=C?Symbol.for("immer-state"):"__$immer_state",Z=("undefined"!=typeof Symbol&&Symbol.iterator,"undefined"!=typeof Reflect&&Reflect.ownKeys?Reflect.ownKeys:void 0!==Object.getOwnPropertySymbols?function(t){return Object.getOwnPropertyNames(t).concat(Object.getOwnPropertySymbols(t))}:Object.getOwnPropertyNames),q=Object.getOwnPropertyDescriptors||function(t){var e={};return Z(t).forEach((function(r){e[r]=Object.getOwnPropertyDescriptor(t,r)})),e},B={},G={get:function(t,e){if(e===X)return t;var r=v(t);if(!c(r,e))return function(t,e,r){var n,o=E(e,r);return o?"value"in o?o.value:null===(n=o.get)||void 0===n?void 0:n.call(t.k):void 0}(t,r,e);var n=r[e];return t.I||!i(n)?n:n===x(t.t,e)?(I(t),t.o[e]=z(t.A.h,n,t)):n},has:function(t,e){return e in v(t)},ownKeys:function(t){return Reflect.ownKeys(v(t))},set:function(t,e,r){var n=E(v(t),e);if(null==n?void 0:n.set)return n.set.call(t.k,r),!0;if(!t.P){var o=x(v(t),e),i=null==o?void 0:o[X];if(i&&i.t===r)return t.o[e]=r,t.D[e]=!1,!0;if(l(r,o)&&(void 0!==r||c(t.t,e)))return!0;I(t),F(t)}return t.o[e]=r,t.D[e]=!0,!0},deleteProperty:function(t,e){return void 0!==x(t.t,e)||e in t.t?(t.D[e]=!1,I(t),F(t)):delete t.D[e],t.o&&delete t.o[e],!0},getOwnPropertyDescriptor:function(t,e){var r=v(t),n=Reflect.getOwnPropertyDescriptor(r,e);return n?{writable:!0,configurable:1!==t.i||"length"!==e,enumerable:n.enumerable,value:r[e]}:n},defineProperty:function(){n(11)},getPrototypeOf:function(t){return Object.getPrototypeOf(t.t)},setPrototypeOf:function(){n(12)}},H={};u(G,(function(t,e){H[t]=function(){return arguments[0]=arguments[0][0],e.apply(this,arguments)}})),H.deleteProperty=function(t,e){return G.deleteProperty.call(this,t[0],e)},H.set=function(t,e,r){return G.set.call(this,t[0],e,r,t[0])};var L=new(function(){function t(t){this.O=V,this.N=!0,"boolean"==typeof(null==t?void 0:t.useProxies)&&this.setUseProxies(t.useProxies),"boolean"==typeof(null==t?void 0:t.autoFreeze)&&this.setAutoFreeze(t.autoFreeze),this.produce=this.produce.bind(this),this.produceWithPatches=this.produceWithPatches.bind(this)}var e=t.prototype;return e.produce=function(t,e,r){if("function"==typeof t&&"function"!=typeof e){var o=e;e=t;var u=this;return function(t){var r=this;void 0===t&&(t=o);for(var n=arguments.length,i=Array(n>1?n-1:0),f=1;f<n;f++)i[f-1]=arguments[f];return u.produce(t,(function(t){var n;return(n=e).call.apply(n,[r,t].concat(i))}))}}var f;if("function"!=typeof e&&n(6),void 0!==r&&"function"!=typeof r&&n(7),i(t)){var c=w(this),a=z(this,t,void 0),l=!0;try{f=e(a),l=!1}finally{l?m(c):j(c)}return"undefined"!=typeof Promise&&f instanceof Promise?f.then((function(t){return O(c,r),S(t,c)}),(function(t){throw m(c),t})):(O(c,r),S(f,c))}if(!t||"object"!=typeof t){if((f=e(t))===J)return;return void 0===f&&(f=t),this.N&&y(f,!0),f}n(21,t)},e.produceWithPatches=function(t,e){var r,n,o=this;return"function"==typeof t?function(e){for(var r=arguments.length,n=Array(r>1?r-1:0),i=1;i<r;i++)n[i-1]=arguments[i];return o.produceWithPatches(e,(function(e){return t.apply(void 0,[e].concat(n))}))}:[this.produce(t,e,(function(t,e){r=t,n=e})),r,n]},e.createDraft=function(t){i(t)||n(8),o(t)&&(t=K(t));var e=w(this),r=z(this,t,void 0);return r[X].C=!0,j(e),r},e.finishDraft=function(t,e){var r=(t&&t[X]).A;return O(r,e),S(void 0,r)},e.setAutoFreeze=function(t){this.N=t},e.setUseProxies=function(t){t&&!V&&n(20),this.O=t},e.applyPatches=function(t,e){var r;for(r=e.length-1;r>=0;r--){var n=e[r];if(0===n.path.length&&"replace"===n.op){t=n.value;break}}var i=g("Patches").$;return o(t)?i(t,e):this.produce(t,(function(t){return i(t,e.slice(r+1))}))},t}()),Q=L.produce;L.produceWithPatches.bind(L),L.setAutoFreeze.bind(L),L.setUseProxies.bind(L),L.applyPatches.bind(L),L.createDraft.bind(L),L.finishDraft.bind(L);const Y=Q}}]);