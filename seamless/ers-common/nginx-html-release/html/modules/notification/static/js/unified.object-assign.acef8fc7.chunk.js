/*! For license information please see unified.object-assign.acef8fc7.chunk.js.LICENSE.txt */
(self.webpackChunk_unified_notification=self.webpackChunk_unified_notification||[]).push([[2658],{37320:e=>{"use strict";var t=Object.getOwnPropertySymbols,r=Object.prototype.hasOwnProperty,n=Object.prototype.propertyIsEnumerable;function i(e){if(null==e)throw new TypeError("Object.assign cannot be called with null or undefined");return Object(e)}e.exports=function(){try{if(!Object.assign)return!1;var e=new String("abc");if(e[5]="de","5"===Object.getOwnPropertyNames(e)[0])return!1;for(var t={},r=0;r<10;r++)t["_"+String.fromCharCode(r)]=r;if("0123456789"!==Object.getOwnPropertyNames(t).map((function(e){return t[e]})).join(""))return!1;var n={};return"abcdefghijklmnopqrst".split("").forEach((function(e){n[e]=e})),"abcdefghijklmnopqrst"===Object.keys(Object.assign({},n)).join("")}catch(e){return!1}}()?Object.assign:function(e,o){for(var c,a,f=i(e),s=1;s<arguments.length;s++){for(var u in c=Object(arguments[s]))r.call(c,u)&&(f[u]=c[u]);if(t){a=t(c);for(var b=0;b<a.length;b++)n.call(c,a[b])&&(f[a[b]]=c[a[b]])}}return f}}}]);