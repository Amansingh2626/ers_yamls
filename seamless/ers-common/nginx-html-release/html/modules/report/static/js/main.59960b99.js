(()=>{"use strict";var e,t,r,n,a,i,o,l={33216:(e,t,r)=>{var n=r(2411),a=r(43006),i=r.n(a),o=r(48049),l=r(79692),u=r(12420),f=r(67358),s=r(27556),d=r(47953),c=r(44104),p=r.n(c),h=r(87516),m=(0,l.Z)((function(e){return(0,u.Z)({root:{padding:e.spacing(1)}})}));const v=function(){var e=m(),t=(0,o.useRouteMatch)().path,r=p()().AccessControllProvider,a=(0,h.Z)("REPORT");return a?n.createElement(r,{featureList:a},n.createElement(s.Z,{elevation:0,className:e.root},n.createElement(o.Switch,null,n.createElement(o.Route,{path:"".concat(t,"/dashboard")},n.createElement(d.A,null)),n.createElement(o.Route,{path:t+"/scheduled_reports"},n.createElement(d.iA,null)),n.createElement(o.Route,{exact:!0,path:"".concat(t,"/:id")},n.createElement(d.Aq,null)),n.createElement(o.Route,null,n.createElement(o.Redirect,{to:"".concat(t,"/report")}))))):n.createElement(f.Z,null)};i().render(n.createElement(o.BrowserRouter,null,n.createElement(v,null)),document.getElementById("root"))},71022:(e,t,r)=>{var n=new Error;e.exports=new Promise(((e,t)=>{if("undefined"!=typeof portal)return e();r.l("/remoteEntry.js",(r=>{if("undefined"!=typeof portal)return e();var a=r&&("load"===r.type?"missing":r.type),i=r&&r.target&&r.target.src;n.message="Loading script failed.\n("+a+": "+i+")",n.name="ScriptExternalLoadError",n.type=a,n.request=i,t(n)}),"portal")})).then((()=>portal))},7267:(e,t,r)=>{var n=new Error;e.exports=new Promise(((e,t)=>{if("undefined"!=typeof reseller)return e();r.l("/modules/reseller/remoteEntry.js",(r=>{if("undefined"!=typeof reseller)return e();var a=r&&("load"===r.type?"missing":r.type),i=r&&r.target&&r.target.src;n.message="Loading script failed.\n("+a+": "+i+")",n.name="ScriptExternalLoadError",n.type=a,n.request=i,t(n)}),"reseller")})).then((()=>reseller))}},u={};function f(e){var t=u[e];if(void 0!==t)return t.exports;var r=u[e]={id:e,loaded:!1,exports:{}};return l[e].call(r.exports,r,r.exports,f),r.loaded=!0,r.exports}f.m=l,f.c=u,e=[],f.O=(t,r,n,a)=>{if(!r){var i=1/0;for(u=0;u<e.length;u++){for(var[r,n,a]=e[u],o=!0,l=0;l<r.length;l++)(!1&a||i>=a)&&Object.keys(f.O).every((e=>f.O[e](r[l])))?r.splice(l--,1):(o=!1,a<i&&(i=a));o&&(e.splice(u--,1),t=n())}return t}a=a||0;for(var u=e.length;u>0&&e[u-1][2]>a;u--)e[u]=e[u-1];e[u]=[r,n,a]},f.n=e=>{var t=e&&e.__esModule?()=>e.default:()=>e;return f.d(t,{a:t}),t},r=Object.getPrototypeOf?e=>Object.getPrototypeOf(e):e=>e.__proto__,f.t=function(e,n){if(1&n&&(e=this(e)),8&n)return e;if("object"==typeof e&&e){if(4&n&&e.__esModule)return e;if(16&n&&"function"==typeof e.then)return e}var a=Object.create(null);f.r(a);var i={};t=t||[null,r({}),r([]),r(r)];for(var o=2&n&&e;"object"==typeof o&&!~t.indexOf(o);o=r(o))Object.getOwnPropertyNames(o).forEach((t=>i[t]=()=>e[t]));return i.default=()=>e,f.d(a,i),a},f.d=(e,t)=>{for(var r in t)f.o(t,r)&&!f.o(e,r)&&Object.defineProperty(e,r,{enumerable:!0,get:t[r]})},f.f={},f.e=e=>Promise.all(Object.keys(f.f).reduce(((t,r)=>(f.f[r](e,t),t)),[])),f.u=e=>"static/js/"+({371:"unified.path-to-regexp",388:"unified.hyphenate-style-name",393:"unified.history",1151:"unified.value-equal",1226:"unified.stackblur-canvas",1260:"unified.rgbcolor",1510:"unified.html2canvas",1858:"unified.html-parse-stringify2",2153:"unified.scheduler",2544:"unified.jss-plugin-camel-case",3172:"unified.svg-pathdata",3206:"unified.mini-create-react-context",3251:"unified.react-redux",3596:"unified.raf",3627:"unified.jss-plugin-rule-value-function",3687:"unified.css-vendor",4291:"unified.core-js-pure",4486:"unified.react-router",4653:"unified.regenerator-runtime",4842:"unified.canvg",4934:"unified.react",5170:"unified.dompurify",5491:"unified.react-router-dom",5543:"unified.notistack",6203:"unified.is-in-browser",6522:"unified.jss",6673:"unified.react-dom",6743:"unified.jss-plugin-default-unit",6990:"unified.jss-plugin-props-sort",7193:"unified.symbol-observable",7494:"unified.redux",7507:"unified.jss-plugin-vendor-prefixer",7692:"unified.resolve-pathname",8019:"unified.void-elements",9096:"unified.jss-plugin-global",9213:"unified.react-i18next",9870:"unified.performance-now",9884:"unified.jss-plugin-nested"}[e]||e)+"."+{371:"7bd71a85",388:"66789953",393:"90d82d55",681:"35b8f737",1151:"c4344d3f",1226:"c97c406e",1260:"0fa8fabe",1510:"41866a56",1858:"bb6b66bf",2153:"133c8a90",2544:"f02f3d9d",3172:"3375a99a",3206:"6247c659",3251:"f930fe19",3596:"dc7b15fe",3627:"4037c177",3687:"dcc6a1af",4291:"e9de7a17",4486:"35f3633d",4653:"e29b8b1a",4842:"a0c4ce26",4888:"f2ff89e8",4934:"0b3cdc14",5170:"061ffa10",5491:"fa83b8fb",5543:"4dd71eef",6005:"7ca750a1",6203:"ad835aad",6522:"e429f1cf",6558:"930ec1e5",6673:"b176ff0f",6743:"3fb19234",6990:"3e2a544c",7193:"83de2faa",7494:"ca72517b",7507:"acbf934e",7692:"6c8f8ccf",8019:"72f19bd6",9096:"7c9ec54c",9213:"2fafe07b",9870:"05de29b8",9884:"c2521cb2"}[e]+".chunk.js",f.g=function(){if("object"==typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"==typeof window)return window}}(),f.hmd=e=>((e=Object.create(e)).children||(e.children=[]),Object.defineProperty(e,"exports",{enumerable:!0,set:()=>{throw new Error("ES Modules may not assign module.exports or exports.*, Use ESM export syntax, instead: "+e.id)}}),e),f.o=(e,t)=>Object.prototype.hasOwnProperty.call(e,t),n={},a="@unified/report:",f.l=(e,t,r,i)=>{if(n[e])n[e].push(t);else{var o,l;if(void 0!==r)for(var u=document.getElementsByTagName("script"),s=0;s<u.length;s++){var d=u[s];if(d.getAttribute("src")==e||d.getAttribute("data-webpack")==a+r){o=d;break}}o||(l=!0,(o=document.createElement("script")).charset="utf-8",o.timeout=120,f.nc&&o.setAttribute("nonce",f.nc),o.setAttribute("data-webpack",a+r),o.src=e),n[e]=[t];var c=(t,r)=>{o.onerror=o.onload=null,clearTimeout(p);var a=n[e];if(delete n[e],o.parentNode&&o.parentNode.removeChild(o),a&&a.forEach((e=>e(r))),t)return t(r)},p=setTimeout(c.bind(null,void 0,{type:"timeout",target:o}),12e4);o.onerror=c.bind(null,o.onerror),o.onload=c.bind(null,o.onload),l&&document.head.appendChild(o)}},f.r=e=>{"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},f.nmd=e=>(e.paths=[],e.children||(e.children=[]),e),i={},o={},f.f.remotes=(e,t)=>{f.o(i,e)&&i[e].forEach((e=>{var r=f.R;r||(r=[]);var n=o[e];if(!(r.indexOf(n)>=0)){if(r.push(n),n.p)return t.push(n.p);var a=t=>{t||(t=new Error("Container missing")),"string"==typeof t.message&&(t.message+='\nwhile loading "'+n[1]+'" from '+n[2]),l[e]=()=>{throw t},n.p=0},i=(e,r,i,o,l,u)=>{try{var f=e(r,i);if(!f||!f.then)return l(f,o,u);var s=f.then((e=>l(e,o)),a);if(!u)return s;t.push(n.p=s)}catch(e){a(e)}},u=(e,t,a)=>i(t.get,n[1],r,0,s,a),s=t=>{n.p=1,l[e]=e=>{e.exports=t()}};i(f,n[2],0,0,((e,t,r)=>e?i(f.I,n[0],0,e,u,r):a()),1)}}))},(()=>{f.S={};var e={},t={};f.I=(r,n)=>{n||(n=[]);var a=t[r];if(a||(a=t[r]={}),!(n.indexOf(a)>=0)){if(n.push(a),e[r])return e[r];f.o(f.S,r)||(f.S[r]={});var i=f.S[r],o="@unified/report",l=(e,t,r,n)=>{var a=i[e]=i[e]||{},l=a[t];(!l||!l.loaded&&(!n!=!l.eager?n:o>l.from))&&(a[t]={get:r,from:o,eager:!!n})},u=e=>{var t=e=>{return t="Initialization of sharing external failed: "+e,"undefined"!=typeof console&&console.warn&&console.warn(t);var t};try{var a=f(e);if(!a)return;var i=e=>e&&e.init&&e.init(f.S[r],n);if(a.then)return s.push(a.then(i,t));var o=i(a);if(o&&o.then)return s.push(o.catch(t))}catch(e){t(e)}},s=[];switch(r){case"default":l("@material-ui/styles","4.11.3",(()=>Promise.all([f.e(7717),f.e(1048),f.e(8171),f.e(4451),f.e(6203),f.e(3687),f.e(6743),f.e(9096),f.e(9884),f.e(3627),f.e(7507),f.e(2544),f.e(6990),f.e(388),f.e(9037),f.e(4888)]).then((()=>()=>f(95949))))),l("jss","10.5.1",(()=>Promise.all([f.e(1554),f.e(6203),f.e(6522)]).then((()=>()=>f(87359))))),l("notistack","1.0.5",(()=>Promise.all([f.e(7717),f.e(1048),f.e(8171),f.e(4451),f.e(7468),f.e(5543),f.e(9037),f.e(4017)]).then((()=>()=>f(87442))))),l("react-dom","17.0.1",(()=>Promise.all([f.e(2658),f.e(6673),f.e(2153),f.e(6005)]).then((()=>()=>f(28316))))),l("react-i18next","11.8.9",(()=>Promise.all([f.e(9213),f.e(1858),f.e(8019),f.e(6982)]).then((()=>()=>f(74211))))),l("react-redux","7.2.2",(()=>Promise.all([f.e(7717),f.e(1048),f.e(3251),f.e(6558)]).then((()=>()=>f(21725))))),l("react-router-dom","5.2.0",(()=>Promise.all([f.e(7717),f.e(1048),f.e(4940),f.e(4486),f.e(393),f.e(5491),f.e(371),f.e(3206),f.e(7692),f.e(1151),f.e(681)]).then((()=>()=>f(95074))))),l("react","17.0.1",(()=>Promise.all([f.e(2658),f.e(4934)]).then((()=>()=>f(2784))))),l("redux","4.0.5",(()=>Promise.all([f.e(7193),f.e(7494)]).then((()=>()=>f(18717))))),u(71022),u(7267)}return s.length?e[r]=Promise.all(s).then((()=>e[r]=1)):e[r]=1}}})(),(()=>{var e;f.g.importScripts&&(e=f.g.location+"");var t=f.g.document;if(!e&&t&&(t.currentScript&&(e=t.currentScript.src),!e)){var r=t.getElementsByTagName("script");r.length&&(e=r[r.length-1].src)}if(!e)throw new Error("Automatic publicPath is not supported in this browser");e=e.replace(/#.*$/,"").replace(/\?.*$/,"").replace(/\/[^\/]+$/,"/"),f.p=e+"../../"})(),(()=>{var e=e=>{var t=e=>e.split(".").map((e=>+e==e?+e:e)),r=/^([^-+]+)?(?:-([^+]+))?(?:\+(.+))?$/.exec(e),n=r[1]?t(r[1]):[];return r[2]&&(n.length++,n.push.apply(n,t(r[2]))),r[3]&&(n.push([]),n.push.apply(n,t(r[3]))),n},t=(t,r)=>{t=e(t),r=e(r);for(var n=0;;){if(n>=t.length)return n<r.length&&"u"!=(typeof r[n])[0];var a=t[n],i=(typeof a)[0];if(n>=r.length)return"u"==i;var o=r[n],l=(typeof o)[0];if(i!=l)return"o"==i&&"n"==l||"s"==l||"u"==i;if("o"!=i&&"u"!=i&&a!=o)return a<o;n++}},r=e=>{var t=e[0],n="";if(1===e.length)return"*";if(t+.5){n+=0==t?">=":-1==t?"<":1==t?"^":2==t?"~":t>0?"=":"!=";for(var a=1,i=1;i<e.length;i++)a--,n+="u"==(typeof(l=e[i]))[0]?"-":(a>0?".":"")+(a=2,l);return n}var o=[];for(i=1;i<e.length;i++){var l=e[i];o.push(0===l?"not("+u()+")":1===l?"("+u()+" || "+u()+")":2===l?o.pop()+" "+o.pop():r(l))}return u();function u(){return o.pop().replace(/^\((.+)\)$/,"$1")}},n=(t,r)=>{if(0 in t){r=e(r);var a=t[0],i=a<0;i&&(a=-a-1);for(var o=0,l=1,u=!0;;l++,o++){var f,s,d=l<t.length?(typeof t[l])[0]:"";if(o>=r.length||"o"==(s=(typeof(f=r[o]))[0]))return!u||("u"==d?l>a&&!i:""==d!=i);if("u"==s){if(!u||"u"!=d)return!1}else if(u)if(d==s)if(l<=a){if(f!=t[l])return!1}else{if(i?f>t[l]:f<t[l])return!1;f!=t[l]&&(u=!1)}else if("s"!=d&&"n"!=d){if(i||l<=a)return!1;u=!1,l--}else{if(l<=a||s<d!=i)return!1;u=!1}else"s"!=d&&"n"!=d&&(u=!1,l--)}}var c=[],p=c.pop.bind(c);for(o=1;o<t.length;o++){var h=t[o];c.push(1==h?p()|p():2==h?p()&p():h?n(h,r):!p())}return!!p()},a=(e,a,o,l)=>{var u=((e,r)=>{var n=e[r];return Object.keys(n).reduce(((e,r)=>!e||!n[e].loaded&&t(e,r)?r:e),0)})(e,o);return n(l,u)||"undefined"!=typeof console&&console.warn&&console.warn(((e,t,n)=>"Unsatisfied version "+t+" of shared singleton module "+e+" (required "+r(n)+")")(o,u,l)),i(e[o][u])},i=e=>(e.loaded=1,e.get()),o=e=>function(t,r,n,a){var i=f.I(t);return i&&i.then?i.then(e.bind(e,t,f.S[t],r,n,a)):e(t,f.S[t],r,n,a)},l=o(((e,r,n,a)=>r&&f.o(r,n)?i(((e,r)=>{var n=e[r];return(r=Object.keys(n).reduce(((e,r)=>!e||t(e,r)?r:e),0))&&n[r]})(r,n)):a())),u=o(((e,t,r,n,i)=>t&&f.o(t,r)?a(t,0,r,n):i())),s={},d={76005:()=>u("default","react",[4,17,0,1],(()=>f.e(4934).then((()=>()=>f(2784))))),12801:()=>u("default","react",[,[1,17,0,0],[1,16,0,0],[1,15,0,0],[2,0,14,0],1,1,1],(()=>Promise.all([f.e(2658),f.e(4934)]).then((()=>()=>f(2784))))),58172:()=>u("default","react",[0,15],(()=>Promise.all([f.e(2658),f.e(4934)]).then((()=>()=>f(2784))))),10529:()=>u("default","react-dom",[1,16,14,0],(()=>Promise.all([f.e(2658),f.e(6673),f.e(2153),f.e(6005)]).then((()=>()=>f(28316))))),82883:()=>u("default","redux",[,[1,4,0,0,,0],[1,3,0,0],[1,2,0,0],1,1],(()=>Promise.all([f.e(7193),f.e(7494)]).then((()=>()=>f(18717))))),93304:()=>u("default","react",[,[1,17],[1,16,8,3],1],(()=>Promise.all([f.e(2658),f.e(4934)]).then((()=>()=>f(2784))))),29802:()=>u("default","jss",[4,10,5,1],(()=>Promise.all([f.e(1554),f.e(6522)]).then((()=>()=>f(87359))))),44324:()=>u("default","jss",[1,10,5,1],(()=>Promise.all([f.e(1554),f.e(6522)]).then((()=>()=>f(87359))))),59037:()=>u("default","react",[,[1,17,0,0],[1,16,8,0],1],(()=>Promise.all([f.e(2658),f.e(4934)]).then((()=>()=>f(2784))))),12196:()=>u("default","react",[0,16,6,0],(()=>Promise.all([f.e(2658),f.e(4934)]).then((()=>()=>f(2784))))),54489:()=>u("default","react-dom",[0,16,6,0],(()=>Promise.all([f.e(2658),f.e(6673),f.e(2153),f.e(6005)]).then((()=>()=>f(28316))))),75696:()=>u("default","@material-ui/styles",[1,4,11,3],(()=>Promise.all([f.e(8171),f.e(6203),f.e(3687),f.e(6743),f.e(9096),f.e(9884),f.e(3627),f.e(7507),f.e(2544),f.e(6990),f.e(388),f.e(4888)]).then((()=>()=>f(95949))))),88576:()=>u("default","react-dom",[,[1,17,0,0],[1,16,8,0],1],(()=>Promise.all([f.e(2658),f.e(6673),f.e(2153),f.e(6005)]).then((()=>()=>f(28316))))),76982:()=>u("default","react",[,[4,16,8,0],[0],2],(()=>Promise.all([f.e(2658),f.e(4934)]).then((()=>()=>f(2784))))),840:()=>u("default","react",[0,16,8,0],(()=>f.e(4934).then((()=>()=>f(2784))))),2411:()=>u("default","react",[1,17,0,1],(()=>f.e(4934).then((()=>()=>f(2784))))),19374:()=>u("default","react-redux",[1,7,1,1],(()=>Promise.all([f.e(3251),f.e(6558)]).then((()=>()=>f(21725))))),25773:()=>u("default","redux",[1,4,0,4],(()=>Promise.all([f.e(7193),f.e(7494)]).then((()=>()=>f(18717))))),38670:()=>u("default","react-redux",[1,7,2,2],(()=>Promise.all([f.e(3251),f.e(6558)]).then((()=>()=>f(21725))))),42367:()=>u("default","react",[1,16,8,5],(()=>f.e(4934).then((()=>()=>f(2784))))),44808:()=>l("default","@material-ui/styles",(()=>Promise.all([f.e(8171),f.e(6203),f.e(3687),f.e(6743),f.e(9096),f.e(9884),f.e(3627),f.e(7507),f.e(2544),f.e(6990),f.e(388),f.e(4888)]).then((()=>()=>f(95949))))),45090:()=>u("default","notistack",[1,1,0,5],(()=>f.e(5543).then((()=>()=>f(87442))))),48049:()=>u("default","react-router-dom",[1,5,2,0],(()=>Promise.all([f.e(4486),f.e(393),f.e(5491),f.e(371),f.e(3206),f.e(7692),f.e(1151),f.e(681)]).then((()=>()=>f(95074))))),48565:()=>u("default","react",[1,16,8,0],(()=>f.e(4934).then((()=>()=>f(2784))))),53110:()=>u("default","react",[4,16,8,4],(()=>f.e(4934).then((()=>()=>f(2784))))),53207:()=>u("default","react",[1,16,0,0],(()=>f.e(4934).then((()=>()=>f(2784))))),57046:()=>u("default","react-dom",[1,16,8,5],(()=>Promise.all([f.e(6673),f.e(2153),f.e(6005)]).then((()=>()=>f(28316))))),61552:()=>u("default","react",[1,16,8,6],(()=>f.e(4934).then((()=>()=>f(2784))))),63135:()=>u("default","react-i18next",[1,11,8,9],(()=>Promise.all([f.e(9213),f.e(1858),f.e(8019)]).then((()=>()=>f(74211))))),69583:()=>u("default","react",[0,16],(()=>f.e(4934).then((()=>()=>f(2784))))),75540:()=>u("default","react",[1,16,8,4],(()=>f.e(4934).then((()=>()=>f(2784))))),81934:()=>u("default","react",[0,16,8],(()=>f.e(4934).then((()=>()=>f(2784))))),88901:()=>u("default","react",[1,17,0,0],(()=>f.e(4934).then((()=>()=>f(2784))))),95609:()=>u("default","react",[,[4,0,14,7],[0],2],(()=>f.e(4934).then((()=>()=>f(2784))))),43006:()=>u("default","react-dom",[1,17,0,1],(()=>Promise.all([f.e(6673),f.e(2153),f.e(6005)]).then((()=>()=>f(28316)))))};[59037,12196,54489,75696,88576,76982,840,2411,19374,25773,38670,42367,44808,45090,48049,48565,53110,53207,57046,61552,63135,69583,75540,81934,88901,95609,43006].forEach((e=>{f.m[e]=t=>{s[e]=0,delete f.c[e];var r=d[e]();if("function"!=typeof r)throw new Error("Shared module is not available for eager consumption: "+e);t.exports=r()}}));var c={681:[12801,58172],4888:[29802,44324],6005:[76005],6558:[10529,82883,93304]};f.f.consumes=(e,t)=>{f.o(c,e)&&c[e].forEach((e=>{if(f.o(s,e))return t.push(s[e]);var r=t=>{s[e]=0,f.m[e]=r=>{delete f.c[e],r.exports=t()}},n=t=>{delete s[e],f.m[e]=r=>{throw delete f.c[e],t}};try{var a=d[e]();a.then?t.push(s[e]=a.then(r).catch(n)):r(a)}catch(e){n(e)}}))}})(),(()=>{var e={179:0,9037:0,4017:0,6982:0,395:0};f.f.j=(t,r)=>{var n=f.o(e,t)?e[t]:void 0;if(0!==n)if(n)r.push(n[2]);else if(/^(6(005|558|81|982)|395|4017|4888|9037)$/.test(t))e[t]=0;else{var a=new Promise(((r,a)=>n=e[t]=[r,a]));r.push(n[2]=a);var i=f.p+f.u(t),o=new Error;f.l(i,(r=>{if(f.o(e,t)&&(0!==(n=e[t])&&(e[t]=void 0),n)){var a=r&&("load"===r.type?"missing":r.type),i=r&&r.target&&r.target.src;o.message="Loading chunk "+t+" failed.\n("+a+": "+i+")",o.name="ChunkLoadError",o.type=a,o.request=i,n[1](o)}}),"chunk-"+t,t)}},f.O.j=t=>0===e[t];var t=(t,r)=>{var n,a,[i,o,l]=r,u=0;for(n in o)f.o(o,n)&&(f.m[n]=o[n]);if(l)var s=l(f);for(t&&t(r);u<i.length;u++)a=i[u],f.o(e,a)&&e[a]&&e[a][0](),e[i[u]]=0;return f.O(s)},r=self.webpackChunk_unified_report=self.webpackChunk_unified_report||[];r.forEach(t.bind(null,0)),r.push=t.bind(null,r.push.bind(r))})();var s=f.O(void 0,[7717,1048,8171,6529,2658,4451,1554,7468,4951,597,9130,633,4940,7254,1581,1174,6740,8606,9766,238,4120,2105,2861,7308,6813,1297,5189,4618,6786,3845,3826,5015,9343,6832,7269,422,7131,9827,3704,3590,9203,4005,7220,7847,1540,4347,4400,8593,3710,9037,4017,6982,4404,395],(()=>f(33216)));s=f.O(s)})();