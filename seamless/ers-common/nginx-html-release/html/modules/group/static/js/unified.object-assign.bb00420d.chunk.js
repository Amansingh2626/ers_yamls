/*! For license information please see unified.object-assign.bb00420d.chunk.js.LICENSE.txt */
(self.webpackChunk_unified_group=self.webpackChunk_unified_group||[]).push([[2658],{37320:r=>{"use strict";var e=Object.getOwnPropertySymbols,t=Object.prototype.hasOwnProperty,n=Object.prototype.propertyIsEnumerable;function o(r){if(null==r)throw new TypeError("Object.assign cannot be called with null or undefined");return Object(r)}r.exports=function(){try{if(!Object.assign)return!1;var r=new String("abc");if(r[5]="de","5"===Object.getOwnPropertyNames(r)[0])return!1;for(var e={},t=0;t<10;t++)e["_"+String.fromCharCode(t)]=t;if("0123456789"!==Object.getOwnPropertyNames(e).map((function(r){return e[r]})).join(""))return!1;var n={};return"abcdefghijklmnopqrst".split("").forEach((function(r){n[r]=r})),"abcdefghijklmnopqrst"===Object.keys(Object.assign({},n)).join("")}catch(r){return!1}}()?Object.assign:function(r,c){for(var i,a,u=o(r),f=1;f<arguments.length;f++){for(var s in i=Object(arguments[f]))t.call(i,s)&&(u[s]=i[s]);if(e){a=e(i);for(var p=0;p<a.length;p++)n.call(i,a[p])&&(u[a[p]]=i[a[p]])}}return u}}}]);