(self.webpackChunk_unified_config_safaricom=self.webpackChunk_unified_config_safaricom||[]).push([[692],{613:(n,t,i)=>{"use strict";function r(n){return"/"===n.charAt(0)}function f(n,t){for(var i=t,r=i+1,f=n.length;r<f;i+=1,r+=1)n[i]=n[r];n.pop()}i.d(t,{Z:()=>e});const e=function(n,t){void 0===t&&(t="");var i,e=n&&n.split("/")||[],o=t&&t.split("/")||[],s=n&&r(n),u=t&&r(t),a=s||u;if(n&&r(n)?o=e:e.length&&(o.pop(),o=o.concat(e)),!o.length)return"/";if(o.length){var c=o[o.length-1];i="."===c||".."===c||""===c}else i=!1;for(var h=0,l=o.length;l>=0;l--){var p=o[l];"."===p?f(o,l):".."===p?(f(o,l),h++):h&&(f(o,l),h--)}if(!a)for(;h--;h)o.unshift("..");!a||""===o[0]||o[0]&&r(o[0])||o.unshift("");var g=o.join("/");return i&&"/"!==g.substr(-1)&&(g+="/"),g}}}]);