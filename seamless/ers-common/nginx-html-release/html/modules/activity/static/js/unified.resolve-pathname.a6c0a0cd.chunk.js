(self.webpackChunk_unified_activity=self.webpackChunk_unified_activity||[]).push([[7692],{57613:(t,n,i)=>{"use strict";function e(t){return"/"===t.charAt(0)}function r(t,n){for(var i=n,e=i+1,r=t.length;e<r;i+=1,e+=1)t[i]=t[e];t.pop()}i.d(n,{Z:()=>f});const f=function(t,n){void 0===n&&(n="");var i,f=t&&t.split("/")||[],u=n&&n.split("/")||[],s=t&&e(t),a=n&&e(n),c=s||a;if(t&&e(t)?u=f:f.length&&(u.pop(),u=u.concat(f)),!u.length)return"/";if(u.length){var h=u[u.length-1];i="."===h||".."===h||""===h}else i=!1;for(var o=0,l=u.length;l>=0;l--){var p=u[l];"."===p?r(u,l):".."===p?(r(u,l),o++):o&&(r(u,l),o--)}if(!c)for(;o--;o)u.unshift("..");!c||""===u[0]||u[0]&&e(u[0])||u.unshift("");var v=u.join("/");return i&&"/"!==v.substr(-1)&&(v+="/"),v}}}]);