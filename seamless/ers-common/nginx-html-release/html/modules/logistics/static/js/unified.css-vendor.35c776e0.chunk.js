(self.webpackChunk_unified_logistics=self.webpackChunk_unified_logistics||[]).push([[3687],{5692:(r,e,t)=>{"use strict";t.d(e,{if:()=>v,wR:()=>L,HO:()=>er});var n=t(653);function i(r,e){(null==e||e>r.length)&&(e=r.length);for(var t=0,n=new Array(e);t<e;t++)n[t]=r[t];return n}var o="",u="",a="",l="",s=n.Z&&"ontouchstart"in document.documentElement;if(n.Z){var f={Moz:"-moz-",ms:"-ms-",O:"-o-",Webkit:"-webkit-"},p=document.createElement("p").style;for(var c in f)if(c+"Transform"in p){o=c,u=f[c];break}"Webkit"===o&&"msHyphens"in p&&(o="ms",u=f.ms,l="edge"),"Webkit"===o&&"-apple-trailing-word"in p&&(a="apple")}var d=o,m=u,y=a,b=l,g=s;function v(r){return"-"===r[1]||"ms"===d?r:"@"+m+"keyframes"+r.substr(10)}var x={noPrefill:["appearance"],supportedProperty:function(r){return"appearance"===r&&("ms"===d?"-webkit-"+r:m+r)}},k={noPrefill:["color-adjust"],supportedProperty:function(r){return"color-adjust"===r&&("Webkit"===d?m+"print-"+r:r)}},P=/[-\s]+(.)?/g;function w(r,e){return e?e.toUpperCase():""}function h(r){return r.replace(P,w)}function W(r){return h("-"+r)}var j,A={noPrefill:["mask"],supportedProperty:function(r,e){if(!/^mask/.test(r))return!1;if("Webkit"===d){var t="mask-image";if(h(t)in e)return r;if(d+W(t)in e)return m+r}return r}},E={noPrefill:["text-orientation"],supportedProperty:function(r){return"text-orientation"===r&&("apple"!==y||g?r:m+r)}},O={noPrefill:["transform"],supportedProperty:function(r,e,t){return"transform"===r&&(t.transform?r:m+r)}},z={noPrefill:["transition"],supportedProperty:function(r,e,t){return"transition"===r&&(t.transition?r:m+r)}},C={noPrefill:["writing-mode"],supportedProperty:function(r){return"writing-mode"===r&&("Webkit"===d||"ms"===d&&"edge"!==b?m+r:r)}},S={noPrefill:["user-select"],supportedProperty:function(r){return"user-select"===r&&("Moz"===d||"ms"===d||"apple"===y?m+r:r)}},M={supportedProperty:function(r,e){return!!/^break-/.test(r)&&("Webkit"===d?"WebkitColumn"+W(r)in e&&m+"column-"+r:"Moz"===d&&"page"+W(r)in e&&"page-"+r)}},I={supportedProperty:function(r,e){if(!/^(border|margin|padding)-inline/.test(r))return!1;if("Moz"===d)return r;var t=r.replace("-inline","");return d+W(t)in e&&m+t}},N={supportedProperty:function(r,e){return h(r)in e&&r}},Z={supportedProperty:function(r,e){var t=W(r);return"-"===r[0]||"-"===r[0]&&"-"===r[1]?r:d+t in e?m+r:"Webkit"!==d&&"Webkit"+t in e&&"-webkit-"+r}},_={supportedProperty:function(r){return"scroll-snap"===r.substring(0,11)&&("ms"===d?""+m+r:r)}},H={supportedProperty:function(r){return"overscroll-behavior"===r&&("ms"===d?m+"scroll-chaining":r)}},T={"flex-grow":"flex-positive","flex-shrink":"flex-negative","flex-basis":"flex-preferred-size","justify-content":"flex-pack",order:"flex-order","align-items":"flex-align","align-content":"flex-line-pack"},U={supportedProperty:function(r,e){var t=T[r];return!!t&&d+W(t)in e&&m+t}},R={flex:"box-flex","flex-grow":"box-flex","flex-direction":["box-orient","box-direction"],order:"box-ordinal-group","align-items":"box-align","flex-flow":["box-orient","box-direction"],"justify-content":"box-pack"},$=Object.keys(R),q=function(r){return m+r},B=[x,k,A,E,O,z,C,S,M,I,N,Z,_,H,U,{supportedProperty:function(r,e,t){var n=t.multiple;if($.indexOf(r)>-1){var i=R[r];if(!Array.isArray(i))return d+W(i)in e&&m+i;if(!n)return!1;for(var o=0;o<i.length;o++)if(!(d+W(i[0])in e))return!1;return i.map(q)}return!1}}],D=B.filter((function(r){return r.supportedProperty})).map((function(r){return r.supportedProperty})),F=B.filter((function(r){return r.noPrefill})).reduce((function(r,e){return r.push.apply(r,function(r){if(Array.isArray(r))return i(r)}(t=e.noPrefill)||function(r){if("undefined"!=typeof Symbol&&Symbol.iterator in Object(r))return Array.from(r)}(t)||function(r,e){if(r){if("string"==typeof r)return i(r,e);var t=Object.prototype.toString.call(r).slice(8,-1);return"Object"===t&&r.constructor&&(t=r.constructor.name),"Map"===t||"Set"===t?Array.from(r):"Arguments"===t||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(t)?i(r,e):void 0}}(t)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()),r;var t}),[]),G={};if(n.Z){j=document.createElement("p");var J=window.getComputedStyle(document.documentElement,"");for(var K in J)isNaN(K)||(G[J[K]]=J[K]);F.forEach((function(r){return delete G[r]}))}function L(r,e){if(void 0===e&&(e={}),!j)return r;if(null!=G[r])return G[r];"transition"!==r&&"transform"!==r||(e[r]=r in j.style);for(var t=0;t<D.length&&(G[r]=D[t](r,j.style,e),!G[r]);t++);try{j.style[r]=""}catch(r){return!1}return G[r]}var Q,V={},X={transition:1,"transition-property":1,"-webkit-transition":1,"-webkit-transition-property":1},Y=/(^\s*[\w-]+)|, (\s*[\w-]+)(?![^()]*\))/g;function rr(r,e,t){return"var"===e?"var":"all"===e?"all":"all"===t?", all":(e?L(e):", "+L(t))||e||t}function er(r,e){var t=e;if(!Q||"content"===r)return e;if("string"!=typeof t||!isNaN(parseInt(t,10)))return t;var n=r+t;if(null!=V[n])return V[n];try{Q.style[r]=t}catch(r){return V[n]=!1,!1}if(X[r])t=t.replace(Y,rr);else if(""===Q.style[r]&&("-ms-flex"===(t=m+t)&&(Q.style[r]="-ms-flexbox"),Q.style[r]=t,""===Q.style[r]))return V[n]=!1,!1;return Q.style[r]="",V[n]=t,V[n]}n.Z&&(Q=document.createElement("p"))}}]);