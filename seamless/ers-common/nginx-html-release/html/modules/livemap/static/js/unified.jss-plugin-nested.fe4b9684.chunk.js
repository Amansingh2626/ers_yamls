(self.webpackChunk_unified_livemap=self.webpackChunk_unified_livemap||[]).push([[9884],{1690:(e,n,r)=>{"use strict";function t(){return(t=Object.assign||function(e){for(var n=1;n<arguments.length;n++){var r=arguments[n];for(var t in r)Object.prototype.hasOwnProperty.call(r,t)&&(e[t]=r[t])}return e}).apply(this,arguments)}r.d(n,{Z:()=>a});var i=/\s*,\s*/g,l=/&/g,o=/\$([\w-]+)/g;const a=function(){function e(e,n){return function(r,t){var i=e.getRule(t)||n&&n.getRule(t);return i?(i=i).selector:t}}function n(e,n){for(var r=n.split(i),t=e.split(i),o="",a=0;a<r.length;a++)for(var u=r[a],s=0;s<t.length;s++){var f=t[s];o&&(o+=", "),o+=-1!==f.indexOf("&")?f.replace(l,u):u+" "+f}return o}function r(e,n,r){if(r)return t({},r,{index:r.index+1});var i=e.options.nestingLevel;i=void 0===i?1:i+1;var l=t({},e.options,{nestingLevel:i,index:n.indexOf(e)+1});return delete l.name,l}return{onProcessStyle:function(i,l,a){if("style"!==l.type)return i;var u,s,f=l,c=f.options.parent;for(var p in i){var v=-1!==p.indexOf("&"),d="@"===p[0];if(v||d){if(u=r(f,c,u),v){var g=n(p,f.selector);s||(s=e(c,a)),g=g.replace(o,s),c.addRule(g,i[p],t({},u,{selector:g}))}else d&&c.addRule(p,{},u).addRule(f.key,i[p],{selector:f.selector});delete i[p]}}return i}}}}}]);