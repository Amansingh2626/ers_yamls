(self.webpackChunk_unified_voucher=self.webpackChunk_unified_voucher||[]).push([[9884],{51690:(e,n,r)=>{"use strict";function t(){return(t=Object.assign||function(e){for(var n=1;n<arguments.length;n++){var r=arguments[n];for(var t in r)Object.prototype.hasOwnProperty.call(r,t)&&(e[t]=r[t])}return e}).apply(this,arguments)}r.d(n,{Z:()=>l});var i=/\s*,\s*/g,o=/&/g,u=/\$([\w-]+)/g;const l=function(){function e(e,n){return function(r,t){var i=e.getRule(t)||n&&n.getRule(t);return i?(i=i).selector:t}}function n(e,n){for(var r=n.split(i),t=e.split(i),u="",l=0;l<r.length;l++)for(var s=r[l],a=0;a<t.length;a++){var c=t[a];u&&(u+=", "),u+=-1!==c.indexOf("&")?c.replace(o,s):s+" "+c}return u}function r(e,n,r){if(r)return t({},r,{index:r.index+1});var i=e.options.nestingLevel;i=void 0===i?1:i+1;var o=t({},e.options,{nestingLevel:i,index:n.indexOf(e)+1});return delete o.name,o}return{onProcessStyle:function(i,o,l){if("style"!==o.type)return i;var s,a,c=o,f=c.options.parent;for(var v in i){var d=-1!==v.indexOf("&"),p="@"===v[0];if(d||p){if(s=r(c,f,s),d){var g=n(v,c.selector);a||(a=e(f,l)),g=g.replace(u,a),f.addRule(g,i[v],t({},s,{selector:g}))}else p&&f.addRule(v,{},s).addRule(c.key,i[v],{selector:c.selector});delete i[v]}}return i}}}}}]);