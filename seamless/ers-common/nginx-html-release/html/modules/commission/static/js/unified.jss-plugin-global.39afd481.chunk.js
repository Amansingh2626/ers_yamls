(self.webpackChunk_unified_commission=self.webpackChunk_unified_commission||[]).push([[9096],{68760:(t,e,n)=>{"use strict";function s(){return(s=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var s in n)Object.prototype.hasOwnProperty.call(n,s)&&(t[s]=n[s])}return t}).apply(this,arguments)}n.d(e,{Z:()=>c});var r=n(87359),i="@global",o="@global ",u=function(){function t(t,e,n){for(var o in this.type="global",this.at=i,this.rules=void 0,this.options=void 0,this.key=void 0,this.isProcessed=!1,this.key=t,this.options=n,this.rules=new r.RB(s({},n,{parent:this})),e)this.rules.add(o,e[o]);this.rules.process()}var e=t.prototype;return e.getRule=function(t){return this.rules.get(t)},e.addRule=function(t,e,n){var s=this.rules.add(t,e,n);return s&&this.options.jss.plugins.onProcessRule(s),s},e.indexOf=function(t){return this.rules.indexOf(t)},e.toString=function(){return this.rules.toString()},t}(),l=function(){function t(t,e,n){this.type="global",this.at=i,this.options=void 0,this.rule=void 0,this.isProcessed=!1,this.key=void 0,this.key=t,this.options=n;var r=t.substr(o.length);this.rule=n.jss.createRule(r,e,s({},n,{parent:this}))}return t.prototype.toString=function(t){return this.rule?this.rule.toString(t):""},t}(),a=/\s*,\s*/g;function h(t,e){for(var n=t.split(a),s="",r=0;r<n.length;r++)s+=e+" "+n[r].trim(),n[r+1]&&(s+=", ");return s}const c=function(){return{onCreateRule:function(t,e,n){if(!t)return null;if(t===i)return new u(t,e,n);if("@"===t[0]&&t.substr(0,o.length)===o)return new l(t,e,n);var s=n.parent;return s&&("global"===s.type||s.options.parent&&"global"===s.options.parent.type)&&(n.scoped=!1),!1===n.scoped&&(n.selector=t),null},onProcessRule:function(t,e){"style"===t.type&&e&&(function(t,e){var n=t.options,r=t.style,o=r?r[i]:null;if(o){for(var u in o)e.addRule(u,o[u],s({},n,{selector:h(u,t.selector)}));delete r[i]}}(t,e),function(t,e){var n=t.options,r=t.style;for(var o in r)if("@"===o[0]&&o.substr(0,i.length)===i){var u=h(o.substr(i.length),t.selector);e.addRule(u,r[o],s({},n,{selector:u})),delete r[o]}}(t,e))}}}}}]);