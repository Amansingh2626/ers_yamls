(self.webpackChunk_unified_thresholds=self.webpackChunk_unified_thresholds||[]).push([[9827],{60985:(n,r,t)=>{"use strict";function e(n,r){return n===r}function u(n,r,t){if(null===r||null===t||r.length!==t.length)return!1;for(var e=r.length,u=0;u<e;u++)if(!n(r[u],t[u]))return!1;return!0}function o(n){var r=Array.isArray(n[0])?n[0]:n;if(!r.every((function(n){return"function"==typeof n}))){var t=r.map((function(n){return typeof n})).join(", ");throw new Error("Selector creators expect all input-selectors to be functions, instead received the following types: ["+t+"]")}return r}t.d(r,{P1:()=>l});var l=function(n){for(var r=arguments.length,t=Array(r>1?r-1:0),e=1;e<r;e++)t[e-1]=arguments[e];return function(){for(var r=arguments.length,e=Array(r),u=0;u<r;u++)e[u]=arguments[u];var l=0,i=e.pop(),c=o(e),f=n.apply(void 0,[function(){return l++,i.apply(null,arguments)}].concat(t)),a=n((function(){for(var n=[],r=c.length,t=0;t<r;t++)n.push(c[t].apply(null,arguments));return f.apply(null,n)}));return a.resultFunc=i,a.dependencies=c,a.recomputations=function(){return l},a.resetRecomputations=function(){return l=0},a}}((function(n){var r=arguments.length>1&&void 0!==arguments[1]?arguments[1]:e,t=null,o=null;return function(){return u(r,t,arguments)||(o=n.apply(null,arguments)),t=arguments,o}}))}}]);