(self.webpackChunk_unified_dashboard=self.webpackChunk_unified_dashboard||[]).push([[7692],{7613:(n,t,r)=>{"use strict";function e(n){return"/"===n.charAt(0)}function i(n,t){for(var r=t,e=r+1,i=n.length;e<i;r+=1,e+=1)n[r]=n[e];n.pop()}r.d(t,{Z:()=>f});const f=function(n,t){void 0===t&&(t="");var r,f=n&&n.split("/")||[],s=t&&t.split("/")||[],u=n&&e(n),a=t&&e(t),h=u||a;if(n&&e(n)?s=f:f.length&&(s.pop(),s=s.concat(f)),!s.length)return"/";if(s.length){var o=s[s.length-1];r="."===o||".."===o||""===o}else r=!1;for(var l=0,c=s.length;c>=0;c--){var p=s[c];"."===p?i(s,c):".."===p?(i(s,c),l++):l&&(i(s,c),l--)}if(!h)for(;l--;l)s.unshift("..");!h||""===s[0]||s[0]&&e(s[0])||s.unshift("");var d=s.join("/");return r&&"/"!==d.substr(-1)&&(d+="/"),d}}}]);