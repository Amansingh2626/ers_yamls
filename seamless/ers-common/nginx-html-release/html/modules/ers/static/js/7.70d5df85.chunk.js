(self.webpackChunk_unified_ers=self.webpackChunk_unified_ers||[]).push([[7],{6135:function(e,t){"use strict";var r=this&&this.__assign||function(){return(r=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var o in t=arguments[r])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e}).apply(this,arguments)};Object.defineProperty(t,"__esModule",{value:!0}),t.default=function(e){var t=r({},e);return window.config&&Object.keys(window.config).length>0&&Object.assign(t,window.config),t}},9691:(e,t,r)=>{"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=r(6135);Object.defineProperty(t,"default",{enumerable:!0,get:function(){return n.default}})},6866:(e,t,r)=>{"use strict";t.zL=t.jw=t.iE=void 0;var n=r(9691);Object.defineProperty(t,"iE",{enumerable:!0,get:function(){return n.default}});var o=r(8897);Object.defineProperty(t,"jw",{enumerable:!0,get:function(){return o.loadState}}),Object.defineProperty(t,"zL",{enumerable:!0,get:function(){return o.saveState}})},8897:(e,t)=>{"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.clearState=t.saveState=t.loadState=void 0,t.loadState=function(e){try{var t=localStorage.getItem(e);if(null===t)return;return JSON.parse(t)}catch(e){return}},t.saveState=function(e,t){try{var r=JSON.stringify(e);localStorage.setItem(t,r)}catch(e){}},t.clearState=function(e){try{localStorage.removeItem(e)}catch(e){}}},5007:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>n});const n=(0,r(6866).iE)({language:"en",protocol:"http",server:"localhost",port:"8084",service:"",locale:"/ers/locales/{{lng}}/{{ns}}.json",module:{name:"group",route:"group",url:"group/remoteEntry.js",scope:"group",module:"./App"},currency:"SZL"})}}]);
//# sourceMappingURL=7.70d5df85.chunk.js.map