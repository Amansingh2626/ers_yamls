(self.webpackChunk_unified_report=self.webpackChunk_unified_report||[]).push([[4400],{66892:(t,o,r)=>{"use strict";r.d(o,{Oq:()=>m,dO:()=>p,jn:()=>n,iz:()=>u,Dz:()=>e,cv:()=>a,oc:()=>b});var i=r(61898),e=function(t){var o=t.top,r=t.right,i=t.bottom,e=t.left;return{top:o,right:r,bottom:i,left:e,width:r-e,height:i-o,x:e,y:o,center:{x:(r+e)/2,y:(i+o)/2}}},n=function(t,o){return{top:t.top-o.top,left:t.left-o.left,bottom:t.bottom+o.bottom,right:t.right+o.right}},d=function(t,o){return{top:t.top+o.top,left:t.left+o.left,bottom:t.bottom-o.bottom,right:t.right-o.right}},g={top:0,right:0,bottom:0,left:0},p=function(t){var o=t.borderBox,r=t.margin,i=void 0===r?g:r,p=t.border,f=void 0===p?g:p,a=t.padding,b=void 0===a?g:a,m=e(n(o,i)),u=e(d(o,f)),h=e(d(u,b));return{marginBox:m,borderBox:e(o),paddingBox:u,contentBox:h,margin:i,border:f,padding:b}},f=function(t){var o=t.slice(0,-2);if("px"!==t.slice(-2))return 0;var r=Number(o);return isNaN(r)&&(0,i.Z)(!1),r},a=function(t,o){var r,i,e=t.borderBox,n=t.border,d=t.margin,g=t.padding,f=(i=o,{top:(r=e).top+i.y,left:r.left+i.x,bottom:r.bottom+i.y,right:r.right+i.x});return p({borderBox:f,border:n,margin:d,padding:g})},b=function(t,o){return void 0===o&&(o={x:window.pageXOffset,y:window.pageYOffset}),a(t,o)},m=function(t,o){var r={top:f(o.marginTop),right:f(o.marginRight),bottom:f(o.marginBottom),left:f(o.marginLeft)},i={top:f(o.paddingTop),right:f(o.paddingRight),bottom:f(o.paddingBottom),left:f(o.paddingLeft)},e={top:f(o.borderTopWidth),right:f(o.borderRightWidth),bottom:f(o.borderBottomWidth),left:f(o.borderLeftWidth)};return p({borderBox:t,margin:r,padding:i,border:e})},u=function(t){var o=t.getBoundingClientRect(),r=window.getComputedStyle(t);return m(o,r)}}}]);