(self.webpackChunk_unified_config_grameenphone=self.webpackChunk_unified_config_grameenphone||[]).push([[692],{7613:(n,e,t)=>{"use strict";function r(n){return"/"===n.charAt(0)}function i(n,e){for(var t=e,r=t+1,i=n.length;r<i;t+=1,r+=1)n[t]=n[r];n.pop()}t.d(e,{Z:()=>f});const f=function(n,e){void 0===e&&(e="");var t,f=n&&n.split("/")||[],o=e&&e.split("/")||[],u=n&&r(n),h=e&&r(e),s=u||h;if(n&&r(n)?o=f:f.length&&(o.pop(),o=o.concat(f)),!o.length)return"/";if(o.length){var a=o[o.length-1];t="."===a||".."===a||""===a}else t=!1;for(var c=0,l=o.length;l>=0;l--){var p=o[l];"."===p?i(o,l):".."===p?(i(o,l),c++):c&&(i(o,l),c--)}if(!s)for(;c--;c)o.unshift("..");!s||""===o[0]||o[0]&&r(o[0])||o.unshift("");var g=o.join("/");return t&&"/"!==g.substr(-1)&&(g+="/"),g}}}]);