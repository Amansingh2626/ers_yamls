var admin;(()=>{"use strict";var e,r,t,i,n,a,o,u,s,l,f,d,c,p,h,v={9630:(e,r,t)=>{var i={"./App":()=>Promise.all([t.e(171),t.e(48),t.e(451),t.e(37),t.e(525),t.e(415)]).then((()=>()=>t(3415))),"./Routes":()=>t.e(221).then((()=>()=>t(8221)))},n=(e,r)=>(t.R=r,r=t.o(i,e)?i[e]():Promise.resolve().then((()=>{throw new Error('Module "'+e+'" does not exist in container.')})),t.R=void 0,r),a=(e,r)=>{if(t.S){var i=t.S.default,n="default";if(i&&i!==e)throw new Error("Container initialization failed as it has already been initialized with a different share scope");return t.S[n]=e,t.I(n,r)}};t.d(r,{get:()=>n,init:()=>a})}},m={};function g(e){var r=m[e];if(void 0!==r)return r.exports;var t=m[e]={exports:{}};return v[e](t,t.exports,g),t.exports}g.m=v,g.c=m,e=[],g.O=(r,t,i,n)=>{if(!t){var a=1/0;for(s=0;s<e.length;s++){for(var[t,i,n]=e[s],o=!0,u=0;u<t.length;u++)(!1&n||a>=n)&&Object.keys(g.O).every((e=>g.O[e](t[u])))?t.splice(u--,1):(o=!1,n<a&&(a=n));o&&(e.splice(s--,1),r=i())}return r}n=n||0;for(var s=e.length;s>0&&e[s-1][2]>n;s--)e[s]=e[s-1];e[s]=[t,i,n]},g.n=e=>{var r=e&&e.__esModule?()=>e.default:()=>e;return g.d(r,{a:r}),r},g.d=(e,r)=>{for(var t in r)g.o(r,t)&&!g.o(e,t)&&Object.defineProperty(e,t,{enumerable:!0,get:r[t]})},g.f={},g.e=e=>Promise.all(Object.keys(g.f).reduce(((r,t)=>(g.f[t](e,r),r)),[])),g.u=e=>171===e?"static/js/unified.material-ui.25cda9b9.js":48===e?"static/js/unified.prop-types.26644485.js":451===e?"static/js/unified.clsx.7b09ce78.js":37===e?"static/js/37.2c2eac64.js":525===e?"static/js/525.8db14e98.js":"static/js/"+({96:"unified.jss-plugin-global",151:"unified.value-equal",153:"unified.scheduler",203:"unified.is-in-browser",206:"unified.mini-create-react-context",371:"unified.path-to-regexp",388:"unified.hyphenate-style-name",393:"unified.history",486:"unified.react-router",491:"unified.react-router-dom",507:"unified.jss-plugin-vendor-prefixer",522:"unified.jss",544:"unified.jss-plugin-camel-case",627:"unified.jss-plugin-rule-value-function",658:"unified.object-assign",673:"unified.react-dom",687:"unified.css-vendor",692:"unified.resolve-pathname",717:"unified.hoist-non-react-statics",743:"unified.jss-plugin-default-unit",884:"unified.jss-plugin-nested",934:"unified.react",940:"unified.tiny-invariant",990:"unified.jss-plugin-props-sort"}[e]||e)+"."+{5:"d938fa09",96:"0de1dcab",151:"44cfc8c6",153:"1b628bc8",203:"d07e7ba6",206:"b0f545a9",221:"be0caa01",371:"a7a39595",388:"4ed551dc",393:"c0bc6269",415:"db2fd4ea",486:"8471b41e",491:"5b8c488c",507:"adae1254",522:"26e6ec71",544:"c9c1d226",627:"c97dc7f4",658:"fa1cf069",673:"9f181854",681:"10eef9a3",687:"97bd7ae9",692:"379c89f4",717:"424a3b9a",743:"73d40849",884:"9b6c8ca7",934:"d7722644",940:"4a75717e",990:"d79f7186"}[e]+".chunk.js",g.g=function(){if("object"==typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"==typeof window)return window}}(),g.o=(e,r)=>Object.prototype.hasOwnProperty.call(e,r),r={},t="@unified/admin:",g.l=(e,i,n,a)=>{if(r[e])r[e].push(i);else{var o,u;if(void 0!==n)for(var s=document.getElementsByTagName("script"),l=0;l<s.length;l++){var f=s[l];if(f.getAttribute("src")==e||f.getAttribute("data-webpack")==t+n){o=f;break}}o||(u=!0,(o=document.createElement("script")).charset="utf-8",o.timeout=120,g.nc&&o.setAttribute("nonce",g.nc),o.setAttribute("data-webpack",t+n),o.src=e),r[e]=[i];var d=(t,i)=>{o.onerror=o.onload=null,clearTimeout(c);var n=r[e];if(delete r[e],o.parentNode&&o.parentNode.removeChild(o),n&&n.forEach((e=>e(i))),t)return t(i)},c=setTimeout(d.bind(null,void 0,{type:"timeout",target:o}),12e4);o.onerror=d.bind(null,o.onerror),o.onload=d.bind(null,o.onload),u&&document.head.appendChild(o)}},g.r=e=>{"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},(()=>{g.S={};var e={},r={};g.I=(t,i)=>{i||(i=[]);var n=r[t];if(n||(n=r[t]={}),!(i.indexOf(n)>=0)){if(i.push(n),e[t])return e[t];g.o(g.S,t)||(g.S[t]={});var a=g.S[t],o="@unified/admin",u=(e,r,t,i)=>{var n=a[e]=a[e]||{},u=n[r];(!u||!u.loaded&&(!i!=!u.eager?i:o>u.from))&&(n[r]={get:t,from:o,eager:!!i})},s=[];switch(t){case"default":u("@material-ui/styles","4.11.3",(()=>Promise.all([g.e(171),g.e(717),g.e(48),g.e(451),g.e(522),g.e(687),g.e(743),g.e(96),g.e(884),g.e(627),g.e(507),g.e(544),g.e(990),g.e(203),g.e(388),g.e(37)]).then((()=>()=>g(5949))))),u("react-dom","17.0.1",(()=>Promise.all([g.e(658),g.e(153),g.e(673),g.e(5)]).then((()=>()=>g(8316))))),u("react-router-dom","5.2.0",(()=>Promise.all([g.e(717),g.e(48),g.e(486),g.e(393),g.e(491),g.e(371),g.e(206),g.e(692),g.e(151),g.e(940),g.e(681)]).then((()=>()=>g(5074))))),u("react","17.0.1",(()=>Promise.all([g.e(934),g.e(658)]).then((()=>()=>g(2784)))))}return e[t]=s.length?Promise.all(s).then((()=>e[t]=1)):1}}})(),(()=>{var e;g.g.importScripts&&(e=g.g.location+"");var r=g.g.document;if(!e&&r&&(r.currentScript&&(e=r.currentScript.src),!e)){var t=r.getElementsByTagName("script");t.length&&(e=t[t.length-1].src)}if(!e)throw new Error("Automatic publicPath is not supported in this browser");e=e.replace(/#.*$/,"").replace(/\?.*$/,"").replace(/\/[^\/]+$/,"/"),g.p=e})(),i=e=>{var r=e=>e.split(".").map((e=>+e==e?+e:e)),t=/^([^-+]+)?(?:-([^+]+))?(?:\+(.+))?$/.exec(e),i=t[1]?r(t[1]):[];return t[2]&&(i.length++,i.push.apply(i,r(t[2]))),t[3]&&(i.push([]),i.push.apply(i,r(t[3]))),i},n=(e,r)=>{e=i(e),r=i(r);for(var t=0;;){if(t>=e.length)return t<r.length&&"u"!=(typeof r[t])[0];var n=e[t],a=(typeof n)[0];if(t>=r.length)return"u"==a;var o=r[t],u=(typeof o)[0];if(a!=u)return"o"==a&&"n"==u||"s"==u||"u"==a;if("o"!=a&&"u"!=a&&n!=o)return n<o;t++}},a=e=>{var r=e[0],t="";if(1===e.length)return"*";if(r+.5){t+=0==r?">=":-1==r?"<":1==r?"^":2==r?"~":r>0?"=":"!=";for(var i=1,n=1;n<e.length;n++)i--,t+="u"==(typeof(u=e[n]))[0]?"-":(i>0?".":"")+(i=2,u);return t}var o=[];for(n=1;n<e.length;n++){var u=e[n];o.push(0===u?"not("+s()+")":1===u?"("+s()+" || "+s()+")":2===u?o.pop()+" "+o.pop():a(u))}return s();function s(){return o.pop().replace(/^\((.+)\)$/,"$1")}},o=(e,r)=>{if(0 in e){r=i(r);var t=e[0],n=t<0;n&&(t=-t-1);for(var a=0,u=1,s=!0;;u++,a++){var l,f,d=u<e.length?(typeof e[u])[0]:"";if(a>=r.length||"o"==(f=(typeof(l=r[a]))[0]))return!s||("u"==d?u>t&&!n:""==d!=n);if("u"==f){if(!s||"u"!=d)return!1}else if(s)if(d==f)if(u<=t){if(l!=e[u])return!1}else{if(n?l>e[u]:l<e[u])return!1;l!=e[u]&&(s=!1)}else if("s"!=d&&"n"!=d){if(n||u<=t)return!1;s=!1,u--}else{if(u<=t||f<d!=n)return!1;s=!1}else"s"!=d&&"n"!=d&&(s=!1,u--)}}var c=[],p=c.pop.bind(c);for(a=1;a<e.length;a++){var h=e[a];c.push(1==h?p()|p():2==h?p()&p():h?o(h,r):!p())}return!!p()},u=(e,r)=>{var t=e[r];return Object.keys(t).reduce(((e,r)=>!e||!t[e].loaded&&n(e,r)?r:e),0)},s=(e,r,t)=>"Unsatisfied version "+r+" of shared singleton module "+e+" (required "+a(t)+")",l=(e,r,t,i)=>{var n=u(e,t);return o(i,n)||"undefined"!=typeof console&&console.warn&&console.warn(s(t,n,i)),f(e[t][n])},f=e=>(e.loaded=1,e.get()),d=(e=>function(r,t,i,n){var a=g.I(r);return a&&a.then?a.then(e.bind(e,r,g.S[r],t,i,n)):e(0,g.S[r],t,i,n)})(((e,r,t,i,n)=>r&&g.o(r,t)?l(r,0,t,i):n())),c={},p={9037:()=>d("default","react",[,[1,17,0,0],[1,16,8,0],1],(()=>Promise.all([g.e(934),g.e(658)]).then((()=>()=>g(2784))))),6005:()=>d("default","react",[4,17,0,1],(()=>g.e(934).then((()=>()=>g(2784))))),2801:()=>d("default","react",[,[1,17,0,0],[1,16,0,0],[1,15,0,0],[2,0,14,0],1,1,1],(()=>Promise.all([g.e(934),g.e(658)]).then((()=>()=>g(2784))))),8172:()=>d("default","react",[0,15],(()=>Promise.all([g.e(934),g.e(658)]).then((()=>()=>g(2784))))),2411:()=>d("default","react",[1,17,0,1],(()=>Promise.all([g.e(934),g.e(658)]).then((()=>()=>g(2784))))),5696:()=>d("default","@material-ui/styles",[1,4,11,3],(()=>Promise.all([g.e(171),g.e(717),g.e(522),g.e(687),g.e(743),g.e(96),g.e(884),g.e(627),g.e(507),g.e(544),g.e(990),g.e(203),g.e(388)]).then((()=>()=>g(5949)))))},h={5:[6005],37:[9037],525:[2411,5696],681:[2801,8172]},g.f.consumes=(e,r)=>{g.o(h,e)&&h[e].forEach((e=>{if(g.o(c,e))return r.push(c[e]);var t=r=>{c[e]=0,g.m[e]=t=>{delete g.c[e],t.exports=r()}},i=r=>{delete c[e],g.m[e]=t=>{throw delete g.c[e],r}};try{var n=p[e]();n.then?r.push(c[e]=n.then(t).catch(i)):t(n)}catch(e){i(e)}}))},(()=>{var e={328:0,277:0};g.f.j=(r,t)=>{var i=g.o(e,r)?e[r]:void 0;if(0!==i)if(i)t.push(i[2]);else if(/^(277|37|5|525|681)$/.test(r))e[r]=0;else{var n=new Promise(((t,n)=>i=e[r]=[t,n]));t.push(i[2]=n);var a=g.p+g.u(r),o=new Error;g.l(a,(t=>{if(g.o(e,r)&&(0!==(i=e[r])&&(e[r]=void 0),i)){var n=t&&("load"===t.type?"missing":t.type),a=t&&t.target&&t.target.src;o.message="Loading chunk "+r+" failed.\n("+n+": "+a+")",o.name="ChunkLoadError",o.type=n,o.request=a,i[1](o)}}),"chunk-"+r,r)}},g.O.j=r=>0===e[r];var r=(r,t)=>{var i,n,[a,o,u]=t,s=0;for(i in o)g.o(o,i)&&(g.m[i]=o[i]);if(u)var l=u(g);for(r&&r(t);s<a.length;s++)n=a[s],g.o(e,n)&&e[n]&&e[n][0](),e[a[s]]=0;return g.O(l)},t=self.webpackChunk_unified_admin=self.webpackChunk_unified_admin||[];t.forEach(r.bind(null,0)),t.push=r.bind(null,t.push.bind(t))})();var b=g.O(void 0,[277],(()=>g(9630)));b=g.O(b),admin=b})();