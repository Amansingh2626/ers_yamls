(self.webpackChunk_unified_admin=self.webpackChunk_unified_admin||[]).push([[884],{1690:(e,n,r)=>{"use strict";function t(){return(t=Object.assign||function(e){for(var n=1;n<arguments.length;n++){var r=arguments[n];for(var t in r)Object.prototype.hasOwnProperty.call(r,t)&&(e[t]=r[t])}return e}).apply(this,arguments)}r.d(n,{Z:()=>l});var i=/\s*,\s*/g,o=/&/g,a=/\$([\w-]+)/g;const l=function(){function e(e,n){return function(r,t){var i=e.getRule(t)||n&&n.getRule(t);return i?(i=i).selector:t}}function n(e,n){for(var r=n.split(i),t=e.split(i),a="",l=0;l<r.length;l++)for(var u=r[l],s=0;s<t.length;s++){var f=t[s];a&&(a+=", "),a+=-1!==f.indexOf("&")?f.replace(o,u):u+" "+f}return a}function r(e,n,r){if(r)return t({},r,{index:r.index+1});var i=e.options.nestingLevel;i=void 0===i?1:i+1;var o=t({},e.options,{nestingLevel:i,index:n.indexOf(e)+1});return delete o.name,o}return{onProcessStyle:function(i,o,l){if("style"!==o.type)return i;var u,s,f=o,c=f.options.parent;for(var d in i){var p=-1!==d.indexOf("&"),v="@"===d[0];if(p||v){if(u=r(f,c,u),p){var g=n(d,f.selector);s||(s=e(c,l)),g=g.replace(a,s),c.addRule(g,i[d],t({},u,{selector:g}))}else v&&c.addRule(d,{},u).addRule(f.key,i[d],{selector:f.selector});delete i[d]}}return i}}}}}]);