(self.webpackChunk_unified_customercare=self.webpackChunk_unified_customercare||[]).push([[7692],{7613:(n,t,e)=>{"use strict";function r(n){return"/"===n.charAt(0)}function i(n,t){for(var e=t,r=e+1,i=n.length;r<i;e+=1,r+=1)n[e]=n[r];n.pop()}e.d(t,{Z:()=>u});const u=function(n,t){void 0===t&&(t="");var e,u=n&&n.split("/")||[],f=t&&t.split("/")||[],s=n&&r(n),c=t&&r(t),o=s||c;if(n&&r(n)?f=u:u.length&&(f.pop(),f=f.concat(u)),!f.length)return"/";if(f.length){var a=f[f.length-1];e="."===a||".."===a||""===a}else e=!1;for(var h=0,l=f.length;l>=0;l--){var p=f[l];"."===p?i(f,l):".."===p?(i(f,l),h++):h&&(i(f,l),h--)}if(!o)for(;h--;h)f.unshift("..");!o||""===f[0]||f[0]&&r(f[0])||f.unshift("");var v=f.join("/");return e&&"/"!==v.substr(-1)&&(v+="/"),v}}}]);