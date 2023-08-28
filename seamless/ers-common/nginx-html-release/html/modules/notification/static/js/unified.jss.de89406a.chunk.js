/*! For license information please see unified.jss.de89406a.chunk.js.LICENSE.txt */
(self.webpackChunk_unified_notification=self.webpackChunk_unified_notification||[]).push([[6522],{87359:(e,t,n)=>{"use strict";function r(){return(r=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}n.r(t),n.d(t,{RuleList:()=>H,SheetsManager:()=>ge,SheetsRegistry:()=>X,create:()=>Re,createGenerateId:()=>re,createRule:()=>f,default:()=>Se,getDynamicStyles:()=>ve,hasCSSTOMSupport:()=>me,sheets:()=>Y,toCssValue:()=>y});var i=n(20653),s=n(55518);function o(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function a(e,t,n){return t&&o(e.prototype,t),n&&o(e,n),e}function h(e,t){return(h=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function u(e,t){e.prototype=Object.create(t.prototype),e.prototype.constructor=e,h(e,t)}function l(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}var d={}.constructor;function c(e){if(null==e||"object"!=typeof e)return e;if(Array.isArray(e))return e.map(c);if(e.constructor!==d)return e;var t={};for(var n in e)t[n]=c(e[n]);return t}function f(e,t,n){void 0===e&&(e="unnamed");var r=n.jss,i=c(t);return r.plugins.onCreateRule(e,i,n)||(e[0],null)}var p=function(e,t){for(var n="",r=0;r<e.length&&"!important"!==e[r];r++)n&&(n+=t),n+=e[r];return n},y=function(e,t){if(void 0===t&&(t=!1),!Array.isArray(e))return e;var n="";if(Array.isArray(e[0]))for(var r=0;r<e.length&&"!important"!==e[r];r++)n&&(n+=", "),n+=p(e[r]," ");else n=p(e,", ");return t||"!important"!==e[e.length-1]||(n+=" !important"),n};function v(e,t){for(var n="",r=0;r<t;r++)n+="  ";return n+e}function g(e,t,n){void 0===n&&(n={});var r="";if(!t)return r;var i=n.indent,s=void 0===i?0:i,o=t.fallbacks;if(e&&s++,o)if(Array.isArray(o))for(var a=0;a<o.length;a++){var h=o[a];for(var u in h){var l=h[u];null!=l&&(r&&(r+="\n"),r+=""+v(u+": "+y(l)+";",s))}}else for(var d in o){var c=o[d];null!=c&&(r&&(r+="\n"),r+=""+v(d+": "+y(c)+";",s))}for(var f in t){var p=t[f];null!=p&&"fallbacks"!==f&&(r&&(r+="\n"),r+=""+v(f+": "+y(p)+";",s))}return(r||n.allowEmpty)&&e?(r&&(r="\n"+r+"\n"),v(e+" {"+r,--s)+v("}",s)):r}var m=/([[\].#*$><+~=|^:(),"'`\s])/g,R="undefined"!=typeof CSS&&CSS.escape,S=function(e){return R?R(e):e.replace(m,"\\$1")},k=function(){function e(e,t,n){this.type="style",this.key=void 0,this.isProcessed=!1,this.style=void 0,this.renderer=void 0,this.renderable=void 0,this.options=void 0;var r=n.sheet,i=n.Renderer;this.key=e,this.options=n,this.style=t,r?this.renderer=r.renderer:i&&(this.renderer=new i)}return e.prototype.prop=function(e,t,n){if(void 0===t)return this.style[e];var r=!!n&&n.force;if(!r&&this.style[e]===t)return this;var i=t;n&&!1===n.process||(i=this.options.jss.plugins.onChangeValue(t,e,this));var s=null==i||!1===i,o=e in this.style;if(s&&!o&&!r)return this;var a=s&&o;if(a?delete this.style[e]:this.style[e]=i,this.renderable&&this.renderer)return a?this.renderer.removeProperty(this.renderable,e):this.renderer.setProperty(this.renderable,e,i),this;var h=this.options.sheet;return h&&h.attached,this},e}(),x=function(e){function t(t,n,r){var i;(i=e.call(this,t,n,r)||this).selectorText=void 0,i.id=void 0,i.renderable=void 0;var s=r.selector,o=r.scoped,a=r.sheet,h=r.generateId;return s?i.selectorText=s:!1!==o&&(i.id=h(l(l(i)),a),i.selectorText="."+S(i.id)),i}u(t,e);var n=t.prototype;return n.applyTo=function(e){var t=this.renderer;if(t){var n=this.toJSON();for(var r in n)t.setProperty(e,r,n[r])}return this},n.toJSON=function(){var e={};for(var t in this.style){var n=this.style[t];"object"!=typeof n?e[t]=n:Array.isArray(n)&&(e[t]=y(n))}return e},n.toString=function(e){var t=this.options.sheet,n=t&&t.options.link?r({},e,{allowEmpty:!0}):e;return g(this.selectorText,this.style,n)},a(t,[{key:"selector",set:function(e){if(e!==this.selectorText){this.selectorText=e;var t=this.renderer,n=this.renderable;n&&t&&(t.setSelector(n,e)||t.replaceRule(n,this))}},get:function(){return this.selectorText}}]),t}(k),b={onCreateRule:function(e,t,n){return"@"===e[0]||n.parent&&"keyframes"===n.parent.type?null:new x(e,t,n)}},P={indent:1,children:!0},w=/@([\w-]+)/,C=function(){function e(e,t,n){this.type="conditional",this.at=void 0,this.key=void 0,this.query=void 0,this.rules=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0,this.key=e;var i=e.match(w);for(var s in this.at=i?i[1]:"unknown",this.query=n.name||"@"+this.at,this.options=n,this.rules=new H(r({},n,{parent:this})),t)this.rules.add(s,t[s]);this.rules.process()}var t=e.prototype;return t.getRule=function(e){return this.rules.get(e)},t.indexOf=function(e){return this.rules.indexOf(e)},t.addRule=function(e,t,n){var r=this.rules.add(e,t,n);return r?(this.options.jss.plugins.onProcessRule(r),r):null},t.toString=function(e){if(void 0===e&&(e=P),null==e.indent&&(e.indent=P.indent),null==e.children&&(e.children=P.children),!1===e.children)return this.query+" {}";var t=this.rules.toString(e);return t?this.query+" {\n"+t+"\n}":""},e}(),O=/@media|@supports\s+/,j={onCreateRule:function(e,t,n){return O.test(e)?new C(e,t,n):null}},A={indent:1,children:!0},I=/@keyframes\s+([\w-]+)/,q=function(){function e(e,t,n){this.type="keyframes",this.at="@keyframes",this.key=void 0,this.name=void 0,this.id=void 0,this.rules=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0;var i=e.match(I);i&&i[1]?this.name=i[1]:this.name="noname",this.key=this.type+"-"+this.name,this.options=n;var s=n.scoped,o=n.sheet,a=n.generateId;for(var h in this.id=!1===s?this.name:S(a(this,o)),this.rules=new H(r({},n,{parent:this})),t)this.rules.add(h,t[h],r({},n,{parent:this}));this.rules.process()}return e.prototype.toString=function(e){if(void 0===e&&(e=A),null==e.indent&&(e.indent=A.indent),null==e.children&&(e.children=A.children),!1===e.children)return this.at+" "+this.id+" {}";var t=this.rules.toString(e);return t&&(t="\n"+t+"\n"),this.at+" "+this.id+" {"+t+"}"},e}(),M=/@keyframes\s+/,T=/\$([\w-]+)/g,N=function(e,t){return"string"==typeof e?e.replace(T,(function(e,n){return n in t?t[n]:e})):e},V=function(e,t,n){var r=e[t],i=N(r,n);i!==r&&(e[t]=i)},E={onCreateRule:function(e,t,n){return"string"==typeof e&&M.test(e)?new q(e,t,n):null},onProcessStyle:function(e,t,n){return"style"===t.type&&n?("animation-name"in e&&V(e,"animation-name",n.keyframes),"animation"in e&&V(e,"animation",n.keyframes),e):e},onChangeValue:function(e,t,n){var r=n.options.sheet;if(!r)return e;switch(t){case"animation":case"animation-name":return N(e,r.keyframes);default:return e}}},_=function(e){function t(){for(var t,n=arguments.length,r=new Array(n),i=0;i<n;i++)r[i]=arguments[i];return(t=e.call.apply(e,[this].concat(r))||this).renderable=void 0,t}return u(t,e),t.prototype.toString=function(e){var t=this.options.sheet,n=t&&t.options.link?r({},e,{allowEmpty:!0}):e;return g(this.key,this.style,n)},t}(k),G={onCreateRule:function(e,t,n){return n.parent&&"keyframes"===n.parent.type?new _(e,t,n):null}},U=function(){function e(e,t,n){this.type="font-face",this.at="@font-face",this.key=void 0,this.style=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0,this.key=e,this.style=t,this.options=n}return e.prototype.toString=function(e){if(Array.isArray(this.style)){for(var t="",n=0;n<this.style.length;n++)t+=g(this.at,this.style[n]),this.style[n+1]&&(t+="\n");return t}return g(this.at,this.style,e)},e}(),B=/@font-face/,J={onCreateRule:function(e,t,n){return B.test(e)?new U(e,t,n):null}},Z=function(){function e(e,t,n){this.type="viewport",this.at="@viewport",this.key=void 0,this.style=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0,this.key=e,this.style=t,this.options=n}return e.prototype.toString=function(e){return g(this.key,this.style,e)},e}(),$={onCreateRule:function(e,t,n){return"@viewport"===e||"@-ms-viewport"===e?new Z(e,t,n):null}},z=function(){function e(e,t,n){this.type="simple",this.key=void 0,this.value=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0,this.key=e,this.value=t,this.options=n}return e.prototype.toString=function(e){if(Array.isArray(this.value)){for(var t="",n=0;n<this.value.length;n++)t+=this.key+" "+this.value[n]+";",this.value[n+1]&&(t+="\n");return t}return this.key+" "+this.value+";"},e}(),D={"@charset":!0,"@import":!0,"@namespace":!0},F=[b,j,E,G,J,$,{onCreateRule:function(e,t,n){return e in D?new z(e,t,n):null}}],L={process:!0},W={force:!0,process:!0},H=function(){function e(e){this.map={},this.raw={},this.index=[],this.counter=0,this.options=void 0,this.classes=void 0,this.keyframes=void 0,this.options=e,this.classes=e.classes,this.keyframes=e.keyframes}var t=e.prototype;return t.add=function(e,t,n){var i=this.options,s=i.parent,o=i.sheet,a=i.jss,h=i.Renderer,u=i.generateId,l=i.scoped,d=r({classes:this.classes,parent:s,sheet:o,jss:a,Renderer:h,generateId:u,scoped:l,name:e,keyframes:this.keyframes,selector:void 0},n),c=e;e in this.raw&&(c=e+"-d"+this.counter++),this.raw[c]=t,c in this.classes&&(d.selector="."+S(this.classes[c]));var p=f(c,t,d);if(!p)return null;this.register(p);var y=void 0===d.index?this.index.length:d.index;return this.index.splice(y,0,p),p},t.get=function(e){return this.map[e]},t.remove=function(e){this.unregister(e),delete this.raw[e.key],this.index.splice(this.index.indexOf(e),1)},t.indexOf=function(e){return this.index.indexOf(e)},t.process=function(){var e=this.options.jss.plugins;this.index.slice(0).forEach(e.onProcessRule,e)},t.register=function(e){this.map[e.key]=e,e instanceof x?(this.map[e.selector]=e,e.id&&(this.classes[e.key]=e.id)):e instanceof q&&this.keyframes&&(this.keyframes[e.name]=e.id)},t.unregister=function(e){delete this.map[e.key],e instanceof x?(delete this.map[e.selector],delete this.classes[e.key]):e instanceof q&&delete this.keyframes[e.name]},t.update=function(){var e,t,n;if("string"==typeof(arguments.length<=0?void 0:arguments[0])?(e=arguments.length<=0?void 0:arguments[0],t=arguments.length<=1?void 0:arguments[1],n=arguments.length<=2?void 0:arguments[2]):(t=arguments.length<=0?void 0:arguments[0],n=arguments.length<=1?void 0:arguments[1],e=null),e)this.updateOne(this.map[e],t,n);else for(var r=0;r<this.index.length;r++)this.updateOne(this.index[r],t,n)},t.updateOne=function(t,n,r){void 0===r&&(r=L);var i=this.options,s=i.jss.plugins,o=i.sheet;if(t.rules instanceof e)t.rules.update(n,r);else{var a=t,h=a.style;if(s.onUpdate(n,t,o,r),r.process&&h&&h!==a.style){for(var u in s.onProcessStyle(a.style,a,o),a.style){var l=a.style[u];l!==h[u]&&a.prop(u,l,W)}for(var d in h){var c=a.style[d],f=h[d];null==c&&c!==f&&a.prop(d,null,W)}}}},t.toString=function(e){for(var t="",n=this.options.sheet,r=!!n&&n.options.link,i=0;i<this.index.length;i++){var s=this.index[i].toString(e);(s||r)&&(t&&(t+="\n"),t+=s)}return t},e}(),K=function(){function e(e,t){for(var n in this.options=void 0,this.deployed=void 0,this.attached=void 0,this.rules=void 0,this.renderer=void 0,this.classes=void 0,this.keyframes=void 0,this.queue=void 0,this.attached=!1,this.deployed=!1,this.classes={},this.keyframes={},this.options=r({},t,{sheet:this,parent:this,classes:this.classes,keyframes:this.keyframes}),t.Renderer&&(this.renderer=new t.Renderer(this)),this.rules=new H(this.options),e)this.rules.add(n,e[n]);this.rules.process()}var t=e.prototype;return t.attach=function(){return this.attached||(this.renderer&&this.renderer.attach(),this.attached=!0,this.deployed||this.deploy()),this},t.detach=function(){return this.attached?(this.renderer&&this.renderer.detach(),this.attached=!1,this):this},t.addRule=function(e,t,n){var r=this.queue;this.attached&&!r&&(this.queue=[]);var i=this.rules.add(e,t,n);return i?(this.options.jss.plugins.onProcessRule(i),this.attached?this.deployed?(r?r.push(i):(this.insertRule(i),this.queue&&(this.queue.forEach(this.insertRule,this),this.queue=void 0)),i):i:(this.deployed=!1,i)):null},t.insertRule=function(e){this.renderer&&this.renderer.insertRule(e)},t.addRules=function(e,t){var n=[];for(var r in e){var i=this.addRule(r,e[r],t);i&&n.push(i)}return n},t.getRule=function(e){return this.rules.get(e)},t.deleteRule=function(e){var t="object"==typeof e?e:this.rules.get(e);return!(!t||this.attached&&!t.renderable)&&(this.rules.remove(t),!(this.attached&&t.renderable&&this.renderer)||this.renderer.deleteRule(t.renderable))},t.indexOf=function(e){return this.rules.indexOf(e)},t.deploy=function(){return this.renderer&&this.renderer.deploy(),this.deployed=!0,this},t.update=function(){var e;return(e=this.rules).update.apply(e,arguments),this},t.updateOne=function(e,t,n){return this.rules.updateOne(e,t,n),this},t.toString=function(e){return this.rules.toString(e)},e}(),Q=function(){function e(){this.plugins={internal:[],external:[]},this.registry=void 0}var t=e.prototype;return t.onCreateRule=function(e,t,n){for(var r=0;r<this.registry.onCreateRule.length;r++){var i=this.registry.onCreateRule[r](e,t,n);if(i)return i}return null},t.onProcessRule=function(e){if(!e.isProcessed){for(var t=e.options.sheet,n=0;n<this.registry.onProcessRule.length;n++)this.registry.onProcessRule[n](e,t);e.style&&this.onProcessStyle(e.style,e,t),e.isProcessed=!0}},t.onProcessStyle=function(e,t,n){for(var r=0;r<this.registry.onProcessStyle.length;r++)t.style=this.registry.onProcessStyle[r](t.style,t,n)},t.onProcessSheet=function(e){for(var t=0;t<this.registry.onProcessSheet.length;t++)this.registry.onProcessSheet[t](e)},t.onUpdate=function(e,t,n,r){for(var i=0;i<this.registry.onUpdate.length;i++)this.registry.onUpdate[i](e,t,n,r)},t.onChangeValue=function(e,t,n){for(var r=e,i=0;i<this.registry.onChangeValue.length;i++)r=this.registry.onChangeValue[i](r,t,n);return r},t.use=function(e,t){void 0===t&&(t={queue:"external"});var n=this.plugins[t.queue];-1===n.indexOf(e)&&(n.push(e),this.registry=[].concat(this.plugins.external,this.plugins.internal).reduce((function(e,t){for(var n in t)n in e&&e[n].push(t[n]);return e}),{onCreateRule:[],onProcessRule:[],onProcessStyle:[],onProcessSheet:[],onChangeValue:[],onUpdate:[]}))},e}(),X=function(){function e(){this.registry=[]}var t=e.prototype;return t.add=function(e){var t=this.registry,n=e.options.index;if(-1===t.indexOf(e))if(0===t.length||n>=this.index)t.push(e);else for(var r=0;r<t.length;r++)if(t[r].options.index>n)return void t.splice(r,0,e)},t.reset=function(){this.registry=[]},t.remove=function(e){var t=this.registry.indexOf(e);this.registry.splice(t,1)},t.toString=function(e){for(var t=void 0===e?{}:e,n=t.attached,r=function(e,t){if(null==e)return{};var n,r,i={},s=Object.keys(e);for(r=0;r<s.length;r++)n=s[r],t.indexOf(n)>=0||(i[n]=e[n]);return i}(t,["attached"]),i="",s=0;s<this.registry.length;s++){var o=this.registry[s];null!=n&&o.attached!==n||(i&&(i+="\n"),i+=o.toString(r))}return i},a(e,[{key:"index",get:function(){return 0===this.registry.length?0:this.registry[this.registry.length-1].options.index}}]),e}(),Y=new X,ee="undefined"!=typeof window&&window.Math==Math?window:"undefined"!=typeof self&&self.Math==Math?self:Function("return this")(),te="2f1acc6c3a606b082e5eef5e54414ffb";null==ee[te]&&(ee[te]=0);var ne=ee[te]++,re=function(e){void 0===e&&(e={});var t=0;return function(n,r){t+=1;var i="",s="";return r&&(r.options.classNamePrefix&&(s=r.options.classNamePrefix),null!=r.options.jss.id&&(i=String(r.options.jss.id))),e.minify?""+(s||"c")+ne+i+t:s+n.key+"-"+ne+(i?"-"+i:"")+"-"+t}},ie=function(e){var t;return function(){return t||(t=e()),t}},se=function(e,t){try{return e.attributeStyleMap?e.attributeStyleMap.get(t):e.style.getPropertyValue(t)}catch(e){return""}},oe=function(e,t,n){try{var r=n;if(Array.isArray(n)&&(r=y(n,!0),"!important"===n[n.length-1]))return e.style.setProperty(t,r,"important"),!0;e.attributeStyleMap?e.attributeStyleMap.set(t,r):e.style.setProperty(t,r)}catch(e){return!1}return!0},ae=function(e,t){try{e.attributeStyleMap?e.attributeStyleMap.delete(t):e.style.removeProperty(t)}catch(e){}},he=function(e,t){return e.selectorText=t,e.selectorText===t},ue=ie((function(){return document.querySelector("head")}));var le=ie((function(){var e=document.querySelector('meta[property="csp-nonce"]');return e?e.getAttribute("content"):null})),de=function(e,t,n){try{"insertRule"in e?e.insertRule(t,n):"appendRule"in e&&e.appendRule(t)}catch(e){return!1}return e.cssRules[n]},ce=function(e,t){var n=e.cssRules.length;return void 0===t||t>n?n:t},fe=function(){function e(e){this.getPropertyValue=se,this.setProperty=oe,this.removeProperty=ae,this.setSelector=he,this.element=void 0,this.sheet=void 0,this.hasInsertedRules=!1,this.cssRules=[],e&&Y.add(e),this.sheet=e;var t,n=this.sheet?this.sheet.options:{},r=n.media,i=n.meta,s=n.element;this.element=s||((t=document.createElement("style")).textContent="\n",t),this.element.setAttribute("data-jss",""),r&&this.element.setAttribute("media",r),i&&this.element.setAttribute("data-meta",i);var o=le();o&&this.element.setAttribute("nonce",o)}var t=e.prototype;return t.attach=function(){if(!this.element.parentNode&&this.sheet){!function(e,t){var n=t.insertionPoint,r=function(e){var t=Y.registry;if(t.length>0){var n=function(e,t){for(var n=0;n<e.length;n++){var r=e[n];if(r.attached&&r.options.index>t.index&&r.options.insertionPoint===t.insertionPoint)return r}return null}(t,e);if(n&&n.renderer)return{parent:n.renderer.element.parentNode,node:n.renderer.element};if((n=function(e,t){for(var n=e.length-1;n>=0;n--){var r=e[n];if(r.attached&&r.options.insertionPoint===t.insertionPoint)return r}return null}(t,e))&&n.renderer)return{parent:n.renderer.element.parentNode,node:n.renderer.element.nextSibling}}var r=e.insertionPoint;if(r&&"string"==typeof r){var i=function(e){for(var t=ue(),n=0;n<t.childNodes.length;n++){var r=t.childNodes[n];if(8===r.nodeType&&r.nodeValue.trim()===e)return r}return null}(r);if(i)return{parent:i.parentNode,node:i.nextSibling}}return!1}(t);if(!1!==r&&r.parent)r.parent.insertBefore(e,r.node);else if(n&&"number"==typeof n.nodeType){var i=n,s=i.parentNode;s&&s.insertBefore(e,i.nextSibling)}else ue().appendChild(e)}(this.element,this.sheet.options);var e=Boolean(this.sheet&&this.sheet.deployed);this.hasInsertedRules&&e&&(this.hasInsertedRules=!1,this.deploy())}},t.detach=function(){if(this.sheet){var e=this.element.parentNode;e&&e.removeChild(this.element),this.sheet.options.link&&(this.cssRules=[],this.element.textContent="\n")}},t.deploy=function(){var e=this.sheet;e&&(e.options.link?this.insertRules(e.rules):this.element.textContent="\n"+e.toString()+"\n")},t.insertRules=function(e,t){for(var n=0;n<e.index.length;n++)this.insertRule(e.index[n],n,t)},t.insertRule=function(e,t,n){if(void 0===n&&(n=this.element.sheet),e.rules){var r=e,i=n;if("conditional"===e.type||"keyframes"===e.type){var s=ce(n,t);if(!1===(i=de(n,r.toString({children:!1}),s)))return!1;this.refCssRule(e,s,i)}return this.insertRules(r.rules,i),i}var o=e.toString();if(!o)return!1;var a=ce(n,t),h=de(n,o,a);return!1!==h&&(this.hasInsertedRules=!0,this.refCssRule(e,a,h),h)},t.refCssRule=function(e,t,n){e.renderable=n,e.options.parent instanceof K&&(this.cssRules[t]=n)},t.deleteRule=function(e){var t=this.element.sheet,n=this.indexOf(e);return-1!==n&&(t.deleteRule(n),this.cssRules.splice(n,1),!0)},t.indexOf=function(e){return this.cssRules.indexOf(e)},t.replaceRule=function(e,t){var n=this.indexOf(e);return-1!==n&&(this.element.sheet.deleteRule(n),this.cssRules.splice(n,1),this.insertRule(t,n))},t.getRules=function(){return this.element.sheet.cssRules},e}(),pe=0,ye=function(){function e(e){this.id=pe++,this.version="10.5.1",this.plugins=new Q,this.options={id:{minify:!1},createGenerateId:re,Renderer:i.Z?fe:null,plugins:[]},this.generateId=re({minify:!1});for(var t=0;t<F.length;t++)this.plugins.use(F[t],{queue:"internal"});this.setup(e)}var t=e.prototype;return t.setup=function(e){return void 0===e&&(e={}),e.createGenerateId&&(this.options.createGenerateId=e.createGenerateId),e.id&&(this.options.id=r({},this.options.id,e.id)),(e.createGenerateId||e.id)&&(this.generateId=this.options.createGenerateId(this.options.id)),null!=e.insertionPoint&&(this.options.insertionPoint=e.insertionPoint),"Renderer"in e&&(this.options.Renderer=e.Renderer),e.plugins&&this.use.apply(this,e.plugins),this},t.createStyleSheet=function(e,t){void 0===t&&(t={});var n=t.index;"number"!=typeof n&&(n=0===Y.index?0:Y.index+1);var i=new K(e,r({},t,{jss:this,generateId:t.generateId||this.generateId,insertionPoint:this.options.insertionPoint,Renderer:this.options.Renderer,index:n}));return this.plugins.onProcessSheet(i),i},t.removeStyleSheet=function(e){return e.detach(),Y.remove(e),this},t.createRule=function(e,t,n){if(void 0===t&&(t={}),void 0===n&&(n={}),"object"==typeof e)return this.createRule(void 0,e,t);var i=r({},n,{name:e,jss:this,Renderer:this.options.Renderer});i.generateId||(i.generateId=this.generateId),i.classes||(i.classes={}),i.keyframes||(i.keyframes={});var s=f(e,t,i);return s&&this.plugins.onProcessRule(s),s},t.use=function(){for(var e=this,t=arguments.length,n=new Array(t),r=0;r<t;r++)n[r]=arguments[r];return n.forEach((function(t){e.plugins.use(t)})),this},e}();function ve(e){var t=null;for(var n in e){var r=e[n],i=typeof r;if("function"===i)t||(t={}),t[n]=r;else if("object"===i&&null!==r&&!Array.isArray(r)){var s=ve(r);s&&(t||(t={}),t[n]=s)}}return t}var ge=function(){function e(){this.length=0,this.sheets=new WeakMap}var t=e.prototype;return t.get=function(e){var t=this.sheets.get(e);return t&&t.sheet},t.add=function(e,t){this.sheets.has(e)||(this.length++,this.sheets.set(e,{sheet:t,refs:0}))},t.manage=function(e){var t=this.sheets.get(e);if(t)return 0===t.refs&&t.sheet.attach(),t.refs++,t.sheet;(0,s.Z)(!1,"[JSS] SheetsManager: can't find sheet to manage")},t.unmanage=function(e){var t=this.sheets.get(e);t?t.refs>0&&(t.refs--,0===t.refs&&t.sheet.detach()):(0,s.Z)(!1,"SheetsManager: can't find sheet to unmanage")},a(e,[{key:"size",get:function(){return this.length}}]),e}(),me="object"==typeof CSS&&null!=CSS&&"number"in CSS,Re=function(e){return new ye(e)};const Se=Re()}}]);