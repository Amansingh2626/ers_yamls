(self.webpackChunk_unified_survey=self.webpackChunk_unified_survey||[]).push([[9096],{8760:(t,e,r)=>{"use strict";function n(){return(n=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var r=arguments[e];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(t[n]=r[n])}return t}).apply(this,arguments)}r.d(e,{Z:()=>c});var s=r(7359),i="@global",o="@global ",u=function(){function t(t,e,r){for(var o in this.type="global",this.at=i,this.rules=void 0,this.options=void 0,this.key=void 0,this.isProcessed=!1,this.key=t,this.options=r,this.rules=new s.RB(n({},r,{parent:this})),e)this.rules.add(o,e[o]);this.rules.process()}var e=t.prototype;return e.getRule=function(t){return this.rules.get(t)},e.addRule=function(t,e,r){var n=this.rules.add(t,e,r);return n&&this.options.jss.plugins.onProcessRule(n),n},e.indexOf=function(t){return this.rules.indexOf(t)},e.toString=function(){return this.rules.toString()},t}(),l=function(){function t(t,e,r){this.type="global",this.at=i,this.options=void 0,this.rule=void 0,this.isProcessed=!1,this.key=void 0,this.key=t,this.options=r;var s=t.substr(o.length);this.rule=r.jss.createRule(s,e,n({},r,{parent:this}))}return t.prototype.toString=function(t){return this.rule?this.rule.toString(t):""},t}(),a=/\s*,\s*/g;function h(t,e){for(var r=t.split(a),n="",s=0;s<r.length;s++)n+=e+" "+r[s].trim(),r[s+1]&&(n+=", ");return n}const c=function(){return{onCreateRule:function(t,e,r){if(!t)return null;if(t===i)return new u(t,e,r);if("@"===t[0]&&t.substr(0,o.length)===o)return new l(t,e,r);var n=r.parent;return n&&("global"===n.type||n.options.parent&&"global"===n.options.parent.type)&&(r.scoped=!1),!1===r.scoped&&(r.selector=t),null},onProcessRule:function(t,e){"style"===t.type&&e&&(function(t,e){var r=t.options,s=t.style,o=s?s[i]:null;if(o){for(var u in o)e.addRule(u,o[u],n({},r,{selector:h(u,t.selector)}));delete s[i]}}(t,e),function(t,e){var r=t.options,s=t.style;for(var o in s)if("@"===o[0]&&o.substr(0,i.length)===i){var u=h(o.substr(i.length),t.selector);e.addRule(u,s[o],n({},r,{selector:u})),delete s[o]}}(t,e))}}}}}]);