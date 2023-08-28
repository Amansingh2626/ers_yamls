(()=>{"use strict";var e,t,r,n,i,a={3216:(e,t,r)=>{var n=r(2411),i=r(3006),a=r.n(i),o=r(8049),l=r(4315),u=r(9895),s=r(1120),d=r(5117),f=r(7428),c=n.lazy((function(){return Promise.all([r.e(8171),r.e(7717),r.e(7468),r.e(6529),r.e(4120),r.e(9343),r.e(1174),r.e(6128),r.e(6813),r.e(1297),r.e(7131),r.e(6832),r.e(4951),r.e(597),r.e(9130),r.e(633),r.e(7254),r.e(238),r.e(4017),r.e(6982),r.e(1469),r.e(4830),r.e(7256)]).then(r.bind(r,7256))})),p=n.lazy((function(){return Promise.all([r.e(8171),r.e(7717),r.e(7468),r.e(6529),r.e(4120),r.e(9343),r.e(1174),r.e(6128),r.e(6813),r.e(1297),r.e(7131),r.e(6832),r.e(4951),r.e(9827),r.e(597),r.e(9130),r.e(633),r.e(7254),r.e(4017),r.e(6982),r.e(1469),r.e(4830),r.e(2587),r.e(3527)]).then(r.bind(r,3527))})),h=n.lazy((function(){return Promise.all([r.e(8171),r.e(7717),r.e(7468),r.e(6529),r.e(4120),r.e(9343),r.e(1174),r.e(6128),r.e(6813),r.e(1297),r.e(7131),r.e(6832),r.e(9827),r.e(674),r.e(6852),r.e(5501),r.e(7142),r.e(2470),r.e(7693),r.e(4653),r.e(383),r.e(4481),r.e(9411),r.e(1766),r.e(4017),r.e(6982),r.e(1469),r.e(2587),r.e(7493)]).then(r.bind(r,7493))})),m=(0,s.Z)((function(e){return(0,d.Z)({root:{padding:e.spacing(1),position:"relative"}})}));const v=function(){var e=n.useState({liveEntities:[],staticEntities:[]}),t=(0,l.Z)(e,2),r=t[0],i=t[1],a=n.useState(!1),s=(0,l.Z)(a,2),d=s[0],v=s[1],g=m(),b=(0,o.useRouteMatch)().path;return n.useEffect((function(){v(!0),(0,f.BQ)().then((function(e){return i(e)})).finally((function(){return v(!1)}))}),[]),n.createElement(u.Z,{elevation:0,className:g.root},n.createElement(o.Switch,null,n.createElement(o.Route,{path:"".concat(b,"/trips/:tripId"),exact:!0,component:c}),n.createElement(o.Route,{path:"".concat(b,"/agents/:agentId/trips"),exact:!0,component:p}),n.createElement(o.Route,{path:"".concat(b,"/")},d?"Loading.....":n.createElement(h,{loading:d,entityTypes:r}))))};a().render(n.createElement(o.BrowserRouter,null,n.createElement(v,null)),document.getElementById("root"))},1022:(e,t,r)=>{var n=new Error;e.exports=new Promise(((e,t)=>{if("undefined"!=typeof portal)return e();r.l("/remoteEntry.js",(r=>{if("undefined"!=typeof portal)return e();var i=r&&("load"===r.type?"missing":r.type),a=r&&r.target&&r.target.src;n.message="Loading script failed.\n("+i+": "+a+")",n.name="ScriptExternalLoadError",n.type=i,n.request=a,t(n)}),"portal")})).then((()=>portal))}},o={};function l(e){var t=o[e];if(void 0!==t)return t.exports;var r=o[e]={id:e,loaded:!1,exports:{}};return a[e].call(r.exports,r,r.exports,l),r.loaded=!0,r.exports}l.m=a,l.c=o,e=[],l.O=(t,r,n,i)=>{if(!r){var a=1/0;for(s=0;s<e.length;s++){for(var[r,n,i]=e[s],o=!0,u=0;u<r.length;u++)(!1&i||a>=i)&&Object.keys(l.O).every((e=>l.O[e](r[u])))?r.splice(u--,1):(o=!1,i<a&&(a=i));o&&(e.splice(s--,1),t=n())}return t}i=i||0;for(var s=e.length;s>0&&e[s-1][2]>i;s--)e[s]=e[s-1];e[s]=[r,n,i]},l.n=e=>{var t=e&&e.__esModule?()=>e.default:()=>e;return l.d(t,{a:t}),t},l.d=(e,t)=>{for(var r in t)l.o(t,r)&&!l.o(e,r)&&Object.defineProperty(e,r,{enumerable:!0,get:t[r]})},l.f={},l.e=e=>Promise.all(Object.keys(l.f).reduce(((t,r)=>(l.f[r](e,t),t)),[])),l.u=e=>"static/js/"+({238:"unified.lodash",371:"unified.path-to-regexp",383:"unified.parseuri",388:"unified.hyphenate-style-name",393:"unified.history",597:"unified.decode-uri-component",633:"unified.filter-obj",674:"unified.engine.io-client",1151:"unified.value-equal",1174:"unified.fontsource",1297:"unified.react-spring",1766:"unified.backo2",1858:"unified.html-parse-stringify2",2153:"unified.scheduler",2470:"unified.socket.io",2544:"unified.jss-plugin-camel-case",2658:"unified.object-assign",3206:"unified.mini-create-react-context",3627:"unified.jss-plugin-rule-value-function",3687:"unified.css-vendor",4120:"unified.popper.js",4481:"unified.parseqs",4486:"unified.react-router",4653:"unified.regenerator-runtime",4934:"unified.react",4940:"unified.tiny-invariant",4951:"unified.query-string",5491:"unified.react-router-dom",5501:"unified.engine.io-parser",5543:"unified.notistack",6128:"unified.react-google-maps",6203:"unified.is-in-browser",6522:"unified.jss",6673:"unified.react-dom",6743:"unified.jss-plugin-default-unit",6813:"unified.seamless",6832:"unified.css-loader",6852:"unified.socket.io-client",6990:"unified.jss-plugin-props-sort",7131:"unified.style-loader",7142:"unified.socket.io-parser",7254:"unified.strict-uri-encode",7468:"unified.react-transition-group",7507:"unified.jss-plugin-vendor-prefixer",7692:"unified.resolve-pathname",7693:"unified.yeast",7717:"unified.hoist-non-react-statics",8019:"unified.void-elements",9096:"unified.jss-plugin-global",9130:"unified.split-on-first",9213:"unified.react-i18next",9343:"unified.react-is",9411:"unified.has-cors",9827:"unified.reselect",9884:"unified.jss-plugin-nested"}[e]||e)+"."+{238:"508f230f",371:"ede9d392",383:"b5ecfbd9",388:"6be0700f",393:"d55eedef",597:"aba4e84d",633:"b5e9c2bc",674:"ea9e23e2",681:"f584d80b",1151:"2c1b0c36",1174:"5b0b2e24",1297:"24e83a2e",1469:"d4d0ed5a",1766:"cf35cc81",1858:"58c33975",2153:"bca0e9f5",2470:"82185a58",2544:"5d09d82e",2587:"57bbeea3",2658:"6c193cdb",3206:"82b4071a",3527:"2a005f56",3627:"fb54f541",3687:"f450b61f",4017:"059e1d81",4120:"ddf87a11",4481:"8f7e3618",4486:"7bcaabf4",4653:"d0c7147d",4830:"812f8220",4934:"edab0884",4940:"cbd376d4",4951:"2c345390",5491:"41cd390f",5501:"42394d32",5543:"33427060",6005:"3c155245",6128:"d57af901",6203:"1dc26136",6522:"48b618dc",6673:"e831f3ce",6743:"e8dafd23",6813:"ee314c72",6832:"fa605690",6852:"81099fd1",6982:"aa514e98",6990:"59a86413",7131:"ae7485c6",7142:"ae408b5e",7254:"afb5b61d",7256:"ee1ce47f",7468:"f7e38982",7493:"201d793d",7507:"f4f344a6",7692:"cf03fd31",7693:"8dbd77f0",7717:"d8cc8f3a",8019:"7443eb75",9096:"909f4c79",9130:"fde33889",9213:"f9011cb2",9343:"51846e06",9411:"2cd7719f",9827:"61e47705",9884:"fe4b9684"}[e]+".chunk.js",l.g=function(){if("object"==typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"==typeof window)return window}}(),l.hmd=e=>((e=Object.create(e)).children||(e.children=[]),Object.defineProperty(e,"exports",{enumerable:!0,set:()=>{throw new Error("ES Modules may not assign module.exports or exports.*, Use ESM export syntax, instead: "+e.id)}}),e),l.o=(e,t)=>Object.prototype.hasOwnProperty.call(e,t),t={},r="@unified/livemap:",l.l=(e,n,i,a)=>{if(t[e])t[e].push(n);else{var o,u;if(void 0!==i)for(var s=document.getElementsByTagName("script"),d=0;d<s.length;d++){var f=s[d];if(f.getAttribute("src")==e||f.getAttribute("data-webpack")==r+i){o=f;break}}o||(u=!0,(o=document.createElement("script")).charset="utf-8",o.timeout=120,l.nc&&o.setAttribute("nonce",l.nc),o.setAttribute("data-webpack",r+i),o.src=e),t[e]=[n];var c=(r,n)=>{o.onerror=o.onload=null,clearTimeout(p);var i=t[e];if(delete t[e],o.parentNode&&o.parentNode.removeChild(o),i&&i.forEach((e=>e(n))),r)return r(n)},p=setTimeout(c.bind(null,void 0,{type:"timeout",target:o}),12e4);o.onerror=c.bind(null,o.onerror),o.onload=c.bind(null,o.onload),u&&document.head.appendChild(o)}},l.r=e=>{"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.nmd=e=>(e.paths=[],e.children||(e.children=[]),e),n={},i={},l.f.remotes=(e,t)=>{l.o(n,e)&&n[e].forEach((e=>{var r=l.R;r||(r=[]);var n=i[e];if(!(r.indexOf(n)>=0)){if(r.push(n),n.p)return t.push(n.p);var o=t=>{t||(t=new Error("Container missing")),"string"==typeof t.message&&(t.message+='\nwhile loading "'+n[1]+'" from '+n[2]),a[e]=()=>{throw t},n.p=0},u=(e,r,i,a,l,u)=>{try{var s=e(r,i);if(!s||!s.then)return l(s,a,u);var d=s.then((e=>l(e,a)),o);if(!u)return d;t.push(n.p=d)}catch(e){o(e)}},s=(e,t,i)=>u(t.get,n[1],r,0,d,i),d=t=>{n.p=1,a[e]=e=>{e.exports=t()}};u(l,n[2],0,0,((e,t,r)=>e?u(l.I,n[0],0,e,s,r):o()),1)}}))},(()=>{l.S={};var e={},t={};l.I=(r,n)=>{n||(n=[]);var i=t[r];if(i||(i=t[r]={}),!(n.indexOf(i)>=0)){if(n.push(i),e[r])return e[r];l.o(l.S,r)||(l.S[r]={});var a=l.S[r],o="@unified/livemap",u=(e,t,r,n)=>{var i=a[e]=a[e]||{},l=i[t];(!l||!l.loaded&&(!n!=!l.eager?n:o>l.from))&&(i[t]={get:r,from:o,eager:!!n})},s=[];switch(r){case"default":u("@material-ui/lab","4.0.0-alpha.57",(()=>Promise.all([l.e(8171),l.e(1048),l.e(4451),l.e(7468),l.e(4120),l.e(9343),l.e(9037),l.e(4017)]).then((()=>()=>l(182))))),u("@material-ui/styles","4.11.3",(()=>Promise.all([l.e(8171),l.e(7717),l.e(1048),l.e(4451),l.e(6522),l.e(3687),l.e(6743),l.e(9096),l.e(9884),l.e(3627),l.e(7507),l.e(2544),l.e(6990),l.e(6203),l.e(388),l.e(9037)]).then((()=>()=>l(5949))))),u("@material-ui/styles","4.11.4",(()=>Promise.all([l.e(8171),l.e(7717),l.e(1048),l.e(4451),l.e(6522),l.e(3687),l.e(6743),l.e(9096),l.e(9884),l.e(3627),l.e(7507),l.e(2544),l.e(6990),l.e(6203),l.e(388),l.e(9037)]).then((()=>()=>l(4250))))),u("notistack","1.0.5",(()=>Promise.all([l.e(8171),l.e(7717),l.e(1048),l.e(4451),l.e(7468),l.e(5543),l.e(9037),l.e(4017)]).then((()=>()=>l(7442))))),u("react-dom","17.0.1",(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316))))),u("react-i18next","11.8.8",(()=>Promise.all([l.e(9213),l.e(1858),l.e(8019),l.e(6982)]).then((()=>()=>l(6211))))),u("react-router-dom","5.2.0",(()=>Promise.all([l.e(7717),l.e(1048),l.e(4486),l.e(393),l.e(5491),l.e(371),l.e(3206),l.e(7692),l.e(1151),l.e(4940),l.e(681)]).then((()=>()=>l(5074))))),u("react","17.0.1",(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),(e=>{var t=e=>{return t="Initialization of sharing external failed: "+e,"undefined"!=typeof console&&console.warn&&console.warn(t);var t};try{var i=l(1022);if(!i)return;var a=e=>e&&e.init&&e.init(l.S[r],n);if(i.then)return s.push(i.then(a,t));var o=a(i);o&&o.then&&s.push(o.catch(t))}catch(e){t(e)}})()}return s.length?e[r]=Promise.all(s).then((()=>e[r]=1)):e[r]=1}}})(),(()=>{var e;l.g.importScripts&&(e=l.g.location+"");var t=l.g.document;if(!e&&t&&(t.currentScript&&(e=t.currentScript.src),!e)){var r=t.getElementsByTagName("script");r.length&&(e=r[r.length-1].src)}if(!e)throw new Error("Automatic publicPath is not supported in this browser");e=e.replace(/#.*$/,"").replace(/\?.*$/,"").replace(/\/[^\/]+$/,"/"),l.p=e+"../../"})(),(()=>{var e=e=>{var t=e=>e.split(".").map((e=>+e==e?+e:e)),r=/^([^-+]+)?(?:-([^+]+))?(?:\+(.+))?$/.exec(e),n=r[1]?t(r[1]):[];return r[2]&&(n.length++,n.push.apply(n,t(r[2]))),r[3]&&(n.push([]),n.push.apply(n,t(r[3]))),n},t=(t,r)=>{t=e(t),r=e(r);for(var n=0;;){if(n>=t.length)return n<r.length&&"u"!=(typeof r[n])[0];var i=t[n],a=(typeof i)[0];if(n>=r.length)return"u"==a;var o=r[n],l=(typeof o)[0];if(a!=l)return"o"==a&&"n"==l||"s"==l||"u"==a;if("o"!=a&&"u"!=a&&i!=o)return i<o;n++}},r=e=>{var t=e[0],n="";if(1===e.length)return"*";if(t+.5){n+=0==t?">=":-1==t?"<":1==t?"^":2==t?"~":t>0?"=":"!=";for(var i=1,a=1;a<e.length;a++)i--,n+="u"==(typeof(l=e[a]))[0]?"-":(i>0?".":"")+(i=2,l);return n}var o=[];for(a=1;a<e.length;a++){var l=e[a];o.push(0===l?"not("+u()+")":1===l?"("+u()+" || "+u()+")":2===l?o.pop()+" "+o.pop():r(l))}return u();function u(){return o.pop().replace(/^\((.+)\)$/,"$1")}},n=(t,r)=>{if(0 in t){r=e(r);var i=t[0],a=i<0;a&&(i=-i-1);for(var o=0,l=1,u=!0;;l++,o++){var s,d,f=l<t.length?(typeof t[l])[0]:"";if(o>=r.length||"o"==(d=(typeof(s=r[o]))[0]))return!u||("u"==f?l>i&&!a:""==f!=a);if("u"==d){if(!u||"u"!=f)return!1}else if(u)if(f==d)if(l<=i){if(s!=t[l])return!1}else{if(a?s>t[l]:s<t[l])return!1;s!=t[l]&&(u=!1)}else if("s"!=f&&"n"!=f){if(a||l<=i)return!1;u=!1,l--}else{if(l<=i||d<f!=a)return!1;u=!1}else"s"!=f&&"n"!=f&&(u=!1,l--)}}var c=[],p=c.pop.bind(c);for(o=1;o<t.length;o++){var h=t[o];c.push(1==h?p()|p():2==h?p()&p():h?n(h,r):!p())}return!!p()},i=(e,i,o,l)=>{var u=((e,r)=>{var n=e[r];return Object.keys(n).reduce(((e,r)=>!e||!n[e].loaded&&t(e,r)?r:e),0)})(e,o);return n(l,u)||"undefined"!=typeof console&&console.warn&&console.warn(((e,t,n)=>"Unsatisfied version "+t+" of shared singleton module "+e+" (required "+r(n)+")")(o,u,l)),a(e[o][u])},a=e=>(e.loaded=1,e.get()),o=e=>function(t,r,n,i){var a=l.I(t);return a&&a.then?a.then(e.bind(e,t,l.S[t],r,n,i)):e(t,l.S[t],r,n,i)},u=o(((e,r,n,i)=>r&&l.o(r,n)?a(((e,r)=>{var n=e[r];return(r=Object.keys(n).reduce(((e,r)=>!e||t(e,r)?r:e),0))&&n[r]})(r,n)):i())),s=o(((e,t,r,n,a)=>t&&l.o(t,r)?i(t,0,r,n):a())),d={},f={6005:()=>s("default","react",[4,17,0,1],(()=>l.e(4934).then((()=>()=>l(2784))))),2801:()=>s("default","react",[,[1,17,0,0],[1,16,0,0],[1,15,0,0],[2,0,14,0],1,1,1],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),8172:()=>s("default","react",[0,15],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),2196:()=>s("default","react",[0,16,6,0],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),4489:()=>s("default","react-dom",[0,16,6,0],(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316))))),5696:()=>s("default","@material-ui/styles",[1,4,11,3],(()=>Promise.all([l.e(8171),l.e(7717),l.e(6522),l.e(3687),l.e(6743),l.e(9096),l.e(9884),l.e(3627),l.e(7507),l.e(2544),l.e(6990),l.e(6203),l.e(388)]).then((()=>()=>l(5949))))),8576:()=>s("default","react-dom",[,[1,17,0,0],[1,16,8,0],1],(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316))))),2238:()=>s("default","react",[,[4,16,8,0],[0],2],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),974:()=>s("default","@material-ui/lab",[1,4,0,0,,"alpha",57],(()=>Promise.all([l.e(8171),l.e(1048),l.e(4451),l.e(9037)]).then((()=>()=>l(182))))),3362:()=>s("default","react-dom",[,[1,17,0,0],[1,16,6,3],1],(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316))))),4947:()=>s("default","react",[,[1,17,0,0],[1,16,6,3],1],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),8565:()=>s("default","react",[1,16,8,0],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),9609:()=>s("default","react-i18next",[1,11,8,8],(()=>Promise.all([l.e(9213),l.e(1858),l.e(8019)]).then((()=>()=>l(6211))))),9721:()=>s("default","notistack",[1,1,0,3],(()=>Promise.all([l.e(8171),l.e(1048),l.e(4451),l.e(5543),l.e(9037)]).then((()=>()=>l(7442))))),4808:()=>u("default","@material-ui/styles",(()=>Promise.all([l.e(8171),l.e(1048),l.e(4451),l.e(6522),l.e(3687),l.e(6743),l.e(9096),l.e(9884),l.e(3627),l.e(7507),l.e(2544),l.e(6990),l.e(6203),l.e(388),l.e(9037)]).then((()=>()=>l(5949))))),8901:()=>s("default","react",[1,17,0,0],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),9037:()=>s("default","react",[,[1,17,0,0],[1,16,8,0],1],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),2411:()=>s("default","react",[1,17,0,1],(()=>Promise.all([l.e(4934),l.e(2658)]).then((()=>()=>l(2784))))),7832:()=>s("default","@material-ui/styles",[1,4,11,4],(()=>Promise.all([l.e(8171),l.e(7717),l.e(6522),l.e(3687),l.e(6743),l.e(9096),l.e(9884),l.e(3627),l.e(7507),l.e(2544),l.e(6990),l.e(6203),l.e(388)]).then((()=>()=>l(4250))))),8049:()=>s("default","react-router-dom",[1,5,2,0],(()=>Promise.all([l.e(7717),l.e(4486),l.e(393),l.e(5491),l.e(371),l.e(3206),l.e(7692),l.e(1151),l.e(4940),l.e(681)]).then((()=>()=>l(5074))))),3006:()=>s("default","react-dom",[1,17,0,1],(()=>Promise.all([l.e(2658),l.e(2153),l.e(6673),l.e(6005)]).then((()=>()=>l(8316)))))};[9037,2411,7832,8049,3006].forEach((e=>{l.m[e]=t=>{d[e]=0,delete l.c[e];var r=f[e]();if("function"!=typeof r)throw new Error("Shared module is not available for eager consumption: "+e);t.exports=r()}}));var c={681:[2801,8172],1469:[974,3362,4947,8565,9609],2587:[4808,8901],4017:[2196,4489,5696,8576],4830:[9721],6005:[6005],6982:[2238]};l.f.consumes=(e,t)=>{l.o(c,e)&&c[e].forEach((e=>{if(l.o(d,e))return t.push(d[e]);var r=t=>{d[e]=0,l.m[e]=r=>{delete l.c[e],r.exports=t()}},n=t=>{delete d[e],l.m[e]=r=>{throw delete l.c[e],t}};try{var i=f[e]();i.then?t.push(d[e]=i.then(r).catch(n)):r(i)}catch(e){n(e)}}))}})(),(()=>{var e={179:0,9037:0,9836:0};l.f.j=(t,r)=>{var n=l.o(e,t)?e[t]:void 0;if(0!==n)if(n)r.push(n[2]);else if(/^(6(005|81|982)|(258|401|903)7|1469|9836)$/.test(t))e[t]=0;else{var i=new Promise(((r,i)=>n=e[t]=[r,i]));r.push(n[2]=i);var a=l.p+l.u(t),o=new Error;l.l(a,(r=>{if(l.o(e,t)&&(0!==(n=e[t])&&(e[t]=void 0),n)){var i=r&&("load"===r.type?"missing":r.type),a=r&&r.target&&r.target.src;o.message="Loading chunk "+t+" failed.\n("+i+": "+a+")",o.name="ChunkLoadError",o.type=i,o.request=a,n[1](o)}}),"chunk-"+t,t)}},l.O.j=t=>0===e[t];var t=(t,r)=>{var n,i,[a,o,u]=r,s=0;for(n in o)l.o(o,n)&&(l.m[n]=o[n]);if(u)var d=u(l);for(t&&t(r);s<a.length;s++)i=a[s],l.o(e,i)&&e[i]&&e[i][0](),e[a[s]]=0;return l.O(d)},r=self.webpackChunk_unified_livemap=self.webpackChunk_unified_livemap||[];r.forEach(t.bind(null,0)),r.push=t.bind(null,r.push.bind(r))})();var u=l.O(void 0,[8171,1048,4451,6529,9037,8612,9836],(()=>l(3216)));u=l.O(u)})();