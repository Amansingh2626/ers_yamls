(self.webpackChunk_unified_workflows=self.webpackChunk_unified_workflows||[]).push([[7220],{7296:t=>{"use strict";function e(t){this._maxSize=t,this.clear()}e.prototype.clear=function(){this._size=0,this._values=Object.create(null)},e.prototype.get=function(t){return this._values[t]},e.prototype.set=function(t,e){return this._size>=this._maxSize&&this.clear(),t in this._values||this._size++,this._values[t]=e};var n=/[^.^\]^[]+|(?=\[\]|\.\.)/g,r=/^\d+$/,i=/^\d/,u=/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/g,o=/^\s*(['"]?)(.*?)(\1)\s*$/,s=new e(512),c=new e(512),f=new e(512);function a(t){return s.get(t)||s.set(t,h(t).map((function(t){return t.replace(o,"$2")})))}function h(t){return t.match(n)}function l(t){return"string"==typeof t&&t&&-1!==["'",'"'].indexOf(t.charAt(0))}function p(t){return!l(t)&&(function(t){return t.match(i)&&!t.match(r)}(t)||function(t){return u.test(t)}(t))}t.exports={Cache:e,split:h,normalizePath:a,setter:function(t){var e=a(t);return c.get(t)||c.set(t,(function(t,n){for(var r=0,i=e.length,u=t;r<i-1;){var o=e[r];if("__proto__"===o||"constructor"===o||"prototype"===o)return t;u=u[e[r++]]}u[e[r]]=n}))},getter:function(t,e){var n=a(t);return f.get(t)||f.set(t,(function(t){for(var r=0,i=n.length;r<i;){if(null==t&&e)return;t=t[n[r++]]}return t}))},join:function(t){return t.reduce((function(t,e){return t+(l(e)||r.test(e)?"["+e+"]":(t?".":"")+e)}),"")},forEach:function(t,e,n){!function(t,e,n){var r,i,u,o,s=t.length;for(i=0;i<s;i++)(r=t[i])&&(p(r)&&(r='"'+r+'"'),u=!(o=l(r))&&/^\d+$/.test(r),e.call(n,r,o,u,i,t))}(Array.isArray(t)?t:h(t),e,n)}}}}]);