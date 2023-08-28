(self.webpackChunk_unified_campaign=self.webpackChunk_unified_campaign||[]).push([[4951],{34126:(r,e,t)=>{"use strict";const n=t(57785),a=t(15554),o=t(15452),s=t(40063);function i(r){if("string"!=typeof r||1!==r.length)throw new TypeError("arrayFormatSeparator must be single character string")}function c(r,e){return e.encode?e.strict?n(r):encodeURIComponent(r):r}function u(r,e){return e.decode?a(r):r}function l(r){return Array.isArray(r)?r.sort():"object"==typeof r?l(Object.keys(r)).sort(((r,e)=>Number(r)-Number(e))).map((e=>r[e])):r}function p(r){const e=r.indexOf("#");return-1!==e&&(r=r.slice(0,e)),r}function f(r){const e=(r=p(r)).indexOf("?");return-1===e?"":r.slice(e+1)}function y(r,e){return e.parseNumbers&&!Number.isNaN(Number(r))&&"string"==typeof r&&""!==r.trim()?r=Number(r):!e.parseBooleans||null===r||"true"!==r.toLowerCase()&&"false"!==r.toLowerCase()||(r="true"===r.toLowerCase()),r}function m(r,e){i((e=Object.assign({decode:!0,sort:!0,arrayFormat:"none",arrayFormatSeparator:",",parseNumbers:!1,parseBooleans:!1},e)).arrayFormatSeparator);const t=function(r){let e;switch(r.arrayFormat){case"index":return(r,t,n)=>{e=/\[(\d*)\]$/.exec(r),r=r.replace(/\[\d*\]$/,""),e?(void 0===n[r]&&(n[r]={}),n[r][e[1]]=t):n[r]=t};case"bracket":return(r,t,n)=>{e=/(\[\])$/.exec(r),r=r.replace(/\[\]$/,""),e?void 0!==n[r]?n[r]=[].concat(n[r],t):n[r]=[t]:n[r]=t};case"comma":case"separator":return(e,t,n)=>{const a="string"==typeof t&&t.includes(r.arrayFormatSeparator),o="string"==typeof t&&!a&&u(t,r).includes(r.arrayFormatSeparator);t=o?u(t,r):t;const s=a||o?t.split(r.arrayFormatSeparator).map((e=>u(e,r))):null===t?t:u(t,r);n[e]=s};default:return(r,e,t)=>{void 0!==t[r]?t[r]=[].concat(t[r],e):t[r]=e}}}(e),n=Object.create(null);if("string"!=typeof r)return n;if(!(r=r.trim().replace(/^[?#&]/,"")))return n;for(const a of r.split("&")){if(""===a)continue;let[r,s]=o(e.decode?a.replace(/\+/g," "):a,"=");s=void 0===s?null:["comma","separator"].includes(e.arrayFormat)?s:u(s,e),t(u(r,e),s,n)}for(const r of Object.keys(n)){const t=n[r];if("object"==typeof t&&null!==t)for(const r of Object.keys(t))t[r]=y(t[r],e);else n[r]=y(t,e)}return!1===e.sort?n:(!0===e.sort?Object.keys(n).sort():Object.keys(n).sort(e.sort)).reduce(((r,e)=>{const t=n[e];return Boolean(t)&&"object"==typeof t&&!Array.isArray(t)?r[e]=l(t):r[e]=t,r}),Object.create(null))}e.extract=f,e.parse=m,e.stringify=(r,e)=>{if(!r)return"";i((e=Object.assign({encode:!0,strict:!0,arrayFormat:"none",arrayFormatSeparator:","},e)).arrayFormatSeparator);const t=t=>e.skipNull&&null==r[t]||e.skipEmptyString&&""===r[t],n=function(r){switch(r.arrayFormat){case"index":return e=>(t,n)=>{const a=t.length;return void 0===n||r.skipNull&&null===n||r.skipEmptyString&&""===n?t:null===n?[...t,[c(e,r),"[",a,"]"].join("")]:[...t,[c(e,r),"[",c(a,r),"]=",c(n,r)].join("")]};case"bracket":return e=>(t,n)=>void 0===n||r.skipNull&&null===n||r.skipEmptyString&&""===n?t:null===n?[...t,[c(e,r),"[]"].join("")]:[...t,[c(e,r),"[]=",c(n,r)].join("")];case"comma":case"separator":return e=>(t,n)=>null==n||0===n.length?t:0===t.length?[[c(e,r),"=",c(n,r)].join("")]:[[t,c(n,r)].join(r.arrayFormatSeparator)];default:return e=>(t,n)=>void 0===n||r.skipNull&&null===n||r.skipEmptyString&&""===n?t:null===n?[...t,c(e,r)]:[...t,[c(e,r),"=",c(n,r)].join("")]}}(e),a={};for(const e of Object.keys(r))t(e)||(a[e]=r[e]);const o=Object.keys(a);return!1!==e.sort&&o.sort(e.sort),o.map((t=>{const a=r[t];return void 0===a?"":null===a?c(t,e):Array.isArray(a)?a.reduce(n(t),[]).join("&"):c(t,e)+"="+c(a,e)})).filter((r=>r.length>0)).join("&")},e.parseUrl=(r,e)=>{e=Object.assign({decode:!0},e);const[t,n]=o(r,"#");return Object.assign({url:t.split("?")[0]||"",query:m(f(r),e)},e&&e.parseFragmentIdentifier&&n?{fragmentIdentifier:u(n,e)}:{})},e.stringifyUrl=(r,t)=>{t=Object.assign({encode:!0,strict:!0},t);const n=p(r.url).split("?")[0]||"",a=e.extract(r.url),o=e.parse(a,{sort:!1}),s=Object.assign(o,r.query);let i=e.stringify(s,t);i&&(i=`?${i}`);let u=function(r){let e="";const t=r.indexOf("#");return-1!==t&&(e=r.slice(t)),e}(r.url);return r.fragmentIdentifier&&(u=`#${c(r.fragmentIdentifier,t)}`),`${n}${i}${u}`},e.pick=(r,t,n)=>{n=Object.assign({parseFragmentIdentifier:!0},n);const{url:a,query:o,fragmentIdentifier:i}=e.parseUrl(r,n);return e.stringifyUrl({url:a,query:s(o,t),fragmentIdentifier:i},n)},e.exclude=(r,t,n)=>{const a=Array.isArray(t)?r=>!t.includes(r):(r,e)=>!t(r,e);return e.pick(r,a,n)}}}]);