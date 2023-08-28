var customer;(()=>{"use strict";var e,r,t,n,o,i,a,u,f,l,s,d,c,p,h,v={428:(e,r,t)=>{var n={"./Theme":()=>Promise.all([t.e(48),t.e(171),t.e(174),t.e(832),t.e(131),t.e(652)]).then((()=>()=>t(652))),"./Logo":()=>Promise.all([t.e(411),t.e(565),t.e(396)]).then((()=>()=>t(396))),"./NavLogo":()=>Promise.all([t.e(411),t.e(565),t.e(414)]).then((()=>()=>t(414)))},o=(e,r)=>(t.R=r,r=t.o(n,e)?n[e]():Promise.resolve().then((()=>{throw new Error('Module "'+e+'" does not exist in container.')})),t.R=void 0,r),i=(e,r)=>{if(t.S){var n=t.S.default,o="default";if(n&&n!==e)throw new Error("Container initialization failed as it has already been initialized with a different share scope");return t.S[o]=e,t.I(o,r)}};t.d(r,{get:()=>o,init:()=>i})}},g={};function m(e){var r=g[e];if(void 0!==r)return r.exports;var t=g[e]={id:e,exports:{}};return v[e](t,t.exports,m),t.exports}m.m=v,m.c=g,e=[],m.O=(r,t,n,o)=>{if(!t){var i=1/0;for(f=0;f<e.length;f++){for(var[t,n,o]=e[f],a=!0,u=0;u<t.length;u++)(!1&o||i>=o)&&Object.keys(m.O).every((e=>m.O[e](t[u])))?t.splice(u--,1):(a=!1,o<i&&(i=o));a&&(e.splice(f--,1),r=n())}return r}o=o||0;for(var f=e.length;f>0&&e[f-1][2]>o;f--)e[f]=e[f-1];e[f]=[t,n,o]},m.n=e=>{var r=e&&e.__esModule?()=>e.default:()=>e;return m.d(r,{a:r}),r},m.d=(e,r)=>{for(var t in r)m.o(r,t)&&!m.o(e,t)&&Object.defineProperty(e,t,{enumerable:!0,get:r[t]})},m.f={},m.e=e=>Promise.all(Object.keys(m.f).reduce(((r,t)=>(m.f[t](e,r),r)),[])),m.u=e=>48===e?"static/js/unified.prop-types.e3e88197.js":411===e?"static/js/411.e947eadd.js":"static/js/"+({131:"unified.style-loader",153:"unified.scheduler",171:"unified.material-ui",174:"unified.fontsource",658:"unified.object-assign",673:"unified.react-dom",832:"unified.css-loader",934:"unified.react"}[e]||e)+"."+{5:"a1174332",131:"ab67b5cb",153:"0f2a3cd6",171:"d0b1d68b",174:"cb102f6e",396:"b6db4e56",414:"55f3d55b",565:"3bf75e80",652:"5994263a",658:"2e273af2",673:"3bdb7502",832:"3273e28d",934:"144e30e0"}[e]+".chunk.js",m.g=function(){if("object"==typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"==typeof window)return window}}(),m.o=(e,r)=>Object.prototype.hasOwnProperty.call(e,r),r={},t="@unified/config-vodafone:",m.l=(e,n,o,i)=>{if(r[e])r[e].push(n);else{var a,u;if(void 0!==o)for(var f=document.getElementsByTagName("script"),l=0;l<f.length;l++){var s=f[l];if(s.getAttribute("src")==e||s.getAttribute("data-webpack")==t+o){a=s;break}}a||(u=!0,(a=document.createElement("script")).charset="utf-8",a.timeout=120,m.nc&&a.setAttribute("nonce",m.nc),a.setAttribute("data-webpack",t+o),a.src=e),r[e]=[n];var d=(t,n)=>{a.onerror=a.onload=null,clearTimeout(c);var o=r[e];if(delete r[e],a.parentNode&&a.parentNode.removeChild(a),o&&o.forEach((e=>e(n))),t)return t(n)},c=setTimeout(d.bind(null,void 0,{type:"timeout",target:a}),12e4);a.onerror=d.bind(null,a.onerror),a.onload=d.bind(null,a.onload),u&&document.head.appendChild(a)}},m.r=e=>{"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},(()=>{m.S={};var e={},r={};m.I=(t,n)=>{n||(n=[]);var o=r[t];if(o||(o=r[t]={}),!(n.indexOf(o)>=0)){if(n.push(o),e[t])return e[t];m.o(m.S,t)||(m.S[t]={});var i=m.S[t],a="@unified/config-vodafone",u=(e,r,t,n)=>{var o=i[e]=i[e]||{},u=o[r];(!u||!u.loaded&&(!n!=!u.eager?n:a>u.from))&&(o[r]={get:t,from:a,eager:!!n})},f=[];switch(t){case"default":u("react-dom","17.0.1",(()=>Promise.all([m.e(658),m.e(153),m.e(673),m.e(5)]).then((()=>()=>m(316))))),u("react","17.0.1",(()=>Promise.all([m.e(934),m.e(658)]).then((()=>()=>m(784)))))}return e[t]=f.length?Promise.all(f).then((()=>e[t]=1)):1}}})(),(()=>{var e;m.g.importScripts&&(e=m.g.location+"");var r=m.g.document;if(!e&&r&&(r.currentScript&&(e=r.currentScript.src),!e)){var t=r.getElementsByTagName("script");t.length&&(e=t[t.length-1].src)}if(!e)throw new Error("Automatic publicPath is not supported in this browser");e=e.replace(/#.*$/,"").replace(/\?.*$/,"").replace(/\/[^\/]+$/,"/"),m.p=e})(),n=e=>{var r=e=>e.split(".").map((e=>+e==e?+e:e)),t=/^([^-+]+)?(?:-([^+]+))?(?:\+(.+))?$/.exec(e),n=t[1]?r(t[1]):[];return t[2]&&(n.length++,n.push.apply(n,r(t[2]))),t[3]&&(n.push([]),n.push.apply(n,r(t[3]))),n},o=(e,r)=>{e=n(e),r=n(r);for(var t=0;;){if(t>=e.length)return t<r.length&&"u"!=(typeof r[t])[0];var o=e[t],i=(typeof o)[0];if(t>=r.length)return"u"==i;var a=r[t],u=(typeof a)[0];if(i!=u)return"o"==i&&"n"==u||"s"==u||"u"==i;if("o"!=i&&"u"!=i&&o!=a)return o<a;t++}},i=e=>{var r=e[0],t="";if(1===e.length)return"*";if(r+.5){t+=0==r?">=":-1==r?"<":1==r?"^":2==r?"~":r>0?"=":"!=";for(var n=1,o=1;o<e.length;o++)n--,t+="u"==(typeof(u=e[o]))[0]?"-":(n>0?".":"")+(n=2,u);return t}var a=[];for(o=1;o<e.length;o++){var u=e[o];a.push(0===u?"not("+f()+")":1===u?"("+f()+" || "+f()+")":2===u?a.pop()+" "+a.pop():i(u))}return f();function f(){return a.pop().replace(/^\((.+)\)$/,"$1")}},a=(e,r)=>{if(0 in e){r=n(r);var t=e[0],o=t<0;o&&(t=-t-1);for(var i=0,u=1,f=!0;;u++,i++){var l,s,d=u<e.length?(typeof e[u])[0]:"";if(i>=r.length||"o"==(s=(typeof(l=r[i]))[0]))return!f||("u"==d?u>t&&!o:""==d!=o);if("u"==s){if(!f||"u"!=d)return!1}else if(f)if(d==s)if(u<=t){if(l!=e[u])return!1}else{if(o?l>e[u]:l<e[u])return!1;l!=e[u]&&(f=!1)}else if("s"!=d&&"n"!=d){if(o||u<=t)return!1;f=!1,u--}else{if(u<=t||s<d!=o)return!1;f=!1}else"s"!=d&&"n"!=d&&(f=!1,u--)}}var c=[],p=c.pop.bind(c);for(i=1;i<e.length;i++){var h=e[i];c.push(1==h?p()|p():2==h?p()&p():h?a(h,r):!p())}return!!p()},u=(e,r)=>{var t=e[r];return Object.keys(t).reduce(((e,r)=>!e||!t[e].loaded&&o(e,r)?r:e),0)},f=(e,r,t)=>"Unsatisfied version "+r+" of shared singleton module "+e+" (required "+i(t)+")",l=(e,r,t,n)=>{var o=u(e,t);return a(n,o)||"undefined"!=typeof console&&console.warn&&console.warn(f(t,o,n)),s(e[t][o])},s=e=>(e.loaded=1,e.get()),d=(e=>function(r,t,n,o){var i=m.I(r);return i&&i.then?i.then(e.bind(e,r,m.S[r],t,n,o)):e(0,m.S[r],t,n,o)})(((e,r,t,n,o)=>r&&m.o(r,t)?l(r,0,t,n):o())),c={},p={5:()=>d("default","react",[4,17,0,1],(()=>m.e(934).then((()=>()=>m(784))))),411:()=>d("default","react",[1,17,0,1],(()=>Promise.all([m.e(934),m.e(658)]).then((()=>()=>m(784)))))},h={5:[5],411:[411]},m.f.consumes=(e,r)=>{m.o(h,e)&&h[e].forEach((e=>{if(m.o(c,e))return r.push(c[e]);var t=r=>{c[e]=0,m.m[e]=t=>{delete m.c[e],t.exports=r()}},n=r=>{delete c[e],m.m[e]=t=>{throw delete m.c[e],r}};try{var o=p[e]();o.then?r.push(c[e]=o.then(t).catch(n)):t(o)}catch(e){n(e)}}))},(()=>{var e={727:0,988:0};m.f.j=(r,t)=>{var n=m.o(e,r)?e[r]:void 0;if(0!==n)if(n)t.push(n[2]);else if(/^(411|5|988)$/.test(r))e[r]=0;else{var o=new Promise(((t,o)=>n=e[r]=[t,o]));t.push(n[2]=o);var i=m.p+m.u(r),a=new Error;m.l(i,(t=>{if(m.o(e,r)&&(0!==(n=e[r])&&(e[r]=void 0),n)){var o=t&&("load"===t.type?"missing":t.type),i=t&&t.target&&t.target.src;a.message="Loading chunk "+r+" failed.\n("+o+": "+i+")",a.name="ChunkLoadError",a.type=o,a.request=i,n[1](a)}}),"chunk-"+r,r)}},m.O.j=r=>0===e[r];var r=(r,t)=>{var n,o,[i,a,u]=t,f=0;for(n in a)m.o(a,n)&&(m.m[n]=a[n]);if(u)var l=u(m);for(r&&r(t);f<i.length;f++)o=i[f],m.o(e,o)&&e[o]&&e[o][0](),e[i[f]]=0;return m.O(l)},t=self.webpackChunk_unified_config_vodafone=self.webpackChunk_unified_config_vodafone||[];t.forEach(r.bind(null,0)),t.push=r.bind(null,t.push.bind(t))})();var b=m.O(void 0,[988],(()=>m(428)));b=m.O(b),customer=b})();