(()=>{"use strict";var e,r,t,n,a,i={3216:(e,r,t)=>{var n=t(2411),a=t(3006),i=t.n(a),o=t(8049),l=t(9692),u=t(2420),s=t(7358),d=t(7556),f=t(9294),c=t(4104),h=t.n(c),p=t(4579),m=(0,l.Z)((function(e){return(0,u.Z)({root:{padding:e.spacing(1)}})}));const v=function(){var e=m(),r=(0,o.useRouteMatch)().path,t=h()().AccessControllProvider,a=(0,p.Z)("REGION");return a?n.createElement(t,{featureList:a},n.createElement(d.Z,{elevation:0,className:e.root},n.createElement(o.Switch,null,n.createElement(o.Route,{path:r+"/regions"},n.createElement(f.gr,null)),n.createElement(o.Route,{path:r+"/cell-id"},n.createElement(f.E_,null)),n.createElement(o.Route,{path:r+"/allowed-transfers"},n.createElement(f.zO,null)),n.createElement(o.Route,{path:r+"/region-types"},n.createElement(f.Ku,null)),n.createElement(o.Route,{path:r},n.createElement(o.Redirect,{to:"/home"}))))):n.createElement(s.Z,null)};i().render(n.createElement(o.BrowserRouter,null,n.createElement(v,null)),document.getElementById("root"))},1022:(e,r,t)=>{var n=new Error;e.exports=new Promise(((e,r)=>{if("undefined"!=typeof portal)return e();t.l("/remoteEntry.js",(t=>{if("undefined"!=typeof portal)return e();var a=t&&("load"===t.type?"missing":t.type),i=t&&t.target&&t.target.src;n.message="Loading script failed.\n("+a+": "+i+")",n.name="ScriptExternalLoadError",n.type=a,n.request=i,r(n)}),"portal")})).then((()=>portal))}},o={};function l(e){var r=o[e];if(void 0!==r)return r.exports;var t=o[e]={id:e,loaded:!1,exports:{}};return i[e].call(t.exports,t,t.exports,l),t.loaded=!0,t.exports}l.m=i,l.c=o,e=[],l.O=(r,t,n,a)=>{if(!t){var i=1/0;for(s=0;s<e.length;s++){for(var[t,n,a]=e[s],o=!0,u=0;u<t.length;u++)(!1&a||i>=a)&&Object.keys(l.O).every((e=>l.O[e](t[u])))?t.splice(u--,1):(o=!1,a<i&&(i=a));o&&(e.splice(s--,1),r=n())}return r}a=a||0;for(var s=e.length;s>0&&e[s-1][2]>a;s--)e[s]=e[s-1];e[s]=[t,n,a]},l.n=e=>{var r=e&&e.__esModule?()=>e.default:()=>e;return l.d(r,{a:r}),r},l.d=(e,r)=>{for(var t in r)l.o(r,t)&&!l.o(e,t)&&Object.defineProperty(e,t,{enumerable:!0,get:r[t]})},l.f={},l.e=e=>Promise.all(Object.keys(l.f).reduce(((r,t)=>(l.f[t](e,r),r)),[])),l.u=e=>"static/js/"+({371:"unified.path-to-regexp",388:"unified.hyphenate-style-name",393:"unified.history",1151:"unified.value-equal",1858:"unified.html-parse-stringify2",2153:"unified.scheduler",2544:"unified.jss-plugin-camel-case",2658:"unified.object-assign",3206:"unified.mini-create-react-context",3251:"unified.react-redux",3627:"unified.jss-plugin-rule-value-function",3687:"unified.css-vendor",4486:"unified.react-router",4934:"unified.react",4940:"unified.tiny-invariant",5491:"unified.react-router-dom",5543:"unified.notistack",6203:"unified.is-in-browser",6522:"unified.jss",6673:"unified.react-dom",6743:"unified.jss-plugin-default-unit",6990:"unified.jss-plugin-props-sort",7193:"unified.symbol-observable",7494:"unified.redux",7507:"unified.jss-plugin-vendor-prefixer",7692:"unified.resolve-pathname",8019:"unified.void-elements",9096:"unified.jss-plugin-global",9213:"unified.react-i18next",9884:"unified.jss-plugin-nested"}[e]||e)+"."+{371:"5fdfea31",388:"2cc221d0",393:"18391040",681:"597b3c14",1151:"5cccf727",1858:"5eb50ace",2153:"873f7d52",2544:"10a48823",2658:"5e077814",3206:"552004fb",3251:"cf5de323",3627:"12ac88ea",3687:"83a30733",4486:"07a5ad88",4934:"d1830772",4940:"c9ee8747",5491:"20670c5e",5543:"495d5f54",6005:"33630607",6203:"6ac34f5f",6522:"13367cf7",6558:"7e19acae",6673:"8c9f01f0",6743:"fc7df9b7",6990:"8648eaff",7193:"f273f10c",7494:"8ba2b98a",7507:"b0157900",7692:"2fc738d1",8019:"f8839463",9096:"994b41b2",9213:"124d7b56",9884:"5ce9a75a"}[e]+".chunk.js",l.g=function(){if("object"==typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"==typeof window)return window}}(),l.hmd=e=>((e=Object.create(e)).children||(e.children=[]),Object.defineProperty(e,"exports",{enumerable:!0,set:()=>{throw new Error("ES Modules may not assign module.exports or exports.*, Use ESM export syntax, instead: "+e.id)}}),e),l.o=(e,r)=>Object.prototype.hasOwnProperty.call(e,r),r={},t="@unified/region:",l.l=(e,n,a,i)=>{if(r[e])r[e].push(n);else{var o,u;if(void 0!==a)for(var s=document.getElementsByTagName("script"),d=0;d<s.length;d++){var f=s[d];if(f.getAttribute("src")==e||f.getAttribute("data-webpack")==t+a){o=f;break}}o||(u=!0,(o=document.createElement("script")).charset="utf-8",o.timeout=120,l.nc&&o.setAttribute("nonce",l.nc),o.setAttribute("data-webpack",t+a),o.src=e),r[e]=[n];var c=(t,n)=>{o.onerror=o.onload=null,clearTimeout(h);var a=r[e];if(delete r[e],o.parentNode&&o.parentNode.removeChild(o),a&&a.forEach((e=>e(n))),t)return t(n)},h=setTimeout(c.bind(null,void 0,{type:"timeout",target:o}),12e4);o.onerror=c.bind(null,o.onerror),o.onload=c.bind(null,o.onload),u&&document.head.appendChild(o)}},l.r=e=>{"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.nmd=e=>(e.paths=[],e.children||(e.children=[]),e),n={},a={},l.f.remotes=(e,r)=>{l.o(n,e)&&n[e].forEach((e=>{var t=l.R;t||(t=[]);var n=a[e];if(!(t.indexOf(n)>=0)){if(t.push(n),n.p)return r.push(n.p);var o=r=>{r||(r=new Error("Container missing")),"string"==typeof r.message&&(r.message+='\nwhile loading "'+n[1]+'" from '+n[2]),i[e]=()=>{throw r},n.p=0},u=(e,t,a,i,l,u)=>{try{var s=e(t,a);if(!s||!s.then)return l(s,i,u);var d=s.then((e=>l(e,i)),o);if(!u)return d;r.push(n.p=d)}catch(e){o(e)}},s=(e,r,a)=>u(r.get,n[1],t,0,d,a),d=r=>{n.p=1,i[e]=e=>{e.exports=r()}};u(l,n[2],0,0,((e,r,t)=>e?u(l.I,n[0],0,e,s,t):o()),1)}}))},(()=>{l.S={};var e={},r={};l.I=(t,n)=>{n||(n=[]);var a=r[t];if(a||(a=r[t]={}),!(n.indexOf(a)>=0)){if(n.push(a),e[t])return e[t];l.o(l.S,t)||(l.S[t]={});var i=l.S[t],o="@unified/region",u=(e,r,t,n)=>{var a=i[e]=i[e]||{},l=a[r];(!l||!l.loaded&&(!n!=!l.eager?n:o>l.from))&&(a[r]={get:t,from:o,eager:!!n})},s=[];switch(t){case"default":u("@material-ui/styles","4.11.3",(()=>Promise.all([l.e(7717),l.e(1048),l.e(8171),l.e(4451),l.e(6522),l.e(3687),l.e(6743),l.e(9096),l.e(9884),l.e(3627),l.e(7507),l.e(2544),l.e(6990),l.e(6203),l.e(388),l.e(9037)]).then((()=>()=>l(5949))))),u("notistack","1.0.5",(()=>Promise.all([l.e(7717),l.e(1048),l.e(8171),l.e(4451),l.e(7468),l.e(5543),l.e(9037),l.e(4017)]).then((()=>()=>l(7442))))),u("react-dom","17.0.1",(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316))))),u("react-i18next","11.8.8",(()=>Promise.all([l.e(9213),l.e(1858),l.e(8019),l.e(6982)]).then((()=>()=>l(6211))))),u("react-redux","7.2.2",(()=>Promise.all([l.e(7717),l.e(1048),l.e(3251),l.e(6558)]).then((()=>()=>l(1725))))),u("react-router-dom","5.2.0",(()=>Promise.all([l.e(7717),l.e(1048),l.e(4486),l.e(393),l.e(5491),l.e(371),l.e(3206),l.e(7692),l.e(1151),l.e(4940),l.e(681)]).then((()=>()=>l(5074))))),u("react","17.0.1",(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),u("redux","4.0.5",(()=>Promise.all([l.e(7193),l.e(7494)]).then((()=>()=>l(8717))))),(e=>{var r=e=>{return r="Initialization of sharing external failed: "+e,"undefined"!=typeof console&&console.warn&&console.warn(r);var r};try{var a=l(1022);if(!a)return;var i=e=>e&&e.init&&e.init(l.S[t],n);if(a.then)return s.push(a.then(i,r));var o=i(a);o&&o.then&&s.push(o.catch(r))}catch(e){r(e)}})()}return s.length?e[t]=Promise.all(s).then((()=>e[t]=1)):e[t]=1}}})(),(()=>{var e;l.g.importScripts&&(e=l.g.location+"");var r=l.g.document;if(!e&&r&&(r.currentScript&&(e=r.currentScript.src),!e)){var t=r.getElementsByTagName("script");t.length&&(e=t[t.length-1].src)}if(!e)throw new Error("Automatic publicPath is not supported in this browser");e=e.replace(/#.*$/,"").replace(/\?.*$/,"").replace(/\/[^\/]+$/,"/"),l.p=e+"../../"})(),(()=>{var e=e=>{var r=e=>e.split(".").map((e=>+e==e?+e:e)),t=/^([^-+]+)?(?:-([^+]+))?(?:\+(.+))?$/.exec(e),n=t[1]?r(t[1]):[];return t[2]&&(n.length++,n.push.apply(n,r(t[2]))),t[3]&&(n.push([]),n.push.apply(n,r(t[3]))),n},r=(r,t)=>{r=e(r),t=e(t);for(var n=0;;){if(n>=r.length)return n<t.length&&"u"!=(typeof t[n])[0];var a=r[n],i=(typeof a)[0];if(n>=t.length)return"u"==i;var o=t[n],l=(typeof o)[0];if(i!=l)return"o"==i&&"n"==l||"s"==l||"u"==i;if("o"!=i&&"u"!=i&&a!=o)return a<o;n++}},t=e=>{var r=e[0],n="";if(1===e.length)return"*";if(r+.5){n+=0==r?">=":-1==r?"<":1==r?"^":2==r?"~":r>0?"=":"!=";for(var a=1,i=1;i<e.length;i++)a--,n+="u"==(typeof(l=e[i]))[0]?"-":(a>0?".":"")+(a=2,l);return n}var o=[];for(i=1;i<e.length;i++){var l=e[i];o.push(0===l?"not("+u()+")":1===l?"("+u()+" || "+u()+")":2===l?o.pop()+" "+o.pop():t(l))}return u();function u(){return o.pop().replace(/^\((.+)\)$/,"$1")}},n=(r,t)=>{if(0 in r){t=e(t);var a=r[0],i=a<0;i&&(a=-a-1);for(var o=0,l=1,u=!0;;l++,o++){var s,d,f=l<r.length?(typeof r[l])[0]:"";if(o>=t.length||"o"==(d=(typeof(s=t[o]))[0]))return!u||("u"==f?l>a&&!i:""==f!=i);if("u"==d){if(!u||"u"!=f)return!1}else if(u)if(f==d)if(l<=a){if(s!=r[l])return!1}else{if(i?s>r[l]:s<r[l])return!1;s!=r[l]&&(u=!1)}else if("s"!=f&&"n"!=f){if(i||l<=a)return!1;u=!1,l--}else{if(l<=a||d<f!=i)return!1;u=!1}else"s"!=f&&"n"!=f&&(u=!1,l--)}}var c=[],h=c.pop.bind(c);for(o=1;o<r.length;o++){var p=r[o];c.push(1==p?h()|h():2==p?h()&h():p?n(p,t):!h())}return!!h()},a=(e,a,o,l)=>{var u=((e,t)=>{var n=e[t];return Object.keys(n).reduce(((e,t)=>!e||!n[e].loaded&&r(e,t)?t:e),0)})(e,o);return n(l,u)||"undefined"!=typeof console&&console.warn&&console.warn(((e,r,n)=>"Unsatisfied version "+r+" of shared singleton module "+e+" (required "+t(n)+")")(o,u,l)),i(e[o][u])},i=e=>(e.loaded=1,e.get()),o=e=>function(r,t,n,a){var i=l.I(r);return i&&i.then?i.then(e.bind(e,r,l.S[r],t,n,a)):e(r,l.S[r],t,n,a)},u=o(((e,t,n,a)=>t&&l.o(t,n)?i(((e,t)=>{var n=e[t];return(t=Object.keys(n).reduce(((e,t)=>!e||r(e,t)?t:e),0))&&n[t]})(t,n)):a())),s=o(((e,r,t,n,i)=>r&&l.o(r,t)?a(r,0,t,n):i())),d={},f={6005:()=>s("default","react",[4,17,0,1],(()=>l.e(4934).then((()=>()=>l(2784))))),2801:()=>s("default","react",[,[1,17,0,0],[1,16,0,0],[1,15,0,0],[2,0,14,0],1,1,1],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),8172:()=>s("default","react",[0,15],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),529:()=>s("default","react-dom",[1,16,14,0],(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316))))),2883:()=>s("default","redux",[,[1,4,0,0,,0],[1,3,0,0],[1,2,0,0],1,1],(()=>Promise.all([l.e(7193),l.e(7494)]).then((()=>()=>l(8717))))),3304:()=>s("default","react",[,[1,17],[1,16,8,3],1],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),9037:()=>s("default","react",[,[1,17,0,0],[1,16,8,0],1],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),2196:()=>s("default","react",[0,16,6,0],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),4489:()=>s("default","react-dom",[0,16,6,0],(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316))))),5696:()=>s("default","@material-ui/styles",[1,4,11,3],(()=>Promise.all([l.e(8171),l.e(6522),l.e(3687),l.e(6743),l.e(9096),l.e(9884),l.e(3627),l.e(7507),l.e(2544),l.e(6990),l.e(6203),l.e(388)]).then((()=>()=>l(5949))))),8576:()=>s("default","react-dom",[,[1,17,0,0],[1,16,8,0],1],(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316))))),2238:()=>s("default","react",[,[4,16,8,0],[0],2],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),840:()=>s("default","react",[0,16,8,0],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),2411:()=>s("default","react",[1,17,0,1],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),3248:()=>s("default","notistack",[1,1,0,4],(()=>l.e(5543).then((()=>()=>l(7442))))),4808:()=>u("default","@material-ui/styles",(()=>Promise.all([l.e(8171),l.e(6522),l.e(3687),l.e(6743),l.e(9096),l.e(9884),l.e(3627),l.e(7507),l.e(2544),l.e(6990),l.e(6203),l.e(388)]).then((()=>()=>l(5949))))),8049:()=>s("default","react-router-dom",[1,5,2,0],(()=>Promise.all([l.e(4486),l.e(393),l.e(5491),l.e(371),l.e(3206),l.e(7692),l.e(1151),l.e(4940),l.e(681)]).then((()=>()=>l(5074))))),8565:()=>s("default","react",[1,16,8,0],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),8670:()=>s("default","react-redux",[1,7,2,2],(()=>Promise.all([l.e(3251),l.e(6558)]).then((()=>()=>l(1725))))),8901:()=>s("default","react",[1,17,0,0],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),9609:()=>s("default","react-i18next",[1,11,8,8],(()=>Promise.all([l.e(9213),l.e(1858),l.e(8019)]).then((()=>()=>l(6211))))),3006:()=>s("default","react-dom",[1,17,0,1],(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316)))))};[9037,2196,4489,5696,8576,2238,840,2411,3248,4808,8049,8565,8670,8901,9609,3006].forEach((e=>{l.m[e]=r=>{d[e]=0,delete l.c[e];var t=f[e]();if("function"!=typeof t)throw new Error("Shared module is not available for eager consumption: "+e);r.exports=t()}}));var c={681:[2801,8172],6005:[6005],6558:[529,2883,3304]};l.f.consumes=(e,r)=>{l.o(c,e)&&c[e].forEach((e=>{if(l.o(d,e))return r.push(d[e]);var t=r=>{d[e]=0,l.m[e]=t=>{delete l.c[e],t.exports=r()}},n=r=>{delete d[e],l.m[e]=t=>{throw delete l.c[e],r}};try{var a=f[e]();a.then?r.push(d[e]=a.then(t).catch(n)):t(a)}catch(e){n(e)}}))}})(),(()=>{var e={179:0,9037:0,4017:0,6982:0,5939:0};l.f.j=(r,t)=>{var n=l.o(e,r)?e[r]:void 0;if(0!==n)if(n)t.push(n[2]);else if(/^(6(005|558|81|982)|4017|5939|9037)$/.test(r))e[r]=0;else{var a=new Promise(((t,a)=>n=e[r]=[t,a]));t.push(n[2]=a);var i=l.p+l.u(r),o=new Error;l.l(i,(t=>{if(l.o(e,r)&&(0!==(n=e[r])&&(e[r]=void 0),n)){var a=t&&("load"===t.type?"missing":t.type),i=t&&t.target&&t.target.src;o.message="Loading chunk "+r+" failed.\n("+a+": "+i+")",o.name="ChunkLoadError",o.type=a,o.request=i,n[1](o)}}),"chunk-"+r,r)}},l.O.j=r=>0===e[r];var r=(r,t)=>{var n,a,[i,o,u]=t,s=0;for(n in o)l.o(o,n)&&(l.m[n]=o[n]);if(u)var d=u(l);for(r&&r(t);s<i.length;s++)a=i[s],l.o(e,a)&&e[a]&&e[a][0](),e[i[s]]=0;return l.O(d)},t=self.webpackChunk_unified_region=self.webpackChunk_unified_region||[];t.forEach(r.bind(null,0)),t.push=r.bind(null,t.push.bind(t))})();var u=l.O(void 0,[7717,1048,8171,4451,7468,1174,238,4120,2861,7308,6813,1297,5189,4653,5311,7131,6529,9827,7220,3590,9343,6832,422,7847,1554,9037,4017,6982,2290,5939],(()=>l(3216)));u=l.O(u)})();