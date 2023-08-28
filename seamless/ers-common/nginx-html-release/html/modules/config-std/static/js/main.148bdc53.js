(()=>{"use strict";var e,r,t,n={216:(e,r,t)=>{var n=t(411),o=t(6);const a=function(){return n.createElement(n.Fragment,null)};t.n(o)().render(n.createElement(a,null),document.getElementById("root"))}},o={};function a(e){var r=o[e];if(void 0!==r)return r.exports;var t=o[e]={exports:{}};return n[e](t,t.exports,a),t.exports}a.m=n,a.c=o,e=[],a.O=(r,t,n,o)=>{if(!t){var i=1/0;for(f=0;f<e.length;f++){for(var[t,n,o]=e[f],u=!0,l=0;l<t.length;l++)(!1&o||i>=o)&&Object.keys(a.O).every((e=>a.O[e](t[l])))?t.splice(l--,1):(u=!1,o<i&&(i=o));u&&(e.splice(f--,1),r=n())}return r}o=o||0;for(var f=e.length;f>0&&e[f-1][2]>o;f--)e[f]=e[f-1];e[f]=[t,n,o]},a.n=e=>{var r=e&&e.__esModule?()=>e.default:()=>e;return a.d(r,{a:r}),r},a.d=(e,r)=>{for(var t in r)a.o(r,t)&&!a.o(e,t)&&Object.defineProperty(e,t,{enumerable:!0,get:r[t]})},a.f={},a.e=e=>Promise.all(Object.keys(a.f).reduce(((r,t)=>(a.f[t](e,r),r)),[])),a.u=e=>"static/js/"+({153:"unified.scheduler",658:"unified.object-assign",673:"unified.react-dom",934:"unified.react"}[e]||e)+"."+{5:"d3e35e2b",153:"c1478860",658:"6aebf575",673:"4a4be06a",934:"bdaaaee5"}[e]+".chunk.js",a.g=function(){if("object"==typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"==typeof window)return window}}(),a.o=(e,r)=>Object.prototype.hasOwnProperty.call(e,r),r={},t="@unified/config-std:",a.l=(e,n,o,i)=>{if(r[e])r[e].push(n);else{var u,l;if(void 0!==o)for(var f=document.getElementsByTagName("script"),s=0;s<f.length;s++){var c=f[s];if(c.getAttribute("src")==e||c.getAttribute("data-webpack")==t+o){u=c;break}}u||(l=!0,(u=document.createElement("script")).charset="utf-8",u.timeout=120,a.nc&&u.setAttribute("nonce",a.nc),u.setAttribute("data-webpack",t+o),u.src=e),r[e]=[n];var d=(t,n)=>{u.onerror=u.onload=null,clearTimeout(p);var o=r[e];if(delete r[e],u.parentNode&&u.parentNode.removeChild(u),o&&o.forEach((e=>e(n))),t)return t(n)},p=setTimeout(d.bind(null,void 0,{type:"timeout",target:u}),12e4);u.onerror=d.bind(null,u.onerror),u.onload=d.bind(null,u.onload),l&&document.head.appendChild(u)}},(()=>{a.S={};var e={},r={};a.I=(t,n)=>{n||(n=[]);var o=r[t];if(o||(o=r[t]={}),!(n.indexOf(o)>=0)){if(n.push(o),e[t])return e[t];a.o(a.S,t)||(a.S[t]={});var i=a.S[t],u="@unified/config-std",l=(e,r,t,n)=>{var o=i[e]=i[e]||{},a=o[r];(!a||!a.loaded&&(!n!=!a.eager?n:u>a.from))&&(o[r]={get:t,from:u,eager:!!n})},f=[];switch(t){case"default":l("react-dom","17.0.1",(()=>Promise.all([a.e(658),a.e(153),a.e(673),a.e(5)]).then((()=>()=>a(316))))),l("react","17.0.1",(()=>Promise.all([a.e(934),a.e(658)]).then((()=>()=>a(784)))))}return e[t]=f.length?Promise.all(f).then((()=>e[t]=1)):1}}})(),(()=>{var e;a.g.importScripts&&(e=a.g.location+"");var r=a.g.document;if(!e&&r&&(r.currentScript&&(e=r.currentScript.src),!e)){var t=r.getElementsByTagName("script");t.length&&(e=t[t.length-1].src)}if(!e)throw new Error("Automatic publicPath is not supported in this browser");e=e.replace(/#.*$/,"").replace(/\?.*$/,"").replace(/\/[^\/]+$/,"/"),a.p=e+"../../"})(),(()=>{var e=e=>{var r=e=>e.split(".").map((e=>+e==e?+e:e)),t=/^([^-+]+)?(?:-([^+]+))?(?:\+(.+))?$/.exec(e),n=t[1]?r(t[1]):[];return t[2]&&(n.length++,n.push.apply(n,r(t[2]))),t[3]&&(n.push([]),n.push.apply(n,r(t[3]))),n},r=e=>{var t=e[0],n="";if(1===e.length)return"*";if(t+.5){n+=0==t?">=":-1==t?"<":1==t?"^":2==t?"~":t>0?"=":"!=";for(var o=1,a=1;a<e.length;a++)o--,n+="u"==(typeof(u=e[a]))[0]?"-":(o>0?".":"")+(o=2,u);return n}var i=[];for(a=1;a<e.length;a++){var u=e[a];i.push(0===u?"not("+l()+")":1===u?"("+l()+" || "+l()+")":2===u?i.pop()+" "+i.pop():r(u))}return l();function l(){return i.pop().replace(/^\((.+)\)$/,"$1")}},t=(r,n)=>{if(0 in r){n=e(n);var o=r[0],a=o<0;a&&(o=-o-1);for(var i=0,u=1,l=!0;;u++,i++){var f,s,c=u<r.length?(typeof r[u])[0]:"";if(i>=n.length||"o"==(s=(typeof(f=n[i]))[0]))return!l||("u"==c?u>o&&!a:""==c!=a);if("u"==s){if(!l||"u"!=c)return!1}else if(l)if(c==s)if(u<=o){if(f!=r[u])return!1}else{if(a?f>r[u]:f<r[u])return!1;f!=r[u]&&(l=!1)}else if("s"!=c&&"n"!=c){if(a||u<=o)return!1;l=!1,u--}else{if(u<=o||s<c!=a)return!1;l=!1}else"s"!=c&&"n"!=c&&(l=!1,u--)}}var d=[],p=d.pop.bind(d);for(i=1;i<r.length;i++){var h=r[i];d.push(1==h?p()|p():2==h?p()&p():h?t(h,n):!p())}return!!p()},n=(r,t)=>{var n=r[t];return Object.keys(n).reduce(((r,t)=>!r||!n[r].loaded&&((r,t)=>{r=e(r),t=e(t);for(var n=0;;){if(n>=r.length)return n<t.length&&"u"!=(typeof t[n])[0];var o=r[n],a=(typeof o)[0];if(n>=t.length)return"u"==a;var i=t[n],u=(typeof i)[0];if(a!=u)return"o"==a&&"n"==u||"s"==u||"u"==a;if("o"!=a&&"u"!=a&&o!=i)return o<i;n++}})(r,t)?t:r),0)},o=(e,o,a,u)=>{var l=n(e,a);return t(u,l)||"undefined"!=typeof console&&console.warn&&console.warn(((e,t,n)=>"Unsatisfied version "+t+" of shared singleton module "+e+" (required "+r(n)+")")(a,l,u)),i(e[a][l])},i=e=>(e.loaded=1,e.get()),u=(e=>function(r,t,n,o){var i=a.I(r);return i&&i.then?i.then(e.bind(e,r,a.S[r],t,n,o)):e(r,a.S[r],t,n,o)})(((e,r,t,n,i)=>r&&a.o(r,t)?o(r,0,t,n):i())),l={},f={5:()=>u("default","react",[4,17,0,1],(()=>a.e(934).then((()=>()=>a(784))))),411:()=>u("default","react",[1,17,0,1],(()=>Promise.all([a.e(934),a.e(658)]).then((()=>()=>a(784))))),6:()=>u("default","react-dom",[1,17,0,1],(()=>Promise.all([a.e(658),a.e(153),a.e(673),a.e(5)]).then((()=>()=>a(316)))))};[411,6].forEach((e=>{a.m[e]=r=>{l[e]=0,delete a.c[e];var t=f[e]();if("function"!=typeof t)throw new Error("Shared module is not available for eager consumption: "+e);r.exports=t()}}));var s={5:[5]};a.f.consumes=(e,r)=>{a.o(s,e)&&s[e].forEach((e=>{if(a.o(l,e))return r.push(l[e]);var t=r=>{l[e]=0,a.m[e]=t=>{delete a.c[e],t.exports=r()}},n=r=>{delete l[e],a.m[e]=t=>{throw delete a.c[e],r}};try{var o=f[e]();o.then?r.push(l[e]=o.then(t).catch(n)):t(o)}catch(e){n(e)}}))}})(),(()=>{var e={179:0,411:0,988:0};a.f.j=(r,t)=>{var n=a.o(e,r)?e[r]:void 0;if(0!==n)if(n)t.push(n[2]);else if(/^(411|5|988)$/.test(r))e[r]=0;else{var o=new Promise(((t,o)=>n=e[r]=[t,o]));t.push(n[2]=o);var i=a.p+a.u(r),u=new Error;a.l(i,(t=>{if(a.o(e,r)&&(0!==(n=e[r])&&(e[r]=void 0),n)){var o=t&&("load"===t.type?"missing":t.type),i=t&&t.target&&t.target.src;u.message="Loading chunk "+r+" failed.\n("+o+": "+i+")",u.name="ChunkLoadError",u.type=o,u.request=i,n[1](u)}}),"chunk-"+r,r)}},a.O.j=r=>0===e[r];var r=(r,t)=>{var n,o,[i,u,l]=t,f=0;for(n in u)a.o(u,n)&&(a.m[n]=u[n]);if(l)var s=l(a);for(r&&r(t);f<i.length;f++)o=i[f],a.o(e,o)&&e[o]&&e[o][0](),e[i[f]]=0;return a.O(s)},t=self.webpackChunk_unified_config_std=self.webpackChunk_unified_config_std||[];t.forEach(r.bind(null,0)),t.push=r.bind(null,t.push.bind(t))})();var i=a.O(void 0,[411,988],(()=>a(216)));i=a.O(i)})();