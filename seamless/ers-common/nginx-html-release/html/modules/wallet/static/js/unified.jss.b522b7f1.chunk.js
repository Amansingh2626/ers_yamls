/*! For license information please see unified.jss.b522b7f1.chunk.js.LICENSE.txt */
(self.webpackChunk_unified_wallet=self.webpackChunk_unified_wallet||[]).push([[6522],{7359:(e,t,n)=>{"use strict";function r(){return(r=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}n.d(t,{RB:()=>D,xE:()=>W,Ue:()=>ge,JH:()=>c,_$:()=>ye,HZ:()=>ve,EK:()=>p});var i=n(653);function s(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function o(e,t,n){return t&&s(e.prototype,t),n&&s(e,n),e}function a(e,t){return(a=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function u(e,t){e.prototype=Object.create(t.prototype),e.prototype.constructor=e,a(e,t)}function h(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}var l={}.constructor;function d(e){if(null==e||"object"!=typeof e)return e;if(Array.isArray(e))return e.map(d);if(e.constructor!==l)return e;var t={};for(var n in e)t[n]=d(e[n]);return t}function c(e,t,n){void 0===e&&(e="unnamed");var r=n.jss,i=d(t);return r.plugins.onCreateRule(e,i,n)||(e[0],null)}var f=function(e,t){for(var n="",r=0;r<e.length&&"!important"!==e[r];r++)n&&(n+=t),n+=e[r];return n},p=function(e,t){if(void 0===t&&(t=!1),!Array.isArray(e))return e;var n="";if(Array.isArray(e[0]))for(var r=0;r<e.length&&"!important"!==e[r];r++)n&&(n+=", "),n+=f(e[r]," ");else n=f(e,", ");return t||"!important"!==e[e.length-1]||(n+=" !important"),n};function y(e,t){for(var n="",r=0;r<t;r++)n+="  ";return n+e}function v(e,t,n){void 0===n&&(n={});var r="";if(!t)return r;var i=n.indent,s=void 0===i?0:i,o=t.fallbacks;if(e&&s++,o)if(Array.isArray(o))for(var a=0;a<o.length;a++){var u=o[a];for(var h in u){var l=u[h];null!=l&&(r&&(r+="\n"),r+=""+y(h+": "+p(l)+";",s))}}else for(var d in o){var c=o[d];null!=c&&(r&&(r+="\n"),r+=""+y(d+": "+p(c)+";",s))}for(var f in t){var v=t[f];null!=v&&"fallbacks"!==f&&(r&&(r+="\n"),r+=""+y(f+": "+p(v)+";",s))}return(r||n.allowEmpty)&&e?(r&&(r="\n"+r+"\n"),y(e+" {"+r,--s)+y("}",s)):r}var g=/([[\].#*$><+~=|^:(),"'`\s])/g,m="undefined"!=typeof CSS&&CSS.escape,R=function(e){return m?m(e):e.replace(g,"\\$1")},k=function(){function e(e,t,n){this.type="style",this.key=void 0,this.isProcessed=!1,this.style=void 0,this.renderer=void 0,this.renderable=void 0,this.options=void 0;var r=n.sheet,i=n.Renderer;this.key=e,this.options=n,this.style=t,r?this.renderer=r.renderer:i&&(this.renderer=new i)}return e.prototype.prop=function(e,t,n){if(void 0===t)return this.style[e];var r=!!n&&n.force;if(!r&&this.style[e]===t)return this;var i=t;n&&!1===n.process||(i=this.options.jss.plugins.onChangeValue(t,e,this));var s=null==i||!1===i,o=e in this.style;if(s&&!o&&!r)return this;var a=s&&o;if(a?delete this.style[e]:this.style[e]=i,this.renderable&&this.renderer)return a?this.renderer.removeProperty(this.renderable,e):this.renderer.setProperty(this.renderable,e,i),this;var u=this.options.sheet;return u&&u.attached,this},e}(),x=function(e){function t(t,n,r){var i;(i=e.call(this,t,n,r)||this).selectorText=void 0,i.id=void 0,i.renderable=void 0;var s=r.selector,o=r.scoped,a=r.sheet,u=r.generateId;return s?i.selectorText=s:!1!==o&&(i.id=u(h(h(i)),a),i.selectorText="."+R(i.id)),i}u(t,e);var n=t.prototype;return n.applyTo=function(e){var t=this.renderer;if(t){var n=this.toJSON();for(var r in n)t.setProperty(e,r,n[r])}return this},n.toJSON=function(){var e={};for(var t in this.style){var n=this.style[t];"object"!=typeof n?e[t]=n:Array.isArray(n)&&(e[t]=p(n))}return e},n.toString=function(e){var t=this.options.sheet,n=t&&t.options.link?r({},e,{allowEmpty:!0}):e;return v(this.selectorText,this.style,n)},o(t,[{key:"selector",set:function(e){if(e!==this.selectorText){this.selectorText=e;var t=this.renderer,n=this.renderable;n&&t&&(t.setSelector(n,e)||t.replaceRule(n,this))}},get:function(){return this.selectorText}}]),t}(k),S={onCreateRule:function(e,t,n){return"@"===e[0]||n.parent&&"keyframes"===n.parent.type?null:new x(e,t,n)}},b={indent:1,children:!0},P=/@([\w-]+)/,w=function(){function e(e,t,n){this.type="conditional",this.at=void 0,this.key=void 0,this.query=void 0,this.rules=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0,this.key=e;var i=e.match(P);for(var s in this.at=i?i[1]:"unknown",this.query=n.name||"@"+this.at,this.options=n,this.rules=new D(r({},n,{parent:this})),t)this.rules.add(s,t[s]);this.rules.process()}var t=e.prototype;return t.getRule=function(e){return this.rules.get(e)},t.indexOf=function(e){return this.rules.indexOf(e)},t.addRule=function(e,t,n){var r=this.rules.add(e,t,n);return r?(this.options.jss.plugins.onProcessRule(r),r):null},t.toString=function(e){if(void 0===e&&(e=b),null==e.indent&&(e.indent=b.indent),null==e.children&&(e.children=b.children),!1===e.children)return this.query+" {}";var t=this.rules.toString(e);return t?this.query+" {\n"+t+"\n}":""},e}(),C=/@media|@supports\s+/,O={onCreateRule:function(e,t,n){return C.test(e)?new w(e,t,n):null}},j={indent:1,children:!0},A=/@keyframes\s+([\w-]+)/,I=function(){function e(e,t,n){this.type="keyframes",this.at="@keyframes",this.key=void 0,this.name=void 0,this.id=void 0,this.rules=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0;var i=e.match(A);i&&i[1]?this.name=i[1]:this.name="noname",this.key=this.type+"-"+this.name,this.options=n;var s=n.scoped,o=n.sheet,a=n.generateId;for(var u in this.id=!1===s?this.name:R(a(this,o)),this.rules=new D(r({},n,{parent:this})),t)this.rules.add(u,t[u],r({},n,{parent:this}));this.rules.process()}return e.prototype.toString=function(e){if(void 0===e&&(e=j),null==e.indent&&(e.indent=j.indent),null==e.children&&(e.children=j.children),!1===e.children)return this.at+" "+this.id+" {}";var t=this.rules.toString(e);return t&&(t="\n"+t+"\n"),this.at+" "+this.id+" {"+t+"}"},e}(),q=/@keyframes\s+/,N=/\$([\w-]+)/g,T=function(e,t){return"string"==typeof e?e.replace(N,(function(e,n){return n in t?t[n]:e})):e},E=function(e,t,n){var r=e[t],i=T(r,n);i!==r&&(e[t]=i)},M={onCreateRule:function(e,t,n){return"string"==typeof e&&q.test(e)?new I(e,t,n):null},onProcessStyle:function(e,t,n){return"style"===t.type&&n?("animation-name"in e&&E(e,"animation-name",n.keyframes),"animation"in e&&E(e,"animation",n.keyframes),e):e},onChangeValue:function(e,t,n){var r=n.options.sheet;if(!r)return e;switch(t){case"animation":case"animation-name":return T(e,r.keyframes);default:return e}}},V=function(e){function t(){for(var t,n=arguments.length,r=new Array(n),i=0;i<n;i++)r[i]=arguments[i];return(t=e.call.apply(e,[this].concat(r))||this).renderable=void 0,t}return u(t,e),t.prototype.toString=function(e){var t=this.options.sheet,n=t&&t.options.link?r({},e,{allowEmpty:!0}):e;return v(this.key,this.style,n)},t}(k),_={onCreateRule:function(e,t,n){return n.parent&&"keyframes"===n.parent.type?new V(e,t,n):null}},G=function(){function e(e,t,n){this.type="font-face",this.at="@font-face",this.key=void 0,this.style=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0,this.key=e,this.style=t,this.options=n}return e.prototype.toString=function(e){if(Array.isArray(this.style)){for(var t="",n=0;n<this.style.length;n++)t+=v(this.at,this.style[n]),this.style[n+1]&&(t+="\n");return t}return v(this.at,this.style,e)},e}(),U=/@font-face/,B={onCreateRule:function(e,t,n){return U.test(e)?new G(e,t,n):null}},$=function(){function e(e,t,n){this.type="viewport",this.at="@viewport",this.key=void 0,this.style=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0,this.key=e,this.style=t,this.options=n}return e.prototype.toString=function(e){return v(this.key,this.style,e)},e}(),J={onCreateRule:function(e,t,n){return"@viewport"===e||"@-ms-viewport"===e?new $(e,t,n):null}},H=function(){function e(e,t,n){this.type="simple",this.key=void 0,this.value=void 0,this.options=void 0,this.isProcessed=!1,this.renderable=void 0,this.key=e,this.value=t,this.options=n}return e.prototype.toString=function(e){if(Array.isArray(this.value)){for(var t="",n=0;n<this.value.length;n++)t+=this.key+" "+this.value[n]+";",this.value[n+1]&&(t+="\n");return t}return this.key+" "+this.value+";"},e}(),Z={"@charset":!0,"@import":!0,"@namespace":!0},F=[S,O,M,_,B,J,{onCreateRule:function(e,t,n){return e in Z?new H(e,t,n):null}}],K={process:!0},z={force:!0,process:!0},D=function(){function e(e){this.map={},this.raw={},this.index=[],this.counter=0,this.options=void 0,this.classes=void 0,this.keyframes=void 0,this.options=e,this.classes=e.classes,this.keyframes=e.keyframes}var t=e.prototype;return t.add=function(e,t,n){var i=this.options,s=i.parent,o=i.sheet,a=i.jss,u=i.Renderer,h=i.generateId,l=i.scoped,d=r({classes:this.classes,parent:s,sheet:o,jss:a,Renderer:u,generateId:h,scoped:l,name:e,keyframes:this.keyframes,selector:void 0},n),f=e;e in this.raw&&(f=e+"-d"+this.counter++),this.raw[f]=t,f in this.classes&&(d.selector="."+R(this.classes[f]));var p=c(f,t,d);if(!p)return null;this.register(p);var y=void 0===d.index?this.index.length:d.index;return this.index.splice(y,0,p),p},t.get=function(e){return this.map[e]},t.remove=function(e){this.unregister(e),delete this.raw[e.key],this.index.splice(this.index.indexOf(e),1)},t.indexOf=function(e){return this.index.indexOf(e)},t.process=function(){var e=this.options.jss.plugins;this.index.slice(0).forEach(e.onProcessRule,e)},t.register=function(e){this.map[e.key]=e,e instanceof x?(this.map[e.selector]=e,e.id&&(this.classes[e.key]=e.id)):e instanceof I&&this.keyframes&&(this.keyframes[e.name]=e.id)},t.unregister=function(e){delete this.map[e.key],e instanceof x?(delete this.map[e.selector],delete this.classes[e.key]):e instanceof I&&delete this.keyframes[e.name]},t.update=function(){var e,t,n;if("string"==typeof(arguments.length<=0?void 0:arguments[0])?(e=arguments.length<=0?void 0:arguments[0],t=arguments.length<=1?void 0:arguments[1],n=arguments.length<=2?void 0:arguments[2]):(t=arguments.length<=0?void 0:arguments[0],n=arguments.length<=1?void 0:arguments[1],e=null),e)this.updateOne(this.map[e],t,n);else for(var r=0;r<this.index.length;r++)this.updateOne(this.index[r],t,n)},t.updateOne=function(t,n,r){void 0===r&&(r=K);var i=this.options,s=i.jss.plugins,o=i.sheet;if(t.rules instanceof e)t.rules.update(n,r);else{var a=t,u=a.style;if(s.onUpdate(n,t,o,r),r.process&&u&&u!==a.style){for(var h in s.onProcessStyle(a.style,a,o),a.style){var l=a.style[h];l!==u[h]&&a.prop(h,l,z)}for(var d in u){var c=a.style[d],f=u[d];null==c&&c!==f&&a.prop(d,null,z)}}}},t.toString=function(e){for(var t="",n=this.options.sheet,r=!!n&&n.options.link,i=0;i<this.index.length;i++){var s=this.index[i].toString(e);(s||r)&&(t&&(t+="\n"),t+=s)}return t},e}(),L=function(){function e(e,t){for(var n in this.options=void 0,this.deployed=void 0,this.attached=void 0,this.rules=void 0,this.renderer=void 0,this.classes=void 0,this.keyframes=void 0,this.queue=void 0,this.attached=!1,this.deployed=!1,this.classes={},this.keyframes={},this.options=r({},t,{sheet:this,parent:this,classes:this.classes,keyframes:this.keyframes}),t.Renderer&&(this.renderer=new t.Renderer(this)),this.rules=new D(this.options),e)this.rules.add(n,e[n]);this.rules.process()}var t=e.prototype;return t.attach=function(){return this.attached||(this.renderer&&this.renderer.attach(),this.attached=!0,this.deployed||this.deploy()),this},t.detach=function(){return this.attached?(this.renderer&&this.renderer.detach(),this.attached=!1,this):this},t.addRule=function(e,t,n){var r=this.queue;this.attached&&!r&&(this.queue=[]);var i=this.rules.add(e,t,n);return i?(this.options.jss.plugins.onProcessRule(i),this.attached?this.deployed?(r?r.push(i):(this.insertRule(i),this.queue&&(this.queue.forEach(this.insertRule,this),this.queue=void 0)),i):i:(this.deployed=!1,i)):null},t.insertRule=function(e){this.renderer&&this.renderer.insertRule(e)},t.addRules=function(e,t){var n=[];for(var r in e){var i=this.addRule(r,e[r],t);i&&n.push(i)}return n},t.getRule=function(e){return this.rules.get(e)},t.deleteRule=function(e){var t="object"==typeof e?e:this.rules.get(e);return!(!t||this.attached&&!t.renderable)&&(this.rules.remove(t),!(this.attached&&t.renderable&&this.renderer)||this.renderer.deleteRule(t.renderable))},t.indexOf=function(e){return this.rules.indexOf(e)},t.deploy=function(){return this.renderer&&this.renderer.deploy(),this.deployed=!0,this},t.update=function(){var e;return(e=this.rules).update.apply(e,arguments),this},t.updateOne=function(e,t,n){return this.rules.updateOne(e,t,n),this},t.toString=function(e){return this.rules.toString(e)},e}(),Q=function(){function e(){this.plugins={internal:[],external:[]},this.registry=void 0}var t=e.prototype;return t.onCreateRule=function(e,t,n){for(var r=0;r<this.registry.onCreateRule.length;r++){var i=this.registry.onCreateRule[r](e,t,n);if(i)return i}return null},t.onProcessRule=function(e){if(!e.isProcessed){for(var t=e.options.sheet,n=0;n<this.registry.onProcessRule.length;n++)this.registry.onProcessRule[n](e,t);e.style&&this.onProcessStyle(e.style,e,t),e.isProcessed=!0}},t.onProcessStyle=function(e,t,n){for(var r=0;r<this.registry.onProcessStyle.length;r++)t.style=this.registry.onProcessStyle[r](t.style,t,n)},t.onProcessSheet=function(e){for(var t=0;t<this.registry.onProcessSheet.length;t++)this.registry.onProcessSheet[t](e)},t.onUpdate=function(e,t,n,r){for(var i=0;i<this.registry.onUpdate.length;i++)this.registry.onUpdate[i](e,t,n,r)},t.onChangeValue=function(e,t,n){for(var r=e,i=0;i<this.registry.onChangeValue.length;i++)r=this.registry.onChangeValue[i](r,t,n);return r},t.use=function(e,t){void 0===t&&(t={queue:"external"});var n=this.plugins[t.queue];-1===n.indexOf(e)&&(n.push(e),this.registry=[].concat(this.plugins.external,this.plugins.internal).reduce((function(e,t){for(var n in t)n in e&&e[n].push(t[n]);return e}),{onCreateRule:[],onProcessRule:[],onProcessStyle:[],onProcessSheet:[],onChangeValue:[],onUpdate:[]}))},e}(),W=function(){function e(){this.registry=[]}var t=e.prototype;return t.add=function(e){var t=this.registry,n=e.options.index;if(-1===t.indexOf(e))if(0===t.length||n>=this.index)t.push(e);else for(var r=0;r<t.length;r++)if(t[r].options.index>n)return void t.splice(r,0,e)},t.reset=function(){this.registry=[]},t.remove=function(e){var t=this.registry.indexOf(e);this.registry.splice(t,1)},t.toString=function(e){for(var t=void 0===e?{}:e,n=t.attached,r=function(e,t){if(null==e)return{};var n,r,i={},s=Object.keys(e);for(r=0;r<s.length;r++)n=s[r],t.indexOf(n)>=0||(i[n]=e[n]);return i}(t,["attached"]),i="",s=0;s<this.registry.length;s++){var o=this.registry[s];null!=n&&o.attached!==n||(i&&(i+="\n"),i+=o.toString(r))}return i},o(e,[{key:"index",get:function(){return 0===this.registry.length?0:this.registry[this.registry.length-1].options.index}}]),e}(),X=new W,Y="undefined"!=typeof window&&window.Math==Math?window:"undefined"!=typeof self&&self.Math==Math?self:Function("return this")(),ee="2f1acc6c3a606b082e5eef5e54414ffb";null==Y[ee]&&(Y[ee]=0);var te=Y[ee]++,ne=function(e){void 0===e&&(e={});var t=0;return function(n,r){t+=1;var i="",s="";return r&&(r.options.classNamePrefix&&(s=r.options.classNamePrefix),null!=r.options.jss.id&&(i=String(r.options.jss.id))),e.minify?""+(s||"c")+te+i+t:s+n.key+"-"+te+(i?"-"+i:"")+"-"+t}},re=function(e){var t;return function(){return t||(t=e()),t}},ie=function(e,t){try{return e.attributeStyleMap?e.attributeStyleMap.get(t):e.style.getPropertyValue(t)}catch(e){return""}},se=function(e,t,n){try{var r=n;if(Array.isArray(n)&&(r=p(n,!0),"!important"===n[n.length-1]))return e.style.setProperty(t,r,"important"),!0;e.attributeStyleMap?e.attributeStyleMap.set(t,r):e.style.setProperty(t,r)}catch(e){return!1}return!0},oe=function(e,t){try{e.attributeStyleMap?e.attributeStyleMap.delete(t):e.style.removeProperty(t)}catch(e){}},ae=function(e,t){return e.selectorText=t,e.selectorText===t},ue=re((function(){return document.querySelector("head")}));var he=re((function(){var e=document.querySelector('meta[property="csp-nonce"]');return e?e.getAttribute("content"):null})),le=function(e,t,n){try{"insertRule"in e?e.insertRule(t,n):"appendRule"in e&&e.appendRule(t)}catch(e){return!1}return e.cssRules[n]},de=function(e,t){var n=e.cssRules.length;return void 0===t||t>n?n:t},ce=function(){function e(e){this.getPropertyValue=ie,this.setProperty=se,this.removeProperty=oe,this.setSelector=ae,this.element=void 0,this.sheet=void 0,this.hasInsertedRules=!1,this.cssRules=[],e&&X.add(e),this.sheet=e;var t,n=this.sheet?this.sheet.options:{},r=n.media,i=n.meta,s=n.element;this.element=s||((t=document.createElement("style")).textContent="\n",t),this.element.setAttribute("data-jss",""),r&&this.element.setAttribute("media",r),i&&this.element.setAttribute("data-meta",i);var o=he();o&&this.element.setAttribute("nonce",o)}var t=e.prototype;return t.attach=function(){if(!this.element.parentNode&&this.sheet){!function(e,t){var n=t.insertionPoint,r=function(e){var t=X.registry;if(t.length>0){var n=function(e,t){for(var n=0;n<e.length;n++){var r=e[n];if(r.attached&&r.options.index>t.index&&r.options.insertionPoint===t.insertionPoint)return r}return null}(t,e);if(n&&n.renderer)return{parent:n.renderer.element.parentNode,node:n.renderer.element};if((n=function(e,t){for(var n=e.length-1;n>=0;n--){var r=e[n];if(r.attached&&r.options.insertionPoint===t.insertionPoint)return r}return null}(t,e))&&n.renderer)return{parent:n.renderer.element.parentNode,node:n.renderer.element.nextSibling}}var r=e.insertionPoint;if(r&&"string"==typeof r){var i=function(e){for(var t=ue(),n=0;n<t.childNodes.length;n++){var r=t.childNodes[n];if(8===r.nodeType&&r.nodeValue.trim()===e)return r}return null}(r);if(i)return{parent:i.parentNode,node:i.nextSibling}}return!1}(t);if(!1!==r&&r.parent)r.parent.insertBefore(e,r.node);else if(n&&"number"==typeof n.nodeType){var i=n,s=i.parentNode;s&&s.insertBefore(e,i.nextSibling)}else ue().appendChild(e)}(this.element,this.sheet.options);var e=Boolean(this.sheet&&this.sheet.deployed);this.hasInsertedRules&&e&&(this.hasInsertedRules=!1,this.deploy())}},t.detach=function(){if(this.sheet){var e=this.element.parentNode;e&&e.removeChild(this.element),this.sheet.options.link&&(this.cssRules=[],this.element.textContent="\n")}},t.deploy=function(){var e=this.sheet;e&&(e.options.link?this.insertRules(e.rules):this.element.textContent="\n"+e.toString()+"\n")},t.insertRules=function(e,t){for(var n=0;n<e.index.length;n++)this.insertRule(e.index[n],n,t)},t.insertRule=function(e,t,n){if(void 0===n&&(n=this.element.sheet),e.rules){var r=e,i=n;if("conditional"===e.type||"keyframes"===e.type){var s=de(n,t);if(!1===(i=le(n,r.toString({children:!1}),s)))return!1;this.refCssRule(e,s,i)}return this.insertRules(r.rules,i),i}var o=e.toString();if(!o)return!1;var a=de(n,t),u=le(n,o,a);return!1!==u&&(this.hasInsertedRules=!0,this.refCssRule(e,a,u),u)},t.refCssRule=function(e,t,n){e.renderable=n,e.options.parent instanceof L&&(this.cssRules[t]=n)},t.deleteRule=function(e){var t=this.element.sheet,n=this.indexOf(e);return-1!==n&&(t.deleteRule(n),this.cssRules.splice(n,1),!0)},t.indexOf=function(e){return this.cssRules.indexOf(e)},t.replaceRule=function(e,t){var n=this.indexOf(e);return-1!==n&&(this.element.sheet.deleteRule(n),this.cssRules.splice(n,1),this.insertRule(t,n))},t.getRules=function(){return this.element.sheet.cssRules},e}(),fe=0,pe=function(){function e(e){this.id=fe++,this.version="10.5.1",this.plugins=new Q,this.options={id:{minify:!1},createGenerateId:ne,Renderer:i.Z?ce:null,plugins:[]},this.generateId=ne({minify:!1});for(var t=0;t<F.length;t++)this.plugins.use(F[t],{queue:"internal"});this.setup(e)}var t=e.prototype;return t.setup=function(e){return void 0===e&&(e={}),e.createGenerateId&&(this.options.createGenerateId=e.createGenerateId),e.id&&(this.options.id=r({},this.options.id,e.id)),(e.createGenerateId||e.id)&&(this.generateId=this.options.createGenerateId(this.options.id)),null!=e.insertionPoint&&(this.options.insertionPoint=e.insertionPoint),"Renderer"in e&&(this.options.Renderer=e.Renderer),e.plugins&&this.use.apply(this,e.plugins),this},t.createStyleSheet=function(e,t){void 0===t&&(t={});var n=t.index;"number"!=typeof n&&(n=0===X.index?0:X.index+1);var i=new L(e,r({},t,{jss:this,generateId:t.generateId||this.generateId,insertionPoint:this.options.insertionPoint,Renderer:this.options.Renderer,index:n}));return this.plugins.onProcessSheet(i),i},t.removeStyleSheet=function(e){return e.detach(),X.remove(e),this},t.createRule=function(e,t,n){if(void 0===t&&(t={}),void 0===n&&(n={}),"object"==typeof e)return this.createRule(void 0,e,t);var i=r({},n,{name:e,jss:this,Renderer:this.options.Renderer});i.generateId||(i.generateId=this.generateId),i.classes||(i.classes={}),i.keyframes||(i.keyframes={});var s=c(e,t,i);return s&&this.plugins.onProcessRule(s),s},t.use=function(){for(var e=this,t=arguments.length,n=new Array(t),r=0;r<t;r++)n[r]=arguments[r];return n.forEach((function(t){e.plugins.use(t)})),this},e}();function ye(e){var t=null;for(var n in e){var r=e[n],i=typeof r;if("function"===i)t||(t={}),t[n]=r;else if("object"===i&&null!==r&&!Array.isArray(r)){var s=ye(r);s&&(t||(t={}),t[n]=s)}}return t}var ve="object"==typeof CSS&&null!=CSS&&"number"in CSS,ge=function(e){return new pe(e)};ge()}}]);