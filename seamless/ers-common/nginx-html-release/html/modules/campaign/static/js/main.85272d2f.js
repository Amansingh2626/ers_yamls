(()=>{"use strict";var e,r,t,n,a,i={33216:(e,r,t)=>{var n=t(2411),a=t(43006),i=t.n(a),l=t(48049),o=t(79692),u=t(12420),s=t(67358),d=t(27556),c=t(44104),f=t.n(c),p=t(34579),h=t(7643),m=t(56760),v=(0,o.Z)((function(e){return(0,u.Z)({root:{padding:e.spacing(1)}})}));const g=function(){var e=v(),r=(0,l.useRouteMatch)().path,t=f()().AccessControllProvider,a=(0,p.Z)("CAMPAIGN");return a?n.createElement(t,{featureList:a},n.createElement(d.Z,{elevation:0,className:e.root},n.createElement(l.Switch,null,n.createElement(l.Route,{path:r+"/create"},n.createElement(h.sA,null)),n.createElement(l.Route,{path:r+"/campaigns"},n.createElement(h.Ir,null)),n.createElement(l.Route,{path:r+"/view/:id"},n.createElement(h.vu,null)),n.createElement(l.Route,{path:r+"/edit/:id"},n.createElement(h.Fy,null)),n.createElement(l.Route,{path:r+"/pending-approvals"},n.createElement(h.Mo,null)),n.createElement(l.Route,{path:r+"/adjustment/commission"},n.createElement(m.Z,null)),n.createElement(l.Route,{path:r+"/"},n.createElement(h.Ir,null))))):n.createElement(s.Z,null)};i().render(n.createElement(l.BrowserRouter,null,n.createElement(g,null)),document.getElementById("root"))},71022:(e,r,t)=>{var n=new Error;e.exports=new Promise(((e,r)=>{if("undefined"!=typeof portal)return e();t.l("/remoteEntry.js",(t=>{if("undefined"!=typeof portal)return e();var a=t&&("load"===t.type?"missing":t.type),i=t&&t.target&&t.target.src;n.message="Loading script failed.\n("+a+": "+i+")",n.name="ScriptExternalLoadError",n.type=a,n.request=i,r(n)}),"portal")})).then((()=>portal))},53612:(e,r,t)=>{var n=new Error;e.exports=new Promise(((e,r)=>{if("undefined"!=typeof reseller)return e();t.l("/modules/reseller//remoteEntry.js",(t=>{if("undefined"!=typeof reseller)return e();var a=t&&("load"===t.type?"missing":t.type),i=t&&t.target&&t.target.src;n.message="Loading script failed.\n("+a+": "+i+")",n.name="ScriptExternalLoadError",n.type=a,n.request=i,r(n)}),"reseller")})).then((()=>reseller))}},l={};function o(e){var r=l[e];if(void 0!==r)return r.exports;var t=l[e]={id:e,loaded:!1,exports:{}};return i[e].call(t.exports,t,t.exports,o),t.loaded=!0,t.exports}o.m=i,o.c=l,e=[],o.O=(r,t,n,a)=>{if(!t){var i=1/0;for(s=0;s<e.length;s++){for(var[t,n,a]=e[s],l=!0,u=0;u<t.length;u++)(!1&a||i>=a)&&Object.keys(o.O).every((e=>o.O[e](t[u])))?t.splice(u--,1):(l=!1,a<i&&(i=a));l&&(e.splice(s--,1),r=n())}return r}a=a||0;for(var s=e.length;s>0&&e[s-1][2]>a;s--)e[s]=e[s-1];e[s]=[t,n,a]},o.n=e=>{var r=e&&e.__esModule?()=>e.default:()=>e;return o.d(r,{a:r}),r},o.d=(e,r)=>{for(var t in r)o.o(r,t)&&!o.o(e,t)&&Object.defineProperty(e,t,{enumerable:!0,get:r[t]})},o.f={},o.e=e=>Promise.all(Object.keys(o.f).reduce(((r,t)=>(o.f[t](e,r),r)),[])),o.u=e=>"static/js/"+({371:"unified.path-to-regexp",388:"unified.hyphenate-style-name",393:"unified.history",1151:"unified.value-equal",1858:"unified.html-parse-stringify2",2153:"unified.scheduler",2544:"unified.jss-plugin-camel-case",2658:"unified.object-assign",3206:"unified.mini-create-react-context",3251:"unified.react-redux",3627:"unified.jss-plugin-rule-value-function",3687:"unified.css-vendor",4486:"unified.react-router",4934:"unified.react",4940:"unified.tiny-invariant",5491:"unified.react-router-dom",5543:"unified.notistack",6203:"unified.is-in-browser",6522:"unified.jss",6673:"unified.react-dom",6743:"unified.jss-plugin-default-unit",6990:"unified.jss-plugin-props-sort",7193:"unified.symbol-observable",7494:"unified.redux",7507:"unified.jss-plugin-vendor-prefixer",7692:"unified.resolve-pathname",8019:"unified.void-elements",9096:"unified.jss-plugin-global",9213:"unified.react-i18next",9884:"unified.jss-plugin-nested"}[e]||e)+"."+{371:"2e947638",388:"bb3506aa",393:"23aa9cbe",681:"ba64ba25",1151:"19bdfa2c",1858:"061b0fcc",2153:"b8e82beb",2544:"b561b37d",2658:"301c22b9",3206:"2fb9159a",3251:"e2de757b",3627:"8d520393",3687:"fcdd5908",4486:"f76e8f30",4934:"be465944",4940:"9edd3ecb",5491:"25d32c0c",5543:"9c45ce28",6005:"a9a5bd6c",6203:"21827a90",6522:"44c66d08",6558:"32bbfb06",6673:"b25e4290",6743:"71953bd0",6990:"3550b4f1",7193:"151c6271",7494:"c708662d",7507:"4a0e6ec4",7692:"ddad6c0f",8019:"07524b0a",9096:"5864dd4a",9213:"eee14cb9",9884:"13362c0b"}[e]+".chunk.js",o.g=function(){if("object"==typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"==typeof window)return window}}(),o.hmd=e=>((e=Object.create(e)).children||(e.children=[]),Object.defineProperty(e,"exports",{enumerable:!0,set:()=>{throw new Error("ES Modules may not assign module.exports or exports.*, Use ESM export syntax, instead: "+e.id)}}),e),o.o=(e,r)=>Object.prototype.hasOwnProperty.call(e,r),r={},t="@unified/campaign:",o.l=(e,n,a,i)=>{if(r[e])r[e].push(n);else{var l,u;if(void 0!==a)for(var s=document.getElementsByTagName("script"),d=0;d<s.length;d++){var c=s[d];if(c.getAttribute("src")==e||c.getAttribute("data-webpack")==t+a){l=c;break}}l||(u=!0,(l=document.createElement("script")).charset="utf-8",l.timeout=120,o.nc&&l.setAttribute("nonce",o.nc),l.setAttribute("data-webpack",t+a),l.src=e),r[e]=[n];var f=(t,n)=>{l.onerror=l.onload=null,clearTimeout(p);var a=r[e];if(delete r[e],l.parentNode&&l.parentNode.removeChild(l),a&&a.forEach((e=>e(n))),t)return t(n)},p=setTimeout(f.bind(null,void 0,{type:"timeout",target:l}),12e4);l.onerror=f.bind(null,l.onerror),l.onload=f.bind(null,l.onload),u&&document.head.appendChild(l)}},o.r=e=>{"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},o.nmd=e=>(e.paths=[],e.children||(e.children=[]),e),n={},a={},o.f.remotes=(e,r)=>{o.o(n,e)&&n[e].forEach((e=>{var t=o.R;t||(t=[]);var n=a[e];if(!(t.indexOf(n)>=0)){if(t.push(n),n.p)return r.push(n.p);var l=r=>{r||(r=new Error("Container missing")),"string"==typeof r.message&&(r.message+='\nwhile loading "'+n[1]+'" from '+n[2]),i[e]=()=>{throw r},n.p=0},u=(e,t,a,i,o,u)=>{try{var s=e(t,a);if(!s||!s.then)return o(s,i,u);var d=s.then((e=>o(e,i)),l);if(!u)return d;r.push(n.p=d)}catch(e){l(e)}},s=(e,r,a)=>u(r.get,n[1],t,0,d,a),d=r=>{n.p=1,i[e]=e=>{e.exports=r()}};u(o,n[2],0,0,((e,r,t)=>e?u(o.I,n[0],0,e,s,t):l()),1)}}))},(()=>{o.S={};var e={},r={};o.I=(t,n)=>{n||(n=[]);var a=r[t];if(a||(a=r[t]={}),!(n.indexOf(a)>=0)){if(n.push(a),e[t])return e[t];o.o(o.S,t)||(o.S[t]={});var i=o.S[t],l="@unified/campaign",u=(e,r,t,n)=>{var a=i[e]=i[e]||{},o=a[r];(!o||!o.loaded&&(!n!=!o.eager?n:l>o.from))&&(a[r]={get:t,from:l,eager:!!n})},s=e=>{var r=e=>{return r="Initialization of sharing external failed: "+e,"undefined"!=typeof console&&console.warn&&console.warn(r);var r};try{var a=o(e);if(!a)return;var i=e=>e&&e.init&&e.init(o.S[t],n);if(a.then)return d.push(a.then(i,r));var l=i(a);if(l&&l.then)return d.push(l.catch(r))}catch(e){r(e)}},d=[];switch(t){case"default":u("@material-ui/styles","4.11.3",(()=>Promise.all([o.e(7717),o.e(1048),o.e(8171),o.e(4451),o.e(6522),o.e(3687),o.e(6743),o.e(9096),o.e(9884),o.e(3627),o.e(7507),o.e(2544),o.e(6990),o.e(6203),o.e(388),o.e(9037)]).then((()=>()=>o(95949))))),u("notistack","1.0.5",(()=>Promise.all([o.e(7717),o.e(1048),o.e(8171),o.e(4451),o.e(7468),o.e(5543),o.e(9037),o.e(4017)]).then((()=>()=>o(87442))))),u("react-dom","17.0.1",(()=>Promise.all([o.e(2658),o.e(2153),o.e(6673),o.e(6005)]).then((()=>()=>o(28316))))),u("react-i18next","11.8.8",(()=>Promise.all([o.e(9213),o.e(1858),o.e(8019),o.e(6982)]).then((()=>()=>o(56211))))),u("react-redux","7.2.2",(()=>Promise.all([o.e(7717),o.e(1048),o.e(3251),o.e(6558)]).then((()=>()=>o(21725))))),u("react-router-dom","5.2.0",(()=>Promise.all([o.e(7717),o.e(1048),o.e(4486),o.e(393),o.e(5491),o.e(371),o.e(3206),o.e(7692),o.e(1151),o.e(4940),o.e(681)]).then((()=>()=>o(95074))))),u("react","17.0.1",(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),u("redux","4.0.5",(()=>Promise.all([o.e(7193),o.e(7494)]).then((()=>()=>o(18717))))),s(71022),s(53612)}return d.length?e[t]=Promise.all(d).then((()=>e[t]=1)):e[t]=1}}})(),(()=>{var e;o.g.importScripts&&(e=o.g.location+"");var r=o.g.document;if(!e&&r&&(r.currentScript&&(e=r.currentScript.src),!e)){var t=r.getElementsByTagName("script");t.length&&(e=t[t.length-1].src)}if(!e)throw new Error("Automatic publicPath is not supported in this browser");e=e.replace(/#.*$/,"").replace(/\?.*$/,"").replace(/\/[^\/]+$/,"/"),o.p=e+"../../"})(),(()=>{var e=e=>{var r=e=>e.split(".").map((e=>+e==e?+e:e)),t=/^([^-+]+)?(?:-([^+]+))?(?:\+(.+))?$/.exec(e),n=t[1]?r(t[1]):[];return t[2]&&(n.length++,n.push.apply(n,r(t[2]))),t[3]&&(n.push([]),n.push.apply(n,r(t[3]))),n},r=(r,t)=>{r=e(r),t=e(t);for(var n=0;;){if(n>=r.length)return n<t.length&&"u"!=(typeof t[n])[0];var a=r[n],i=(typeof a)[0];if(n>=t.length)return"u"==i;var l=t[n],o=(typeof l)[0];if(i!=o)return"o"==i&&"n"==o||"s"==o||"u"==i;if("o"!=i&&"u"!=i&&a!=l)return a<l;n++}},t=e=>{var r=e[0],n="";if(1===e.length)return"*";if(r+.5){n+=0==r?">=":-1==r?"<":1==r?"^":2==r?"~":r>0?"=":"!=";for(var a=1,i=1;i<e.length;i++)a--,n+="u"==(typeof(o=e[i]))[0]?"-":(a>0?".":"")+(a=2,o);return n}var l=[];for(i=1;i<e.length;i++){var o=e[i];l.push(0===o?"not("+u()+")":1===o?"("+u()+" || "+u()+")":2===o?l.pop()+" "+l.pop():t(o))}return u();function u(){return l.pop().replace(/^\((.+)\)$/,"$1")}},n=(r,t)=>{if(0 in r){t=e(t);var a=r[0],i=a<0;i&&(a=-a-1);for(var l=0,o=1,u=!0;;o++,l++){var s,d,c=o<r.length?(typeof r[o])[0]:"";if(l>=t.length||"o"==(d=(typeof(s=t[l]))[0]))return!u||("u"==c?o>a&&!i:""==c!=i);if("u"==d){if(!u||"u"!=c)return!1}else if(u)if(c==d)if(o<=a){if(s!=r[o])return!1}else{if(i?s>r[o]:s<r[o])return!1;s!=r[o]&&(u=!1)}else if("s"!=c&&"n"!=c){if(i||o<=a)return!1;u=!1,o--}else{if(o<=a||d<c!=i)return!1;u=!1}else"s"!=c&&"n"!=c&&(u=!1,o--)}}var f=[],p=f.pop.bind(f);for(l=1;l<r.length;l++){var h=r[l];f.push(1==h?p()|p():2==h?p()&p():h?n(h,t):!p())}return!!p()},a=(e,a,l,o)=>{var u=((e,t)=>{var n=e[t];return Object.keys(n).reduce(((e,t)=>!e||!n[e].loaded&&r(e,t)?t:e),0)})(e,l);return n(o,u)||"undefined"!=typeof console&&console.warn&&console.warn(((e,r,n)=>"Unsatisfied version "+r+" of shared singleton module "+e+" (required "+t(n)+")")(l,u,o)),i(e[l][u])},i=e=>(e.loaded=1,e.get()),l=e=>function(r,t,n,a){var i=o.I(r);return i&&i.then?i.then(e.bind(e,r,o.S[r],t,n,a)):e(r,o.S[r],t,n,a)},u=l(((e,t,n,a)=>t&&o.o(t,n)?i(((e,t)=>{var n=e[t];return(t=Object.keys(n).reduce(((e,t)=>!e||r(e,t)?t:e),0))&&n[t]})(t,n)):a())),s=l(((e,r,t,n,i)=>r&&o.o(r,t)?a(r,0,t,n):i())),d={},c={76005:()=>s("default","react",[4,17,0,1],(()=>o.e(4934).then((()=>()=>o(2784))))),12801:()=>s("default","react",[,[1,17,0,0],[1,16,0,0],[1,15,0,0],[2,0,14,0],1,1,1],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),58172:()=>s("default","react",[0,15],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),10529:()=>s("default","react-dom",[1,16,14,0],(()=>Promise.all([o.e(2658),o.e(2153),o.e(6673),o.e(6005)]).then((()=>()=>o(28316))))),82883:()=>s("default","redux",[,[1,4,0,0,,0],[1,3,0,0],[1,2,0,0],1,1],(()=>Promise.all([o.e(7193),o.e(7494)]).then((()=>()=>o(18717))))),93304:()=>s("default","react",[,[1,17],[1,16,8,3],1],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),59037:()=>s("default","react",[,[1,17,0,0],[1,16,8,0],1],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),12196:()=>s("default","react",[0,16,6,0],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),54489:()=>s("default","react-dom",[0,16,6,0],(()=>Promise.all([o.e(2658),o.e(2153),o.e(6673),o.e(6005)]).then((()=>()=>o(28316))))),75696:()=>s("default","@material-ui/styles",[1,4,11,3],(()=>Promise.all([o.e(8171),o.e(6522),o.e(3687),o.e(6743),o.e(9096),o.e(9884),o.e(3627),o.e(7507),o.e(2544),o.e(6990),o.e(6203),o.e(388)]).then((()=>()=>o(95949))))),88576:()=>s("default","react-dom",[,[1,17,0,0],[1,16,8,0],1],(()=>Promise.all([o.e(2658),o.e(2153),o.e(6673),o.e(6005)]).then((()=>()=>o(28316))))),92238:()=>s("default","react",[,[4,16,8,0],[0],2],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),840:()=>s("default","react",[0,16,8,0],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),2411:()=>s("default","react",[1,17,0,1],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),13248:()=>s("default","notistack",[1,1,0,4],(()=>o.e(5543).then((()=>()=>o(87442))))),19721:()=>s("default","notistack",[1,1,0,3],(()=>o.e(5543).then((()=>()=>o(87442))))),29609:()=>s("default","react-i18next",[1,11,8,8],(()=>Promise.all([o.e(9213),o.e(1858),o.e(8019)]).then((()=>()=>o(56211))))),38670:()=>s("default","react-redux",[1,7,2,2],(()=>Promise.all([o.e(3251),o.e(6558)]).then((()=>()=>o(21725))))),44808:()=>u("default","@material-ui/styles",(()=>Promise.all([o.e(8171),o.e(6522),o.e(3687),o.e(6743),o.e(9096),o.e(9884),o.e(3627),o.e(7507),o.e(2544),o.e(6990),o.e(6203),o.e(388)]).then((()=>()=>o(95949))))),48049:()=>s("default","react-router-dom",[1,5,2,0],(()=>Promise.all([o.e(4486),o.e(393),o.e(5491),o.e(371),o.e(3206),o.e(7692),o.e(1151),o.e(4940),o.e(681)]).then((()=>()=>o(95074))))),48565:()=>s("default","react",[1,16,8,0],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),88901:()=>s("default","react",[1,17,0,0],(()=>Promise.all([o.e(4934),o.e(2658)]).then((()=>()=>o(2784))))),43006:()=>s("default","react-dom",[1,17,0,1],(()=>Promise.all([o.e(2658),o.e(2153),o.e(6673),o.e(6005)]).then((()=>()=>o(28316)))))};[59037,12196,54489,75696,88576,92238,840,2411,13248,19721,29609,38670,44808,48049,48565,88901,43006].forEach((e=>{o.m[e]=r=>{d[e]=0,delete o.c[e];var t=c[e]();if("function"!=typeof t)throw new Error("Shared module is not available for eager consumption: "+e);r.exports=t()}}));var f={681:[12801,58172],6005:[76005],6558:[10529,82883,93304]};o.f.consumes=(e,r)=>{o.o(f,e)&&f[e].forEach((e=>{if(o.o(d,e))return r.push(d[e]);var t=r=>{d[e]=0,o.m[e]=t=>{delete o.c[e],t.exports=r()}},n=r=>{delete d[e],o.m[e]=t=>{throw delete o.c[e],r}};try{var a=c[e]();a.then?r.push(d[e]=a.then(t).catch(n)):t(a)}catch(e){n(e)}}))}})(),(()=>{var e={179:0,9037:0,4017:0,6982:0,5939:0};o.f.j=(r,t)=>{var n=o.o(e,r)?e[r]:void 0;if(0!==n)if(n)t.push(n[2]);else if(/^(6(005|558|81|982)|4017|5939|9037)$/.test(r))e[r]=0;else{var a=new Promise(((t,a)=>n=e[r]=[t,a]));t.push(n[2]=a);var i=o.p+o.u(r),l=new Error;o.l(i,(t=>{if(o.o(e,r)&&(0!==(n=e[r])&&(e[r]=void 0),n)){var a=t&&("load"===t.type?"missing":t.type),i=t&&t.target&&t.target.src;l.message="Loading chunk "+r+" failed.\n("+a+": "+i+")",l.name="ChunkLoadError",l.type=a,l.request=i,n[1](l)}}),"chunk-"+r,r)}},o.O.j=r=>0===e[r];var r=(r,t)=>{var n,a,[i,l,u]=t,s=0;for(n in l)o.o(l,n)&&(o.m[n]=l[n]);if(u)var d=u(o);for(r&&r(t);s<i.length;s++)a=i[s],o.o(e,a)&&e[a]&&e[a][0](),e[i[s]]=0;return o.O(d)},t=self.webpackChunk_unified_campaign=self.webpackChunk_unified_campaign||[];t.forEach(r.bind(null,0)),t.push=r.bind(null,t.push.bind(t))})();var u=o.O(void 0,[7717,1048,8171,4451,7468,1174,238,4120,2861,7308,6813,1297,5189,4653,4951,7131,6529,9827,7220,3590,9343,6832,422,597,7847,9130,1554,633,7254,9037,4017,6982,8055,5939],(()=>o(33216)));u=o.O(u)})();