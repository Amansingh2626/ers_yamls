(self.webpackChunk_unified_dashboard=self.webpackChunk_unified_dashboard||[]).push([[9884],{1690:(e,n,r)=>{"use strict";function t(){return(t=Object.assign||function(e){for(var n=1;n<arguments.length;n++){var r=arguments[n];for(var t in r)Object.prototype.hasOwnProperty.call(r,t)&&(e[t]=r[t])}return e}).apply(this,arguments)}r.d(n,{Z:()=>s});var i=/\s*,\s*/g,o=/&/g,a=/\$([\w-]+)/g;const s=function(){function e(e,n){return function(r,t){var i=e.getRule(t)||n&&n.getRule(t);return i?(i=i).selector:t}}function n(e,n){for(var r=n.split(i),t=e.split(i),a="",s=0;s<r.length;s++)for(var l=r[s],u=0;u<t.length;u++){var f=t[u];a&&(a+=", "),a+=-1!==f.indexOf("&")?f.replace(o,l):l+" "+f}return a}function r(e,n,r){if(r)return t({},r,{index:r.index+1});var i=e.options.nestingLevel;i=void 0===i?1:i+1;var o=t({},e.options,{nestingLevel:i,index:n.indexOf(e)+1});return delete o.name,o}return{onProcessStyle:function(i,o,s){if("style"!==o.type)return i;var l,u,f=o,c=f.options.parent;for(var d in i){var p=-1!==d.indexOf("&"),v="@"===d[0];if(p||v){if(l=r(f,c,l),p){var g=n(d,f.selector);u||(u=e(c,s)),g=g.replace(a,u),c.addRule(g,i[d],t({},l,{selector:g}))}else v&&c.addRule(d,{},l).addRule(f.key,i[d],{selector:f.selector});delete i[d]}}return i}}}}}]);