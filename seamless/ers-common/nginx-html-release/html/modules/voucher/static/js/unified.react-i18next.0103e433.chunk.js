(self.webpackChunk_unified_voucher=self.webpackChunk_unified_voucher||[]).push([[9213],{74211:(e,n,t)=>{"use strict";function r(e,n){if(null==e)return{};var t,r,o=function(e,n){if(null==e)return{};var t,r,o={},i=Object.keys(e);for(r=0;r<i.length;r++)t=i[r],n.indexOf(t)>=0||(o[t]=e[t]);return o}(e,n);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(r=0;r<i.length;r++)t=i[r],n.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(o[t]=e[t])}return o}function o(e){return(o="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function i(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}t.r(n),t.d(n,{I18nContext:()=>b,I18nextProvider:()=>W,Trans:()=>z,Translation:()=>$,composeInitialProps:()=>w,getDefaults:()=>h,getI18n:()=>O,getInitialProps:()=>P,initReactI18next:()=>j,setDefaults:()=>g,setI18n:()=>v,useSSR:()=>Y,useTranslation:()=>F,withSSR:()=>G,withTranslation:()=>_});var a=t(76982),c=t.n(a),s=t(12897),u=t.n(s);function l(e,n){for(var t=0;t<n.length;t++){var r=n[t];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function f(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);n&&(r=r.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,r)}return t}function p(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?f(Object(t),!0).forEach((function(n){i(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):f(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}var d,y={bindI18n:"languageChanged",bindI18nStore:"",transEmptyNodeValue:"",transSupportBasicHtmlNodes:!0,transKeepBasicHtmlNodesFor:["br","strong","i","p"],useSuspense:!0},b=c().createContext();function g(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};y=p(p({},y),e)}function h(){return y}var m=function(){function e(){!function(e,n){if(!(e instanceof n))throw new TypeError("Cannot call a class as a function")}(this,e),this.usedNamespaces={}}var n,t;return n=e,(t=[{key:"addUsedNamespaces",value:function(e){var n=this;e.forEach((function(e){n.usedNamespaces[e]||(n.usedNamespaces[e]=!0)}))}},{key:"getUsedNamespaces",value:function(){return Object.keys(this.usedNamespaces)}}])&&l(n.prototype,t),e}();function v(e){d=e}function O(){return d}var j={type:"3rdParty",init:function(e){g(e.options.react),v(e)}};function w(e){return function(n){return new Promise((function(t){var r=P();e.getInitialProps?e.getInitialProps(n).then((function(e){t(p(p({},e),r))})):t(r)}))}}function P(){var e=O(),n=e.reportNamespaces?e.reportNamespaces.getUsedNamespaces():[],t={},r={};return e.languages.forEach((function(t){r[t]={},n.forEach((function(n){r[t][n]=e.getResourceBundle(t,n)||{}}))})),t.initialI18nStore=r,t.initialLanguage=e.language,t}function S(){if(console&&console.warn){for(var e,n=arguments.length,t=new Array(n),r=0;r<n;r++)t[r]=arguments[r];"string"==typeof t[0]&&(t[0]="react-i18next:: ".concat(t[0])),(e=console).warn.apply(e,t)}}var E={};function k(){for(var e=arguments.length,n=new Array(e),t=0;t<e;t++)n[t]=arguments[t];"string"==typeof n[0]&&E[n[0]]||("string"==typeof n[0]&&(E[n[0]]=new Date),S.apply(void 0,n))}function N(e,n,t){e.loadNamespaces(n,(function(){e.isInitialized?t():e.on("initialized",(function n(){setTimeout((function(){e.off("initialized",n)}),0),t()}))}))}function I(e,n){var t=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{};if(!n.languages||!n.languages.length)return k("i18n.languages were undefined or empty",n.languages),!0;var r=n.languages[0],o=!!n.options&&n.options.fallbackLng,i=n.languages[n.languages.length-1];if("cimode"===r.toLowerCase())return!0;var a=function(e,t){var r=n.services.backendConnector.state["".concat(e,"|").concat(t)];return-1===r||2===r};return!(t.bindI18n&&t.bindI18n.indexOf("languageChanging")>-1&&n.services.backendConnector.backend&&n.isLanguageChangingTo&&!a(n.isLanguageChangingTo,e)||!n.hasResourceBundle(r,e)&&n.services.backendConnector.backend&&(!a(r,e)||o&&!a(i,e)))}function x(e){return e.displayName||e.name||("string"==typeof e&&e.length>0?e:"Unknown")}function D(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);n&&(r=r.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,r)}return t}function C(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?D(Object(t),!0).forEach((function(n){i(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):D(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function R(e,n){if(!e)return!1;var t=e.props?e.props.children:e.children;return n?t.length>0:!!t}function T(e){return e?e&&e.children?e.children:e.props&&e.props.children:[]}function A(e){return Array.isArray(e)?e:[e]}function L(e,n){if(!e)return"";var t="",i=A(e),a=n.transKeepBasicHtmlNodesFor||[];return i.forEach((function(e,i){if("string"==typeof e)t+="".concat(e);else if(c().isValidElement(e)){var s=Object.keys(e.props).length,u=a.indexOf(e.type)>-1,l=e.props.children;if(!l&&u&&0===s)t+="<".concat(e.type,"/>");else if(l||u&&0===s)if(e.props.i18nIsDynamicList)t+="<".concat(i,"></").concat(i,">");else if(u&&1===s&&"string"==typeof l)t+="<".concat(e.type,">").concat(l,"</").concat(e.type,">");else{var f=L(l,n);t+="<".concat(i,">").concat(f,"</").concat(i,">")}else t+="<".concat(i,"></").concat(i,">")}else if("object"===o(e)){var p=e.format,d=r(e,["format"]),y=Object.keys(d);if(1===y.length){var b=p?"".concat(y[0],", ").concat(p):y[0];t+="{{".concat(b,"}}")}else S("react-i18next: the passed in object contained more than one variable - the object should look like {{ value, format }} where format is optional.",e)}else S("Trans: the passed in value is invalid - seems you passed in a variable like {number} - please pass in variables for interpolation as full objects like {{number}}.",e)})),t}function z(e){var n=e.children,t=e.count,i=e.parent,s=e.i18nKey,l=e.tOptions,f=void 0===l?{}:l,p=e.values,d=e.defaults,y=e.components,g=e.ns,m=e.i18n,v=e.t,j=r(e,["children","count","parent","i18nKey","tOptions","values","defaults","components","ns","i18n","t"]),w=(0,a.useContext)(b)||{},P=w.i18n,S=w.defaultNS,E=m||P||O();if(!E)return k("You will need to pass in an i18next instance by using i18nextReactModule"),n;var N=v||E.t.bind(E)||function(e){return e},I=C(C({},h()),E.options&&E.options.react),x=g||N.ns||S||E.options&&E.options.defaultNS;x="string"==typeof x?[x]:x||["translation"];var D=d||L(n,I)||I.transEmptyNodeValue||s,z=I.hashTransKey,B=s||(z?z(D):D),U=p?f.interpolation:{interpolation:C(C({},f.interpolation),{},{prefix:"#$?",suffix:"?$#"})},V=C(C(C(C({},f),{},{count:t},p),U),{},{defaultValue:D,ns:x}),K=function(e,n,t,r,i){if(""===n)return[];var a=r.transKeepBasicHtmlNodesFor||[],s=n&&new RegExp(a.join("|")).test(n);if(!e&&!s)return[n];var l={};!function e(n){A(n).forEach((function(n){"string"!=typeof n&&(R(n)?e(T(n)):"object"!==o(n)||c().isValidElement(n)||Object.assign(l,n))}))}(e);var f=t.services.interpolator.interpolate(n,C(C({},l),i),t.language),p=u().parse("<0>".concat(f,"</0>"));function d(e,n,t){var r=T(e),o=b(r,n.children,t);return function(e){return"[object Array]"===Object.prototype.toString.call(e)&&e.every((function(e){return c().isValidElement(e)}))}(r)&&0===o.length?r:o}function y(e,n,t,r,o){e.dummy&&(e.children=n),t.push(c().cloneElement(e,C(C({},e.props),{},{key:r}),o?void 0:n))}function b(n,t,i){var u=A(n);return A(t).reduce((function(n,t,l){var f,p,g,h=t.children&&t.children[0]&&t.children[0].content;if("tag"===t.type){var m=u[parseInt(t.name,10)];!m&&1===i.length&&i[0][t.name]&&(m=i[0][t.name]),m||(m={});var v=0!==Object.keys(t.attrs).length?(f={props:t.attrs},(g=C({},p=m)).props=Object.assign(f.props,p.props),g):m,O=c().isValidElement(v),j=O&&R(t,!0)&&!t.voidElement,w=s&&"object"===o(v)&&v.dummy&&!O,P="object"===o(e)&&null!==e&&Object.hasOwnProperty.call(e,t.name);if("string"==typeof v)n.push(v);else if(R(v)||j)y(v,d(v,t,i),n,l);else if(w){var S=b(u,t.children,i);n.push(c().cloneElement(v,C(C({},v.props),{},{key:l}),S))}else if(Number.isNaN(parseFloat(t.name)))if(P)y(v,d(v,t,i),n,l,t.voidElement);else if(r.transSupportBasicHtmlNodes&&a.indexOf(t.name)>-1)if(t.voidElement)n.push(c().createElement(t.name,{key:"".concat(t.name,"-").concat(l)}));else{var E=b(u,t.children,i);n.push(c().createElement(t.name,{key:"".concat(t.name,"-").concat(l)},E))}else if(t.voidElement)n.push("<".concat(t.name," />"));else{var k=b(u,t.children,i);n.push("<".concat(t.name,">").concat(k,"</").concat(t.name,">"))}else if("object"!==o(v)||O)1===t.children.length&&h?n.push(c().cloneElement(v,C(C({},v.props),{},{key:l}),h)):n.push(c().cloneElement(v,C(C({},v.props),{},{key:l})));else{var N=t.children[0]?h:null;N&&n.push(N)}}else"text"===t.type&&n.push(t.content);return n}),[])}return T(b([{dummy:!0,children:e}],p,A(e||[]))[0])}(y||n,B?N(B,V):D,E,I,V),F=void 0!==i?i:I.defaultTransParent;return F?c().createElement(F,j,K):K}function B(e,n){(null==n||n>e.length)&&(n=e.length);for(var t=0,r=new Array(n);t<n;t++)r[t]=e[t];return r}function U(e,n){return function(e){if(Array.isArray(e))return e}(e)||function(e,n){if("undefined"!=typeof Symbol&&Symbol.iterator in Object(e)){var t=[],r=!0,o=!1,i=void 0;try{for(var a,c=e[Symbol.iterator]();!(r=(a=c.next()).done)&&(t.push(a.value),!n||t.length!==n);r=!0);}catch(e){o=!0,i=e}finally{try{r||null==c.return||c.return()}finally{if(o)throw i}}return t}}(e,n)||function(e,n){if(e){if("string"==typeof e)return B(e,n);var t=Object.prototype.toString.call(e).slice(8,-1);return"Object"===t&&e.constructor&&(t=e.constructor.name),"Map"===t||"Set"===t?Array.from(e):"Arguments"===t||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(t)?B(e,n):void 0}}(e,n)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function V(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);n&&(r=r.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,r)}return t}function K(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?V(Object(t),!0).forEach((function(n){i(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):V(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function F(e){var n=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{},t=n.i18n,r=(0,a.useContext)(b)||{},o=r.i18n,i=r.defaultNS,c=t||o||O();if(c&&!c.reportNamespaces&&(c.reportNamespaces=new m),!c){k("You will need to pass in an i18next instance by using initReactI18next");var s=function(e){return Array.isArray(e)?e[e.length-1]:e},u=[s,{},!1];return u.t=s,u.i18n={},u.ready=!1,u}c.options.react&&void 0!==c.options.react.wait&&k("It seems you are still using the old wait option, you may migrate to the new useSuspense behaviour.");var l=K(K(K({},h()),c.options.react),n),f=l.useSuspense,p=e||i||c.options&&c.options.defaultNS;p="string"==typeof p?[p]:p||["translation"],c.reportNamespaces.addUsedNamespaces&&c.reportNamespaces.addUsedNamespaces(p);var d=(c.isInitialized||c.initializedStoreOnce)&&p.every((function(e){return I(e,c,l)}));function y(){return{t:c.getFixedT(null,"fallback"===l.nsMode?p:p[0])}}var g=(0,a.useState)(y()),v=U(g,2),j=v[0],w=v[1],P=(0,a.useRef)(!0);(0,a.useEffect)((function(){var e=l.bindI18n,n=l.bindI18nStore;function t(){P.current&&w(y())}return P.current=!0,d||f||N(c,p,(function(){P.current&&w(y())})),e&&c&&c.on(e,t),n&&c&&c.store.on(n,t),function(){P.current=!1,e&&c&&e.split(" ").forEach((function(e){return c.off(e,t)})),n&&c&&n.split(" ").forEach((function(e){return c.store.off(e,t)}))}}),[c,p.join()]);var S=(0,a.useRef)(!0);(0,a.useEffect)((function(){P.current&&!S.current&&w(y()),S.current=!1}),[c]);var E=[j.t,c,d];if(E.t=j.t,E.i18n=c,E.ready=d,d)return E;if(!d&&!f)return E;throw new Promise((function(e){N(c,p,(function(){e()}))}))}function H(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);n&&(r=r.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,r)}return t}function M(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?H(Object(t),!0).forEach((function(n){i(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):H(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function _(e){var n=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};return function(t){function o(o){var i=o.forwardedRef,a=r(o,["forwardedRef"]),s=U(F(e,a),3),u=s[0],l=s[1],f=s[2],p=M(M({},a),{},{t:u,i18n:l,tReady:f});return n.withRef&&i?p.ref=i:!n.withRef&&i&&(p.forwardedRef=i),c().createElement(t,p)}return o.displayName="withI18nextTranslation(".concat(x(t),")"),o.WrappedComponent=t,n.withRef?c().forwardRef((function(e,n){return c().createElement(o,Object.assign({},e,{forwardedRef:n}))})):o}}function $(e){var n=e.ns,t=e.children,o=U(F(n,r(e,["ns","children"])),3),i=o[0],a=o[1],c=o[2];return t(i,{i18n:a,lng:a.language},c)}function W(e){var n=e.i18n,t=e.defaultNS,r=e.children,o=(0,a.useMemo)((function(){return{i18n:n,defaultNS:t}}),[n,t]);return(0,a.createElement)(b.Provider,{value:o},r)}function Y(e,n){var t=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{},r=t.i18n,o=(0,a.useContext)(b)||{},i=o.i18n,c=r||i||O();c.options&&c.options.isClone||(e&&!c.initializedStoreOnce&&(c.services.resourceStore.data=e,c.options.ns=Object.values(e).reduce((function(e,n){return Object.keys(n).forEach((function(n){e.indexOf(n)<0&&e.push(n)})),e}),c.options.ns),c.initializedStoreOnce=!0,c.isInitialized=!0),n&&!c.initializedLanguageOnce&&(c.changeLanguage(n),c.initializedLanguageOnce=!0))}function q(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);n&&(r=r.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,r)}return t}function G(){return function(e){function n(n){var t=n.initialI18nStore,o=n.initialLanguage,a=r(n,["initialI18nStore","initialLanguage"]);return Y(t,o),c().createElement(e,function(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?q(Object(t),!0).forEach((function(n){i(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):q(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}({},a))}return n.getInitialProps=w(e),n.displayName="withI18nextSSR(".concat(x(e),")"),n.WrappedComponent=e,n}}}}]);