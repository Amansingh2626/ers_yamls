(self.webpackChunk_unified_thresholds=self.webpackChunk_unified_thresholds||[]).push([[7692],{57613:(n,t,e)=>{"use strict";function r(n){return"/"===n.charAt(0)}function i(n,t){for(var e=t,r=e+1,i=n.length;r<i;e+=1,r+=1)n[e]=n[r];n.pop()}e.d(t,{Z:()=>s});const s=function(n,t){void 0===t&&(t="");var e,s=n&&n.split("/")||[],h=t&&t.split("/")||[],f=n&&r(n),u=t&&r(t),o=f||u;if(n&&r(n)?h=s:s.length&&(h.pop(),h=h.concat(s)),!h.length)return"/";if(h.length){var l=h[h.length-1];e="."===l||".."===l||""===l}else e=!1;for(var a=0,c=h.length;c>=0;c--){var p=h[c];"."===p?i(h,c):".."===p?(i(h,c),a++):a&&(i(h,c),a--)}if(!o)for(;a--;a)h.unshift("..");!o||""===h[0]||h[0]&&r(h[0])||h.unshift("");var v=h.join("/");return e&&"/"!==v.substr(-1)&&(v+="/"),v}}}]);