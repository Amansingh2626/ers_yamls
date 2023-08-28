/*! For license information please see unified.popper.js.a5e96660.js.LICENSE.txt */
(self.webpackChunk_unified_voucher=self.webpackChunk_unified_voucher||[]).push([[4120],{3619:(e,t,n)=>{"use strict";n.d(t,{Z:()=>ie});var o="undefined"!=typeof window&&"undefined"!=typeof document&&"undefined"!=typeof navigator,r=function(){for(var e=["Edge","Trident","Firefox"],t=0;t<e.length;t+=1)if(o&&navigator.userAgent.indexOf(e[t])>=0)return 1;return 0}(),i=o&&window.Promise?function(e){var t=!1;return function(){t||(t=!0,window.Promise.resolve().then((function(){t=!1,e()})))}}:function(e){var t=!1;return function(){t||(t=!0,setTimeout((function(){t=!1,e()}),r))}};function a(e){return e&&"[object Function]"==={}.toString.call(e)}function s(e,t){if(1!==e.nodeType)return[];var n=e.ownerDocument.defaultView.getComputedStyle(e,null);return t?n[t]:n}function f(e){return"HTML"===e.nodeName?e:e.parentNode||e.host}function p(e){if(!e)return document.body;switch(e.nodeName){case"HTML":case"BODY":return e.ownerDocument.body;case"#document":return e.body}var t=s(e),n=t.overflow,o=t.overflowX,r=t.overflowY;return/(auto|scroll|overlay)/.test(n+r+o)?e:p(f(e))}function l(e){return e&&e.referenceNode?e.referenceNode:e}var u=o&&!(!window.MSInputMethodContext||!document.documentMode),d=o&&/MSIE 10/.test(navigator.userAgent);function c(e){return 11===e?u:10===e?d:u||d}function h(e){if(!e)return document.documentElement;for(var t=c(10)?document.body:null,n=e.offsetParent||null;n===t&&e.nextElementSibling;)n=(e=e.nextElementSibling).offsetParent;var o=n&&n.nodeName;return o&&"BODY"!==o&&"HTML"!==o?-1!==["TH","TD","TABLE"].indexOf(n.nodeName)&&"static"===s(n,"position")?h(n):n:e?e.ownerDocument.documentElement:document.documentElement}function m(e){return null!==e.parentNode?m(e.parentNode):e}function g(e,t){if(!(e&&e.nodeType&&t&&t.nodeType))return document.documentElement;var n=e.compareDocumentPosition(t)&Node.DOCUMENT_POSITION_FOLLOWING,o=n?e:t,r=n?t:e,i=document.createRange();i.setStart(o,0),i.setEnd(r,0);var a,s,f=i.commonAncestorContainer;if(e!==f&&t!==f||o.contains(r))return"BODY"===(s=(a=f).nodeName)||"HTML"!==s&&h(a.firstElementChild)!==a?h(f):f;var p=m(e);return p.host?g(p.host,t):g(e,m(t).host)}function v(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"top",n="top"===t?"scrollTop":"scrollLeft",o=e.nodeName;if("BODY"===o||"HTML"===o){var r=e.ownerDocument.documentElement,i=e.ownerDocument.scrollingElement||r;return i[n]}return e[n]}function b(e,t){var n=arguments.length>2&&void 0!==arguments[2]&&arguments[2],o=v(t,"top"),r=v(t,"left"),i=n?-1:1;return e.top+=o*i,e.bottom+=o*i,e.left+=r*i,e.right+=r*i,e}function w(e,t){var n="x"===t?"Left":"Top",o="Left"===n?"Right":"Bottom";return parseFloat(e["border"+n+"Width"])+parseFloat(e["border"+o+"Width"])}function y(e,t,n,o){return Math.max(t["offset"+e],t["scroll"+e],n["client"+e],n["offset"+e],n["scroll"+e],c(10)?parseInt(n["offset"+e])+parseInt(o["margin"+("Height"===e?"Top":"Left")])+parseInt(o["margin"+("Height"===e?"Bottom":"Right")]):0)}function x(e){var t=e.body,n=e.documentElement,o=c(10)&&getComputedStyle(n);return{height:y("Height",t,n,o),width:y("Width",t,n,o)}}var E=function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")},O=function(){function e(e,t){for(var n=0;n<t.length;n++){var o=t[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}return function(t,n,o){return n&&e(t.prototype,n),o&&e(t,o),t}}(),L=function(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e},T=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var o in n)Object.prototype.hasOwnProperty.call(n,o)&&(e[o]=n[o])}return e};function D(e){return T({},e,{right:e.left+e.width,bottom:e.top+e.height})}function M(e){var t={};try{if(c(10)){t=e.getBoundingClientRect();var n=v(e,"top"),o=v(e,"left");t.top+=n,t.left+=o,t.bottom+=n,t.right+=o}else t=e.getBoundingClientRect()}catch(e){}var r={left:t.left,top:t.top,width:t.right-t.left,height:t.bottom-t.top},i="HTML"===e.nodeName?x(e.ownerDocument):{},a=i.width||e.clientWidth||r.width,f=i.height||e.clientHeight||r.height,p=e.offsetWidth-a,l=e.offsetHeight-f;if(p||l){var u=s(e);p-=w(u,"x"),l-=w(u,"y"),r.width-=p,r.height-=l}return D(r)}function N(e,t){var n=arguments.length>2&&void 0!==arguments[2]&&arguments[2],o=c(10),r="HTML"===t.nodeName,i=M(e),a=M(t),f=p(e),l=s(t),u=parseFloat(l.borderTopWidth),d=parseFloat(l.borderLeftWidth);n&&r&&(a.top=Math.max(a.top,0),a.left=Math.max(a.left,0));var h=D({top:i.top-a.top-u,left:i.left-a.left-d,width:i.width,height:i.height});if(h.marginTop=0,h.marginLeft=0,!o&&r){var m=parseFloat(l.marginTop),g=parseFloat(l.marginLeft);h.top-=u-m,h.bottom-=u-m,h.left-=d-g,h.right-=d-g,h.marginTop=m,h.marginLeft=g}return(o&&!n?t.contains(f):t===f&&"BODY"!==f.nodeName)&&(h=b(h,t)),h}function F(e){var t=arguments.length>1&&void 0!==arguments[1]&&arguments[1],n=e.ownerDocument.documentElement,o=N(e,n),r=Math.max(n.clientWidth,window.innerWidth||0),i=Math.max(n.clientHeight,window.innerHeight||0),a=t?0:v(n),s=t?0:v(n,"left"),f={top:a-o.top+o.marginTop,left:s-o.left+o.marginLeft,width:r,height:i};return D(f)}function k(e){var t=e.nodeName;if("BODY"===t||"HTML"===t)return!1;if("fixed"===s(e,"position"))return!0;var n=f(e);return!!n&&k(n)}function C(e){if(!e||!e.parentElement||c())return document.documentElement;for(var t=e.parentElement;t&&"none"===s(t,"transform");)t=t.parentElement;return t||document.documentElement}function H(e,t,n,o){var r=arguments.length>4&&void 0!==arguments[4]&&arguments[4],i={top:0,left:0},a=r?C(e):g(e,l(t));if("viewport"===o)i=F(a,r);else{var s=void 0;"scrollParent"===o?"BODY"===(s=p(f(t))).nodeName&&(s=e.ownerDocument.documentElement):s="window"===o?e.ownerDocument.documentElement:o;var u=N(s,a,r);if("HTML"!==s.nodeName||k(a))i=u;else{var d=x(e.ownerDocument),c=d.height,h=d.width;i.top+=u.top-u.marginTop,i.bottom=c+u.top,i.left+=u.left-u.marginLeft,i.right=h+u.left}}var m="number"==typeof(n=n||0);return i.left+=m?n:n.left||0,i.top+=m?n:n.top||0,i.right-=m?n:n.right||0,i.bottom-=m?n:n.bottom||0,i}function B(e){return e.width*e.height}function A(e,t,n,o,r){var i=arguments.length>5&&void 0!==arguments[5]?arguments[5]:0;if(-1===e.indexOf("auto"))return e;var a=H(n,o,i,r),s={top:{width:a.width,height:t.top-a.top},right:{width:a.right-t.right,height:a.height},bottom:{width:a.width,height:a.bottom-t.bottom},left:{width:t.left-a.left,height:a.height}},f=Object.keys(s).map((function(e){return T({key:e},s[e],{area:B(s[e])})})).sort((function(e,t){return t.area-e.area})),p=f.filter((function(e){var t=e.width,o=e.height;return t>=n.clientWidth&&o>=n.clientHeight})),l=p.length>0?p[0].key:f[0].key,u=e.split("-")[1];return l+(u?"-"+u:"")}function S(e,t,n){var o=arguments.length>3&&void 0!==arguments[3]?arguments[3]:null,r=o?C(t):g(t,l(n));return N(n,r,o)}function W(e){var t=e.ownerDocument.defaultView.getComputedStyle(e),n=parseFloat(t.marginTop||0)+parseFloat(t.marginBottom||0),o=parseFloat(t.marginLeft||0)+parseFloat(t.marginRight||0);return{width:e.offsetWidth+o,height:e.offsetHeight+n}}function P(e){var t={left:"right",right:"left",bottom:"top",top:"bottom"};return e.replace(/left|right|bottom|top/g,(function(e){return t[e]}))}function j(e,t,n){n=n.split("-")[0];var o=W(e),r={width:o.width,height:o.height},i=-1!==["right","left"].indexOf(n),a=i?"top":"left",s=i?"left":"top",f=i?"height":"width",p=i?"width":"height";return r[a]=t[a]+t[f]/2-o[f]/2,r[s]=n===s?t[s]-o[p]:t[P(s)],r}function I(e,t){return Array.prototype.find?e.find(t):e.filter(t)[0]}function R(e,t,n){return(void 0===n?e:e.slice(0,function(e,t,n){if(Array.prototype.findIndex)return e.findIndex((function(e){return e.name===n}));var o=I(e,(function(e){return e.name===n}));return e.indexOf(o)}(e,0,n))).forEach((function(e){e.function&&console.warn("`modifier.function` is deprecated, use `modifier.fn`!");var n=e.function||e.fn;e.enabled&&a(n)&&(t.offsets.popper=D(t.offsets.popper),t.offsets.reference=D(t.offsets.reference),t=n(t,e))})),t}function U(){if(!this.state.isDestroyed){var e={instance:this,styles:{},arrowStyles:{},attributes:{},flipped:!1,offsets:{}};e.offsets.reference=S(this.state,this.popper,this.reference,this.options.positionFixed),e.placement=A(this.options.placement,e.offsets.reference,this.popper,this.reference,this.options.modifiers.flip.boundariesElement,this.options.modifiers.flip.padding),e.originalPlacement=e.placement,e.positionFixed=this.options.positionFixed,e.offsets.popper=j(this.popper,e.offsets.reference,e.placement),e.offsets.popper.position=this.options.positionFixed?"fixed":"absolute",e=R(this.modifiers,e),this.state.isCreated?this.options.onUpdate(e):(this.state.isCreated=!0,this.options.onCreate(e))}}function Y(e,t){return e.some((function(e){var n=e.name;return e.enabled&&n===t}))}function V(e){for(var t=[!1,"ms","Webkit","Moz","O"],n=e.charAt(0).toUpperCase()+e.slice(1),o=0;o<t.length;o++){var r=t[o],i=r?""+r+n:e;if(void 0!==document.body.style[i])return i}return null}function _(){return this.state.isDestroyed=!0,Y(this.modifiers,"applyStyle")&&(this.popper.removeAttribute("x-placement"),this.popper.style.position="",this.popper.style.top="",this.popper.style.left="",this.popper.style.right="",this.popper.style.bottom="",this.popper.style.willChange="",this.popper.style[V("transform")]=""),this.disableEventListeners(),this.options.removeOnDestroy&&this.popper.parentNode.removeChild(this.popper),this}function q(e){var t=e.ownerDocument;return t?t.defaultView:window}function z(e,t,n,o){var r="BODY"===e.nodeName,i=r?e.ownerDocument.defaultView:e;i.addEventListener(t,n,{passive:!0}),r||z(p(i.parentNode),t,n,o),o.push(i)}function G(e,t,n,o){n.updateBound=o,q(e).addEventListener("resize",n.updateBound,{passive:!0});var r=p(e);return z(r,"scroll",n.updateBound,n.scrollParents),n.scrollElement=r,n.eventsEnabled=!0,n}function X(){this.state.eventsEnabled||(this.state=G(this.reference,this.options,this.state,this.scheduleUpdate))}function Z(){var e,t;this.state.eventsEnabled&&(cancelAnimationFrame(this.scheduleUpdate),this.state=(e=this.reference,t=this.state,q(e).removeEventListener("resize",t.updateBound),t.scrollParents.forEach((function(e){e.removeEventListener("scroll",t.updateBound)})),t.updateBound=null,t.scrollParents=[],t.scrollElement=null,t.eventsEnabled=!1,t))}function J(e){return""!==e&&!isNaN(parseFloat(e))&&isFinite(e)}function K(e,t){Object.keys(t).forEach((function(n){var o="";-1!==["width","height","top","right","bottom","left"].indexOf(n)&&J(t[n])&&(o="px"),e.style[n]=t[n]+o}))}var Q=o&&/Firefox/i.test(navigator.userAgent);function $(e,t,n){var o=I(e,(function(e){return e.name===t})),r=!!o&&e.some((function(e){return e.name===n&&e.enabled&&e.order<o.order}));if(!r){var i="`"+t+"`",a="`"+n+"`";console.warn(a+" modifier is required by "+i+" modifier in order to work, be sure to include it before "+i+"!")}return r}var ee=["auto-start","auto","auto-end","top-start","top","top-end","right-start","right","right-end","bottom-end","bottom","bottom-start","left-end","left","left-start"],te=ee.slice(3);function ne(e){var t=arguments.length>1&&void 0!==arguments[1]&&arguments[1],n=te.indexOf(e),o=te.slice(n+1).concat(te.slice(0,n));return t?o.reverse():o}var oe={placement:"bottom",positionFixed:!1,eventsEnabled:!0,removeOnDestroy:!1,onCreate:function(){},onUpdate:function(){},modifiers:{shift:{order:100,enabled:!0,fn:function(e){var t=e.placement,n=t.split("-")[0],o=t.split("-")[1];if(o){var r=e.offsets,i=r.reference,a=r.popper,s=-1!==["bottom","top"].indexOf(n),f=s?"left":"top",p=s?"width":"height",l={start:L({},f,i[f]),end:L({},f,i[f]+i[p]-a[p])};e.offsets.popper=T({},a,l[o])}return e}},offset:{order:200,enabled:!0,fn:function(e,t){var n,o=t.offset,r=e.placement,i=e.offsets,a=i.popper,s=i.reference,f=r.split("-")[0];return n=J(+o)?[+o,0]:function(e,t,n,o){var r=[0,0],i=-1!==["right","left"].indexOf(o),a=e.split(/(\+|\-)/).map((function(e){return e.trim()})),s=a.indexOf(I(a,(function(e){return-1!==e.search(/,|\s/)})));a[s]&&-1===a[s].indexOf(",")&&console.warn("Offsets separated by white space(s) are deprecated, use a comma (,) instead.");var f=/\s*,\s*|\s+/,p=-1!==s?[a.slice(0,s).concat([a[s].split(f)[0]]),[a[s].split(f)[1]].concat(a.slice(s+1))]:[a];return(p=p.map((function(e,o){var r=(1===o?!i:i)?"height":"width",a=!1;return e.reduce((function(e,t){return""===e[e.length-1]&&-1!==["+","-"].indexOf(t)?(e[e.length-1]=t,a=!0,e):a?(e[e.length-1]+=t,a=!1,e):e.concat(t)}),[]).map((function(e){return function(e,t,n,o){var r=e.match(/((?:\-|\+)?\d*\.?\d*)(.*)/),i=+r[1],a=r[2];if(!i)return e;if(0===a.indexOf("%")){var s=void 0;switch(a){case"%p":s=n;break;case"%":case"%r":default:s=o}return D(s)[t]/100*i}return"vh"===a||"vw"===a?("vh"===a?Math.max(document.documentElement.clientHeight,window.innerHeight||0):Math.max(document.documentElement.clientWidth,window.innerWidth||0))/100*i:i}(e,r,t,n)}))}))).forEach((function(e,t){e.forEach((function(n,o){J(n)&&(r[t]+=n*("-"===e[o-1]?-1:1))}))})),r}(o,a,s,f),"left"===f?(a.top+=n[0],a.left-=n[1]):"right"===f?(a.top+=n[0],a.left+=n[1]):"top"===f?(a.left+=n[0],a.top-=n[1]):"bottom"===f&&(a.left+=n[0],a.top+=n[1]),e.popper=a,e},offset:0},preventOverflow:{order:300,enabled:!0,fn:function(e,t){var n=t.boundariesElement||h(e.instance.popper);e.instance.reference===n&&(n=h(n));var o=V("transform"),r=e.instance.popper.style,i=r.top,a=r.left,s=r[o];r.top="",r.left="",r[o]="";var f=H(e.instance.popper,e.instance.reference,t.padding,n,e.positionFixed);r.top=i,r.left=a,r[o]=s,t.boundaries=f;var p=t.priority,l=e.offsets.popper,u={primary:function(e){var n=l[e];return l[e]<f[e]&&!t.escapeWithReference&&(n=Math.max(l[e],f[e])),L({},e,n)},secondary:function(e){var n="right"===e?"left":"top",o=l[n];return l[e]>f[e]&&!t.escapeWithReference&&(o=Math.min(l[n],f[e]-("right"===e?l.width:l.height))),L({},n,o)}};return p.forEach((function(e){var t=-1!==["left","top"].indexOf(e)?"primary":"secondary";l=T({},l,u[t](e))})),e.offsets.popper=l,e},priority:["left","right","top","bottom"],padding:5,boundariesElement:"scrollParent"},keepTogether:{order:400,enabled:!0,fn:function(e){var t=e.offsets,n=t.popper,o=t.reference,r=e.placement.split("-")[0],i=Math.floor,a=-1!==["top","bottom"].indexOf(r),s=a?"right":"bottom",f=a?"left":"top",p=a?"width":"height";return n[s]<i(o[f])&&(e.offsets.popper[f]=i(o[f])-n[p]),n[f]>i(o[s])&&(e.offsets.popper[f]=i(o[s])),e}},arrow:{order:500,enabled:!0,fn:function(e,t){var n;if(!$(e.instance.modifiers,"arrow","keepTogether"))return e;var o=t.element;if("string"==typeof o){if(!(o=e.instance.popper.querySelector(o)))return e}else if(!e.instance.popper.contains(o))return console.warn("WARNING: `arrow.element` must be child of its popper element!"),e;var r=e.placement.split("-")[0],i=e.offsets,a=i.popper,f=i.reference,p=-1!==["left","right"].indexOf(r),l=p?"height":"width",u=p?"Top":"Left",d=u.toLowerCase(),c=p?"left":"top",h=p?"bottom":"right",m=W(o)[l];f[h]-m<a[d]&&(e.offsets.popper[d]-=a[d]-(f[h]-m)),f[d]+m>a[h]&&(e.offsets.popper[d]+=f[d]+m-a[h]),e.offsets.popper=D(e.offsets.popper);var g=f[d]+f[l]/2-m/2,v=s(e.instance.popper),b=parseFloat(v["margin"+u]),w=parseFloat(v["border"+u+"Width"]),y=g-e.offsets.popper[d]-b-w;return y=Math.max(Math.min(a[l]-m,y),0),e.arrowElement=o,e.offsets.arrow=(L(n={},d,Math.round(y)),L(n,c,""),n),e},element:"[x-arrow]"},flip:{order:600,enabled:!0,fn:function(e,t){if(Y(e.instance.modifiers,"inner"))return e;if(e.flipped&&e.placement===e.originalPlacement)return e;var n=H(e.instance.popper,e.instance.reference,t.padding,t.boundariesElement,e.positionFixed),o=e.placement.split("-")[0],r=P(o),i=e.placement.split("-")[1]||"",a=[];switch(t.behavior){case"flip":a=[o,r];break;case"clockwise":a=ne(o);break;case"counterclockwise":a=ne(o,!0);break;default:a=t.behavior}return a.forEach((function(s,f){if(o!==s||a.length===f+1)return e;o=e.placement.split("-")[0],r=P(o);var p=e.offsets.popper,l=e.offsets.reference,u=Math.floor,d="left"===o&&u(p.right)>u(l.left)||"right"===o&&u(p.left)<u(l.right)||"top"===o&&u(p.bottom)>u(l.top)||"bottom"===o&&u(p.top)<u(l.bottom),c=u(p.left)<u(n.left),h=u(p.right)>u(n.right),m=u(p.top)<u(n.top),g=u(p.bottom)>u(n.bottom),v="left"===o&&c||"right"===o&&h||"top"===o&&m||"bottom"===o&&g,b=-1!==["top","bottom"].indexOf(o),w=!!t.flipVariations&&(b&&"start"===i&&c||b&&"end"===i&&h||!b&&"start"===i&&m||!b&&"end"===i&&g),y=!!t.flipVariationsByContent&&(b&&"start"===i&&h||b&&"end"===i&&c||!b&&"start"===i&&g||!b&&"end"===i&&m),x=w||y;(d||v||x)&&(e.flipped=!0,(d||v)&&(o=a[f+1]),x&&(i=function(e){return"end"===e?"start":"start"===e?"end":e}(i)),e.placement=o+(i?"-"+i:""),e.offsets.popper=T({},e.offsets.popper,j(e.instance.popper,e.offsets.reference,e.placement)),e=R(e.instance.modifiers,e,"flip"))})),e},behavior:"flip",padding:5,boundariesElement:"viewport",flipVariations:!1,flipVariationsByContent:!1},inner:{order:700,enabled:!1,fn:function(e){var t=e.placement,n=t.split("-")[0],o=e.offsets,r=o.popper,i=o.reference,a=-1!==["left","right"].indexOf(n),s=-1===["top","left"].indexOf(n);return r[a?"left":"top"]=i[n]-(s?r[a?"width":"height"]:0),e.placement=P(t),e.offsets.popper=D(r),e}},hide:{order:800,enabled:!0,fn:function(e){if(!$(e.instance.modifiers,"hide","preventOverflow"))return e;var t=e.offsets.reference,n=I(e.instance.modifiers,(function(e){return"preventOverflow"===e.name})).boundaries;if(t.bottom<n.top||t.left>n.right||t.top>n.bottom||t.right<n.left){if(!0===e.hide)return e;e.hide=!0,e.attributes["x-out-of-boundaries"]=""}else{if(!1===e.hide)return e;e.hide=!1,e.attributes["x-out-of-boundaries"]=!1}return e}},computeStyle:{order:850,enabled:!0,fn:function(e,t){var n=t.x,o=t.y,r=e.offsets.popper,i=I(e.instance.modifiers,(function(e){return"applyStyle"===e.name})).gpuAcceleration;void 0!==i&&console.warn("WARNING: `gpuAcceleration` option moved to `computeStyle` modifier and will not be supported in future versions of Popper.js!");var a,s,f=void 0!==i?i:t.gpuAcceleration,p=h(e.instance.popper),l=M(p),u={position:r.position},d=function(e,t){var n=e.offsets,o=n.popper,r=n.reference,i=Math.round,a=Math.floor,s=function(e){return e},f=i(r.width),p=i(o.width),l=-1!==["left","right"].indexOf(e.placement),u=-1!==e.placement.indexOf("-"),d=t?l||u||f%2==p%2?i:a:s,c=t?i:s;return{left:d(f%2==1&&p%2==1&&!u&&t?o.left-1:o.left),top:c(o.top),bottom:c(o.bottom),right:d(o.right)}}(e,window.devicePixelRatio<2||!Q),c="bottom"===n?"top":"bottom",m="right"===o?"left":"right",g=V("transform");if(s="bottom"===c?"HTML"===p.nodeName?-p.clientHeight+d.bottom:-l.height+d.bottom:d.top,a="right"===m?"HTML"===p.nodeName?-p.clientWidth+d.right:-l.width+d.right:d.left,f&&g)u[g]="translate3d("+a+"px, "+s+"px, 0)",u[c]=0,u[m]=0,u.willChange="transform";else{var v="bottom"===c?-1:1,b="right"===m?-1:1;u[c]=s*v,u[m]=a*b,u.willChange=c+", "+m}var w={"x-placement":e.placement};return e.attributes=T({},w,e.attributes),e.styles=T({},u,e.styles),e.arrowStyles=T({},e.offsets.arrow,e.arrowStyles),e},gpuAcceleration:!0,x:"bottom",y:"right"},applyStyle:{order:900,enabled:!0,fn:function(e){var t,n;return K(e.instance.popper,e.styles),t=e.instance.popper,n=e.attributes,Object.keys(n).forEach((function(e){!1!==n[e]?t.setAttribute(e,n[e]):t.removeAttribute(e)})),e.arrowElement&&Object.keys(e.arrowStyles).length&&K(e.arrowElement,e.arrowStyles),e},onLoad:function(e,t,n,o,r){var i=S(r,t,e,n.positionFixed),a=A(n.placement,i,t,e,n.modifiers.flip.boundariesElement,n.modifiers.flip.padding);return t.setAttribute("x-placement",a),K(t,{position:n.positionFixed?"fixed":"absolute"}),n},gpuAcceleration:void 0}}},re=function(){function e(t,n){var o=this,r=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{};E(this,e),this.scheduleUpdate=function(){return requestAnimationFrame(o.update)},this.update=i(this.update.bind(this)),this.options=T({},e.Defaults,r),this.state={isDestroyed:!1,isCreated:!1,scrollParents:[]},this.reference=t&&t.jquery?t[0]:t,this.popper=n&&n.jquery?n[0]:n,this.options.modifiers={},Object.keys(T({},e.Defaults.modifiers,r.modifiers)).forEach((function(t){o.options.modifiers[t]=T({},e.Defaults.modifiers[t]||{},r.modifiers?r.modifiers[t]:{})})),this.modifiers=Object.keys(this.options.modifiers).map((function(e){return T({name:e},o.options.modifiers[e])})).sort((function(e,t){return e.order-t.order})),this.modifiers.forEach((function(e){e.enabled&&a(e.onLoad)&&e.onLoad(o.reference,o.popper,o.options,e,o.state)})),this.update();var s=this.options.eventsEnabled;s&&this.enableEventListeners(),this.state.eventsEnabled=s}return O(e,[{key:"update",value:function(){return U.call(this)}},{key:"destroy",value:function(){return _.call(this)}},{key:"enableEventListeners",value:function(){return X.call(this)}},{key:"disableEventListeners",value:function(){return Z.call(this)}}]),e}();re.Utils=("undefined"!=typeof window?window:n.g).PopperUtils,re.placements=ee,re.Defaults=oe;const ie=re}}]);