(self.webpackChunk_unified_campaign=self.webpackChunk_unified_campaign||[]).push([[9884],{51690:(e,n,r)=>{"use strict";function t(){return(t=Object.assign||function(e){for(var n=1;n<arguments.length;n++){var r=arguments[n];for(var t in r)Object.prototype.hasOwnProperty.call(r,t)&&(e[t]=r[t])}return e}).apply(this,arguments)}r.d(n,{Z:()=>l});var i=/\s*,\s*/g,a=/&/g,o=/\$([\w-]+)/g;const l=function(){function e(e,n){return function(r,t){var i=e.getRule(t)||n&&n.getRule(t);return i?(i=i).selector:t}}function n(e,n){for(var r=n.split(i),t=e.split(i),o="",l=0;l<r.length;l++)for(var u=r[l],s=0;s<t.length;s++){var c=t[s];o&&(o+=", "),o+=-1!==c.indexOf("&")?c.replace(a,u):u+" "+c}return o}function r(e,n,r){if(r)return t({},r,{index:r.index+1});var i=e.options.nestingLevel;i=void 0===i?1:i+1;var a=t({},e.options,{nestingLevel:i,index:n.indexOf(e)+1});return delete a.name,a}return{onProcessStyle:function(i,a,l){if("style"!==a.type)return i;var u,s,c=a,f=c.options.parent;for(var p in i){var d=-1!==p.indexOf("&"),v="@"===p[0];if(d||v){if(u=r(c,f,u),d){var g=n(p,c.selector);s||(s=e(f,l)),g=g.replace(o,s),f.addRule(g,i[p],t({},u,{selector:g}))}else v&&f.addRule(p,{},u).addRule(c.key,i[p],{selector:c.selector});delete i[p]}}return i}}}}}]);