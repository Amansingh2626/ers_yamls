(self.webpackChunk_unified_actor=self.webpackChunk_unified_actor||[]).push([[7692],{7613:(n,t,r)=>{"use strict";function e(n){return"/"===n.charAt(0)}function i(n,t){for(var r=t,e=r+1,i=n.length;e<i;r+=1,e+=1)n[r]=n[e];n.pop()}r.d(t,{Z:()=>f});const f=function(n,t){void 0===t&&(t="");var r,f=n&&n.split("/")||[],u=t&&t.split("/")||[],o=n&&e(n),s=t&&e(t),a=o||s;if(n&&e(n)?u=f:f.length&&(u.pop(),u=u.concat(f)),!u.length)return"/";if(u.length){var c=u[u.length-1];r="."===c||".."===c||""===c}else r=!1;for(var h=0,l=u.length;l>=0;l--){var p=u[l];"."===p?i(u,l):".."===p?(i(u,l),h++):h&&(i(u,l),h--)}if(!a)for(;h--;h)u.unshift("..");!a||""===u[0]||u[0]&&e(u[0])||u.unshift("");var v=u.join("/");return r&&"/"!==v.substr(-1)&&(v+="/"),v}}}]);